import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[\n\r]");
		HashMap<String,Pair<String,String>> dg = new HashMap<String,Pair<String,String>>();
		ArrayList<String> part2 = new ArrayList<>();
		String current_node = "AAA";
		int steps = 0;
		String[] raw_inst = inp.next().split("(?!^)");
		Queue<Character> instr = new LinkedList<>();
		System.out.println(raw_inst.length);
		for(int i = 0; i < raw_inst.length; i++) {
			instr.add(raw_inst[i].charAt(0));
		}
		inp.next();
		while(inp.hasNext()) {
			//Note: Valuable data is in line[0],[4],[6]
			String[] line = inp.next().split("[^A-Z]");
			dg.put(line[0], new Pair(line[4],line[6]));
			if(line[0].charAt(2) == 'A')
				part2.add(line[0]);
		}
		inp.close();
		
		while(current_node.compareTo("ZZZ") != 0) {
			char next = instr.remove(); //pop off and add to the end
			Pair<String,String> children = dg.get(current_node);
			instr.add(next);
			steps++;
			current_node = next == 'L' ? children.get_key() : children.get_value();
		}
		
		int lim = part2.size();
		ArrayList<HashMap<String,Integer>> eachVisited = new ArrayList<HashMap<String,Integer>>();
		ArrayList<Integer> cycles = new ArrayList<>();
		ArrayList<Integer> triggers = new ArrayList<>();
		for(int i = 0; i < lim; i++) {
			eachVisited.add(new HashMap<String,Integer>());
			cycles.add(-1);
			triggers.add(-1);
		}
		steps = 1;
		while(!all_cycles_found(triggers)) {
			char next = instr.remove();
			instr.add(next);
			for(int i = 0; i < lim; i++) {
				if(triggers.get(i) == 1)
					continue;
				String node = part2.get(i);
				Pair<String,String> children = dg.get(node);
				node = next == 'L' ? children.get_key() : children.get_value();
				part2.set(i, node);
				if(node.charAt(2) == 'Z') { //start counting
					cycles.set(i,steps);
					triggers.set(i, 1);
				}
			}
			steps++;
			
		}
		long prod = 1;
		for(int i = 0; i < cycles.size(); i++) {
			prod *= (cycles.get(i) / 281);
		}
		System.out.println((long)prod * 281);
	}
	public static Boolean all_cycles_found(ArrayList<Integer> arg) {
		for(int i = 0; i < arg.size(); i++) {
			if (arg.get(i) == 1)
				continue;
			return false;
		}
		return true;
	}
}
