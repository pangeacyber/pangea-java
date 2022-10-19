package cloud.pangeacyber.pangea.exceptions;
import cloud.pangeacyber.pangea.ResponseHeader;

public class ParseResultFailed extends PangeaException {
    ResponseHeader header;
    String body;

    public ParseResultFailed(String message, ResponseHeader header, String body) {
        super(message);
        this.header = header;
    }    
}
