package com.btr.proxy.search.browser.firefox;

import java.io.IOException;
import java.nio.file.Path;

/*****************************************************************************
 * Finds the Firefox profile on Windows platforms.
 * On Windows the profiles are located in the users appdata directory under:
 * <p>
 * <i>Mozilla\Firefox\Profiles\</i>
 * </p>
 * The location of the appdata folder is read from the windows registry.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 ****************************************************************************/

// TODO 02.06.2015 bros Format has changed in newer versions of firefox.

class WinFirefoxProfileSource extends FirefoxProfileSource {

	/*************************************************************************
	 * Constructor
	 ************************************************************************/

	public WinFirefoxProfileSource() {
		super();
	}

	/*************************************************************************
	 * Reads the current location of the app data folder from the environment.
	 * 
	 * @return a path to the folder.
	 ************************************************************************/

	private String getAppFolder() {
		return System.getenv("APPDATA");
	}

	/*************************************************************************
	 * Get profile folder for the Windows Firefox profile
	 * 
	 * @throws IOException
	 *             on error.
	 ************************************************************************/

	@Override
	public Path getProfileFolder() throws IOException {
		return getProfileFolder(getAppFolder());
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new WinFirefoxProfileSource().getProfileFolder());
	}

}
