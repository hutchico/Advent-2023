import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[,\n]");
		int sum = 0;
		
		ArrayList<ArrayList<String>> boxes = new ArrayList<>();
		ArrayList<HashMap<String,Integer>> lenses = new ArrayList<>();
		for(int i = 0; i < 256; i++) {
			boxes.add(new ArrayList<>());
			lenses.add(new HashMap<>());
		}
		
		while(inp.hasNext()) {
			String part1 = inp.next();
			String[] seq = part1.split("(?!^)");
			String id = "";
			int lens = -1;
			boolean remove = false;
			int boxNum;
			sum += hash(part1);
			for(int i = 0; i < seq.length; i++) {
				if(seq[i].charAt(0) == '-') 
					remove = true;
				else if (seq[i].charAt(0) == '=') {
					lens = ((int) seq[i+1].charAt(0) - 48);
					break;
				}
				else
					id += seq[i];
			}
			
			boxNum = hash(id);
			
			if(remove) { //pull something out
				boxes.get(boxNum).remove(id); //maybe this works maybe it doesn't idk
				lenses.get(boxNum).remove(id);
			}
			else { //put something in
				if(boxes.get(boxNum).contains(id)) 
					boxes.get(boxNum).set(boxes.get(boxNum).indexOf(id), id);
				else 
					boxes.get(boxNum).add(id);
				
				lenses.get(boxNum).put(id, lens);
			}
		}
		inp.close();
		
		System.out.println(sum);
		
		sum = 0;
		for(int i = 0; i < 256; i++) {
			int boxSum = 0;
			for(int j = 0; j < boxes.get(i).size(); j++) 
				boxSum += (i + 1) * (j + 1) * lenses.get(i).get(boxes.get(i).get(j));
			sum += boxSum;
		}
		System.out.println(sum);
	}

	public static int hash(String key) {
		int seqVal = 0;
		for(int i = 0; i < key.length(); i++) {
			seqVal += (int) key.charAt(i);
			seqVal *= 17;
			seqVal = seqVal % 256;
		}
		return seqVal;
	}
}
