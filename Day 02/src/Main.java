import java.util.Scanner;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		//p1 goal: discover which games from input set are possible with 12 red, 13 green, 14 blue
		//p2 goal: count the greatest number recorded for each color in each set
		int IDsum = 0;
		int powSum = 0;
		int id = 0; //not gonna bother tracking IDs in specific lines
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
				Boolean out = false;
				int minred = -1;
				int mingreen = -1;
				int minblue = -1;
				String[] single = line.split(":"); //full line
				String[] groups = single[1].split(";"); //everything after :
				for(int i = 0; i < groups.length; i++) {
					String[] each = groups[i].split(",");
					for(int j = 0; j < each.length; j++) {
						each[j] = each[j].substring(1,each[j].length()); //strip leading space
						String[] pull = each[j].split(" "); //split into number + text
						int num = Integer.valueOf(pull[0]);
						switch (pull[1]) {
						case "red":
							minred = num > minred ? num : minred;
							if (num > Rlim) 
								out = true; //part 1 optimization: break if this evaluates true					
							break;
						case "green":
							mingreen = num > mingreen ? num : mingreen;
							if (num > Glim) 
								out = true;							
							break;
						case "blue":
							minblue = num > minblue ? num : minblue;
							if (num > Blim) 
								out = true;
							break;
						}
					}
				}
				powSum += minred * mingreen * minblue;
				
				if(out != true) //not found to be impossible
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
