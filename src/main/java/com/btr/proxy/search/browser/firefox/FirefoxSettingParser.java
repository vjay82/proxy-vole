package com.btr.proxy.search.browser.firefox;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/*****************************************************************************
 * Parser for the Firefox settings file.
 * Will extract all relevant proxy settings form the configuration file.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 ****************************************************************************/

class FirefoxSettingParser {

	/*************************************************************************
	 * Constructor
	 ************************************************************************/
	
	public FirefoxSettingParser() {
		super();
	}
	
	/*************************************************************************
	 * Parse the settings file and extract all network.proxy.* settings from it.
	 * @param source of the Firefox profiles.
	 * @return the parsed properties.
	 * @throws IOException on read error.
	 ************************************************************************/
	
	public Properties parseSettings(FirefoxProfileSource source) throws IOException {
		// Search settings folder
		Path profileFolder = source.getProfileFolder();
		
		// Read settings from file
		Path settingsFile = profileFolder.resolve( "prefs.js");
	

		Properties result = new Properties();
		try (BufferedReader fin = Files.newBufferedReader(settingsFile);){
			String line = fin.readLine();
			while (line != null) {
				line = line.trim();
				if (line.startsWith("user_pref(\"network.proxy")) {
					line = line.substring(10, line.length()-2);
					int index = line.indexOf(",");
					String key = line.substring(0, index).trim();
					if (key.startsWith("\"")) {
						key = key.substring(1);
					}
					if (key.endsWith("\"")) {
						key = key.substring(0, key.length()-1);
					}
					String value = line.substring(index+1).trim();
					if (value.startsWith("\"")) {
						value = value.substring(1);
					}
					if (value.endsWith("\"")) {
						value = value.substring(0, value.length()-1);
					}
					result.put(key, value);
				}
				line = fin.readLine();
			}
		} 

		return result;
	}
	
	
}
