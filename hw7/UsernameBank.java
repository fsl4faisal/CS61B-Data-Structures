import java.util.Map;
import java.util.HashMap;
import java.lang.Exception;

public class UsernameBank {

    // Instance variables (remember, they should be private!)
    Map<String, String> UsertoEmail;
    Map<String, String> EmailtoUser;
    Map<String, Integer> badEmails;
    Map<String, Integer> badUsernames;
    

    public UsernameBank() {
        Map<String,String> UsertoEmail = new HashMap<String,String>();
        Map<String,String> EmailtoUser = new HashMap<String,String>();
        Map<String,Integer> badEmails = new HashMap<String,Integer>();
        Map<String,Integer> badUsernames = new HashMap<String,Integer>();
    }

    public void generateUsername(String username, String email) {
        
    }

    public String getEmail(String username) {
        if (username == null) {
            throw new NullPointerException();
        }
        if (username.length() != 2 || username.length() != 3) {
            if (badUsernames.get(username) != null) {
                badUsernames.put(username, badUsernames.get(username) + 1);
            } else {
                badUsernames.put(username, 1);
            }
            return null;
        }
        char data[] = username.toCharArray();
        for (char i: data) {
            int check = (int) i;
            if (check <= 122 && check >= 97 || check <= 90 && check >=65 || check >= 48 && check <= 57) {
                return null;
            }
            if (badUsernames.get(username) != null) {
                badUsernames.put(username, badUsernames.get(username) + 1);
            } else {
                badUsernames.put(username, 1);
            }
            return null;
        }
        return UsertoEmail.get(username);
    }

    public String getUsername(String userEmail)  {
        if (userEmail == null) {
            throw new NullPointerException();
        }
        return EmailtoUser.get(userEmail);
    }

    public Map<String, Integer> getBadEmails() {
        // YOUR CODE HERE
        return badEmails;
    }

    public Map<String, Integer> getBadUsernames() {
        // YOUR CODE HERE
        return badUsernames;
    }

    public String suggestUsername() {
        // YOUR CODE HERE
        return null;
    }

    // The answer is somewhere in between 3 and 1000.
    public static final int followUp() {
        // YOUR CODE HERE
        return 0;
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadUsername(String username) {
        // YOUR CODE HERE
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadEmail(String email) {
        // YOUR CODE HERE
    }
}