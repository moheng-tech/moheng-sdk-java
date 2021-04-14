package tech.moheng.test;

import java.io.IOException;

import org.bouncycastle.math.ec.ECPoint;

import tech.moheng.chain.crypto.Credentials;
import tech.moheng.chain.crypto.ECKeyPair;
import tech.moheng.chain.crypto.Keys;
import tech.moheng.chain.crypto.Sign;
import tech.moheng.chain.utils.utils.Bytes;
import tech.moheng.chain.utils.utils.Numeric;

public class sss {
	public static void main(String[] args) throws IOException {
		System.out.println(64<<1);
		String privateKey = "0xdb2df9bae17e9e6dd9828254522ccc33ee6765d40f20ba8c3404f8633d487098";
		ECKeyPair ecKeyPair = Credentials.createSM(privateKey).getEcKeyPair();
//		ECPoint publicKey = Sign.publicPointFromPrivate(Numeric.toBigInt(privateKey));
//		String publicKeyStr = Bytes.byteToHex(publicKey.getEncoded(false));
		String publicKeyStr = Keys.getPublicKeyStrSM(ecKeyPair);
		System.out.println(publicKeyStr);
	}

}
