
import java.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UtilProp {
	static String _PROP_FILENAME_OSX_LOCAL = "/Users/emmieballs/Desktop/Computer Science/CSCI 4830/workspace4830/workspace4830-tech-exercise-Doerr/TechExercise/WebContent/config.properties";
	// * Remote server path
	static String _PROP_FILENAME_REMOTE = "/var/lib/tomcat7/webapps/TechExercise/config.properties";
	static Properties prop = new Properties();

	public static void loadProperty() throws Exception {
		FileInputStream inputStream = null;

		if (new File(_PROP_FILENAME_OSX_LOCAL).exists()) {
			System.out.println("[DBG] Loaded: " + new File(_PROP_FILENAME_OSX_LOCAL).getAbsolutePath());
			inputStream = new FileInputStream(_PROP_FILENAME_OSX_LOCAL);
		}
		if (new File(_PROP_FILENAME_REMOTE).exists()) {
			System.out.println("[DBG] Loaded: " + new File(_PROP_FILENAME_REMOTE).getAbsolutePath());
			inputStream = new FileInputStream(_PROP_FILENAME_REMOTE);
		}
		if (inputStream == null) {
			throw new FileNotFoundException();
		}
		prop.load(inputStream);
	}

	public static String getProp(String key) {
		return prop.getProperty(key).trim();
	}
}
