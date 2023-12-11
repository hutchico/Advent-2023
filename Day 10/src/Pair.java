public class Pair<K,V>{

	public Pair(K a, V b) {
		this.key = a;
		this.value = b;
	}
	
	public void set_key(K a) {
		this.key = a;
	}
	
	public void set_value(V b) {
		this.value = b;
	}
	
	
	public K get_key() {
		return key;
	}
	
	public V get_value() {
		return value;
	}
	
	private K key;
	private V value;
}
