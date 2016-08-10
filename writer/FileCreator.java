package writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileCreator {

	private String filename;

	public FileCreator(String filename) {
		this.filename = filename;
	}
	
	public void write() throws FileNotFoundException, IOException {
		
		try ( FileOutputStream fos = new FileOutputStream("test.txt") ) {
				  
			
			byte[] buf = new byte[8];
			int n = 0;
			buf[0] = 4;
			
			fos.write(buf);       
			for(byte bit : buf)
				System.out.print("\t" + bit + "(" + (char)bit + ")");         
			   
			System.out.println("");
			  

			System.out.println("Copie terminée !");
			        
			} 
		
	}
	
}
