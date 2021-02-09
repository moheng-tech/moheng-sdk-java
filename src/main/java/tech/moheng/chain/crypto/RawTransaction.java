package tech.moheng.chain.crypto;

import java.math.BigInteger;

import tech.moheng.chain.utils.utils.Numeric;


public class RawTransaction {

	private BigInteger nonce;
	private BigInteger gas;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	private String to;
	private BigInteger value;
	private String data;
	private BigInteger shardingFlag;
	private String via = "";
	private BigInteger systemContract = new BigInteger("0");
	

	protected RawTransaction(BigInteger nonce, BigInteger gas, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value,
			String data, BigInteger shardingFlag) {
		this.nonce = nonce;
		this.gas = gas;
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
		this.to = to;
		this.value = value;

		if (data != null) {
			this.data = Numeric.cleanHexPrefix(data);
		}
		
		this.shardingFlag = shardingFlag;
	}

	public static RawTransaction createTransaction(BigInteger nonce, BigInteger gas, BigInteger gasPrice, BigInteger gasLimit,
			String to, BigInteger value, String data, BigInteger shardingFlag) {

		return new RawTransaction(nonce, gas, gasPrice, gasLimit, to, value, data, shardingFlag);
	}

	public BigInteger getNonce() {
		return nonce;
	}

	public BigInteger getGas() {
		return gas;
	}

	public BigInteger getGasPrice() {
		return gasPrice;
	}

	public BigInteger getGasLimit() {
		return gasLimit;
	}

	public String getTo() {
		return to;
	}

	public BigInteger getValue() {
		return value;
	}

	public String getData() {
		return data;
	}

	public BigInteger getShardingFlag() {
		return shardingFlag;
	}

	public String getVia() {
		return via;
	}

	public BigInteger getSystemContract() {
		return systemContract;
	}

}
