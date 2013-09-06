package ca.bsolomon.gw2trade.logic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConfigReader {

	public static String username;
	public static String password;
	
	public static boolean readConfig() {
		Path path = Paths.get("config.txt");
		
		if (Files.exists(path, new LinkOption[0])) {
			try {
				List<String> fileLines = Files.readAllLines(path, Charset.defaultCharset());
				if (fileLines.size() == 2) {
					username = fileLines.get(0);
					password = fileLines.get(1);
					return true;
				}
			} catch (IOException e) {}
		} 
		
		return false;
	}	
}