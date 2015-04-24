import java.util.*;
/** 
 *  @author Hubert Pham
 */

public class TreeMapDemo{
	private Map<String, Integer> keys;
	private Map<Integer, String> values;


	public TreeMapDemo() {
		keys = new TreeMap<String, Integer>();
		values = new TreeMap<Integer, String>();
	}

	public static void main(String[] args){
		TreeMapDemo h = new TreeMapDemo();
		h.keys.put("what", 90);
		h.keys.put("hi", 50);
		h.keys.put("hubert", 100);
		h.keys.put("hubert", 10);
		h.values.put(90, "what");
		h.values.put(50, "hi");
		h.values.put(100,"hubert");
		System.out.println(h.keys.keySet());
		System.out.println(h.values.keySet());
	}	
}
	