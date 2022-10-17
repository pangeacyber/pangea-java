package cloud.pangeacyber.pangea.audit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

import cloud.pangeacyber.pangea.exceptions.SignerException;


public class LogSigner {
    String privateKeyFilename;
    Ed25519PrivateKeyParameters privateKey = null;
    Ed25519PublicKeyParameters publicKey = null;

    public LogSigner(String privateKeyFilename){
        this.privateKeyFilename = privateKeyFilename;
    }

    public String sign(String data) throws SignerException{
        if(this.privateKey == null || this.publicKey == null){
            this.loadKeys();
        }

        // create the signature
        Signer signer = new Ed25519Signer();
        byte[] message;
        try{
            message = data.getBytes("utf-8");
        } catch(UnsupportedEncodingException e){
            throw new SignerException("Failed to convert message to sign. Encoding not supported");
        }

        System.out.printf("Signing: .%s. \n", data);
        signer.reset();
        signer.init(true, this.privateKey);
        signer.update(message, 0, message.length);
        byte[] signature;
        // FIXME: Signature is not the same as in Go SDK
        try{
            signature = signer.generateSignature();
        } catch(Exception e){
            throw new SignerException("Failed to generate signature");
        }

        return new String(Base64.getEncoder().encode(signature));
    }

    private void loadKeys() throws SignerException{
        System.out.println("Loading keys...");
        String key;
        try{
            key = new String(Files.readAllBytes(Paths.get(this.privateKeyFilename)));
        } catch(IOException e){
            throw new SignerException("Failed to load keys from file: " + this.privateKeyFilename);
        }

        String privateKeyPEM = key
          .replace("-----BEGIN PRIVATE KEY-----", "")
          .replaceAll(System.lineSeparator(), "")
          .replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPEM);

        this.privateKey = new Ed25519PrivateKeyParameters(encoded, 16);
        this.publicKey = this.privateKey.generatePublicKey(); 
    }

    public String getPublicKey() throws SignerException{
        if(this.privateKey == null || this.publicKey == null){
            this.loadKeys();
        }

        return new String(Base64.getEncoder().encode(this.publicKey.getEncoded()));
    }

}
