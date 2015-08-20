package de.rub.nds.eccjava.realtest;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Arrays;
import javax.crypto.KeyAgreement;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class ECTestBC {

    @Test
    public void testECFromBook() throws Exception {

	// curve definition
	String a = "2";
	String b = "2";
	String q = "17"; // modulus in the elliptic curve

	// base point initialization
	String basePointX = "5";
	String basePointY = "1";
	String order = "19";

	// private key
	String privateKey = "7";

	// public key points
	String publicKeyX = "11";
	String publicKeyY = "1";

	// y^2 = x^3 + ax + b mod q
	EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(q)), // q
										  // (modulus)
		new BigInteger(a), // a
		new BigInteger(b)); // b

	ECParameterSpec spec = new ECParameterSpec(curve, new ECPoint(new BigInteger(basePointX), new BigInteger(
		basePointY)), // G
		new BigInteger(order), // n
		1); // h

	ECPrivateKeySpec priKeySpec = new ECPrivateKeySpec(new BigInteger(privateKey), // d
		spec);

	KeyAgreement ka = KeyAgreement.getInstance("ECDH", new BouncyCastleProvider());

	KeyFactory f = KeyFactory.getInstance("EC", new BouncyCastleProvider());
	// KeyFactory f = KeyFactory.getInstance("EC");
	PrivateKey sKey = f.generatePrivate(priKeySpec);
	// ECPrivateKeyImpl sKey = new ECPrivateKeyImpl(new
	// BigInteger(privateKey), spec);

	ka.init(sKey, spec);

	ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(new ECPoint(new BigInteger(publicKeyX), new BigInteger(
		publicKeyY)), // Q
		spec);

	ka.doPhase(f.generatePublic(pubKeySpec), true);

	System.out.println("Secret: ");
	System.out.println(Arrays.toString(ka.generateSecret("TlsPremasterSecret").getEncoded()));
    }

    @Test
    public void testRandomECDH() throws Exception {

	// y^2 = x^3 + ax + b mod q
	EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger("17")), // q
										     // (modulus)
		new BigInteger("2", 16), // a
		new BigInteger("2", 16)); // b

	ECParameterSpec spec = new ECParameterSpec(curve, new ECPoint(new BigInteger("5"), BigInteger.ONE), // G
		new BigInteger("19"), // n
		1); // h

	KeyPairGenerator g = KeyPairGenerator.getInstance("ECDH", new BouncyCastleProvider());

	g.initialize(spec, new SecureRandom());

	KeyPair aKeyPair = g.generateKeyPair();

	KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", new BouncyCastleProvider());

	aKeyAgree.init(aKeyPair.getPrivate());

	KeyPair bKeyPair = g.generateKeyPair();

	KeyAgreement bKeyAgree = KeyAgreement.getInstance("ECDH", new BouncyCastleProvider());

	bKeyAgree.init(bKeyPair.getPrivate());

	//
	// agreement
	//
	aKeyAgree.doPhase(bKeyPair.getPublic(), true);
	bKeyAgree.doPhase(aKeyPair.getPublic(), true);

	System.out.println(bKeyPair.getPublic());

	byte[] aSecret = aKeyAgree.generateSecret();
	byte[] bSecret = bKeyAgree.generateSecret();

	System.out.println(Arrays.toString(aSecret));
	System.out.println(Arrays.toString(bSecret));
    }

    @Test
    public void generatePoints() {

	// curve definition
	String a = "1";
	String b = "0";
	String q = "23"; // modulus in the elliptic curve

	// base point initialization
	String basePointX = "1";
	String basePointY = "5";
	String order = "23";

	// private key
	String privateKey = "6";

	// public key points
	String publicKeyX = "11";
	String publicKeyY = "1";

	// y^2 = x^3 + ax + b mod q
	EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(q)), // q
										  // (modulus)
		new BigInteger(a), // a
		new BigInteger(b)); // b

	ECParameterSpec spec = new ECParameterSpec(curve, new ECPoint(new BigInteger(basePointX), new BigInteger(
		basePointY)), // G
		new BigInteger(order), // n
		1); // h
    }
}
