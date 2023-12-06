import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;

public class Main {

	public static void main(String[] args) throws FileNotFoundException { //ignoring it now
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[\r\n]");
		Vector<Long> seeds = new Vector<Long>();
		Vector<Long> part2;
		Vector<Vector<Fmap>> maps = new Vector<Vector<Fmap>>();
		
		String[] raw = inp.next().split(": ");
		raw = raw[1].split(" ");
		for(int i = 0; i < raw.length; i++) {
			seeds.add(Long.valueOf(raw[i]));
		}
		part2 = new Vector<Long>(seeds);
		
		inp.next(); //chew up blank line
		inp.next(); //chew up descriptor
		for(int i = 0; i < 7; i++) {
			Vector<Fmap> tmp = new Vector<Fmap>();
			while(inp.hasNext()) {
				raw = inp.next().split(" ");
				if (raw.length <= 1) //end of this section
					break;
				tmp.add(new Fmap(raw[0],raw[1],raw[2]));
			}
			maps.add(new Vector<Fmap>(tmp));
			if(inp.hasNext())
				inp.next(); //chew up descriptor line
		}
		
		inp.close();

		//part 1: just find the lowest mappable location number
		for(int i = 0; i < seeds.size(); i++) {
			for(int j = 0; j < 7; j++) {
				for(int k = 0; k < maps.get(j).size(); k++) {
					long seed = seeds.get(i);
					long route = maps.get(j).get(k).map(seed);
					if (route != -1) {
						seeds.set(i,route);
						break;
					}
				}
			}
		}
		Collections.sort(seeds);
		System.out.println(seeds.get(0));
		//part 2: naive bruteforce using part 1 code
		long lowest = Long.MAX_VALUE;
		for(int t = 0; t < part2.size(); t +=2) {
			for(long i = part2.get(t); i < part2.get(t) + part2.get(t + 1); i++) {
				long p2seed = i;
				for(int j = 0; j < 7; j++) {
					for(int k = 0; k < maps.get(j).size(); k++) {
						long seed = p2seed;
						long route = maps.get(j).get(k).map(seed);
						if (route != -1) {
							p2seed = route;
							break;
						}
					}
				}
				if(p2seed < lowest)
					lowest = p2seed;
			}
		}
		System.out.println(lowest);
	}
}