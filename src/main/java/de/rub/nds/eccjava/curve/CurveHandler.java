package de.rub.nds.eccjava.curve;

import de.rub.nds.eccjava.bean.AbstractBean;
import de.rub.nds.eccjava.bean.TextAreaLog;
import de.rub.nds.eccjava.helpers.XMLPersistanceHelper;
import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class CurveHandler extends AbstractBean implements Serializable {

    public static final String PROP_CURVE = "curve";
    private Curve curve = new Curve();
    private static final TextAreaLog playAreaLog = new TextAreaLog();
    private boolean initialized = false;
    static Logger LOG = Logger.getLogger(CurveHandler.class.getName());
    private final Oracle oracle = new Oracle();
    private String fileName;
    private Point playBasePoint;
    private Point currentPlayPoint;

    public void loadCurve(String curveName) throws Exception {
        Curve oldValue = curve;
        if (curveName.endsWith(".xml")) {
            fileName = curveName;
        } else {
            fileName = curveName + ".xml";
        }
        File f = new File(fileName);
        curve = (Curve) XMLPersistanceHelper.loadObjectFromFile(f, Curve.class);
        firePropertyChange(PROP_CURVE, oldValue, curve);
        initialized = true;
        oracle.setCurve(curve);
    }

    public void initializeNewCurve(String name, String p, String a, String b, String basePointX, String basePointY) {
        Curve oldValue = curve;
        curve.setName(name);
        curve.setP(new BigInteger(p));
        curve.setA(new BigInteger(a));
        curve.setB(new BigInteger(b));
        curve.setBasePointX(new BigInteger(basePointX));
        curve.setBasePointY(new BigInteger(basePointY));
        firePropertyChange(PROP_CURVE, oldValue, curve);
        initialized = true;
        oracle.setCurve(curve);
        fileName = name + ".xml";
    }

    public synchronized void storeCurve() {
        if (curve != null && initialized) {
            File f = new File(fileName);
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Would You Like to Save your Curve? (File " + fileName + ")",
                    "Curve saving", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    XMLPersistanceHelper.saveObjectToFile(f, curve);
                } catch (Exception e) {
                    LOG.warn(e.getLocalizedMessage(), e);
                    JOptionPane.showMessageDialog(null, e, "Error saving", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public synchronized void storeCurveCommandLine() throws Exception {
        if (curve != null && initialized) {
            File f = new File(fileName);
            XMLPersistanceHelper.saveObjectToFile(f, curve);
        }
    }

    public Curve getCurve() {
        return curve;
    }

    public void setCurve(Curve value) {
        this.initialized = true;
        this.fileName = value.getName() + ".xml";
        Curve oldValue = curve;
        curve = value;
        firePropertyChange(PROP_CURVE, oldValue, value);
    }

    public String getFileName() {
        return fileName;
    }

    public Oracle getOracle() {
        return oracle;
    }

    public Point getPlayBasePoint() {
        return playBasePoint;
    }

    public void setPlayBasePoint(Point playBasePoint) {
        this.playBasePoint = playBasePoint;
        this.currentPlayPoint = playBasePoint;
        playAreaLog.setLog(playBasePoint.toString());
    }

    public Point getCurrentPlayPoint() {
        return currentPlayPoint;
    }

    public void setCurrentPlayPoint(Point currentPlayPoint) {
        this.currentPlayPoint = currentPlayPoint;
    }

    public Point computeNextPlayPointWithAdd() {
        try {
            currentPlayPoint = oracle.add(currentPlayPoint, playBasePoint, true);
            playAreaLog.appendLog("\n" + currentPlayPoint.toString());
            return currentPlayPoint;
        } catch (DivisionException e) {
            LOG.warn(e);
            return null;
        }
    }

    public Point computeNextPlayPointWithDouble() {
        try {
            currentPlayPoint = oracle.dbl(currentPlayPoint, true);
            playAreaLog.appendLog("\n" + currentPlayPoint.toString());
            return currentPlayPoint;
        } catch (DivisionException e) {
            LOG.warn(e);
            return null;
        }
    }

    public TextAreaLog getPlayAreaLog() {
        return playAreaLog;
    }
}
