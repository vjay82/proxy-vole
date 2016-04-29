package com.btr.proxy.search.browser.firefox;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

/*****************************************************************************
 * A profile source for Firefox profiles.
 * 
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 ****************************************************************************/

abstract class FirefoxProfileSource {

	/*************************************************************************
	 * Gets a profile folder found on the current system.
	 * If multiple profile folders are available the "default" profile is chosen.
	 * 
	 * @return a profile folder.
	 * @throws IOException
	 *             on error.
	 ************************************************************************/

	public abstract Path getProfileFolder() throws IOException;

	public Path getProfileFolder(String baseDir) throws IOException {
		Path cfgDir = Paths.get(baseDir, "Mozilla", "Firefox");
		if (!Files.exists(cfgDir)) {
			throw new IOException("Firefox settings folder " + cfgDir + " not found. ");
		}

		Path profilesIni = cfgDir.resolve("profiles.ini");

		if (!Files.isReadable(profilesIni)) {
			throw new IOException(profilesIni + " is not readable");
		}

		try (Reader reader = Files.newBufferedReader(profilesIni);) {
			Ini ini = new Ini(reader);
			for (Section section : ini.values()) {
				String path = section.get("Path");
				if (path != null) {
					String def = section.get("Default");
					if (def != null) {
						if (1 == Integer.parseInt(def)) {
							if (1 == Integer.parseInt(section.get("IsRelative"))) {
								return cfgDir.resolve(path);
							}
						}
					}
				}
			}

			for (Section section : ini.values()) {
				String path = section.get( "Path");
				if (path != null) {
					if (1 == Integer.parseInt(section.get( "IsRelative"))) {
						return cfgDir.resolve(path);
					} else {
						return Paths.get(path);
					}
				}
			}
		}

		throw new IOException("No active profile found.");
	}
}
