import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		ArrayList<Integer> races = new ArrayList<Integer>();
		int[] results = {0,0,0,0};
		String bigrace = "";
		long bigR;
		String bigdist = "";
		long bigD;
		String[] numbers = inp.next().split(":");
		numbers = numbers[1].split(" ");
		for(int i = 0; i < numbers.length; i++) {
			if(numbers[i] != "") { 
				races.add(Integer.valueOf(numbers[i]));
				bigrace += numbers[i];
			}
		}
		bigR = Long.valueOf(bigrace);
		numbers = inp.next().split(":");
		numbers = numbers[1].split(" ");
		for(int i = 0; i < numbers.length; i++) {
			if(numbers[i] != "") {
				races.add(Integer.valueOf(numbers[i]));
				bigdist += numbers[i];
			}
		}
		bigD = Long.valueOf(bigdist);
		
		
		inp.close();
		/*array format:
		 *  TIME1 TIME2 TIME3 TIME4 DIST1 DIST2 DIST3 DIST4
		 */ 
		for(int race = 0; race < 4; race++) {
			int time = races.get(race);
			int Mdist = races.get(race + 4);
			for(int t = 0; t < time; t++) {
				//"t" refers to time spent charging boat and therefore boat velocity
				int distance = t * (time - t);
				if(distance > Mdist)
					results[race] += 1;
			}
		}
		
		System.out.println(results[0] * results[1] * results[2] * results[3]);
		
		long num_ways = 0;
		for(long t = 0; t < bigR; t++) {
			long distance = t * (bigR - t);
			if(distance > bigD)
				num_ways++;
		}
		
		System.out.println(num_ways);
		
	}
}
