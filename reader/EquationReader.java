package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import com.opencsv.CSVReader;

public class EquationReader {
	
	public String fileName = "equations";
	
	public EquationReader() throws IOException {
		
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
	
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			
			try {
				equations.add(new EquationDatas(nextLine));
			} catch (IncorrectEntryFormatException e) {
				System.out.print("Equation : \"");
				for (int i = 0; i < nextLine.length; i++) {
					System.out.print(nextLine[i] + ";");
				}
				System.out.println("\" doesn't respect the excepted format. It will be ignored." );
			}
			
		}
		
		reader.close();
		
		
	}

	public EquationReader(String fileName) {
		
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
	
	private final LinkedList<EquationDatas> equations = new LinkedList<EquationDatas>();

	public LinkedList<EquationDatas> getEquations() {
		return equations;
	}
	
	
}
