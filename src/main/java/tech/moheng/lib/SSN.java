package tech.moheng.lib;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tech.moheng.chain.crypto.Credentials;
import tech.moheng.chain.crypto.RawTransaction;
import tech.moheng.chain.crypto.TransactionEncoder;
import tech.moheng.chain.utils.utils.Convert;
import tech.moheng.common.HttpClient;
import tech.moheng.common.MhUtil;

/**
 * 墨珩联盟链SSN
 * 
 * @author yyl
 * @date 2021年2月8日
 */
public class SSN {
	private String ssnAddress;
	private int chainId;
	private static ResourceBundle bundle = ResourceBundle.getBundle("application");
	private String isSm;

	public SSN(String ssnAddress){
		this.ssnAddress = ssnAddress;
		this.chainId = Integer.valueOf(bundle.getString("chainId"));
	}
	
	public SSN(String ssnAddress,int chainId){
		this.ssnAddress = ssnAddress;
		this.chainId = chainId;
	}
	
	public SSN(String ssnAddress,String isSm){
		this.ssnAddress = ssnAddress;
		this.chainId = Integer.valueOf(bundle.getString("chainId"));
		this.isSm = isSm;
	}
	
	public SSN(String ssnAddress,String isSm,int chainId){
		this.ssnAddress = ssnAddress;
		this.chainId = chainId;
		this.isSm = isSm;
	}

	/**
	 * 获取ssnId
	 * 
	 * @return
	 */
	public String getSsnId() {
		String result = HttpClient.post(ssnAddress, "mh_getSSNId", null);
		return result;
	}

	/**
	 * 获取用户nonce
	 * 
	 * @param address
	 * @return
	 */
	public int getNonce(String address) {
		JSONArray params = new JSONArray();
		params.add(address);
		String result = HttpClient.post(ssnAddress, "mh_getNonce", params);
		return Integer.parseInt(result);
	}

	/**
	 * 获取ssn余额
	 * 
	 * @param address
	 * @return
	 */
	public BigDecimal getBalance(String address) {
		JSONArray params = new JSONArray();
		params.add(address);
		String result = HttpClient.post(ssnAddress, "mh_getBalance", params);
		System.out.println(result);
		BigDecimal bigBalance = Convert.hexToNumber(result);
		BigDecimal balance = Convert.fromSha(bigBalance, Convert.Unit.MC);
		return balance;
	}

	/**
	 * 获取ssn最新区块高度
	 * 
	 * @return
	 */
	public BigDecimal getBlockNumber() {
		String result = HttpClient.post(ssnAddress, "mh_getBlockNumber", null);
		BigDecimal blockNumber = new BigDecimal(new BigInteger(result.substring(2), 16));
		return blockNumber;
	}

	/**
	 * 获取ssn下一次停止区块高度
	 * 
	 * @return
	 */
	public BigDecimal getBlockThreshold() {
		String result = HttpClient.post(ssnAddress, "mh_getBlockNumber", null);
		BigDecimal blockNumber = new BigDecimal(new BigInteger(result.substring(2), 16));
		return blockNumber;
	}

	/**
	 * 获取某一区块信息
	 * 
	 * @return
	 */
	public JSONObject getBlock(int number) {
		JSONArray params = new JSONArray();
		params.add(Convert.intToHex(number));
		String result = HttpClient.post(ssnAddress, "mh_getBlock", params);
		JSONObject blockInfo = JSONObject.parseObject(result);
		return blockInfo;
	}

	/**
	 * 获取某一区间内的多个区块信息
	 * 
	 * @param start 开始区块号
	 * @param end 结束区块号
	 * @return
	 */
	public JSONObject getBlockList(int start, int end) {
		JSONArray params = new JSONArray();
		params.add(Convert.intToHex(start));
		params.add(Convert.intToHex(end));
		String result = HttpClient.post(ssnAddress, "mh_getBlockList", params);
		JSONObject blockList = JSONObject.parseObject(result);
		return blockList;
	}

	/**
	 * 获取SSN详细信息
	 * 
	 * @return
	 */
	public JSONObject getAppChainInfo() {
		String result = HttpClient.post(ssnAddress, "mh_getAppChainInfo", null);
		JSONObject appChainInfo = JSONObject.parseObject(result);
		return appChainInfo;
	}

	/**
	 * 根据TxHash获取交易信息
	 * 
	 * @param hash 交易hash
	 * @return
	 */
	public JSONObject getTransactionByHash(String hash) {
		JSONArray params = new JSONArray();
		params.add(hash);
		String result = HttpClient.post(ssnAddress, "mh_getTransactionByHash", params);
		JSONObject transactionInfo = JSONObject.parseObject(result);
		return transactionInfo;
	}

	/**
	 * 根据TxHash获取receipt信息
	 * 
	 * @param hash 交易hash
	 * @return
	 */
	public JSONObject getReceiptByHash(String hash) {
		JSONArray params = new JSONArray();
		params.add(hash);
		String result = HttpClient.post(ssnAddress, "mh_getReceiptByHash", params);
		JSONObject receiptInfo = JSONObject.parseObject(result);
		return receiptInfo;
	}

	/**
	 * 获SSN已注册合约列表
	 * 
	 * @return
	 */
	public JSONArray getContractAddrList() {
		String result = HttpClient.post(ssnAddress, "mh_getContractAddrList", null);
		JSONArray dappList = JSONArray.parseArray(result);
		return dappList;
	}

	/**
	 * 调用子链dapp合约public方法-异步 contractAddress: dapp合约地址 method:
	 * 例如合约中存在一个无参的方法getDechatInfo，则传入["getDechatInfo"];
	 * 存在一个有参的方法getTopicList(uint pageNum, uint pageSize), 则传入["getTopicList",
	 * "0", "20"] (无论合约数据类型是什么，传入全为字符串格式)
	 */
	public JSONObject anyCall(String contractAddress, JSONArray method) {
		JSONObject param = new JSONObject();
		param.put("Sender", "085baafb872a9eb63403a00ad89beceb796abc9a");
		param.put("DappAddr", contractAddress);
		param.put("Params", method);

		JSONArray params = new JSONArray();
		params.add(param);

		String result = HttpClient.post(ssnAddress, "mh_anyCall", params);
		JSONObject anyCallInfo = JSONObject.parseObject(result);
		return anyCallInfo;
	}

	/**
	 * 调用子链dapp合约public方法
	 * 
	 * @param contractAddress dapp合约地址
	 * @param funcName 合约方法名 例 "issue"
	 * @param list 合约方法及参数 列表 例 List list = new ArrayList(); list.add(new Address("0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0"));
	 * @return
	 */
	public JSONObject call(String contractAddress, String funcName, List list) {
		JSONObject param = new JSONObject();
		param.put("From", "085baafb872a9eb63403a00ad89beceb796abc9a");
		param.put("DappAddr", contractAddress);
		param.put("FuncName", funcName);
		String dataStr = MhUtil.getData(funcName, list);
		param.put("Data", dataStr);

		JSONArray params = new JSONArray();
		params.add(param);

		String result = HttpClient.post(ssnAddress, "mh_call", params);
		JSONObject anyCallInfo = JSONObject.parseObject(result);
		return anyCallInfo;
	}

	/**
	 * 加签交易(转账、信息上链)
	 * 
	 * @param from 发送方钱包地址
	 * @param to 接收方钱包地址
	 * @param amount 交易金额
	 * @param strData 上链信息
	 * @param privateKey 发送方钱包私钥
	 * @return
	 * @throws IOException 
	 */
	public String sendRawTransactionTransfer (String fromAddress, String toAddress, double amount, String strData, String privateKey) throws IOException{
		int resultNonce = getNonce(fromAddress);
		BigInteger nonce = BigInteger.valueOf(resultNonce);
		BigInteger gas = new BigInteger("0");
		BigInteger gasPrice = new BigInteger("0");
		BigInteger gasLimit = new BigInteger("0");
		
		long amountLong = Convert.toSha(String.valueOf(amount), Convert.Unit.MC).longValue();
		BigInteger value = BigInteger.valueOf(amountLong);
		
		BigInteger shardingFlag = new BigInteger("2");
		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gas, gasPrice, gasLimit, toAddress,
				value, strData, shardingFlag);
		net.sf.json.JSONObject rawTx = net.sf.json.JSONObject.fromObject(rawTransaction);
		System.out.println("rawTx:" + rawTx.toString());
		
		String signedTx = "";
		if("sm".equals(isSm)){
			signedTx = TransactionEncoder.signMessageSM(rawTransaction, chainId, Credentials.createSM(privateKey));
		}else{
			signedTx = TransactionEncoder.signMessage(rawTransaction, chainId, Credentials.create(privateKey));
		}

		// 发送交易
		String result = sendSignTransaction(signedTx);
		return result;
	}

	/**
	 * 加签交易(转账、信息上链)
	 * nonce自传
	 * 
	 * @param from 发送方钱包地址
	 * @param to 接收方钱包地址
	 * @param amount 交易金额
	 * @param strData 上链信息
	 * @param privateKey 发送方钱包私钥
	 * @param nonce 发送方钱包交易nonce,可根据方法getNonce(address)得到
	 * @return
	 * @throws IOException 
	 */
	public String sendRawTransactionTransfer(String fromAddress, String toAddress, double amount, String strData, String privateKey, int nonce) throws IOException {
		BigInteger noncePaream = BigInteger.valueOf(nonce);
		BigInteger gas = new BigInteger("0");
		BigInteger gasPrice = new BigInteger("0");
		BigInteger gasLimit = new BigInteger("0");

		long amountLong = Convert.toSha(String.valueOf(amount), Convert.Unit.MC).longValue();
		BigInteger value = BigInteger.valueOf(amountLong);
		
		BigInteger shardingFlag = new BigInteger("2");
		RawTransaction rawTransaction = RawTransaction.createTransaction(noncePaream, gas, gasPrice, gasLimit,
				toAddress, value, strData, shardingFlag);
		net.sf.json.JSONObject rawTx = net.sf.json.JSONObject.fromObject(rawTransaction);
		System.out.println("rawTx:" + rawTx.toString());

		String signedTx = "";
		if("sm".equals(isSm)){
			signedTx = TransactionEncoder.signMessageSM(rawTransaction, chainId, Credentials.createSM(privateKey));
		}else{
			signedTx = TransactionEncoder.signMessage(rawTransaction, chainId, Credentials.create(privateKey));
		}
		
		// 发送交易
		String result = sendSignTransaction(signedTx);
		return result;
	}

	/**
	 * 发送子链加签交易(只用于调用需要发送交易的子链合约方法)
	 * 
	 * @param fromAddress 发送方钱包地址
	 * @param contractAddress 合约钱包地址
	 * @param amount 交易金额
	 * @param funcName 合约方法名 例 "registerDapp"
	 * @param list 合约方法及参数 列表 例  List list = new ArrayList(); list.add(new Address("0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0"));
	 * 合约方法参数类型对应JAVA类 参照 com.moheng.chain.abi.datatypes 包下
	 * @param privateKey 发送方钱包私钥
	 * @return
	 * @throws IOException 
	 */
	public String sendRawTransaction(String fromAddress, String contractAddress, double amount, String funcName, List list, String privateKey) throws IOException {
		int nonce = getNonce(fromAddress);
		BigInteger noncePaream = BigInteger.valueOf(nonce);

		String strData = MhUtil.getData(funcName, list);

		BigInteger gas = new BigInteger("0");
		BigInteger gasPrice = new BigInteger("0");
		BigInteger gasLimit = new BigInteger("0");

		long amountLong = Convert.toSha(String.valueOf(amount), Convert.Unit.MC).longValue();
		BigInteger value = BigInteger.valueOf(amountLong);
		
		BigInteger shardingFlag = new BigInteger("1");

		RawTransaction rawTransaction = RawTransaction.createTransaction(noncePaream, gas, gasPrice, gasLimit,
				contractAddress, value, strData, shardingFlag);
		net.sf.json.JSONObject rawTx = net.sf.json.JSONObject.fromObject(rawTransaction);
		System.out.println("rawTx:" + rawTx.toString());

		String signedTx = "";
		if("sm".equals(isSm)){
			signedTx = TransactionEncoder.signMessageSM(rawTransaction, chainId, Credentials.createSM(privateKey));
		}else{
			signedTx = TransactionEncoder.signMessage(rawTransaction, chainId, Credentials.create(privateKey));
		}
		
		// 发送交易
		String result = sendSignTransaction(signedTx);
		return result;
	}
	
	/**
	 * 发送子链加签交易(只用于调用需要发送交易的子链合约方法)
	 * nonce 自传
	 * 
	 * @param fromAddress 发送方钱包地址
	 * @param contractAddress 合约钱包地址
	 * @param amount 交易金额
	 * @param funcName 合约方法名 例 "registerDapp"
	 * @param list 合约方法及参数 列表 例  List list = new ArrayList(); list.add(new Address("0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0"));
	 * 合约方法参数类型对应JAVA类 参照 com.moheng.chain.abi.datatypes 包下
	 * @param privateKey 发送方钱包私钥
	 * @param nonce 发送方钱包交易nonce,可根据方法getNonce(address)得到
	 * @return
	 * @throws IOException 
	 */
	public String sendRawTransaction(String fromAddress, String contractAddress, double amount, String funcName, List list, String privateKey,int nonce) throws IOException {
		BigInteger noncePaream = BigInteger.valueOf(nonce);

		String strData = MhUtil.getData(funcName, list);

		BigInteger gas = new BigInteger("0");
		BigInteger gasPrice = new BigInteger("0");
		BigInteger gasLimit = new BigInteger("0");

		long amountLong = Convert.toSha(String.valueOf(amount), Convert.Unit.MC).longValue();
		BigInteger value = BigInteger.valueOf(amountLong);
		
		BigInteger shardingFlag = new BigInteger("1");

		RawTransaction rawTransaction = RawTransaction.createTransaction(noncePaream, gas, gasPrice, gasLimit,
				contractAddress, value, strData, shardingFlag);
		net.sf.json.JSONObject rawTx = net.sf.json.JSONObject.fromObject(rawTransaction);
		System.out.println("rawTx:" + rawTx.toString());

		String signedTx = "";
		if("sm".equals(isSm)){
			signedTx = TransactionEncoder.signMessageSM(rawTransaction, chainId, Credentials.createSM(privateKey));
		}else{
			signedTx = TransactionEncoder.signMessage(rawTransaction, chainId, Credentials.create(privateKey));
		}
		
		// 发送交易
		String result = sendSignTransaction(signedTx);
		return result;
	}

	/**
	 * 发送加签交易
	 * 
	 * @param signTx
	 * @return
	 */
	public String sendSignTransaction(String signTx) {
		JSONArray params = new JSONArray();
		params.add(signTx);
		String result = HttpClient.post(ssnAddress, "mh_sendRawTransaction", params);
		return result;
	}
	
	/**
	 * 获取加签后的tx
	 * 
	 * @param fromAddress 发起人钱包地址
	 * @param contractAddress 合约地址
	 * @param amount 交易金额
	 * @param funcName 合约方法名 例 "registerDapp"
	 * @param list 合约方法及参数 列表 例  List list = new ArrayList(); list.add(new Address("0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0"));
	 * 合约方法参数类型对应JAVA类 参照 com.moheng.chain.abi.datatypes 包下
	 * @param privateKey 发送方钱包私钥
	 * @return
	 * @throws IOException 
	 */
	public String getSignedTx(String fromAddress, String contractAddress, double amount, String funcName, List list, String privateKey) throws IOException{
		int nonce = getNonce(fromAddress);
		BigInteger noncePaream = BigInteger.valueOf(nonce);

		String strData = MhUtil.getData(funcName, list);

		BigInteger gas = new BigInteger("0");
		BigInteger gasPrice = new BigInteger("0");
		BigInteger gasLimit = new BigInteger("0");

		long amountLong = Convert.toSha(String.valueOf(amount), Convert.Unit.MC).longValue();
		BigInteger value = BigInteger.valueOf(amountLong);
		
		BigInteger shardingFlag = new BigInteger("1");

		RawTransaction rawTransaction = RawTransaction.createTransaction(noncePaream, gas, gasPrice, gasLimit,
				contractAddress, value, strData, shardingFlag);
		net.sf.json.JSONObject rawTx = net.sf.json.JSONObject.fromObject(rawTransaction);
		System.out.println("rawTx:" + rawTx.toString());

		String signedTx = "";
		if("sm".equals(isSm)){
			signedTx = TransactionEncoder.signMessageSM(rawTransaction, chainId, Credentials.createSM(privateKey));
		}else{
			signedTx = TransactionEncoder.signMessage(rawTransaction, chainId, Credentials.create(privateKey));
		}
		
		return signedTx;
	}

}
