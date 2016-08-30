package reader;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class Configuration {

	private String login;
	private String password;
	private String database;
	private String server;
	
	
	public Configuration(String fileToRead) throws IOException {
		
		FileReader fr = new FileReader(fileToRead);
		CSVReader reader = new CSVReader(fr, ':');
		
		String[] nextLine = reader.readNext();
		nextLine = reader.readNext();
		login = nextLine[1];
		nextLine = reader.readNext();
		password = nextLine[1];
		nextLine = reader.readNext();
		database = nextLine[1];
		nextLine = reader.readNext();
		server = nextLine[1];
		
		
		reader.close();
			
			
		
		
	}

	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}


	public String getDatabase() {
		return database;
	}


	public String getServer() {
		return server;
	}


	
}
