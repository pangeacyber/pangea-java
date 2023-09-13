package cloud.pangeacyber.pangea.filters;

import java.util.List;

public class FilterMatch<T> extends FilterEqual<T> {

    public FilterMatch(String name, Filter map) {
        super(name, map);
    }

	@SuppressWarnings("unchecked")
	public List<T> getContains() {
		return (List<T>) map.get(name + "__contains");
	}

	public void setContains(List<T> value) {
		map.put(name + "__contains", value);
	}

	@SuppressWarnings("unchecked")
	public List<T> getIn() {
		return (List<T>) map.get(name + "__in");
	}

	public void setIn(List<T> value) {
		map.put(name + "__in", value);
	}
}
