package cloud.pangeacyber.pangea.authn.models;

abstract class FilterFullRangeEqual<T> extends FilterEqual<T> {

    String name;
    Filter map;

    public FilterFullRangeEqual(String name, Filter map){
        super(name, map);
        this.name = name;
        this.map = map;
    }

}
