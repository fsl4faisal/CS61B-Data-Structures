import java.util.AbstractList;
import java.lang.IllegalArgumentException;
/** 
 *  @author Hubert Pham
 */

public class ArrayList61B<Sally> extends AbstractList<Sally> {
	public Sally[] items;
	public int size;

	public ArrayList61B(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		size = 0;
		items = (Sally[]) new Object[initialCapacity];
		}

	public ArrayList61B() {
		size = 0;
		items = (Sally[]) new Object[1];
	}

	public Sally get(int i) {
		if (i < 0 || i >= size()) {
			throw new IllegalArgumentException();
		}
		return items[i];
	}

	public boolean add(Sally item) {
		if (size == items.length) {
			Sally[] temp = items;
			items = (Sally[]) new Object[size * 2];
			size = 0;
			for (Sally i: temp) {
				add(i);
			}
		}
			items[size] = item;
			size += 1;
	return true;
}

	public int size() {
		return size;
	}




}
	