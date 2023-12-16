import java.util.ArrayList;

public class Tile {
	
	public Tile(char type) {
		this.type = type;
		this.energized = false;
		this.visited = new ArrayList<>();
	}
	
	public char get_type() {
		return this.type;
	}
	
	public void on() {
		this.energized = true;
	}
	
	public ArrayList<Direction> get_v(){
		return visited;
	}
	
	public boolean visit(Direction d) {
		if(this.visited.contains(d))
			return false;
		else
			this.visited.add(d);
		return true;
	}
	
	public void reset() {
		this.visited.clear();
		this.energized = false;
	}
	
	public boolean state() {
		return energized;
	}
	
	private ArrayList<Direction> visited;
	private char type;
	private boolean energized;
}
