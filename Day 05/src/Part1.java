import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;

public class Part1 {

	public static void main(String[] args) throws FileNotFoundException { //ignoring it now
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[\r\n]");
		Vector<Long> seeds = new Vector<Long>();
		Vector<Long> part2;
		Vector<Fmap> seedSoil = new Vector<Fmap>();
		Vector<Fmap> soilFert = new Vector<Fmap>();
		Vector<Fmap> fertWatr = new Vector<Fmap>();
		Vector<Fmap> watrLght = new Vector<Fmap>();
		Vector<Fmap> lghtTemp = new Vector<Fmap>();
		Vector<Fmap> tempHmdy = new Vector<Fmap>();
		Vector<Fmap> hmdyLocn = new Vector<Fmap>();
		
		String[] raw = inp.next().split(": ");
		raw = raw[1].split(" ");
		for(int i = 0; i < raw.length; i++) {
			seeds.add(Long.valueOf(raw[i]));
		}
		part2 = new Vector<Long>(seeds);
		
		inp.next();  //chew up blank line
		inp.next(); //chew up line we don't care about
		while(true) { //seed to soil
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			seedSoil.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //soil to fertilizer
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			soilFert.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //fertilizer to water
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			fertWatr.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //water to light
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			watrLght.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //light to temperature
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			lghtTemp.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //temperature to humidity
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			tempHmdy.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.next();
		while(true) { //humidity to location
			if (inp.hasNext() == false)
				break;
			raw = inp.next().split(" ");
			if (raw.length <= 1) //end of this section
				break;
			hmdyLocn.add(new Fmap(raw[0],raw[1],raw[2]));
		}
		inp.close();

		//part 1: just find the lowest mappable location number
		for(int i = 0; i < seeds.size(); i++) {
			for(int j = 0; j < seedSoil.size(); j++) {
				long seed = seeds.get(i);
				long soil = seedSoil.get(j).map(seed);
				if (soil != -1) {
					seeds.set(i, soil);
					break;
				}
			}
			for(int j = 0; j < soilFert.size(); j++) {
				long soil = seeds.get(i);
				long fert = soilFert.get(j).map(soil);
				if (fert != -1) {
					seeds.set(i, fert);
					break;
				}
			}
			for(int j = 0; j < fertWatr.size(); j++) {
				long fert = seeds.get(i);
				long water = fertWatr.get(j).map(fert);
				if (water != -1) {
					seeds.set(i, water);
					break;
				}
			}
			for(int j = 0; j < watrLght.size(); j++) {
				long water = seeds.get(i);
				long light = watrLght.get(j).map(water);
				if (light != -1) {
					seeds.set(i, light);
					break;
				}
			}
			for(int j = 0; j < lghtTemp.size(); j++) {
				long light = seeds.get(i);
				long temp = lghtTemp.get(j).map(light);
				if (temp != -1) {
					seeds.set(i, temp);
					break;
				}
			}
			for(int j = 0; j < tempHmdy.size(); j++) {
				long temp = seeds.get(i);
				long hmdy = tempHmdy.get(j).map(temp);
				if (hmdy != -1) {
					seeds.set(i, hmdy);
					break;
				}
			}
			for(int j = 0; j < hmdyLocn.size(); j++) {
				long hmdy = seeds.get(i);
				long locn = hmdyLocn.get(j).map(hmdy);
				if (locn != -1) {
					seeds.set(i, locn);
					break;
				}
			}
		}
		Collections.sort(seeds);
		System.out.println(seeds.get(0));
		//part 2: just gonna copy and paste part 1 and change it
		long lowest = Long.MAX_VALUE;
		for(int t = 0; t < part2.size(); t +=2) {
			for(long i = part2.get(t); i < part2.get(t) + part2.get(t + 1); i++) {
				long p2seed = i;
				for(int j = 0; j < seedSoil.size(); j++) {
					long seed = p2seed;
					long soil = seedSoil.get(j).map(seed);
					if (soil != -1) {
						p2seed = soil;
						break;
					}
				}
				for(int j = 0; j < soilFert.size(); j++) {
					long soil = p2seed;
					long fert = soilFert.get(j).map(soil);
					if (fert != -1) {
						p2seed = fert;
						break;
					}
				}
				for(int j = 0; j < fertWatr.size(); j++) {
					long fert = p2seed;
					long water = fertWatr.get(j).map(fert);
					if (water != -1) {
						p2seed = water;
						break;
					}
				}
				for(int j = 0; j < watrLght.size(); j++) {
					long water = p2seed;
					long light = watrLght.get(j).map(water);
					if (light != -1) {
						p2seed = light;
						break;
					}
				}
				for(int j = 0; j < lghtTemp.size(); j++) {
					long light = p2seed;
					long temp = lghtTemp.get(j).map(light);
					if (temp != -1) {
						p2seed = temp;
						break;
					}
				}
				for(int j = 0; j < tempHmdy.size(); j++) {
					long temp = p2seed;
					long hmdy = tempHmdy.get(j).map(temp);
					if (hmdy != -1) {
						p2seed = hmdy;
						break;
					}
				}
				for(int j = 0; j < hmdyLocn.size(); j++) {
					long hmdy = p2seed;
					long locn = hmdyLocn.get(j).map(hmdy);
					if (locn != -1) {
						p2seed = locn;
						break;
					}
				}
				if(p2seed < lowest)
					lowest = p2seed;
			}
		}
		System.out.println(lowest);
	}

}
