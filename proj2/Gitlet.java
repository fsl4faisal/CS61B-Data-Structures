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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** 
 *  @author Hubert Pham
 */


public class Gitlet implements Serializable {
	private HashSet<String> branches;
	private HashSet<String> prev;
	private HashSet<String> staged;
	private HashSet<String> remove;
	private String branch;
	private String message;
	private int counter;
	private static final long serialVersionUID = 1925489565777408074L;

	public Gitlet() {
		branches = new HashSet<String>();
		branches.add("master");
		branch = "master";
		prev = new HashSet<String>();
		staged = new HashSet<String>();
		remove = new HashSet<String>();
		counter = 1;
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
			saveGitlet(g);
			gitletDirectory.mkdir();
			GitLinkedList linked = new GitLinkedList();
			saveGitLinkedList(linked, g.branch);
		 } else if (command.equals("add")) {
			String filename = args[1];
			boolean check = new File(filename).exists();
			if (!check) {
				System.out.println("File does not exist.");
				return;
			}
			for (int i = (git.counter - 1); i > 0; i--) {
				boolean check2 = new File(".gitlet/commit" + i + "/" + filename).exists();
				if (check2) {
					if (areFilesEqual(filename, ".gitlet/commit" + i + "/" + filename)) { 
						System.out.println("File has not been modified since the last commit.");
						return;
					}
				}
			}
			if (git.remove.contains(filename)) {
				git.remove.remove(filename);
			}
			git.staged.add(filename);
			git.prev.add(filename);
		} else if (command.equals("commit")) {
			GitLinkedList link = tryLoadingGitLinkedList(git.branch);
			try {
				String m = args[1];
				if (m.length() == 0 || !m.matches(".*\\w.*")) {
    			throw new ArrayIndexOutOfBoundsException();
				}
				git.message = m;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please enter a commit message.");
				return;
			}
			if (git.staged.isEmpty() && git.remove.isEmpty()) {
				System.out.println("No changes added to the commit.");
				return;
			}
			File f = new File(".gitlet/commit" + git.counter);
			f.mkdir();
			for (String i: git.remove) {
				git.prev.remove(i);
			} 
			for (String s: git.staged) {
				File copy = new File(".gitlet/commit" + git.counter + "/" + s);
				try {
					copy.createNewFile();
					copyFile(s);
				} catch (IOException e) {
					System.out.println("FileStreamsTest: " + e);
				}
			}
			link = new GitLinkedList(link, git.prev, git.message, getDate(), git.counter);
			git.staged = new HashSet<String>();
			git.remove = new HashSet<String>(); 
			git.counter += 1;
			saveGitLinkedList(link, git.branch);
		} else if (command.equals("rm")) {
			String filename = args[1];
			if (git.staged.contains(filename) || git.prev.contains(filename)) {
				System.out.println("No reason to remove the file.");
				return;
			}
			git.remove.add(filename);
		} else if (command.equals("log")) {
			GitLinkedList link = tryLoadingGitLinkedList(git.branch);
			GitLinkedList copy = link;
			while (copy != null) {
				String result = "====" + System.getProperty("line.separator") + "Commit " + copy.commitNum + "." + System.getProperty("line.separator") + copy.commitDate + System.getProperty("line.separator") + copy.commitName + System.getProperty("line.separator");
				System.out.println(result);
				copy = copy.next;
			}
			saveGitLinkedList(link, git.branch);
		} else if (command.equals("find")) {
			GitLinkedList link = tryLoadingGitLinkedList(git.branch);
			String message = args[1];
			GitLinkedList copy = link;
			while (copy != null) {
				if (message.equals(copy.commitName)) {
				System.out.println(copy.commitNum);
				}
				copy = copy.next;
			}	
			saveGitLinkedList(link, git.branch);
		} else if (command.equals("status")) {
			String result = "=== " + "Branches" + " ===" + System.getProperty("line.separator");
			for (String s: git.branches) {
				if (s == git.branch) {
					result = result + "*" + s + System.getProperty("line.separator");
				} else {
					result = result + s + System.getProperty("line.separator");
				}
			}
			result = result + System.getProperty("line.separator") + "=== Staged Files ===" + System.getProperty("line.separator");
			for (String p: git.staged) {
				result = result + p + System.getProperty("line.separator");
			}
			result = result + System.getProperty("line.separator") + "=== Files Marked for Removal ===" + System.getProperty("line.separator");
			for (String i: git.remove) {
				result = result + i + System.getProperty("line.separator");
			}
			System.out.println(result);
		} else if (command.equals("checkout")) {
			if (args.length > 2) {
				try {
					File f = new File(".gitlet/commit" + args[1]);
					if (!f.exists()) {
						System.out.println("No commit with that id exists.");
					}
					Integer num = Integer.parseInt(args[1]);
					String filename = args[2];
					git.revertFile(num, filename);
				} catch (IOException e) {
					System.out.println("File does not exist in that commit.");
					return;
				}
			}
			String name = args[1];
			if (!git.branches.contains(name)) {
				System.out.println("File does not exist in the most recent commit, or no such branch exists.");
				return;
			}
			if (git.branches.contains(name)) {
				if (git.branch.equals(name)) {
					System.out.println("No need to checkout the current branch.");
					return;
				}
				GitLinkedList link = tryLoadingGitLinkedList(name);
				git.branch = name;
				for (String s:link.files) {
					try {
						revertFile(link.commitNum, s);
					} catch (IOException e) {
						System.out.println("File does not exist in that commit.");
						return;
					}
				}
				saveGitLinkedList(link, git.branch);
			} else {
				try {
				revertFile((git.counter - 1), name);
				} catch (IOException e) {
					return;
				}  
			}
		} else if (command.equals("hubert")) {

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

	private static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	Calendar cal = Calendar.getInstance();
	   	return dateFormat.format(cal.getTime());
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

    private static GitLinkedList tryLoadingGitLinkedList(String branch) {
        GitLinkedList git = null;
        File gitFile = new File(".gitlet/"+ branch + ".ser");
        if (gitFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(gitFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                git = (GitLinkedList) objectIn.readObject();
                fileIn.close();
                objectIn.close();
            } catch (IOException|ClassNotFoundException e) {
                System.out.println(e);
        	}
        }
        return git;
    }

    private static void saveGitLinkedList(GitLinkedList git, String branch) {
        if (git == null) {
            return;
        }
        try {
            File gitletFile = new File(".gitlet/" + branch + ".ser");
            FileOutputStream fileOut = new FileOutputStream(gitletFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(git);
            fileOut.close();
            objectOut.close();
        } catch (IOException e) {
            System.out.println("IOException while saving GitLinkedList.");
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

  	private static void revertFile(Integer commitID, String filename) throws IOException {
		Gitlet g = tryLoadingGitlet();
		Path TO = Paths.get(filename);
    	Path FROM = Paths.get(".gitlet/commit" + commitID + "/" + filename);
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  	}

}
	