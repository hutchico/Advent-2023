import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("\n");
		ArrayList<ArrayList<Tile>> map = new ArrayList<>();
		int lumens = 0;
		while(inp.hasNext()) {
			String[] raw = inp.next().split("(?!^)");
			ArrayList<Tile> line = new ArrayList<>();
			
			for(int i = 0; i < raw.length; i++) 
				line.add(new Tile(raw[i].charAt(0)));
			
			map.add(line);
		}
		inp.close();
		
		int xrng = map.get(0).size() - 1;
		int yrng = map.size() - 1;
		
		lumens = beam(map,0,0,Direction.Right);
		System.out.println(lumens);
		
		lumens = 0;
		for(int i = 0; i < yrng; i++) { //left edge
			int res = beam(map,0,i,Direction.Right);
			lumens = res > lumens ? res : lumens;
		}
		for(int i = 0; i < yrng; i++) { //right edge
			int res = beam(map,xrng,i,Direction.Left);
			lumens = res > lumens ? res : lumens;
		}
		for(int j = 0; j < xrng; j++) { //top edge
			int res = beam(map,j,0,Direction.Down);
			lumens = res > lumens ? res : lumens;
		}
		for(int j = 0; j < xrng; j++) { //bottom edge
			int res = beam(map,j,yrng,Direction.Up);
			lumens = res > lumens ? res : lumens;
		}
		System.out.println(lumens);
	}
	
	public static int beam(ArrayList<ArrayList<Tile>> map, int start_x, int start_y, Direction start_dir) {
		Queue<Trio> order = new LinkedList<>();
		int lumens = 0;
		order.add(new Trio<Integer,Integer,Direction>(start_x,start_y,start_dir));
		while(order.size() > 0) {
			Trio current = order.poll();
			
			int next_x1 = (int) current.get_key();
			int next_y1 = (int) current.get_value();
			int next_x2 = next_x1;
			int next_y2 = next_y1;
			Direction old_dir = (Direction) current.get_third();
			Direction next1 = null;
			Direction next2 = null; //potential split
		
			if(next_x1 < 0 || next_x1 > map.get(0).size()-1)
				continue;
			if(next_y1 < 0 || next_y1 > map.size()-1) 
				continue;
			
			map.get(next_y1).get(next_x1).on();
			if(!map.get(next_y1).get(next_x1).visit(old_dir))
				continue;
			switch(map.get(next_y1).get(next_x1).get_type()) {
			case '.':
				next1 = old_dir;
				break;
			case '/':
				if(old_dir == Direction.Left || old_dir == Direction.Right)
					next1 = ccw(old_dir);
				else
					next1 = cw(old_dir);
				break;
			case '\\':
				if(old_dir == Direction.Left || old_dir == Direction.Right)
					next1 = cw(old_dir);
				else
					next1 = ccw(old_dir);
				break;
			case '-':
				if(old_dir == Direction.Left || old_dir == Direction.Right) 
					next1 = old_dir;
				else {
					next1 = Direction.Left;
					next2 = Direction.Right;
				}
				break;
			case '|':
				if(old_dir == Direction.Left || old_dir == Direction.Right) {
					next1 = Direction.Up;
					next2 = Direction.Down;
				}
				else
					next1 = old_dir;
				break;
			}	
			switch(next1) {
			case Up:
				next_y1--;
				break;
			case Right:
				next_x1++;
				break;
			case Down:
				next_y1++;
				break;
			case Left:
				next_x1--;
				break;
			}
			order.add(new Trio(next_x1,next_y1,next1));
			if(next2 != null) {
				switch(next2) {
				case Up:
					next_y2--;
					break;
				case Right:
					next_x2++;
					break;
				case Down:
					next_y2++;
					break;
				case Left:
					next_x2--;
					break;
				}
				order.add(new Trio(next_x2,next_y2,next2));
			}
		}
		
		for(int my = 0; my < map.size(); my++) {
			for(int mx = 0; mx < map.get(0).size(); mx++) {
				if(map.get(my).get(mx).state())
					lumens++;
				map.get(my).get(mx).reset();
			}
		}
		
		return lumens;
	}
	
	public static Direction ccw(Direction d) {
		switch(d) {
		case Right:
			return Direction.Up;
		case Up:
			return Direction.Left;
		case Left:
			return Direction.Down;
		case Down:
			return Direction.Right;
		}
		return Direction.Up;
	}
	
	public static Direction cw(Direction d) {
		switch(d) {
		case Left:
			return Direction.Up;
		case Down:
			return Direction.Left;
		case Right:
			return Direction.Down;
		case Up:
			return Direction.Right;
		}
		return Direction.Up;
	}
	
}
