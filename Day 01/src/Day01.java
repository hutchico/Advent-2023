import java.io.*;
import java.util.*;

public class Day01 {

	public static void main(String[] args) {
		FileInputStream inp = null;
		int p1sum = 0;
		int p2sum = 0;
		try {
			inp = new FileInputStream("input.txt");
			int c;
			String line = "";
			while ((c = inp.read()) != -1) {
	            if (c == 10) { //newline
        			int lineVal = 0;
        			//part 1
        			for(int j = 0; j < line.length(); j++) {
        				char n = line.charAt(j);
        				if(n >= 48 && n <= 57) { //this is a digit
        					lineVal += (n - 48) * 10;
        					break;
        				}
        			}
        			for(int j = line.length(); j > 0; j--) {
        				char n = line.charAt(j-1);
        				if(n >= 48 && n <= 57) { //this is a digit
        					lineVal += n - 48;
        					break;
        				}
        			}
        			p1sum += lineVal;
	        		
	            	//part 2
	        		String[] numbers = {"one","two","three","four","five","six","seven","eight","nine"};
	        		String[] nos = {"1","2","3","4","5","6","7","8","9"};
        			lineVal = 0;
        			int fpos = 99; //arbitrary number > 10
        			int cpos, npos;
        			int ref = 0;
        			for(int j = 0; j < 9; j++) {
        				int earliest;
        				cpos = line.indexOf(numbers[j]);
        				npos = line.indexOf(nos[j]);
        				if (npos > -1 && cpos > -1) 
        					earliest = npos < cpos ? npos : cpos;
        				else if (cpos < 0 && npos < 0)  //break cond
        					continue;
        				else if (npos < 0)
        					earliest = cpos;
        				else
        					earliest = npos;
        				if (earliest < fpos) {
        					fpos = earliest;
        					ref = j + 1;
        				}
        			}
        			lineVal += ref * 10;
        			
        			fpos = -2; //reset
        			for(int j = 0; j < 9; j++) {
        				int latest;
        				cpos = line.lastIndexOf(numbers[j]);
        				npos = line.lastIndexOf(nos[j]);
        				if (npos > -1 && cpos > -1) 
        					latest = npos > cpos ? npos : cpos;
        				else if (cpos < 0 && npos < 0) //break cond
        					continue;
        				else if (npos < 0)
        					latest = cpos;
        				else
        					latest = npos;
        				if (latest > fpos) {
        					fpos = latest;
        					ref = j + 1;
        				}
        			}
        			p2sum += lineVal + ref;
        			
	            	line = ""; //reset
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
		
		System.out.println(p1sum);
		System.out.println(p2sum);
	}
}
