public class Trio<K,V,T>{

	public Trio(K a, V b, T c) {
		this.key = a;
		this.value = b;
		this.third = c;
	}
	
	public void set_key(K a) {
		this.key = a;
	}
	
	public void set_value(V b) {
		this.value = b;
	}
	
	public void set_third(T c) {
		this.third = c;
	}
	
	public Object get_key() {
		return key;
	}
	
	public Object get_value() {
		return value;
	}
	
	public Object get_third() {
		return third;
	}
	
	private K key;
	private V value;
	private T third;
	
	
}
