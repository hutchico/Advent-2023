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
		inp.close();
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
		for(int i = 0; i < chart.size(); i++) {
			for(int j = 0; j < chart.get(0).size(); j++) {
				if(chart.get(i).get(j) != '.') {
					locations.add(new Pair(j,i));
				}
			}
		}
		
		int part1 = 0;
		long part2 = 0;
		for(int i = 0; i < locations.size(); i++) {
			for(int j = i + 1; j < locations.size(); j++) {
				if(i == j)
					continue;
				long x1 = locations.get(i).get_key();
				long x2 = locations.get(j).get_key();
				long y1 = locations.get(i).get_value();
				long y2 = locations.get(j).get_value();
				int p1x1 = (int) x1;
				int p1x2 = (int) x2;
				int p1y1 = (int) y1;
				int p1y2 = (int) y2;
				//count how many jumps occur between x1 and x2, and y1 and y2
				long greater = x1 > x2 ? x1 : x2;
				long lesser = greater == x2 ? x1 : x2;
				int xjumps = 0;
				if(x1 != x2) {
					for(int r = 0; r < expand_h.size(); r++) {
						if (expand_h.get(r) > lesser && expand_h.get(r) < greater)
							xjumps++;
					}
				}
				
				greater = y1 > y2 ? y1 : y2;
				lesser = greater == y2 ? y1 : y2;
				int yjumps = 0;
				if(y1 != y2) {
					for(int r = 0; r < expand_v.size(); r++) {
						if (expand_v.get(r) > lesser && expand_v.get(r) < greater)
							yjumps++;
					}
				}
				//add 1m to each per jump
				int p1x = xjumps * 2 - xjumps;
				int p1y = yjumps * 2 - yjumps;
				long xjumpt = xjumps * 1000000 - xjumps;
				long yjumpt = yjumps * 1000000 - yjumps;
				
				greater = x1 > x2 ? 1 : 2;
				lesser = y1 > y2 ? 1 : 2; //more accurately named "greater(but y instead)"
				
				if(greater == 1) {
					x1 += xjumpt;
					p1x1 += p1x;
				}
				else {
					x2 += xjumpt;
					p1x2 += p1x;
				}
				
				if(lesser == 1) {
					y1 += yjumpt;
					p1y1 += p1y;
				}
				else {
					y2 += yjumpt;
					p1y2 += p1y;
				}
				part1 += Manhattan(p1x1,p1y1,p1x2,p1y2);
				part2 += Manhattan(x1,y1,x2,y2);
			}
		}
		System.out.println(part1);
		System.out.println(part2);
		
	}

	public static long Manhattan(long x1, long y1, long x2, long y2) {
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
