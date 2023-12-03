import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		//going to assume it's never going to use the same number more than once⌈
		ArrayList<ArrayList<Integer>> schema = new ArrayList<ArrayList<Integer>>();
		
		try {
			FileInputStream src = new FileInputStream("input.txt");
			Scanner file = new Scanner(src).useDelimiter("[\n\r]");
			while(file.hasNext()) {
				String input = file.next();
				//System.out.println(input);
				if(input.length() <= 1)
					continue;
				ArrayList<Integer> line = new ArrayList<Integer>();
				for(int i = 0; i < input.length(); i++) {
					line.add(-1); //initialize to .
				}
				//System.out.println(line.size());
				int part = 0;
				int place = 0;
				for(int i = input.length()-1; i >= 0; i--) {
					//System.out.println(part);
					int bit = input.charAt(i);
					//System.out.println((char)bit);
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
						line.set(i, -2); //placeholder val
					}
					
					else { //symbol
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
				/*
				for(int i = 0; i < line.size(); i++) {
					System.out.printf("%d", line.get(i));
					System.out.printf("%s", " ");
				}
				*/
				schema.add(line);
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print_2d(schema);
		int y = schema.size();
		int x = schema.get(0).size();
		int partSum = 0;
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				int bit = schema.get(i).get(j);
				if (bit == '.') //blank
					continue;
				else if (bit == -2) { //symbol
					//8 possible situations, ignore duplicates
					//ArrayList<Integer> surround = new ArrayList<Integer>();
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
					
					//Object[] test = li.values().toArray();
					for(int value : li.values()) {
						if(value > 0) //something in the above code is adding -2 and idk where it is
							partSum += value;
					}
					li.clear();
				}
				else
					continue;
			}
		}
		
		System.out.println(partSum);
		
	}
	
	public static void print_2d(ArrayList<ArrayList<Integer>> chart) {
		for(int i = 0; i < chart.size(); i++) {
			for(int j = 0; j < chart.get(0).size(); j++) {
				int res = chart.get(i).get(j);
				if(res == -2)
					res = 0;
				System.out.printf("%d", res);
				if (res < 0)
					System.out.printf("%s", "  ");
				else if(res < 10)
					System.out.printf("%s", "   ");
				else if(res < 100)
					System.out.printf("%s", "  ");
				else if(res < 1000)
					System.out.printf("%s", " ");
			}
			System.out.printf("%c", '\n');
		}
		
	}

}
