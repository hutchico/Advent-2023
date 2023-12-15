import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[,\n]");
		int p1sum = 0;
		long p2sum = 0;
		
		ArrayList<ArrayList<String>> boxes = new ArrayList<>();
		ArrayList<HashMap<String,Integer>> lenses = new ArrayList<>();
		for(int i = 0; i < 256; i++) {
			boxes.add(new ArrayList<>());
			lenses.add(new HashMap<>());
		}
		
		while(inp.hasNext()) {
			String[] seq = inp.next().split("(?!^)");
			String part1 = "";
			String id = "";
			int lens = -1;
			Boolean remove = false;
			int boxNum;
			for(int i = 0; i < seq.length; i++) {
				part1 += seq[i];
				switch(seq[i].charAt(0)) {
				case '-':
					remove = true;
					break;
				case '=':
					lens = ((int) seq[i+1].charAt(0)) - 48;
					break;
				default:
					id += seq[i]; //note this adds [0-9] to id, removed below
				}
			}
			p1sum += hash(part1); //something screwy with seq.toString() idk
			
			if(!remove)
				id = id.substring(0, id.length()-1); //quirk of switch case is adding end # to id, strip that off;
			
			boxNum = hash(id);
			
			if(remove) { //pull something out
				boxes.get(boxNum).remove(id); //maybe this works maybe it doesn't idk
				lenses.get(boxNum).remove(id);
			}
			else { //put something in
				if(boxes.get(boxNum).contains(id)) {
					int ind = boxes.get(boxNum).indexOf(id);
					boxes.get(boxNum).set(ind, id);
				}
				else 
					boxes.get(boxNum).add(id);
				
				lenses.get(boxNum).put(id, lens);
			}
		}
		inp.close();
		
		for(int i = 0; i < 256; i++) {
			int boxSum = 0;
			for(int j = 0; j < boxes.get(i).size(); j++) {
				boxSum += (i + 1) * (j + 1) * lenses.get(i).get(boxes.get(i).get(j));
			}
			p2sum += boxSum;
		}
		
		System.out.println(p1sum);
		System.out.println(p2sum);
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
