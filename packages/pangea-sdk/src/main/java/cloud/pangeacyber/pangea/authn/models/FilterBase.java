package cloud.pangeacyber.pangea.authn.models;

public class FilterBase {
    protected String name;
    protected Filter map;

    FilterBase(String name, Filter map){
        this.name = name;
        this.map = map;
    }

}
