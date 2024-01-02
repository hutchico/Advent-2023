import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		ArrayList<Hand> part1;
		ArrayList<Hand> part2 = new ArrayList<Hand>();
		HashMap<Character, Integer> deck2 = new HashMap<>();
		deck2.put('A', 0);
		deck2.put('K', 1);
		deck2.put('Q', 2);
		deck2.put('T', 3); //part 1: n + 1, J = 3
		deck2.put('9', 4);
		deck2.put('8', 5);
		deck2.put('7', 6);
		deck2.put('6', 7);
		deck2.put('5', 8);
		deck2.put('4', 9);
		deck2.put('3', 10);
		deck2.put('2', 11);
		deck2.put('J', 12); 
		while(inp.hasNext()) {
			String[] raw = inp.next().split(" ");
			part2.add(new Hand(raw[0],Integer.valueOf(raw[1]),-1));
		}
		part1 = new ArrayList<Hand>(part2); //working backward because part 1 code is unrecognizable after p2
		inp.close();
		
		//part 1: iterate through Hands + evaluate wins, assign basic rank to each
		for(int i = 0; i < part2.size(); i++) {
			Hand one = part2.get(i);
			HashMap<Character,Integer> char_counts = new HashMap<Character,Integer>();
			for(int n = 0; n < 5; n++) {
				char card = ((String) one.get_key()).charAt(n);
				if (char_counts.get(card) != null) 
					char_counts.put(card, char_counts.get(card) + 1);
				else
					char_counts.put(card, 1);
			}
			int jcount;
			if(char_counts.get('J') != null) {
				jcount = char_counts.get('J');
				char_counts.remove('J'); 
				ArrayList<Pair<Character,Integer>> counts = new ArrayList<Pair<Character,Integer>>();
				
				for(Map.Entry<Character, Integer> ea : char_counts.entrySet()) {
					counts.add(new Pair(ea.getKey(),ea.getValue()));
				}
				if(counts.size() == 0) {
					char_counts.put('J', 5);
				}
				else {
					Collections.sort(counts,new CustomSort());
					int val = -1;
					char fchoice = find_char(one.get_key(),deck2); //find the highest card suite in hand
					char cchoice = counts.get(0).get_key();
					char choice;
					if(cchoice == 'J')
						cchoice = counts.get(1).get_key();
					if(deck2.get(cchoice) > deck2.get(fchoice)) {
						choice = cchoice;
						val = char_counts.get(cchoice);
					}
					else {
						choice = fchoice;
						val = char_counts.get(fchoice);
					}
					char_counts.put(choice, val + jcount);
				}
			}
			
			switch(char_counts.size()) {
			case (1): //five
				one.set_third(0);
				break;
			case(2): //four or full house
				ArrayList<Pair<Character,Integer>> pos = new ArrayList<Pair<Character,Integer>>();
				for(Map.Entry<Character, Integer> ea : char_counts.entrySet()) {
					pos.add(new Pair(ea.getKey(),ea.getValue()));
				}
				Collections.sort(pos,new CustomSort());
				char toFind = pos.get(0).get_key();
				
				int count = char_counts.get(toFind);
				if(count == 4 || count == 1)
					one.set_third(1); //four
				else
					one.set_third(2); //house ie count is 2 or 3
				break;
			case(3): //either three kind or two pair
				Integer[] counts = {0,0,0};
				int which = 0;
				for(int ea : char_counts.values()) { //hacky but saves using another arraylist
					counts[which] = ea; 
					which++;
				}
				Arrays.sort(counts, Collections.reverseOrder());
				if(counts[0] == 3) // 3 1 1 - three kind
					one.set_third(3);
				else
					one.set_third(4); // 2 2 1 - two pair
				break;
			case(4): //one pair
				one.set_third(5);
				break;
			default: //high
				one.set_third(6);
				break;
			}
			part2.set(i, one);
		}
		part2.sort((x,y) -> (x.compare(y)));
		
		int sum = 0;
		for(int i = 0; i < part2.size(); i++) { //oh it's weakest to strongest
			sum += part2.get(i).get_value() * (i + 1);
		}
		System.out.println(sum);
	}
	
	static class CustomSort implements Comparator<Pair<Character,Integer>>{
		//Character[] c = {'A','K','Q','J','T','9','8','7','6','5','4','3','2'}; //part 1 precedence list, 0 > 12
		Character[] c = {'A','K','Q','T','9','8','7','6','5','4','3','2','J'}; //part 2 precedence list, 0 > 12
		List<Character> deck = Arrays.asList(c);
		
		public int compare(Pair<Character,Integer> a, Pair<Character,Integer> b) {
			
			char bv = b.get_key();
			char av = a.get_key();
			
			int bl = deck.indexOf(bv);
			int al = deck.indexOf(av);
			
			int numcp = b.get_value() - a.get_value();
			int charcp = al - bl;
			
			return (numcp == 0) ? charcp : numcp;
		}
	}
	
	public static Boolean hasJ(String input) {
		if(input.indexOf('J') > 0)
			return true;
		return false;
	}
	
	public static char find_char(String input, HashMap<Character,Integer> deck) {
		int loc = 6;
		int val = 25;
		for(int i = 0; i < input.length(); i++) {
			char single = input.charAt(i);
			if(deck.get(single) < val) {
				val = deck.get(single);
				loc = i;
			}
		}
		return input.charAt(loc);
	}
}
