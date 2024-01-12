package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.vault.models.*;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.VaultClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(VaultClient.serviceName);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        var client = new VaultClient.Builder(cfg).build();

        try {
            // First create an encryption key, either from the Pangea Console or
            // programmatically as below.
            var dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            var time = dtf.format(LocalDateTime.now());
            var generateResp = client.symmetricGenerate(
                    new SymmetricGenerateRequest.Builder(
                            SymmetricAlgorithm.AES256_CFB,
                            KeyPurpose.ENCRYPTION,
                            "Java example " + time).build());
            var encryptionKeyId = generateResp.getResult().getId();

            // Structured data that we'll encrypt.
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("foo", "bar");
            data.put("some", "thing");

            // Encrypt
            var encryptResponse = client.encryptStructured(
                    new EncryptStructuredRequest.Builder<String, Object, Map<String, Object>>(
                            encryptionKeyId,
                            data,
                            "$.foo").build());
            System.out.println("Encrypted result: " + encryptResponse.getResult().getStructuredData());
        } catch (Exception error) {
            System.out.println("Error: " + error);
            System.exit(1);
        }
    }
}
