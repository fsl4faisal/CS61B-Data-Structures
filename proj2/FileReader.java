import java.io.*;
import java.nio.*;
import java.nio.file.Files;

import java.util.*;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.String;
import java.text.*;



/** 
 *  @author Hubert Pham
 */

public class FileReader {
	
	private static void revertFile(Integer commitID, String filename) throws IOException {
		Path TO = Paths.get(filename);
    	Path FROM = Paths.get(".gitlet/commit" + commitID + "/" + filename);
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  	}

	public static void main(String[] args) throws IOException{
		FileReader.revertFile(3, "wug.txt");
		String m = "====";
		String n = "Commit 2";
		String h = m + System.getProperty("line.separator") + n + System.getProperty("line.separator") + "what" + System.getProperty("line.separator");

		 System.out.println(h);
		 System.out.println(m);
		 System.out.println(n);
		 System.out.println("whatsup");
		 System.out.println("");
		 System.out.println(m);
		Path FROM = Paths.get("hello.txt");
    	Path TO = Paths.get("test_files/hi.txt");
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    }; 
    Files.copy(FROM, TO, options);
  } 
  		}
	