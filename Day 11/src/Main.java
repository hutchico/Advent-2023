import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		ArrayList<ArrayList<Character>> chart = new ArrayList<>();
		ArrayList<Pair<Integer,Integer>> locations = new ArrayList<>();
		ArrayList<Integer> expand_v = new ArrayList<>(); //track row/cols to insert blank lines into
		ArrayList<Integer> expand_h = new ArrayList<>();
		while(inp.hasNext()) {
			ArrayList<Character> line = new ArrayList<>();
			String[] raw = inp.next().split("(?!^)");
			for(int i = 0; i < raw.length; i++) {
				char space = raw[i].charAt(0);
				line.add(space);
			}
			chart.add(line);
		}
		for(int i = 0; i < chart.size(); i++) {
			if(check_horiz(chart.get(i))) {
				expand_v.add(i);
			}
		}
		for(int i = 0; i < chart.get(0).size(); i++) {
			if(check_vert(chart,i)) {
				expand_h.add(i);
			}
		}
		
		System.out.println(expand_v.size());
		System.out.println(expand_h.size());
		
		for(int i = expand_v.size() - 1; i >= 0; i--) {
			ArrayList<Character> line = new ArrayList<>();
			for(int j = 0; j < chart.size(); j++) {
				line.add('.');
			}
			chart.add(expand_v.get(i), line);
		}
		
		for(int i = expand_h.size() - 1; i >= 0; i--) {
			int pos = expand_h.get(i);
			for(int j = 0; j < chart.size(); j++) {
				ArrayList<Character> li = chart.get(j);
				li.add(pos, '.');
				chart.set(j,li);
			}
		}
		
		for(int i = 0; i < chart.size(); i++) {
			for(int j = 0; j < chart.get(0).size(); j++) {
				System.out.printf("%c ", chart.get(i).get(j));
			}
			System.out.println();
		}
		
		for(int i = 0; i < chart.size(); i++) {
			for(int j = 0; j < chart.get(0).size(); j++) {
				if(chart.get(i).get(j) != '.') {
					locations.add(new Pair(i,j));
					System.out.println(i + " " + j);
				}
			}
		}
		int gsum = 0;
		for(int i = 0; i < locations.size(); i++) {
			for(int j = i + 1; j < locations.size(); j++) {
				int sum;
				if(i == j)
					continue;
				int x1, x2, y1, y2;
				x1 = locations.get(i).get_key();
				x2 = locations.get(j).get_key();
				y1 = locations.get(i).get_value();
				y2 = locations.get(j).get_value();
				sum = Manhattan(x1,y1,x2,y2);
				gsum += sum;
				//System.out.println(locations.get(i).get_key() + "," + locations.get(i).get_value() + " " + locations.get(j).get_key() + "," + locations.get(j).get_value());
				System.out.println((i + 1) + " " + (j + 1) + " " + sum);
			}
		}
		System.out.println(gsum);
		
	}

	public static int Manhattan(int x1, int y1, int x2, int y2) {
		System.out.println(x1 + ", " + y1 + " : " + x2 + "," + y2);
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	public static Boolean check_horiz(ArrayList<Character> line) {
		for(int i = 0; i < line.size(); i++) {
			if(line.get(i) != '.')
				return false;
		}
		return true;
	}
	
	public static Boolean check_vert(ArrayList<ArrayList<Character>> chart, int pos) {
		for(int i = 0; i < chart.size(); i++) {
			if(chart.get(i).get(pos) != '.')
				return false;
		}
		return true;
	}
}
