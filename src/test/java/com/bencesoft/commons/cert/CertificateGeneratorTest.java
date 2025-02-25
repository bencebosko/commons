package com.bencesoft.commons.cert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CertificateGeneratorTest {

    @Test
    public void generate_ShouldThrowExceptionIfKeyStorePathIsNull() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath(null)
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldThrowExceptionIfKeyStorePathIsEmpty() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath("")
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldThrowExceptionIfKeyStorePathNotStartsWithSrc() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath("notSrc/keystore.p12")
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldThrowExceptionIfKeyStorePathNotContainsFileName() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath("src/invalid_file")
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldThrowExceptionForNotExistingDirectory() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath("src/not_existing_directory/keystore.p12")
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldThrowExceptionForInvalidParams() {
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePassword("")
            .build();
        Assertions.assertThrows(CertificateGeneratorException.class, certificateGenerator::generate);
    }

    @Test
    public void generate_ShouldInitializeWithDefaultParams() {
        // GIVEN
        var certificateGenerator = CertificateGenerator.builder().build();
        // THEN
        Assertions.assertEquals("src/main/resources/cert/keystore.p12", certificateGenerator.getKeyStorePath());
        Assertions.assertEquals("PKCS12", certificateGenerator.getKeyStoreType());
        Assertions.assertNotNull(certificateGenerator.getKeyStorePassword());
        Assertions.assertEquals("certificate-key", certificateGenerator.getKeyAlias());
        Assertions.assertEquals("RSA", certificateGenerator.getKeyAlg());
        Assertions.assertEquals(4096, certificateGenerator.getKeySize());
        Assertions.assertEquals(99999, certificateGenerator.getKeyValidityDays());
        Assertions.assertEquals("", certificateGenerator.getCommonName());
        Assertions.assertEquals("", certificateGenerator.getOrganization());
        Assertions.assertEquals("", certificateGenerator.getCountry());
        Assertions.assertEquals("", certificateGenerator.getState());
        Assertions.assertEquals("", certificateGenerator.getLocation());
    }

    @Test
    public void generate_ShouldGenerateCertificate() {
        // GIVEN
        var certificateGenerator = CertificateGenerator.builder()
            .keyStorePath("src/test/resources/cert/keystore.p12")
            .keyStoreType("PKCS12")
            .keyStorePassword("password")
            .keyAlias("cert-key")
            .keyAlg("RSA")
            .keySize(2048)
            .keyValidityDays(365)
            .commonName("bencesoft.com")
            .organization("bencesoft")
            .country("Hungary")
            .state("Budapest")
            .location("Budapest")
            .build();
        // THEN
        Assertions.assertDoesNotThrow(certificateGenerator::generate);
        Assertions.assertTrue(Files.exists(Paths.get(certificateGenerator.getKeyStorePath())));
    }
}
