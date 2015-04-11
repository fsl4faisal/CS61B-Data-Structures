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
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
			if (git.remove.contains(filename)) {
				git.remove.remove(filename);
				saveGitlet(git);
				return;
			}
			boolean check = new File(filename).exists();
			if (!check) {
				System.out.println("File does not exist.");
				return;
			}
			if (git.prev.contains(filename)) {
				for (int i = (git.counter - 1); i > 0; i--) {
					boolean check2 = new File(".gitlet/commit" + i + "/" + fileName(filename)).exists();
					if (check2) {
						if (areFilesEqual(filename, ".gitlet/commit" + i + "/" + fileName(filename))) { 
							System.out.println("File has not been modified since the last commit.");
							return;
						}
					}
				}
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
			for (String i: git.remove) 	{
				git.prev.remove(i);
			} 
			for (String s: git.staged) {
				File copy = new File(".gitlet/commit" + git.counter + "/" + fileName(s));
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
			if (!git.staged.contains(filename) && !git.prev.contains(filename)) {
				System.out.println("No reason to remove the file.");
				return;
			}
			if (git.staged.contains(filename)) {
				git.staged.remove(filename);
				saveGitlet(git);
				return;
			} else if (git.prev.contains(filename)) {
				git.remove.add(filename);
			}
		} else if (command.equals("log")) {
			GitLinkedList link = tryLoadingGitLinkedList(git.branch);
			GitLinkedList copy = link;
			while (copy != null) {
				String result = "====" + System.getProperty("line.separator") + "Commit " + copy.commitNum + "." + System.getProperty("line.separator") + copy.commitDate + System.getProperty("line.separator") + copy.commitName + System.getProperty("line.separator");
				System.out.println(result);
				copy = copy.next;
			}
		} else if (command.equals("global-log")) {
			for (String b: git.branches) {
				GitLinkedList link = tryLoadingGitLinkedList(git.branch);
				GitLinkedList copy = link;
				while (copy != null) {
					String result = "====" + System.getProperty("line.separator") + "Commit " + copy.commitNum + "." + System.getProperty("line.separator") + copy.commitDate + System.getProperty("line.separator") + copy.commitName + System.getProperty("line.separator");
					System.out.println(result);
					copy = copy.next;
				}
			}
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
		} else if (command.equals("status")) {
			String result = "=== " + "Branches" + " ===" + System.getProperty("line.separator");
			for (String s: git.branches) {
				if (s.equals(git.branch)) {
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
			System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			String answer = readInput();
			if (answer.equals("yes")) {
				if (args.length > 2) {
					try {
						File f = new File(".gitlet/commit" + fileName(args[1]));
						if (!f.exists()) {
							System.out.println("No commit with that id exists.");
						}
						Integer num = Integer.parseInt(args[1]);
						String filename = fileName(args[2]);
						git.revertFile(num, filename);
					} catch (IOException e) {
						System.out.println("File does not exist in that commit.");
						return;
					}
				}
				String name = fileName(args[1]);
				if (git.branches.contains(name)) {
					if (git.branch.equals(name)) {
						System.out.println("No need to checkout the current branch.");
						return;
					}
					GitLinkedList link = tryLoadingGitLinkedList(name);
					if (link != null) {
						for (String s:link.files) {
							try {
								revertFile(link.commitNum, s);
							} catch (IOException e) {
								System.out.println("File does not exist in that commit.");
								return;
							}
						}
					} else {
						link = tryLoadingGitLinkedList(git.branch);
					}
					git.branch = name;
					saveGitLinkedList(link, git.branch);
				} else {
					try {
						revertFile((git.counter - 1), args[1]);
					} catch (IOException e) {
						System.out.println("File does not exist in the most recent commit, or no such branch exists.");
						return;
					}  
				}
			}
		} else if (command.equals("branch")) {
			String b = args[1];
			if (git.branches.contains(b)) {
				System.out.println("A branch with that name already exists.");
				return;
			}
			git.branches.add(b);
		} else if (command.equals("rm-branch")) {
			String rm = args[1];
			if (!git.branches.contains(rm)) {
				System.out.println("A branch with that name does not exist.");
				return;
			}
			if (git.branch == rm) {
				System.out.println("Cannot remove the current branch.");
				return;
			}
			git.branches.remove(rm);
		} else if (command.equals("reset")) {
			System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			String answer = readInput();
			if (answer.equals("yes")) {
				String a = args[1].replaceAll("\\s","");
				int commitId = Integer.parseInt(a);
				GitLinkedList link = tryLoadingGitLinkedList(git.branch);
				while (link.commitNum != commitId) {
					link = link.next; 
				}
				for (String s: link.files) {
					try {
						revertFile(link.commitNum, s);
					} catch (IOException e) {
						System.out.println(e); 
						return;
					}
				}
				git.prev = link.files;
				saveGitLinkedList(link, git.branch);
			}
		} else if (command.equals("merge")) {
			System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			String answer = readInput();
			if (answer.equals("yes")) {
				String b = args[1];
				if (!git.branches.contains(b)) {
					System.out.println("A branch with that name does not exist.");
					return;
				}
				if (git.branch.equals(b)) {
					System.out.println("Cannot merge a branch with itself.");
					return;
				}
				GitLinkedList thisOne = tryLoadingGitLinkedList(git.branch);
				GitLinkedList thisCopy = thisOne;
				HashSet<Integer> commitNums = new HashSet<Integer>();
				while (thisCopy != null) {
					commitNums.add(thisCopy.commitNum);
					thisCopy = thisCopy.next;
				}
				GitLinkedList other = tryLoadingGitLinkedList(b);
				GitLinkedList otherCopy = other;
				int splitCommit;
				while (!commitNums.contains(otherCopy.commitNum)) {
					otherCopy = otherCopy.next;
				}
				splitCommit = otherCopy.commitNum;
				System.out.println(splitCommit);
				HashSet<String> thisFiles = thisOne.files; 
				HashSet<String> otherFiles = other.files;
				for (String f: otherFiles) {
					File g = new File(".gitlet/commit" + splitCommit + "/" + fileName(f));
					if (!thisFiles.contains(f)) {
							if (g.exists() && areFilesEqual(f, g.getPath())) {
								return;
							}
							try {
								File n = new File(f);
								n.createNewFile();
								createFile(f);
							} catch (IOException e) {
								System.out.println("FileStreamsTest: " + e);
							}	
					} else if (thisFiles.contains(f) && otherFiles.contains(f) && !fileModified(f, splitCommit)) {
						try {
						File conflict = new File(f + ".conflicted");
						conflict.createNewFile();
						createFile(f);
						} catch (IOException e) {
							System.out.println(e);
							return;
						}
					}
				}
			}
		} else if (command.equals("rebase")) {
			System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			String answer = readInput();
			if (answer.equals("yes")) {
				String branch = args[1];
				if (!git.branches.contains(branch)) {
					System.out.println("A branch with that name does not exist.");
					return;
				}
				if (git.branch.equals(branch)) {
					System.out.println("Cannot rebase a branch onto itself.");
					return;
				}
				GitLinkedList thisOne = tryLoadingGitLinkedList(git.branch);
				GitLinkedList thisCopy = thisOne;
				GitLinkedList other = tryLoadingGitLinkedList(branch);
				GitLinkedList otherCopy = other;
				while (thisOne != null) {
					if (thisOne.commitNum == other.commitNum) {
						System.out.println("Already up-to-date.");
						return;
					}
					thisOne = thisOne.next;
				}
				HashSet<Integer> commitNums = new HashSet<Integer>();
				while (thisCopy != null) {
					commitNums.add(thisCopy.commitNum);
					thisCopy = thisCopy.next;
				}

				int splitCommit;
				while (!commitNums.contains(otherCopy.commitNum)) {
					otherCopy = otherCopy.next;
				}
				splitCommit = otherCopy.commitNum;
				while (thisOne.commitNum != splitCommit) {
					other = new GitLinkedList(other, thisOne.files, thisOne.commitName, getDate(), git.counter);
					git.counter += 1;
					thisOne = thisOne.next;
				}
				saveGitLinkedList(other, git.branch);
			}
		} else if (command.equals("i-rebase")) {
			System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			String a = readInput();
			if (a.equals("yes")) {
				String branch = args[1];
				if (!git.branches.contains(branch)) {
					System.out.println("A branch with that name does not exist.");
					return;
				}
				if (git.branch.equals(branch)) {
					System.out.println("Cannot rebase a branch onto itself.");
					return;
				}
				GitLinkedList thisOne = tryLoadingGitLinkedList(git.branch);
				GitLinkedList thisCopy = thisOne;
				GitLinkedList thisCopy2 = thisOne;
				GitLinkedList other = tryLoadingGitLinkedList(branch);
				GitLinkedList otherCopy = other;
				while (thisCopy2 != null) {
					if (thisCopy2.commitNum == other.commitNum) {
						System.out.println("Already up-to-date.");
						return;
					}
					thisCopy2 = thisCopy2.next;
				}
				HashSet<Integer> commitNums = new HashSet<Integer>();
				while (thisCopy != null) {
					commitNums.add(thisCopy.commitNum);
					thisCopy = thisCopy.next;
				}

				int splitCommit;
				while (!commitNums.contains(otherCopy.commitNum)) {
					otherCopy = otherCopy.next;
				}
				splitCommit = otherCopy.commitNum;
				while (thisOne.commitNum != splitCommit) {
					System.out.println("Currently replaying:");
					String result = "====" + System.getProperty("line.separator") + "Commit " + thisOne.commitNum + "." + System.getProperty("line.separator") + thisOne.commitDate + System.getProperty("line.separator") + thisOne.commitName + System.getProperty("line.separator");
					System.out.println(result);
					System.out.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage?");
					String answer = readInput();
					if (answer.equals("c")) {
						other = new GitLinkedList(other, thisOne.files, thisOne.commitName, getDate(), git.counter);
					}
					if (answer.equals("s")) {
						thisOne = thisOne.next;
						continue;
					}
					if (answer.equals("m")) {
						System.out.println("Please enter a new message for this commit.");
						String message = readInput();
						other = new GitLinkedList(other, thisOne.files, message, getDate(), git.counter);
					}
					git.counter += 1;
					thisOne = thisOne.next;
				}
				saveGitLinkedList(other, git.branch);
			}
		}
		saveGitlet(git);
	}

	private static String readInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String answer = null;
	 
	    try {
	       answer = br.readLine();
	    } catch (IOException e) {
	       System.out.println("IO error trying to read your name!");
	    } 
	    return answer;
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

	private static boolean fileModified(String filename, int commitID) {
		Gitlet g = tryLoadingGitlet();
		GitLinkedList link = tryLoadingGitLinkedList(g.branch);
		while (link.commitNum != commitID) {
			link = link.next;
		}
		File f = new File(".gitlet/commit" + link.commitNum + "/" + fileName(filename));
		int c = commitID;
		while (f.exists() == false && c > 0) {
			f = new File(".gitlet/commit" + link.next.commitNum + "/" + fileName(filename));
			c -= 1;
			link = link.next;
		}
		return areFilesEqual(f.getPath(), filename);
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

    private static void createFile(String filename) throws IOException {
		Path FROM = Paths.get(filename);
    	Path TO = Paths.get(fileName(filename));
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  	}

	private static void copyFile(String filename) throws IOException {
		Gitlet g = tryLoadingGitlet();
		Path FROM = Paths.get(filename);
    	Path TO = Paths.get(".gitlet/commit" + g.counter + "/" + fileName(filename));
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  	}

  	private static void revertFile(Integer commitID, String filename) throws IOException {
  		String file = fileName(filename);
		Path TO = Paths.get(filename);
		File f = new File(".gitlet/commit" + commitID + "/" + file);
		int c = commitID - 1;
		while (f.exists() == false && c > 0) {
			f = new File(".gitlet/commit" + c + "/" + file);
			c -= 1;
		}
    	Path FROM = Paths.get(f.getPath());
    	//overwrite existing file, if exists
    	CopyOption[] options = new CopyOption[]{
      	StandardCopyOption.REPLACE_EXISTING,
      	StandardCopyOption.COPY_ATTRIBUTES
    	}; 
    Files.copy(FROM, TO, options);
  	}

}
	