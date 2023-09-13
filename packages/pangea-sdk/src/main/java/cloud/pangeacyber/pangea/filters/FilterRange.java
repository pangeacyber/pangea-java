package cloud.pangeacyber.pangea.filters;

public class FilterRange<T> extends FilterEqual<T>{

    public FilterRange(String name, Filter map){
        super(name, map);
    }

    public void setLessThan(T value){
        this.map.put(name + "__lt", value);
    }

    @SuppressWarnings("unchecked")
    public T getLessThan() {
        return (T)this.map.get(name + "__lt");
    }

    public void setLessThanEqual(T value){
        this.map.put(name + "__lte", value);
    }

    @SuppressWarnings("unchecked")
    public T getLessThanEqual() {
        return (T)this.map.get(name + "__lte");
    }

    public void setGreaterThan(T value){
        this.map.put(name + "__gt", value);
    }

    @SuppressWarnings("unchecked")
    public T getGreaterThan() {
        return (T)this.map.get(name + "__gt");
    }

    public void setGreaterThanEqual(T value){
        this.map.put(name + "__gte", value);
    }

    @SuppressWarnings("unchecked")
    public T getGreaterThanEqual() {
        return (T)this.map.get(name + "__gte");
    }

}
