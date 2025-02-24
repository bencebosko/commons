package com.bencesoft.commons.cert;

public class CertificateGeneratorException extends RuntimeException {

    public CertificateGeneratorException(Exception ex) {
        super(ex);
    }

    public CertificateGeneratorException(String msg) {
        super(msg);
    }
}
