package de.rub.nds.eccjava.helpers;

import de.rub.nds.eccjava.controller.AppController;
import org.apache.log4j.Logger;

/**
 * Stolen from Openid attacker...again
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class Bootstrap {
    private static final Logger LOG = Logger.getLogger(Bootstrap.class.getCanonicalName());
    private static boolean started = false;
    private static boolean stopped = false;
    private static final AppController controller = new AppController();

    private Bootstrap() {

    }

    public static void onStart() {
	try {
	    throwIfAlreadyStarted();
	    LOG.info("Started");
	} catch (Exception ex) {
	    LOG.warn("Error while starting the program");
	}
    }

    public static void onStop() {
	try {
	    throwIfAlreadyStopped();
	} catch (Exception ex) {
	    LOG.warn("Error while stopping the program");
	    LOG.debug(ex);
	}
    }

    private static void throwIfAlreadyStopped() throws IllegalStateException {
	if (stopped) {
	    throw new IllegalStateException("onStop() Method was already invoked");
	}
	stopped = true;
    }

    private static void throwIfAlreadyStarted() throws IllegalStateException {
	if (started) {
	    throw new IllegalStateException("onStart() Method was already invoked");
	}
	started = true;
    }
}
