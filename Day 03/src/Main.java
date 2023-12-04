import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		//going to assume it's never going to use the same number more than once⌈
		ArrayList<ArrayList<Integer>> schema = new ArrayList<ArrayList<Integer>>();
		
		try {
			FileInputStream src = new FileInputStream("input.txt");
			Scanner file = new Scanner(src).useDelimiter("[\n\r]");
			while(file.hasNext()) {
				String input = file.next();
				//if(input.length() <= 1) //correcting oddity with example input
				//	continue;
				ArrayList<Integer> line = new ArrayList<Integer>();
				for(int i = 0; i < input.length(); i++) {
					line.add(-1); //initialize to .
				}
				int part = 0;
				int place = 0;
				for(int i = input.length()-1; i >= 0; i--) {
					int bit = input.charAt(i);
					if(bit == 46) {
						line.set(i, -1);
						if(part > 0) {
							for(int z = 0; z < place; z++) {
								int prev = z + i + 1;
								line.set(prev,part);
							}
							part = 0;
							place = 0;
						}
					}
					else if(bit >= 48 && bit <= 57) {
						part += (bit - 48) * Math.pow(10,place);
						place += 1;
						line.set(i, 0); //placeholder val
					}
					
					else { //symbol
						if(bit == 42)
							line.set(i, -3);
						else
							line.set(i, -2);
						
						if(part > 0) {
							for(int z = 0; z < place; z++) {
								int prev = z + i + 1;
								line.set(prev,part);
							}
							part = 0;
							place = 0;
						}
					}
					
					if(bit != 48 && i == 0) { //catch for end of line
						for(int z = 0; z < place; z++) {
							int prev = z + i;
							line.set(prev,part);
						}
						part = 0;
						place = 0;
					}
						
				}
				schema.add(line);
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int y = schema.size();
		int x = schema.get(0).size();
		int partSum = 0;
		int gearSum = 0;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				int bit = schema.get(i).get(j);
				if (bit == '.') //blank
					continue;
				else if (bit == -2 || bit == -3) { //symbol for part 1
					//8 possible situations, store in map to avoid duplicate values
					HashMap<Integer,Integer> li = new HashMap<Integer,Integer>();
					if(i > 0) {
						if(j > 0)
							li.put(schema.get(i-1).get(j-1),schema.get(i-1).get(j-1));
						if(j < x)
							li.put(schema.get(i-1).get(j+1),schema.get(i-1).get(j+1));
						li.put(schema.get(i-1).get(j),schema.get(i-1).get(j));
					}
					if(i < y) {
						if(j > 0)
							li.put(schema.get(i+1).get(j-1),schema.get(i+1).get(j-1));
						if(j < x)
							li.put(schema.get(i+1).get(j+1),schema.get(i+1).get(j+1));
						li.put(schema.get(i+1).get(j),schema.get(i+1).get(j));
					}
					if(j > 0)
						li.put(schema.get(i).get(j-1),schema.get(i).get(j-1));
					if(j < x)
						li.put(schema.get(i).get(j+1),schema.get(i).get(j+1));
					
					li.remove(-1); //catch anything non-numeric from the put block
					for(int value : li.values()) 
						partSum += value;
					
					if(bit == -3) //special case for part 2
						if(li.size() == 2) {
							int g = 1;
							for(int val : li.values()) 
								g *= val;
							gearSum += g;
						}
				}
				else
					continue;
			}
		}
		
		System.out.println(partSum);
		System.out.println(gearSum);
	}
}
