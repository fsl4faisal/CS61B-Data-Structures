import java.io.Serializable;
import java.util.HashSet; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** 
 *  @author Hubert Pham
 */
public class GitLinkedList implements Serializable {
		GitLinkedList next;
		HashSet<String> files;
		int commitNum;
		String commitName;
		String commitDate;
		private static final long serialVersionUID = 7997659400448822981L;

		public GitLinkedList() {
			next = null;
			files = new HashSet<String>();
			commitName = "initial commit";
			commitNum = 0;
			commitDate = getDate();
		}

		public GitLinkedList(GitLinkedList a, HashSet<String> f, String name, String timestamp, int number) {
			next = a;
			files = f;
			commitName = name;
			commitDate = timestamp;
			commitNum = number;
		}

		private static String getDate() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   		Calendar cal = Calendar.getInstance();
	   		return dateFormat.format(cal.getTime());
		}
	} 