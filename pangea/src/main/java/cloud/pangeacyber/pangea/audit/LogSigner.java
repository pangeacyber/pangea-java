package cloud.pangeacyber.pangea.audit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;


public class LogSigner {
    String privateKeyFilename;
    Ed25519PrivateKeyParameters privateKey = null;
    Ed25519PublicKeyParameters publicKey = null;

    public LogSigner(String privateKeyFilename){
        this.privateKeyFilename = privateKeyFilename;
    }

    public String sign(String data) throws CryptoException, DataLengthException, UnsupportedEncodingException, IOException, Exception{
        // TODO: Throw just one exception type.
        if(this.privateKey == null || this.publicKey == null){
            this.loadKeys();
        }

        // create the signature
        Signer signer = new Ed25519Signer();
        byte[] message = data.getBytes("utf-8");

        System.out.printf("Signing: .%s. \n", data);
        signer.reset();
        signer.init(true, this.privateKey);
        signer.update(message, 0, message.length);

        // FIXME: Signature is not the same as in Go SDK
        byte[] signature = signer.generateSignature();

        return new String(Base64.getEncoder().encode(signature));
    }

    private void loadKeys() throws IOException, Exception {
        System.out.println("Loading keys...");
        String key = new String(Files.readAllBytes(Paths.get(this.privateKeyFilename)));
        String privateKeyPEM = key
          .replace("-----BEGIN PRIVATE KEY-----", "")
          .replaceAll(System.lineSeparator(), "")
          .replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPEM);

        this.privateKey = new Ed25519PrivateKeyParameters(encoded, 16);
        this.publicKey = this.privateKey.generatePublicKey(); 
    }

    public String getPublicKey() throws IOException, Exception{
        if(this.privateKey == null || this.publicKey == null){
            this.loadKeys();
        }

        return new String(Base64.getEncoder().encode(this.publicKey.getEncoded()));
    }

}
