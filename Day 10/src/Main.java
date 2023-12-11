import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("input.txt");
		Scanner inp = new Scanner(file).useDelimiter("[\n\r]");
		ArrayList<ArrayList<Node>> map = new ArrayList<>();
		ArrayList<ArrayList<Node>> explode = new ArrayList<>();
		Stack<Node> coords = new Stack<>(); //would ideally do this with a set but w/e
		int curX = 0;
		int curY = 0;
		Direction prevDir;
		int steps = 1;
		Node current;
		while(inp.hasNext()) {
			int count = 0;
			String[] raw = inp.next().split("(?!^)");
			ArrayList<Node> line = new ArrayList<>();
			for(int i = 0; i < raw.length; i++) {
				if(raw[i].compareTo("S") == 0) {
					curX = i;
					curY = map.size();
				}
				line.add(new Node(raw[i].charAt(0),i,count));
			}
			map.add(line);
			count++;
		}
		for(int h = 0; h < map.size() * 3; h++) {
			int count = 0;
			ArrayList<Node> blankline = new ArrayList<>();
			for(int i = 0; i < map.get(0).size() * 3; i++) {
				blankline.add(new Node('@',i,count));
			}
			explode.add(blankline);
			count++;
		}
		
		inp.close();
		//traverse loop
		//looked at input and determined start dir to be north or west, choosing north
		prevDir = Direction.North;
		curY--;
		current = map.get(curY).get(curX);
		coords.add(current);
		while(current.get_type() != 'S') {
			prevDir = current.get_dir1() == negate(prevDir) ? current.get_dir2() : current.get_dir1();
			switch(prevDir){
			case South:
				curY++;
				break;
			case North:
				curY--;
				break;
			case East:
				curX++;
				break;
			case West:
				curX--;
				break;
			}
			steps++;
			current = map.get(curY).get(curX);
			coords.add(current);
		}
		
		System.out.println(steps / 2);
		
		for(int i = 0; i < map.size(); i++) {
			for(int j = 0; j < map.get(0).size(); j++) {
				current = map.get(i).get(j);
				if(coords.search(current) != -1) {
					continue;
				}
				else {
					ArrayList<Node> sub = map.get(i);
					current = sub.get(j);
					if(current.get_type() != 'S')
						current.set_type('@');
					sub.set(j, current);
					map.set(i, sub);
				}
			}
		}
		
		for(int i = 1; i < explode.size()-1; i+=3) {
			for(int j = 1; j < explode.get(0).size()-1; j+=3) {
				char exc = map.get((i-1)/3).get((j-1)/3).get_type();
				switch(exc) {
				
				case '|':
					change(explode.get(i-1),"@|@",j-1);
					change(explode.get(i),  "@|@",j-1);
					change(explode.get(i+1),"@|@",j-1);
					break;
					
				case '-':
					change(explode.get(i-1),"@@@",j-1);
					change(explode.get(i),  "---",j-1);
					change(explode.get(i+1),"@@@",j-1);
					break;
					
				case 'L':
					change(explode.get(i-1),"@|@",j-1);
					change(explode.get(i),  "@L-",j-1);
					change(explode.get(i+1),"@@@",j-1);
					break;
				case 'J':
					change(explode.get(i-1),"@|@",j-1);
					change(explode.get(i),  "-J@",j-1);
					change(explode.get(i+1),"@@@",j-1);
					break;
				case '7':
					change(explode.get(i-1),"@@@",j-1);
					change(explode.get(i),  "-7@",j-1);
					change(explode.get(i+1),"@|@",j-1);
					break;
					
				case 'F':
					change(explode.get(i-1),"@@@",j-1);
					change(explode.get(i),  "@F-",j-1);
					change(explode.get(i+1),"@|@",j-1);
					break;
					
				case 'S': //S in this case is a J shape
					change(explode.get(i-1),"@S@",j-1);
					change(explode.get(i),  "SS@",j-1);
					change(explode.get(i+1),"@@@",j-1);
					break;
					
				default: // @ $ . * etc
					String o = "";
					o = o + exc;
					o = o + exc;
					o = o + exc;
					change(explode.get(i-1),o,j-1);
					change(explode.get(i),  o,j-1);
					change(explode.get(i+1),o,j-1);
					break;
				}
			}
		}
		//flood(210,210,explode);
		
		//iterative conversion of recursive solution due to memory issues
		Stack<Pair<Integer,Integer>> node_stack = new Stack<>();
		curX = 0;
		curY = 0;
		node_stack.add(new Pair<Integer,Integer>(curX,curY));
		while(node_stack.size() != 0) {
			Pair<Integer,Integer> currC = node_stack.pop();
			curX = (Integer)currC.get_key();
			curY = (Integer)currC.get_value();
			if (curX < 0 || curX > explode.get(0).size()-1)
				continue;
			if(curY < 0 || curY > explode.size()-1) 
				continue;
			Node current_node = explode.get(curY).get(curX);
			if(current_node.visited())
				continue;
			if(current_node.get_type() != '@') 
				continue;
			current_node.set_type('*');
			current_node.visit();
			ArrayList<Node> cline = explode.get(curY);
			cline.set(curX, current_node);
			explode.set(curY, cline);
			
			node_stack.add(new Pair<>(curX+1,curY));
			node_stack.add(new Pair<>(curX-1,curY));
			node_stack.add(new Pair<>(curX,curY-1));
			node_stack.add(new Pair<>(curX,curY+1));
			node_stack.add(new Pair<>(curX-1,curY-1));	
			node_stack.add(new Pair<>(curX+1,curY-1));
			node_stack.add(new Pair<>(curX-1,curY+1));
			node_stack.add(new Pair<>(curX+1,curY+1));
		}
		
		steps = 0; //reuse this variable: at this point it means "tiles inside loop"
		//idea: check each tile and only "count" if it was unmodified by the floodfill
		for(int i = 1; i < explode.size() - 1; i+=3) {
			for(int j = 1; j < explode.get(0).size() - 1; j+=3) {
				String tile = "";
				tile += explode.get(i-1).get(j-1).get_type();
				tile += explode.get(i-1).get(j).get_type();
				tile += explode.get(i-1).get(j+1).get_type();
				tile += explode.get(i).get(j-1).get_type();
				tile += explode.get(i).get(j).get_type();
				tile += explode.get(i).get(j+1).get_type();
				tile += explode.get(i+1).get(j-1).get_type();
				tile += explode.get(i+1).get(j-1).get_type();
				tile += explode.get(i+1).get(j-1).get_type();
				
				//would like to use a switch here but string.compare doesn't work that way?
				if(tile.compareTo("@|@@|@@|@") == 0)
					steps++;
				else if(tile.compareTo("@@@---@@@") == 0)
					steps++;
				else if(tile.compareTo("@|@-L-@@@") == 0)
					steps++;
				else if(tile.compareTo("@|@-J-@@@") == 0)
					steps++;
				else if(tile.compareTo("@@@-7-@|@") == 0)
					steps++;
				else if(tile.compareTo("@@@-F-@|@") == 0)
					steps++;
				else if(tile.compareTo("@S@SS-@@@") == 0)
					steps++;
				else if(tile.compareTo("@@@@@@@@@") == 0)
					steps++;
			}
		}
		System.out.println(steps);
	}

	public static Direction negate(Direction d) {
		switch(d) {
		case South:
			return Direction.North;
		case North:
			return Direction.South;
		case East:
			return Direction.West;
		case West:
			return Direction.East;
		}
		return Direction.North; //not happening, switch catches all cases
	}
	
	//leaving this in because it works, the map is just too large for a recursive solution
	public static void flood(int x, int y, ArrayList<ArrayList<Node>> map) {
		if (x < 0 || x > map.get(0).size()-1)
			return;
		if(y < 0 || y > map.size()-1) 
			return;
		Node this_node = map.get(y).get(x);
		if(this_node.visited())
			return; //"visited"
		if(this_node.get_type() != '@')
			return;
		this_node.set_type('$');
		this_node.visit();
		ArrayList<Node> sub = map.get(y);
		sub.set(x, this_node);
		map.set(y, sub);
		flood(x-1,y-1,map);
		flood(x,y-1,map);
		flood(x+1,y-1,map);
		flood(x-1,y,map);
		flood(x+1,y,map);
		flood(x-1,y+1,map);
		flood(x,y+1,map);
		flood(x+1,y+1,map);
	}
	
	public static void change(ArrayList<Node> line, String type, int pos) {
		for(int i = 0; i < 3; i++) {
			Node piece = line.get(pos+i);
			piece.set_type(type.charAt(i));
			line.set(pos+i, piece);
		}
	}
}