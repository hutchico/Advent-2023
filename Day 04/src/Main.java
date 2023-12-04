import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		FileInputStream file;
		
		int score = 0;
		int all_cards = 0;
		int card = 0; //track which card we're on
		HashMap<Integer,Integer> total_cards = new HashMap<Integer,Integer>();
		for(int i = 0; i < 1000; i++) { //arbitrary large number
			total_cards.put(i, 0);
		}
		try {
			file = new FileInputStream("input.txt");
			Scanner inp = new Scanner(file).useDelimiter("[\r\n]");
			while(inp.hasNext()) {
				card++;
				String raw = inp.next();
				String[] line = raw.split(":");
				String[] data = line[1].split(" ");
				ArrayList<String> actual = new ArrayList<String>();
				for(int i = 0; i < data.length; i++) {
					try {
						Integer.parseInt(data[i]);
					}
					catch(NumberFormatException e) {
						continue;
					}
					actual.add(data[i]);
					
				}
				List<String> winners = actual.subList(0, 10); //test: 5 actual:10
				List<String> nums = actual.subList(10, 35); //test:5,13 actual:10,35
				
				int cardScore = 0;
				for(int i = 0; i < winners.size(); i++) {
					if(nums.contains(winners.get(i)))
						cardScore++;
				}
				//either only have one card or previously won cards + 1
				
				int current_num = total_cards.get(card);
				current_num++; //add currently calculated card on top of previous copies
				total_cards.put(card, current_num);
				
				for(int i = 1; i <= cardScore; i++) {
					int prev = total_cards.get(card + i);
					total_cards.put(card + i,prev + current_num);
				}
				if(cardScore > 0) {
					score += Math.pow(2, cardScore - 1);
				}
			}
			inp.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int num : total_cards.values()) {
			all_cards += num;
		}
		System.out.println(score);
		System.out.println(all_cards);
	}
}
