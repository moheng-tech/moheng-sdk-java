package tech.moheng.chain.crypto;

import java.util.ArrayList;
import java.util.List;

import tech.moheng.chain.rlp.RlpEncoder;
import tech.moheng.chain.rlp.RlpList;
import tech.moheng.chain.rlp.RlpString;
import tech.moheng.chain.rlp.RlpType;
import tech.moheng.chain.utils.utils.Bytes;
import tech.moheng.chain.utils.utils.Numeric;

public class TransactionEncoder {

	public static String signMessage(RawTransaction rawTransaction, int chainId, Credentials credentials) {
		byte[] encodedTransaction = encode(rawTransaction, chainId);
		Sign.SignatureData signatureData = Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

		Sign.SignatureData eip155SignatureData = createEip155SignatureData(signatureData, chainId);
		byte[] message = encode(rawTransaction, eip155SignatureData);
		String signedMsg = Numeric.toHexString(message);
		return signedMsg;
	}

	public static Sign.SignatureData createEip155SignatureData(Sign.SignatureData signatureData, int chainId) {
		int v = signatureData.getV() + (chainId * 2) + 8;

		return new Sign.SignatureData(v, signatureData.getR(), signatureData.getS());
	}

	public static byte[] encode(RawTransaction rawTransaction) {
		return encode(rawTransaction, null);
	}

	public static byte[] encode(RawTransaction rawTransaction, int chainId) {
		Sign.SignatureData signatureData = new Sign.SignatureData(chainId, new byte[] {}, new byte[] {});
		return encode(rawTransaction, signatureData);
	}

	private static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
		List<RlpType> values = asRlpValues(rawTransaction, signatureData);
		RlpList rlpList = new RlpList(values);
		return RlpEncoder.encode(rlpList);
	}

	static List<RlpType> asRlpValues(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
		List<RlpType> result = new ArrayList<>();

		result.add(RlpString.create(rawTransaction.getNonce()));
		result.add(RlpString.create(rawTransaction.getSystemContract()));
		result.add(RlpString.create(rawTransaction.getGasPrice()));
		result.add(RlpString.create(rawTransaction.getGasLimit()));

		// an empty to address (contract creation) should not be encoded as a
		// numeric 0 value
		String to = rawTransaction.getTo();
		if (to != null && to.length() > 0) {
			// addresses that start with zeros should be encoded with the zeros
			// included, not
			// as numeric values
			result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
		} else {
			result.add(RlpString.create(""));
		}

		result.add(RlpString.create(rawTransaction.getValue()));

		// value field will already be hex encoded, so we need to convert into
		// binary first
		byte[] data = Numeric.hexStringToByteArray(rawTransaction.getData());
		result.add(RlpString.create(data));

		result.add(RlpString.create(rawTransaction.getShardingFlag()));

		String viaAddress = rawTransaction.getVia();
		if (viaAddress != null && viaAddress.length() > 0) {
			// addresses that start with zeros should be encoded with the zeros
			// included, not
			// as numeric values
			result.add(RlpString.create(Numeric.hexStringToByteArray(viaAddress)));
		} else {
			result.add(RlpString.create(""));
		}

		if (signatureData != null) {
			result.add(RlpString.create(signatureData.getV()));
			result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR())));
			result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS())));
		}
		return result;
	}
}
