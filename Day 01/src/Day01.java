import java.io.*;
import java.util.*;

public class Day01 {

	public static void main(String[] args) {
		FileInputStream inp = null;
		Vector<String> lines = new Vector<String>(1);
		try {
			inp = new FileInputStream("input.txt");
			//System.out.println(inp.available());
			int c;
			String line = "";
			while ((c = inp.read()) != -1) {
	            if (c == 10) {
	            	lines.add(line);
	            	line = "";
	            }
	            else {
	            	line += (char)(c);
	            }
	        }
			
			inp.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		int sum = 0;
		for(int i = 0; i < lines.size(); i++) {
			int lineNum = 0;
			String line = lines.get(i);
			for(int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if(c >= 48 && c <= 57) { //this is a digit
					lineNum += (c - 48) * 10;
					break;
				}
			}
			for(int j = line.length(); j > 0; j--) {
				char c = line.charAt(j-1);
				if(c >= 48 && c <= 57) { //this is a digit
					lineNum += c - 48;
					break;
				}
			}
			//System.out.println(lineNum);
			sum += lineNum;
		}
		System.out.println(sum);
		
		String[] numbers = {"zero","one","two","three","four","five","six","seven","eight","nine"};
		String[] nos = {"0","1","2","3","4","5","6","7","8","9"};
		int revisedSum = 0;
		for(int i = 0; i < lines.size(); i++) {
			int lineNum = 0;
			int fpos = 99; //arbitrary number > 10
			int cpos;
			int npos;
			int ref = -1;
			String line = lines.get(i);
			for(int j = 1; j < 10; j++) {
				int earliest;
				cpos = line.indexOf(numbers[j]);
				npos = line.indexOf(nos[j]);
				if (npos > -1 && cpos > -1) {
					earliest = npos < cpos ? npos : cpos;
				}
				else if (cpos < 0 && npos < 0) { //break cond
					continue;
				}
				else if (npos < 0){
					earliest = cpos;
				}
				else
					earliest = npos;
				
				if (earliest < fpos) {
					fpos = earliest;
					ref = j;
				}
			}
			lineNum += ref * 10;
			fpos = -2; //reset
			ref = -1;
			for(int j = 1; j < 10; j++) {
				int latest;
				cpos = line.lastIndexOf(numbers[j]);
				npos = line.lastIndexOf(nos[j]);
				if (npos > -1 && cpos > -1) {
					latest = npos > cpos ? npos : cpos;
				}
				else if (cpos < 0 && npos < 0) { //break cond
					continue;
				}
				else if (npos < 0){
					latest = cpos;
				}
				else
					latest = npos;
				
				if (latest > fpos) {
					fpos = latest;
					ref = j;
				}
			}
			lineNum += ref;
			revisedSum += lineNum;
		}
		System.out.println(revisedSum);
	}

}
