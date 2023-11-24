package certificategeneration;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SelfSignedCertificateGenerator1 {
    public static void main(String[] args) throws Exception {
        // Add the Bouncy Castle provider
        Security.addProvider(new BouncyCastleProvider());

        // Generate a new key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048); // 2048-bit key size
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Create the self-signed X.509 certificate
        X509Certificate certificate = generateSelfSignedCertificate(keyPair);

        // Save the certificate to a file
        saveCertificateToFile(certificate, "self_signed_certificate.crt");
    }

    private static X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws Exception {
        // Prepare the certificate information
        X500NameBuilder builder = new X500NameBuilder();
        builder.addRDN(org.bouncycastle.asn1.x500.style.BCStyle.CN, "Self Signed Certificate");
        builder.addRDN(org.bouncycastle.asn1.x500.style.BCStyle.O, "JKBank");
        builder.addRDN(org.bouncycastle.asn1.x500.style.BCStyle.C, "India");

        Date validityStartDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // One day ago
        Date validityEndDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000); // One year from now

        // Create the certificate
        X509CertificateHolder certHolder = new JcaX509v3CertificateBuilder(
                builder.build(),
                BigInteger.valueOf(System.currentTimeMillis()),
                validityStartDate,
                validityEndDate,
                builder.build(),
                keyPair.getPublic()
        ).build(new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate()));

        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    private static void saveCertificateToFile(X509Certificate certificate, String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(certificate.getEncoded());
        fos.close();

        System.out.println("Certificate saved to: " + fileName);
    }
}

