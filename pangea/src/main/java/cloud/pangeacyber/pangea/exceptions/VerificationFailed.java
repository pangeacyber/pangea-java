package cloud.pangeacyber.pangea.exceptions;


public class VerificationFailed extends AuditException{
    String hash;

    public VerificationFailed(String message, String hash) {
        super(message);
        this.hash = hash;
    }

}
