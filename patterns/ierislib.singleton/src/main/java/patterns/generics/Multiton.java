package patterns.generics;

import java.util.HashMap;

public class Multiton<V, K> {
	private HashMap<K, V> instances;

	private Multiton() {
		instances = new HashMap<K, V>();
	}

	public V getInstance(K key) {
		V value = instances.get(key);
		if (value == null) {
			instances.put(key, value);
		};
		return value;
	}
}
