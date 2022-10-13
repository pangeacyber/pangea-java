package cloud.pangeacyber.pangea.audit.utils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Hash {
    static public String hash(String data){
        return DigestUtils.sha256Hex(data);
    }

    static public byte[] unhexlify(String hex){
        byte[] ans = new byte[hex.length() / 2];
        for (int i = 0; i < ans.length; i++) {
            int index = i * 2;
            int val = Integer.parseInt(hex.substring(index, index + 2), 16);
            ans[i] = (byte)val;
        }
        return ans;
    }

    static public byte[] decode(String hash){        
        return unhexlify(hash);
    }

    static public byte[] hash(byte[] h1, byte[] h2) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(h1);
        outputStream.write(h2);
        byte data[] = outputStream.toByteArray( );
        return DigestUtils.sha256(data);
    }

}
