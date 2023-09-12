package cloud.pangeacyber.pangea.authn.models;

public class FilterFullRange<T> extends FilterFullRangeEqual<T> {

    String name;
    Filter map;

    public FilterFullRange(String name, Filter map){
        super(name, map);
        this.name = name;
        this.map = map;
    }

}
