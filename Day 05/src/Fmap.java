public class Fmap {
	
	public Fmap(String dest, String src, String rng) {
		long inD = Long.valueOf(dest);
		long inS = Long.valueOf(src);
		long inR = Long.valueOf(rng);
		
		inpStart = inS;
		inpEnd = inS + inR - 1;
		outStart = inD;
		outEnd = inD + inR - 1;
		range = inR;
	}
	
	long inpStart;
	long inpEnd;
	long outStart;
	long outEnd;
	long range;
	
	public long map(long src) {
		long middle;
		if(inRange(src)) {
			middle = src - inpStart;
			long mod = outStart + middle;
			return mod;
		}
		
		return -1; //supplied src is not in this map range
	}
	
	public long getStart() {
		return inpStart;
	}
	
	public long getEnd() {
		return inpEnd;
	}
	
	
	private Boolean inRange(long var) {
		if(var >= inpStart && var <= inpEnd)
			return true;
		return false;
	}
}
