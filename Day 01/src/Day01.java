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
			System.out.println(lineNum);
			sum += lineNum;
		}
		System.out.println(sum);
	}

}
