package gpd.util;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public abstract class PropManager {
	
	private Hashtable<String, Hashtable<String, String>> config = new Hashtable<String, Hashtable<String, String>>();
	abstract protected HashSet<String> getPropertiesValue();

    protected final void loadConfig() {
        HashSet<String> propertiesValues = getPropertiesValue();
        for (String element : propertiesValues) {
            config.put(element, PropReader.readProp(element));
        }
    }

	protected String getPropertyValue(String propertiesFileName, String key) {
        Hashtable<String, String> values = config.get(propertiesFileName);
        String value = null;
        if (null != values) {
            value = values.get(key);
        }
        return value;
    }
    
    protected final void configLog4J() {
    	Logger logger = Logger.getLogger(PropManager.class);
    	logger.info("Inicio configuracion Log4j...");
    	Hashtable<String, String> values = config.get(CnstProp.PROP_LOG4J);
    	Properties prop = new Properties();
    	if (null != values) {
    		Enumeration<String> e = values.keys();
    		Object key;
    		Object value;
    		while(e.hasMoreElements()) {
    			key = e.nextElement();
				value = values.get(key);
				System.out.println("Clave : " + key + " - Valor : " + value);
				prop.setProperty(String.valueOf(key), String.valueOf(value));
    		}
    	}
    	PropertyConfigurator.configure(prop);
    	logger.info("Log4j configurado correctamente...");
    }
    
    
}
