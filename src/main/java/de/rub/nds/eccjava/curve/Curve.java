    package de.rub.nds.eccjava.curve;

import de.rub.nds.eccjava.bean.AbstractBean;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
@XmlRootElement(name = "Curve")
@XmlType(propOrder = {"name", "p", "a", "b", "basePointX", "basePointY"})
public class Curve extends AbstractBean implements Serializable {

    public static final String PROP_NAME = "name";
    public static final String PROP_P = "p";
    public static final String PROP_A = "a";
    public static final String PROP_B = "b";
    public static final String PROP_BASE_POINT_X = "basePointX";
    public static final String PROP_BASE_POINT_Y = "basePointY";
    private String name;
    private BigInteger p;
    private BigInteger a;
    private BigInteger b;
    private BigInteger basePointX;
    private BigInteger basePointY;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        String oldValue = name;
        name = value;
        firePropertyChange(PROP_NAME, oldValue, name);
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        BigInteger oldValue = this.p;
        this.p = p;
        firePropertyChange(PROP_P, oldValue, p);
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        BigInteger oldValue = this.a;
        this.a = a;
        firePropertyChange(PROP_A, oldValue, a);
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        BigInteger oldValue = this.b;
        this.b = b;
        firePropertyChange(PROP_B, oldValue, b);
    }

    public BigInteger getBasePointX() {
        return basePointX;
    }

    public void setBasePointX(BigInteger basePointX) {
        BigInteger oldValue = this.basePointX;
        this.basePointX = basePointX;
        firePropertyChange(PROP_BASE_POINT_X, oldValue, basePointX);
    }

    public BigInteger getBasePointY() {
        return basePointY;
    }

    public void setBasePointY(BigInteger basePointY) {
        BigInteger oldValue = this.basePointY;
        this.basePointY = basePointY;
        firePropertyChange(PROP_BASE_POINT_Y, oldValue, basePointY);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Curve other = (Curve) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
