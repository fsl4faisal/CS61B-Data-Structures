import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.ArrayDeque;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/** 
 *  @author Hubert Pham
 */


public class Gitlet implements Serializable {
	private HashSet<String> branches;
	private HashSet<String> staged;
	private HashSet<String> remove;
	private int counter;
	private static final long serialVersionUID = 1925489565777408074L;

	public Gitlet() {
		branches = new HashSet<String>();
		staged = new HashSet<String>();
		remove = new HashSet<String>();
	}


	public static void main(String[] args) {
		Gitlet git = tryLoadingGitlet();
		String command = args[0];
		if (command.equals("init")) {
			File gitletDirectory = new File(".gitlet");
			if (!gitletDirectory.mkdir()) { 
				System.out.println("A gitlet version control system already exists in the current directory.");
				return;
			}
			Gitlet g = new Gitlet();
			gitletDirectory.mkdir();
			saveGitlet(g);
		 } else if (command.equals("add")) {
			String filename = args[1];
			boolean check = new File(filename).exists();
			if (!check) {
				System.out.println("File does not exist.");
				return;
			}
			boolean check2 = new File(".gitlet/commit" + git.counter + "/" + filename).exists();
			if (check2) {
				if (areFilesEqual(filename, ".gitlet/commit" + git.counter + "/" + filename)) { 
					System.out.println("File has not been modified since the last commit.");
					return;
				}
			}
			if (git.remove.contains(filename)) {
				git.remove.remove(filename);
			}
			git.staged.add(filename);
		} else if (command.equals("commit")) {
			String message = args[1];
			if (git.staged.isEmpty() && git.remove.isEmpty()) {
				System.out.println("No changes added to the commit.");
				return;
			}
			File f = new File(".gitlet/commit" + git.counter);
			f.mkdir(); 
			for (String s: git.staged) {
				File copy = new File(".gitlet/commit" + git.counter + "/" + s);
				try {
					copy.createNewFile();
					copyFile(s);
				} catch (IOException e) {
					System.out.println("FileStreamsTest: " + e);
				}
			}
			git.counter += 1;
		} else if (command.equals("rm")) {
			File gitletDirectory = new File(".gitlet");
			gitletDirectory.delete();
		} else if (command.equals("log")) {
			System.out.println(git.staged.contains("hellos/hi.txt"));
		} 
		saveGitlet(git);
	}

	private static String fileName(String filename) {
		Deque<String> stack = new ArrayDeque<String>();
		String delimiter = "/";
		String[] temp;
		temp = filename.split(delimiter, 10);
		for (String s: temp) {
			stack.push(s);
		}
		return stack.pop();
	}

	private static boolean areFilesEqual(String a, String b) {
		try {
			File aFile = new File(a);
			File bFile = new File(b);
            FileInputStream fis = new FileInputStream(aFile);
            FileInputStream fis2 = new FileInputStream(bFile);
            int data = fis.read();
            int data2 = fis2.read();

            while (data != -1 || data2 != -1) {
               if (data != data2) {
            		return false;
               	}
               	data = fis.read();
               	data2 = fis2.read();
            }
            fis.close();
            fis2.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileStreamsTest: " + e);
        } catch (IOException e) {
            System.out.println("FileStreamsTest: " + e);
        }
        return true;
	}

	private static Gitlet tryLoadingGitlet() {
        Gitlet gitlet = null;
        File gitletFile = new File(".gitlet/gitlet.ser");
        if (gitletFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(gitletFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                gitlet = (Gitlet) objectIn.readObject();
                fileIn.close();
                objectIn.close();
            } catch (IOException|ClassNotFoundException e) {
                System.out.println(e);
        	}
        }
        return gitlet;
    }

    private static void saveGitlet(Gitlet gitlet) {
        if (gitlet == null) {
            return;
        }
        try {
            File gitletFile = new File(".gitlet/gitlet.ser");
            FileOutputStream fileOut = new FileOutputStream(gitletFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gitlet);
            fileOut.close();
            objectOut.close();
        } catch (IOException e) {
            System.out.println("IOException while saving gitlet.");
        }
    }

	private static void copyFile(String filename) throws IOException {
		Gitlet g = tryLoadingGitlet();
		Path FROM = Paths.get(filename);
    	Path TO = Paths.get(".gitlet/commit" + g.counter + "/" + filename);
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  }

  	private class GitLinkedList implements Serializable {
		private GitLinkedList next;
		private ArrayList<String> files;
		private String commitName;

		public GitLinkedList() {
			next = null;
			files = new ArrayList<String>();
			commitName = "initial commit";
		}

		public GitLinkedList(GitLinkedList a, ArrayList<String> f, String name) {
			next = a;
			files = f;
			commitName = name;
		}
	} 
}
	