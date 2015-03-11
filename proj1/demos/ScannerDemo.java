import java.util.*;

public class ScannerDemo {

   public static void main(String[] args) {
;
	In reader = new In("../ngrams/words_that_start_with_q.csv");
        while (reader.hasNextLine()) {
            String[] read = reader.readLine().split("\\s+");
            String str = read[0];
            Integer year = Integer.valueOf(read[1]);
            Integer count = Integer.valueOf(read[2]);
            System.out.println(count);
        }
        /*
         String[] a = scanner.readAllLines();
         System.out.println(a[300]);
         String[] c = a[1300].split("\t");
         System.out.println(c[0]);
         System.out.println(c[1]);
System.out.println(c[2]);

         */

   }
}