package de.rub.nds.eccjava.log;

import de.rub.nds.eccjava.controller.AppController;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class TextAreaAppender extends WriterAppender {

    private final LogHistory logHistory = new AppController().getLogHistory();

    @Override
    public void append(LoggingEvent event) {
	logHistory.setLog(layout.format(event));
    }

    @Override
    public void close() {
    }

    public String getLog() {
	return logHistory.toString();
    }
}
