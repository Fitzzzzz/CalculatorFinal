package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.opencsv.CSVReader;

/**
 * A parser designed to read the equations to check from an external file.
 * Equations should be written following this principle : The equation followed by a ";" and then unit
 * Don't add any extra spaces and use '>', '=' or '<' alone. ('<=' isn't accepted)
 * @author hamme
 *
 */
public class EquationReader {
	
	/**
	 * The name of the file containing the equations. Uses openCSV as reading tool.
	 */
	public String fileName = "equations.txt";
	
	/**
	 * The equations.
	 */
	private final LinkedList<EquationDatas> equations = new LinkedList<EquationDatas>();
	
	/**
	 * Constructor.
	 * @throws IOException if the equations file isn't accessible.
	 */
	public EquationReader(Configuration config) throws IOException {
		
		FileReader fr = new FileReader(fileName);
		CSVReader reader = new CSVReader(fr, ';');
		
		int currentLine = 1;
		
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			
			// Will try to add the currently read line to the equations list, creating a EquationDatas.
			// Will throw an IncorrectEntryFormatException if the equation doesn't respect the expected format.
			try {
//				System.out.println("Line about to start = " + currentLine);
				equations.add(new EquationDatas(nextLine, config));
			} catch (IncorrectEntryFormatException e) {
				System.out.print("Equation : \"");
				for (int i = 0; i < nextLine.length; i++) {
					System.out.print(nextLine[i] + ";");
				}
				System.out.println("\" from line " + currentLine + " doesn't respect the excepted format. It will be ignored." );
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ArrayIndexOutOfBoundsException at line " + currentLine 
						+ ". Be careful not that add any extra empty line at the end of the file.");
			}
			currentLine++;
		}
		
		reader.close();
		
		
	}

	
	
	public LinkedList<EquationDatas> getEquations() {
		return equations;
	}
	
	
}
