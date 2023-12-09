import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		long sum_forward = 0;
		long sum_backward = 0;
		while(inp.hasNext()) {
			String[] line = inp.next().split(" ");
			Stack<Integer> last = new Stack<>();
			Stack<Integer> first = new Stack<>();
			ArrayList<Integer> entries = new ArrayList<>();
			ArrayList<Integer> diff = new ArrayList<>();
			long lineSum = 0;
			long firstVal = 0;
			for(int i = 0; i < line.length; i++) {
				entries.add(Integer.valueOf(line[i]));
			}
			//last.add(entries.get(entries.size() - 1)); //dunno why this was breaking it, leaving it to sort later
			while(!oops_all_zeroes(entries)) {
				for(int i = 0; i < entries.size() - 1; i++) {
					diff.add(entries.get(i + 1) - entries.get(i));
				}
				last.add(entries.get(entries.size() - 1));
				first.add(entries.get(0));
				entries = new ArrayList<>(diff);
				diff.clear();
			}
			while(!last.empty()) {
				lineSum += last.pop();
			}
			
			while(!first.empty()) {
				int temp = first.pop();
				firstVal = temp - firstVal;
			}
			sum_forward += lineSum;
			sum_backward += firstVal;
		}
		inp.close();
		System.out.println(sum_forward);
		System.out.println(sum_backward);
	}
	
	public static Boolean oops_all_zeroes(ArrayList<Integer> toCheck) {
		for(int i = 0; i < toCheck.size(); i++) {
			if(toCheck.get(i) != 0)
				return false;
		}
		return true;
	}
}
