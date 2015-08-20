package de.rub.nds.eccjava.curve;

import java.math.BigInteger;
import org.apache.log4j.Logger;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class Oracle {

    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private Curve curve;
    private BigInteger secret;
    static Logger LOG = Logger.getLogger(Oracle.class.getName());

    public Point dbl(Point p) throws DivisionException {

        BigInteger x = p.getX();
        BigInteger y = p.getY();

        if (y.equals(BigInteger.ZERO)) {
            throw new DivisionException("y was equal to zero");
        }

        BigInteger l1 = ((THREE.multiply(x.pow(2))).add(curve.getA()));
        BigInteger l2 = TWO.multiply(y).modInverse(curve.getP());
        BigInteger l = l1.multiply(l2).mod(curve.getP());

        BigInteger xr = l.pow(2).subtract(TWO.multiply(x)).mod(curve.getP());
        BigInteger yr = l.multiply(x.subtract(xr)).subtract(y).mod(curve.getP());
        Point ret = new Point(xr, yr);
        return ret;
    }

    public Point dbl(Point p, boolean checkInfinity) throws DivisionException {
        if (checkInfinity) {
            if (p.isInfinity()) {
                return p;
            }
            if (p.getY().signum() == 0) {
                return new Point(true);
            }
        }
        return dbl(p);
    }

    public Point add(Point p, Point q) throws DivisionException {
        BigInteger xp = p.getX();
        BigInteger yp = p.getY();
        BigInteger xq = q.getX();
        BigInteger yq = q.getY();

        if (xq.subtract(xp).mod(curve.getP()).equals(BigInteger.ZERO)) {
            throw new DivisionException("xq was equal to xp (mod p)");
        }

        BigInteger l = ((yq.subtract(yp)).multiply((xq.subtract(xp)).modInverse(curve.getP()))).mod(curve.getP());
        BigInteger xr = l.pow(2).subtract(xp).subtract(xq).mod(curve.getP());
        BigInteger yr = (l.multiply(xp.subtract(xr))).subtract(yp).mod(curve.getP());
        Point ret = new Point(xr, yr);
        return ret;
    }

    public Point add(Point p, Point q, boolean checkInfinity) throws DivisionException {
        if (checkInfinity) {
            if (p == null || p.isInfinity()) {
                return q;
            }
            if (q == null || q.isInfinity()) {
                return p;
            }

            if (p.getX().equals(q.getX())) {
                if (p.getY().equals(q.getY())) {
                    return dbl(p, true);
                } else {
                    return new Point(true);
                }
            }
        }
        return add(p, q);
    }

    public Point mul(Point p, boolean checkInfinity) throws DivisionException {

        Point r = new Point(p.getX(), p.getY());
        for (int i = 1; i < secret.bitLength(); i++) {
            try {
                r = dbl(r, checkInfinity);
                if (secret.testBit(secret.bitLength() - 1 - i)) {
                    r = add(r, p, checkInfinity);
                }
            } catch (DivisionException e) {
                throw new DivisionException(e.getLocalizedMessage(), i);
            }
        }
        return r;
    }

    public boolean mulValid(Point p) {
        try {
            mul(p, false);
        } catch (DivisionException de) {
            LOG.debug("Multiplication failed: " + de.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public synchronized boolean mulValid(Point p, BigInteger secret) {
        BigInteger oldSecret = this.secret;
        this.secret = secret;
        try {
            mul(p, false);
        } catch (DivisionException de) {
            LOG.debug("Multiplication failed: " + de.getLocalizedMessage());
            this.secret = oldSecret;
            return false;
        }
        this.secret = oldSecret;
        return true;
    }

    public BigInteger modSecret(BigInteger x) {
        return secret.mod(x);
    }

    public BigInteger getSecret() {
        return secret;
    }

    public void setSecret(BigInteger secret) {
        this.secret = secret;
    }

    public void setSecret(String sequence) {
        this.secret = BigInteger.valueOf(Long.valueOf(sequence, 2));
    }

    public void setCurve(Curve c) {
        this.curve = c;
    }

}
