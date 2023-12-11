public class Node {

	Node(char type,int x, int y){
		this.set_type(type);
		this.visited = false;
		this.xval = x;
		this.yval = y;
	}
	
	public void set_type(char type) {
		this.type = type;
		switch(this.type) {
		case '|':
			this.dir1 = Direction.North;
			this.dir2 = Direction.South;
			break;
		case '-':
			this.dir1 = Direction.West;
			this.dir2 = Direction.East;
			break;
		case 'L':
			this.dir1 = Direction.North;
			this.dir2 = Direction.East;
			break;
		case 'J':
			this.dir1 = Direction.North;
			this.dir2 = Direction.West;
			break;
		case '7':
			this.dir1 = Direction.South;
			this.dir2 = Direction.West;
			break;
		case 'F':
			this.dir1 = Direction.South;
			this.dir2 = Direction.East;
			break;
		default: //S for part 1, @ for part 2
			dir1 = null;
			dir2 = null;
			break;
		}
	}
	
	public void visit() {
		this.visited = true;
	}
	
	public Boolean visited() {
		return this.visited;
	}
	
	public char get_type() {
		return this.type;
	}
	
	public Direction get_dir1() {
		return this.dir1;
	}
	
	public Direction get_dir2() {
		return this.dir2;
	}
	
	public int get_x() {
		return xval;
	}
	public int get_y() {
		return yval;
	}
	
	
	private int xval;
	private int yval;
	private Boolean visited;
	private char type;
	private Direction dir1;
	private Direction dir2;
	
}
