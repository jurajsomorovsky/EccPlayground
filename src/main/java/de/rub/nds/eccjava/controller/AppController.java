package de.rub.nds.eccjava.controller;

import de.rub.nds.eccjava.curve.CurveHandler;
import de.rub.nds.eccjava.log.LogHistory;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class AppController {

    private static final LogHistory logHistory = new LogHistory();

    private static final CurveHandler curveHandler = new CurveHandler();

    public LogHistory getLogHistory() {
	return logHistory;
    }

    public CurveHandler getCurveHandler() {
	return curveHandler;
    }
}
