package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVReader;

public class EquationReader {
	
	public static final String filename = "equations";
	
	public EquationReader() throws IOException {
		
		FileReader fr = new FileReader(filename);
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
		
		
	}

	private final Set<EquationDatas> equations = new HashSet<EquationDatas>();

	public Set<EquationDatas> getEquations() {
		return equations;
	}
	
	
}
