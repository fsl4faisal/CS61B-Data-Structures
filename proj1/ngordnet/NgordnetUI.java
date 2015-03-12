package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author Hubert Pham
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        WordNet w = new WordNet(synsetFile, hyponymFile);
        NGramMap n = new NGramMap(wordFile, countFile);
        WordLengthProcessor processor = new WordLengthProcessor();
        int startDate = 1505;
        int endDate = 2008;
        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            switch (command) {
                case "quit": return;
                case "help":
                    In i = new In("./wordnet/help.txt");
                    String helpStr = i.readAll();
                    System.out.println(helpStr);
                    break;  
                case "range": 
                    startDate = Integer.parseInt(tokens[0]); 
                    endDate = Integer.parseInt(tokens[1]);
                    break;
                case "count":
                    String s = tokens[0];
                    Integer year = Integer.parseInt(tokens[1]);
                    System.out.println(n.countInYear(s, year));
                    break;
                case "history":
                    Plotter.plotAllWords(n, tokens, startDate, endDate);
                    break;
                case "hypohist":
                    Plotter.plotCategoryWeights(n, w, tokens, startDate, endDate);
                    break;
                case "hyponyms":
                    System.out.println(w.hyponyms(tokens[0]));
                    break;
                case "wordlength":
                    Plotter.plotProcessedHistory(n, startDate, endDate, processor);
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }

    }
} 
