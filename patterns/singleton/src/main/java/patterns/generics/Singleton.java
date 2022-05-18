package patterns.generics;

public class Singleton<T> {
	private T instance;

	private Singleton() {}

	public T getInstance() {
		return instance;
	}
}
