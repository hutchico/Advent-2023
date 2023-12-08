import java.util.Arrays;
import java.util.List;

public class Hand{

	public Hand(String a, Integer b, int c) {
		this.key = a;
		this.value = b;
		this.third = c;
	}
	
	public void set_key(String a) {
		this.key = a;
	}
	
	public void set_value(Integer b) {
		this.value = b;
	}
	
	public void set_third(int c) {
		this.third = c;
	}
	
	public String get_key() {
		return key;
	}
	
	public Integer get_value() {
		return value;
	}
	
	public int get_third() {
		return third;
	}
	
	public int compare(Hand y) {
		if(y.get_third() == this.get_third()) { //the two have equal priority, time to match them up char by char
			//Character[] c = {'A','K','Q','J','T','9','8','7','6','5','4','3','2'}; //part 1 precedence list, 0 > 12
			Character[] c = {'A','K','Q','T','9','8','7','6','5','4','3','2','J'}; //part 2 precedence list, 0 > 12
			List<Character> deck = Arrays.asList(c);
			for(int i = 0; i < 5; i++) {
				int tval = deck.indexOf(this.get_key().charAt(i));
				int yval = deck.indexOf(y.get_key().charAt(i));
				if(tval == yval)
					continue;
				else
					return tval - yval < 0 ? 1 : -1;
			}
		}
		else
			return this.get_third() - y.get_third() < 0 ? 1 : -1;
		return 69420; //compiler please we are never going to return this stop complaining
	}
	
	private String key;
	private Integer value;
	private Integer third;
}
