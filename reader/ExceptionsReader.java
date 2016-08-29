package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import com.opencsv.CSVReader;

public class ExceptionsReader {

private String fileName;

public ExceptionsReader(String fileName) {
		
		this.fileName = fileName;
		
	}
	
	public HashSet<String> readAllFile() throws IOException {
		
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		String[] nextLine;
		
		HashSet<String> file = new HashSet<String>();
		
		while ((nextLine = reader.readNext()) != null) {
			for (int i = 0; i < nextLine.length; i++)
				file.add(nextLine[i]);
			
		}
		reader.close();
		return file;
		
	}
	
	
}
