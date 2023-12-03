import java.util.Scanner;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		//p1 goal: discover which games from input set are possible with 12 red, 13 green, 14 blue
		//p2 goal: count the greatest number recorded for each color in each set
		int IDsum = 0;
		int powSum = 0;
		int id = 0;
		final int Rlim = 12;
		final int Glim = 13;
		final int Blim = 14;
		try {
			FileInputStream file = new FileInputStream("input.txt");
			Scanner inp = new Scanner(file).useDelimiter("\n");
			while(inp.hasNext()) {
				String line = "";
				line = inp.next();
				id += 1;
				Boolean is_impossible = false;
				int minred = -1;
				int mingreen = -1;
				int minblue = -1;
				String[] single = line.split(":"); //ignore format text
				String[] pulls = single[1].split("[,;]"); //split into [number][color] pairs
				for(int i = 0; i < pulls.length; i++) {
					String[] pair = pulls[i].split(" "); //there's a leading space that will be pair[0]
					int num = Integer.valueOf(pair[1]);  //but we're just gonna ignore that
					switch (pair[2]) {
					case "red":
						minred = num > minred ? num : minred;
						if (num > Rlim) 
							is_impossible = true; //part 1 optimization: break if this evaluates true					
						break;
					case "green":
						mingreen = num > mingreen ? num : mingreen;
						if (num > Glim) 
							is_impossible = true;							
						break;
					case "blue":
						minblue = num > minblue ? num : minblue;
						if (num > Blim) 
							is_impossible = true;
						break;
					}
				}
				
				powSum += minred * mingreen * minblue;
				
				if(!is_impossible) //not found to be impossible
					IDsum += id;
			}
			inp.close();
			System.out.println(IDsum);
			System.out.println(powSum);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//thank you eclipse god I hate mandatory exception handling
			e.printStackTrace();
		}
	}
}