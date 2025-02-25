package com.bencesoft.commons.cert;

import lombok.Builder;
import lombok.Getter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class CertificateGenerator {

    private static final String ROOT_FOLDER = "src";
    private static final String DEFAULT_SEPARATOR = "/";

    @Builder.Default
    private final String keyStorePath = "src/main/resources/cert/keystore.p12";
    @Builder.Default
    private final String keyStoreType = "PKCS12";
    @Builder.Default
    private final String keyStorePassword = UUID.randomUUID().toString();
    @Builder.Default
    private final String keyAlias = "certificate-key";
    @Builder.Default
    private final String keyAlg = "RSA";
    @Builder.Default
    private final Integer keySize = 4096;
    @Builder.Default
    private final Integer keyValidityDays = 99999;
    @Builder.Default
    private final String commonName = "";
    @Builder.Default
    private final String organization = "";
    @Builder.Default
    private final String country = "";
    @Builder.Default
    private final String state = "";
    @Builder.Default
    private final String location = "";

    public void generate() throws CertificateGeneratorException {
        final String[] validatedKeyStorePath = getValidKeyStorePath();
        final StringBuilder keytoolOutput = new StringBuilder();
        var exitCode = 0;
        try {
            deleteCertFileIfExists();
            final Process keytoolProcess = startKeytoolProcess(validatedKeyStorePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(keytoolProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    keytoolOutput.append(line).append("\n");
                }
            }
            exitCode = keytoolProcess.waitFor();
        } catch (Exception ex) {
            throw new CertificateGeneratorException(ex);
        }
        if (exitCode != 0) {
            throw new CertificateGeneratorException(keytoolOutput.toString());
        }
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(keytoolOutput);
        System.out.println("Certificate directory: " + keyStorePath);
        System.out.println("----------------------------------------------------------------------------------------");
    }

    private Process startKeytoolProcess(String[] validatedKeyStorePath) throws IOException {
        final var keyStoreFileName = validatedKeyStorePath[validatedKeyStorePath.length - 1];
        final var keyStoreDirectory = Paths.get(String.join(DEFAULT_SEPARATOR, Arrays.copyOf(validatedKeyStorePath, validatedKeyStorePath.length - 1))).toFile();
        final var csr = "CN=" + commonName + "O=" + organization + "C=" + country + "L=" + location + "ST=" + state;
        final String[] command = {
            "keytool",
            "-genkeypair",
            "-alias", keyAlias,
            "-keyalg", keyAlg,
            "-keysize", String.valueOf(keySize),
            "-keystore", keyStoreFileName,
            "-storetype", keyStoreType,
            "-storepass", keyStorePassword,
            "-validity", String.valueOf(keyValidityDays),
            "-dname", csr
        };
        final var processBuilder = new ProcessBuilder(command);
        processBuilder.directory(keyStoreDirectory);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    private void deleteCertFileIfExists() throws IOException {
        final var certFile = Paths.get(keyStorePath);
        Files.deleteIfExists(certFile);
    }

    private String[] getValidKeyStorePath() {
        if (Objects.isNull(keyStorePath) || keyStorePath.isBlank()) {
            throw new CertificateGeneratorException("Keystore path is empty.");
        }
        final var keyStorePathTokens = keyStorePath.split(DEFAULT_SEPARATOR);
        if (!Objects.equals(keyStorePathTokens[0], ROOT_FOLDER) || !keyStorePathTokens[keyStorePathTokens.length - 1].matches(".+\\..+")) {
            throw new CertificateGeneratorException("Keystore path is invalid: " + keyStorePath);
        }
        return keyStorePathTokens;
    }
}
