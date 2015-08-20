package de.rub.nds.eccjava.realtest;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import javax.crypto.KeyAgreement;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @version 0.1
 */
public class ECTestJDK {

    @Test
    public void testGeneration() throws Exception {
	KeyPairGenerator kpg;
	kpg = KeyPairGenerator.getInstance("EC");
	System.out.println(kpg.getProvider());
	ECGenParameterSpec ecsp;
	ecsp = new ECGenParameterSpec("secp256r1");
	kpg.initialize(ecsp);

	KeyPair kp = kpg.genKeyPair();
	PrivateKey privKey = kp.getPrivate();
	PublicKey pubKey = kp.getPublic();

	System.out.println(privKey.toString());
	System.out.println(pubKey.toString());
    }

    @Test
    public void testEnc() throws Exception {

	KeyPairGenerator kpg;
	kpg = KeyPairGenerator.getInstance("EC");
	ECGenParameterSpec ecsp;

	ecsp = new ECGenParameterSpec("secp256r1");
	kpg.initialize(ecsp);

	KeyPair kpU = kpg.genKeyPair();

	PrivateKey privKeyU = kpU.getPrivate();
	PublicKey pubKeyU = kpU.getPublic();
	System.out.println("User U: " + privKeyU.toString());
	System.out.println("User U: " + pubKeyU.toString());
	KeyPair kpV = kpg.genKeyPair();
	PrivateKey privKeyV = kpV.getPrivate();
	PublicKey pubKeyV = kpV.getPublic();
	System.out.println("User V: " + privKeyV.toString());
	System.out.println("User V: " + pubKeyV.toString());
	KeyAgreement ecdhU = KeyAgreement.getInstance("ECDH", new BouncyCastleProvider());
	ecdhU.init(privKeyU);
	ecdhU.doPhase(pubKeyV, true);
	KeyAgreement ecdhV = KeyAgreement.getInstance("ECDH");
	ecdhV.init(privKeyV);
	ecdhV.doPhase(pubKeyU, true);
	System.out.println("Secret computed by U: 0x"
		+ (new BigInteger(1, ecdhU.generateSecret("TlsPremasterSecret").getEncoded()).toString(16))
			.toUpperCase());
	System.out.println("Secret computed by V: 0x"
		+ (new BigInteger(1, ecdhV.generateSecret()).toString(16)).toUpperCase());
    }
}
