package cloud.pangeacyber.pangea.exceptions;
import cloud.pangeacyber.pangea.ResponseHeader;

public class ParseResultFailed extends PangeaException {
    ResponseHeader header;
    String body;

    public ParseResultFailed(String message, Throwable cause, ResponseHeader header, String body) {
        super(message, cause);
        this.header = header;
    }
}
