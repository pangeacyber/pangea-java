package cloud.pangeacyber.pangea.filters;

public class FilterEqual<T> extends FilterBase {

	public FilterEqual(String name, Filter map) {
		super(name, map);
	}

	public void set(T value) {
		this.map.put(name, value);
	}

	@SuppressWarnings("unchecked")
	public T get() {
		return (T) this.map.get(name);
	}
}
