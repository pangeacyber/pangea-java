package cloud.pangeacyber.pangea.filters;

public class FilterBase {

	protected String name;
	protected Filter map;

	public FilterBase(String name, Filter map) {
		this.name = name;
		this.map = map;
	}
}
