package de.rub.nds.eccjava.curve;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class DivisionException extends Exception {

    private int round;

    public DivisionException(String message) {
        super(message);
    }

    public DivisionException(String message, int i) {
        super(message + " Error happend in round " + i);
        round = i;
    }

    public int getRound() {
        return round;
    }
}
