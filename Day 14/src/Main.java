import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		
		ArrayList<ArrayList<Character>> part1 = new ArrayList<>();
		ArrayList<ArrayList<Character>> part2 = new ArrayList<>();
		
		HashMap<Integer,Integer> history = new HashMap<>();
		HashMap<Integer,Integer> inverse_history = new HashMap<>();
		HashMap<ArrayList<ArrayList<Character>>,Integer> boards = new HashMap<>();
		
		int weight = 0;
		int oneb_index = 0;
		while(inp.hasNext()) {
			String[] raw = inp.next().split("(?!^)");
			ArrayList<Character> line1 = new ArrayList<>();
			ArrayList<Character> line2 = new ArrayList<>();
			for(int i = 0; i < raw.length; i++) {
				line1.add(raw[i].charAt(0));
				line2.add(raw[i].charAt(0));
			}
			part1.add(line1);
			part2.add(line2);
		}
		inp.close();
		
		for(int i = 0; i < part1.get(0).size(); i++)
			roll_north(part1,i);
		
		for(int i = 0; i < part1.size(); i++) {
			int rowsum = 0;
			for(int j = 0; j < part1.get(0).size(); j++) {
				rowsum += part1.get(i).get(j) == 'O' ? 1 : 0;
			}
			weight += rowsum * (part1.size() - i);
		}
		System.out.println(weight);
		
		//part 2
		for(int n = 1; n < 1001; n++) {
			for(int i = 0; i < part2.get(0).size(); i++) 
				roll_north(part2,i);
			
			for(int i = 0; i < part2.get(0).size(); i++) 
				roll_west(part2,i);
			
			for(int i = 0; i < part2.get(0).size(); i++) 
				roll_south(part2,i);
			
			for(int i = 0; i < part2.get(0).size(); i++) 
				roll_east(part2,i);
			
			weight = 0;
			for(int i = 0; i < part2.size(); i++) {
				int rowsum = 0;
				for(int j = 0; j < part2.get(0).size(); j++) {
					rowsum += part2.get(i).get(j) == 'O' ? 1 : 0;
				}
				weight += rowsum * (part2.size() - i);
			}
			inverse_history.put(n,weight);
			
			if(history.get(weight) == null)
				history.put(weight, n);
			
			if(boards.get(part2) != null) {
				oneb_index = (1000000000 - (n + 1)) % (n - history.get(weight)) + history.get(weight);
				break;
			}
			else
				boards.put(part2, 1);
		}

		 /* this spits out the correct answer but full disclosure it was largely guesswork after discovering a cycle in board weights
		 * proper work on a solution only happened after I could guess + check where cycles started
		 *		with a valid answer to work backward from */	 
		weight = inverse_history.get(oneb_index);
		System.out.println(weight);
	}
	
	public static void roll_north(ArrayList<ArrayList<Character>> plane, int index) {
		int last = 0;
		int rock = 0;
		while(true) {
			Boolean changed = false;
			for(int i = last; i < plane.size(); i++) {
				switch (plane.get(i).get(index)) {
				case '.':
					continue;
				case '#':
					rock = i;
					break;
				case 'O':
					if(i > last) {
						last = i;
						changed = true;
					}
					break;
				}
				if(changed == true)
					break;
			}
			if(changed == true) {
				for(int i = rock; i < last; i++) {
					if(plane.get(i).get(index) == '.') {
						plane.get(i).set(index, 'O');
						plane.get(last).set(index, '.');
						break;
					}
				}
			}
			if(changed == false)
				break;
		}
	}
	
	public static void roll_south(ArrayList<ArrayList<Character>> plane, int index) {
		int last = plane.size() - 1;
		int rock = plane.size() - 1;
		while(true) {
			Boolean changed = false;
			for(int i = last; i >= 0; i--) {
				switch (plane.get(i).get(index)) {
				case '.':
					continue;
				case '#':
					rock = i;
					break;
				case 'O':
					if(i < last) {
						last = i;
						changed = true;
					}
					break;
				}
				if(changed == true)
					break;
			}
			if(changed == true) {
				for(int i = rock; i >= last; i--) {
					if(plane.get(i).get(index) == '.') {
						plane.get(i).set(index, 'O');
						plane.get(last).set(index, '.');
						break;
					}
				}
			}
			if(changed == false)
				break;
		}
	}
	
	public static void roll_west(ArrayList<ArrayList<Character>> plane, int index) {
		int last = 0;
		int rock = 0;
		while(true) {
			Boolean changed = false;
			for(int i = last; i < plane.get(0).size(); i++) {
				switch (plane.get(index).get(i)) {
				case '.':
					continue;
				case '#':
					rock = i;
					break;
				case 'O':
					if(i > last) {
						last = i;
						changed = true;
					}
					break;
				}
				if(changed == true)
					break;
			}
			if(changed == true) {
				for(int i = rock; i < last; i++) {
					if(plane.get(index).get(i) == '.') {
						plane.get(index).set(i, 'O');
						plane.get(index).set(last, '.');
						break;
					}
				}
			}
			if(changed == false)
				break;
		}
	}
	
	public static void roll_east(ArrayList<ArrayList<Character>> plane, int index) {
		int last = plane.get(0).size() - 1;
		int rock = plane.get(0).size() - 1;
		while(true) {
			Boolean changed = false;
			for(int i = last; i >= 0; i--) {
				switch (plane.get(index).get(i)) {
				case '.':
					continue;
				case '#':
					rock = i;
					break;
				case 'O':
					if(i < last) {
						last = i;
						changed = true;
					}
					break;
				}
				if(changed == true)
					break;
			}
			if(changed == true) {
				for(int i = rock; i >= last; i--) {
					if(plane.get(index).get(i) == '.') {
						plane.get(index).set(i, 'O');
						plane.get(index).set(last, '.');
						break;
					}
				}
			}
			if(changed == false)
				break;
		}
	}
}
