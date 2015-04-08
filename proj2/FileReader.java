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


/** 
 *  @author Hubert Pham
 */

public class FileReader {
	

	public static void main(String[] args) throws IOException{
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
	