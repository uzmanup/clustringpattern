package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
	
	private static PropertyManager instance;
	private static Properties properties;
	
	public static void main(String[] args) {
		PropertyManager properties = PropertyManager.getInstance();
		System.out.println(properties.getProperty("Dataset"));
	}
	
	private PropertyManager() {
		properties = new Properties();
		InputStream propertiesIS;
		try {
			propertiesIS = new FileInputStream("dat/properties.dat");
			properties.load(propertiesIS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static PropertyManager getInstance() {
		if(instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	
}
