package certificategeneration;

import java.io.FileOutputStream;

import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public class SelfSignedCertificateGenerator {

	public static void main(String[] args) {

		try {

			// Generate the key pair

			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

			keyPairGenerator.initialize(2048);

			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			// Generate the self-signed X.509 certificate

			X509Certificate certificate = generateSelfSignedCertificate(keyPair);

			// Save the certificate and private key to files

			saveCertificateToFile(certificate, "self_signed_certificate.cer");

			savePrivateKeyToFile(keyPair.getPrivate(), "private_key.key");

			System.out.println("Self-signed certificate and key generated successfully!");

			System.out.println("Certificate saved to: self_signed_certificate.cer");

			System.out.println("Private key saved to: private_key.key");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private static X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws Exception {

		X500Principal subject = new X500Principal("CN=localhost, O=JKBANK, L=Srinagar, ST=JAMMU&KASHMIR, C=INDIA");

		// Set the certificate validity period (in days)

		int validityDays = 365;

		// Generate the self-signed certificate

		X509Certificate cert;

		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

		cert = (X509Certificate) certFactory.generateCertificate(null);
		// cert.getSerialNumber();
		/*
		 * cert.setSerialNumber(System.currentTimeMillis());
		 * 
		 * cert.setSubjectDN(subject);
		 * 
		 * cert.setIssuerDN(subject);
		 * 
		 * cert.setPublicKey(keyPair.getPublic());
		 * 
		 * cert.setNotBefore(new java.util.Date());
		 * 
		 * cert.setNotAfter(new java.util.Date(System.currentTimeMillis() + validityDays
		 * * 86400000L));
		 * 
		 * cert.sign(keyPair.getPrivate(), "SHA256withRSA");
		 */

		return cert;

	}

	private static void saveCertificateToFile(X509Certificate certificate, String fileName) throws Exception {

		try (FileOutputStream fos = new FileOutputStream(fileName)) {

			fos.write(certificate.getEncoded());

		}

	}

	private static void savePrivateKeyToFile(PrivateKey privateKey, String fileName) throws Exception {

		try (FileOutputStream fos = new FileOutputStream(fileName)) {

			fos.write(privateKey.getEncoded());

		}

	}

}
