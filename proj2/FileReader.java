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
	
	

	
	public static void main(String[] args) throws IOException{
		System.out.print("Enter your name: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
      String userName = null;
 
      //  read the username from the command-line; need to use try/catch with the
      //  readLine() method
      try {
         userName = br.readLine();
      } catch (IOException ioe) {
         System.out.println("IO error trying to read your name!");
         System.exit(1);
      }
 
      System.out.println("Thanks for the name, " + userName);

  	}

  }
	