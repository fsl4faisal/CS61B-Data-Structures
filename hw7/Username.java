public class Username {

    // Potentially useless note: (int) '0' == 48, (int) 'a' == 97

    // Instance Variables (remember, they should be private!)
    private String user;
    private char[] chars;

    public Username() {
        int tmp = (int) (Math.random() * 3 + 2);
        char data[] = new char[tmp];
        for (int i = 0; i < (tmp - 1); i++) {
            int rand = (int) (Math.random() * 25 + 97);
            int rand1 = (int) (Math.random() * 9 + 48);
            int rand2 = (int) (Math.random() * 25 + 65);
            char c = (char) rand;
            char d = (char) rand1;
            char e = (char) rand2;
            chars = new char[] {c, d, e};
            data[i] = chars[(int) (Math.random() * 3)];
        }
        user = new String(data);
    }

    public Username(String reqName) {
        if (reqName == null) {
            throw new NullPointerException("Requested username is null!");
        }
        if (reqName.length() != 2 || reqName.length() != 3) {
            throw new IllegalArgumentException("Wrong length");
        }
        char data[] = reqName.toCharArray();
        for (char i: data) {
            int check = (int) i;
            if (check <= 122 && check >= 97 || check <= 90 && check >= 65 || check >= 48 && check <= 57) {
                return;
            }
            throw new IllegalArgumentException("Username contains char that is not alphanumerical");
        }
        user = reqName;
    }

    @Override
    public boolean equals(Object o) {
        if (user ==((Username) o).user) {
            return true;
        } 
        return false;
    }

    @Override
    public int hashCode() { 
        int result = 0;
        for (char c: chars) {
            int i = (int) c;
            result += i;
        }
        return result;
    }

    public static void main(String[] args) {
        // You can put some simple testing here.
        Username n = new Username();
        System.out.println(n.user);
    }
}
