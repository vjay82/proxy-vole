package com.btr.proxy.search.browser.firefox;

import java.io.IOException;
import java.nio.file.Path;
import com.btr.proxy.util.PlatformUtil;

/*****************************************************************************
 * Searches for Firefox profile on an Linux / Unix base system.
 * This will scan the <i>.mozilla</i> folder in the users home directory to find the 
 * profiles. 
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 ****************************************************************************/

// TODO 02.06.2015 bros Format has changed in newer versions of firefox.

class LinuxFirefoxProfileSource extends FirefoxProfileSource {

	/*************************************************************************
	 * Get profile folder for the Linux Firefox profile
	 ************************************************************************/
	
	@Override
	public Path getProfileFolder() throws IOException {
		return getProfileFolder(PlatformUtil.getUserHomeDir());
	}

}
