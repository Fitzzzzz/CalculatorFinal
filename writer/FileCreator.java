package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.LinkedList;

import reader.EquationDatas;

public class FileCreator {

	private BufferedWriter writer;

	public FileCreator(String fileName) throws IOException {

		OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, };
		Charset charset = Charset.forName("US-ASCII");
	    Path path = FileSystems.getDefault().getPath("output", fileName);
	    BufferedWriter writer = Files.newBufferedWriter(path, charset, options);
	    this.writer = writer;

	}
	
	public void write(LinkedList<EquationDatas> equations) {
		
		Iterator<EquationDatas> itr = equations.iterator();
		while (itr.hasNext()) {
			
			
			
		}
		
		
	}
	
	public void writeString(String msg) throws IOException {
		
		writer.write(msg);
		
		
	}
	
}
