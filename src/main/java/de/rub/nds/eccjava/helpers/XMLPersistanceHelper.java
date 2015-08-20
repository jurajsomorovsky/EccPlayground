package de.rub.nds.eccjava.helpers;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @author Christian Mainka
 * @version 0.1
 */
public class XMLPersistanceHelper {

    static Logger LOG = Logger.getLogger(XMLPersistanceHelper.class.getName());

    /**
     * Saves an object to XML File.
     * 
     * @param saveFile
     */
    public static void saveObjectToFile(File saveFile, final Object object) throws Exception {
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(object, saveFile);
	    LOG.info(String.format("Saved successfully config to '%s'", saveFile.getAbsoluteFile()));
	} catch (JAXBException ex) {
	    throw new Exception(String.format("Could not save config to File '%s'", saveFile.getAbsoluteFile()), ex);
	}
    }

    /**
     * Load an object from XML File
     * 
     * @param loadFile
     * @param c
     * @return
     * @throws Exception
     */
    public static Object loadObjectFromFile(final File loadFile, Class c) throws Exception {
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(c);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    Object result = jaxbUnmarshaller.unmarshal(loadFile);
	    LOG.info(String.format("Loaded successfully associations from '%s'", loadFile.getAbsoluteFile()));
	    return result;
	} catch (JAXBException ex) {
	    throw new Exception(String.format("Could not load associations from " + "File '%s'",
		    loadFile.getAbsoluteFile()), ex);
	}
    }
}