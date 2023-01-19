package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to read an xml used to store the properties
 * 
 * @author shahaal
 * @author avonva
 *
 */
public class GithubConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(GithubConfig.class);

	private static final String CONFIG_PATH = "config/githubConfig.xml";
	private static final String PROXY_PATH = "config/proxyConfig.xml";

	private static final String USR_LOCAL_APPDATA_PATH = System.getProperty("user.home") + "\\AppData\\Local\\";
	//public static final String JAVA_PATH = System.getProperty("java.home"); //"Application.JavaPath";
	public static final String REPOSITORY_NAME = "Github.RepositoryName";
	public static final String REPOSITORY_OWNER = "Github.RepositoryOwner";
	private static final String APP_FOLDER = "Application.Folder";
	public static final String JAR_PATH = "Application.JarPath";
	public static final String APP_ICON = "Application.IconEntry";

	public static final String APP_CONFIG_FILE = "Application.ConfigFile";
	public static final String APP_VERSION_ENTRY = "Application.VersionEntry";
	private static final String APP_ICON_FOLDER = "Application.IconFolder";
	public static final String APP_NAME_ENTRY = "Application.NameEntry";
	public static final String APP_DB_FOLDER = "Application.DatabaseFolder";

	public static final String APP_KEYWORD_NAME = "Application.Keyword";

	/**
	 * Get the real application version
	 * 
	 * @return
	 */
	public String getApplicationVersion() {
		String version = getAppConfigEntry(APP_VERSION_ENTRY);

		if (version == null || version.isEmpty()) {
			version = GithubChecker.DEFAULT_VERSION;
		}
		LOGGER.info("The real application version " + version);
		return version;
	}

	public String getApplicationName() {
		return getAppConfigEntry(APP_NAME_ENTRY);
	}

	/**
	 * the method return the Catalogue browser folder located into the user AppData
	 * 
	 * @author shahaal
	 * @return
	 */
	public String getAppPath() {
		return USR_LOCAL_APPDATA_PATH + getValue(APP_FOLDER);
	}

	public String getJarPath() {
		return getValue(JAR_PATH);
	}

	/**
	 * Get the real application icon
	 * 
	 * @return
	 */
	public String getApplicationIconPath() {
		String appFolder = getAppPath() + System.getProperty("file.separator");
		String iconFolder = getValue(APP_ICON_FOLDER) + System.getProperty("file.separator");
		return appFolder + iconFolder + getAppConfigEntry(APP_ICON);
	}

	private String getAppConfigEntry(String appEntry) {
		String appFolder = getAppPath();
		String appConfigFile = getValue(APP_CONFIG_FILE);
		String appConfigEntry = getValue(appEntry);

		return getValue(appFolder + System.getProperty("file.separator") + appConfigFile, appConfigEntry);
	}

	/**
	 * Read the application properties from the xml file
	 * 
	 * @return
	 */
	public Properties getProperties(String filename) {

		Properties properties = new Properties();

		try (InputStream stream = new FileInputStream(filename)) {
			properties.loadFromXML(stream);
		} catch (IOException e) {
			LOGGER.error("The " + filename + " file was not found. Please check!");
			e.printStackTrace();
		}

		return properties;
	}

	/**
	 * Get a property value given the key
	 * 
	 * @param property
	 * @return
	 */
	public String getValue(String property) {
		return getValue(CONFIG_PATH, property);
	}

	private String getValue(String propertiesFilename, String property) {

		Properties prop = getProperties(propertiesFilename);

		if (prop == null)
			return null;

		String value = prop.getProperty(property);

		return value;
	}

	public static String getProxyPath() {
		return PROXY_PATH;
	}
}
