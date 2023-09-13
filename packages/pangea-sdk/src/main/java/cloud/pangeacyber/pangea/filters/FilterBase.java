package cloud.pangeacyber.pangea.filters;

import cloud.pangeacyber.pangea.authn.models.Filter;

public class FilterBase {
    protected String name;
    protected Filter map;

    public FilterBase(String name, Filter map){
        this.name = name;
        this.map = map;
    }

}
