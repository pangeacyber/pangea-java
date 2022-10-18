package cloud.pangeacyber.pangea.exceptions;
import cloud.pangeacyber.pangea.ResponseHeader;

public class ResultParseFail extends PangeaException {
    ResponseHeader header;
    String body;

    public ResultParseFail(String message, ResponseHeader header, String body) {
        super(message);
        this.header = header;
    }    
}
