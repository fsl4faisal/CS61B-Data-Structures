import java.util.*;

public class ScannerDemo {

   public static void main(String[] args) {
;

      // create a new scanner with the specified String Object
      In scanner = new In("hubert.txt");
      while (scanner.hasNextLine()) {
         String[] read = scanner.readLine().split(",");
         System.out.println(read[0]);
         System.out.println(read[1]);
      }
   }
}