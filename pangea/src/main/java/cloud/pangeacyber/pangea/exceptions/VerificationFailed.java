package cloud.pangeacyber.pangea.exceptions;


public class VerificationFailed extends AuditException{
    String hash;

    public VerificationFailed(String message, Throwable cause, String hash) {
        super(message, cause);
        this.hash = hash;
    }

}
