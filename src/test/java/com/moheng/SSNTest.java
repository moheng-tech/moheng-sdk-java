package com.moheng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tech.moheng.chain.abi.datatypes.Address;
import tech.moheng.chain.abi.datatypes.Utf8String;
import tech.moheng.lib.SSN;

/**
 * Unit test for simple App.
 */
public class SSNTest {
	// private static String address =
	// "0x18fbfd8971f3fa8edced66939714663a0a637f5e";
	private static String address = "0xabd9e6c9951c9377171d2977d0b11383723926f1";
	private static String txHash = "0x76339c06fbbf158c646b44dca3de8a52127b913b8de476029eafcef141af2c16";

	public static void main(String[] args) {
		SSN ssn = new SSN("http://192.168.10.20:2012");
//		SSN ssn = new SSN("http://121.37.215.105:8646");

//		test_getSsnId(ssn);
//		test_getNonce(ssn);
//		test_getBalance(ssn);
//		test_getBlockNumber(ssn);
//		test_getBlock(ssn);
//		test_getBlockList(ssn);
//		test_getAppChainInfo(ssn);
//		test_getTransactionByHash(ssn);
//		test_getReceiptByHash(ssn);
//		test_getContractAddrList(ssn);
//		test_anyCall(ssn);
//		test_call(ssn);
//		test_sendRawTransactionTransfer(ssn);
//		test_sendRawTransaction(ssn);
		test_getSignedTx(ssn);
	}

	public static void test_getSsnId(SSN ssn) {
		System.out.println("1-------getSsnId-------------");
		String ssnId = ssn.getSsnId();
		System.out.println(ssnId);
		System.out.println("-------getSsnId-------------");
		System.out.println();
	}

	public static void test_getNonce(SSN ssn) {
		System.out.println("2-------getNonce-------------");
		int nonce = ssn.getNonce(address);
		System.out.println(nonce);
		System.out.println("-------getNonce-------------");
		System.out.println();
	}

	public static void test_getBalance(SSN ssn) {
		System.out.println("3-------getBalance-------------");
		BigDecimal balance = ssn.getBalance(address);
		System.out.println(balance);
		System.out.println("-------getBalance-------------");
		System.out.println();
	}

	public static void test_getBlockNumber(SSN ssn) {
		System.out.println("4-------mh_getBlockNumber-------------");
		BigDecimal blockNumber = ssn.getBlockNumber();
		System.out.println(blockNumber);
		System.out.println("-------mh_getBlockNumber-------------");
		System.out.println();
	}

	public static void test_getBlock(SSN ssn) {
		System.out.println("5-------mh_getBlock-------------");
		JSONObject blockInfo = ssn.getBlock(10);
		System.out.println(blockInfo);
		System.out.println("-------mh_getBlock-------------");
		System.out.println();
	}

	public static void test_getBlockList(SSN ssn) {
		System.out.println("6-------mh_getBlockList-------------");
		JSONObject blockList = ssn.getBlockList(10, 12);
		System.out.println(blockList);
		System.out.println("-------mh_getBlockList-------------");
		System.out.println();
	}

	public static void test_getAppChainInfo(SSN ssn) {
		System.out.println("7-------mh_getAppChainInfo-------------");
		JSONObject appChainInfo = ssn.getAppChainInfo();
		System.out.println(appChainInfo);
		System.out.println("-------mh_getAppChainInfo-------------");
		System.out.println();
	}

	public static void test_getTransactionByHash(SSN ssn) {
		System.out.println("8-------mh_getTransactionByHash-------------");
		JSONObject transactionInfo = ssn.getTransactionByHash(txHash);
		System.out.println(transactionInfo);
		System.out.println("-------mh_getTransactionByHash-------------");
		System.out.println();
	}

	public static void test_getReceiptByHash(SSN ssn) {
		System.out.println("9-------mh_getReceiptByHash-------------");
		JSONObject receiptInfo = ssn.getReceiptByHash(txHash);
		System.out.println(receiptInfo);
		System.out.println("-------mh_getReceiptByHash-------------");
		System.out.println();
	}

	public static void test_getContractAddrList(SSN ssn) {
		System.out.println("10-------mh_getContractAddrList-------------");
		JSONArray dappList = ssn.getContractAddrList();
		System.out.println("total:" + dappList.size());
		System.out.println(dappList);
		System.out.println("-------mh_getContractAddrList-------------");
		System.out.println();
	}

	public static void test_anyCall(SSN ssn) {
		System.out.println("11-------mh_anyCall-------------");
		JSONArray arry = new JSONArray();
		arry.add("balanceOf");
		arry.add("0x708678c49fe4e99fb8ccb487b1f667800ee2ecc8");
		JSONObject anyCallInfo = ssn.anyCall("0xf137f9efe9b6707ea218d1ea9a1697e54b75c82c", arry);
		System.out.println(anyCallInfo);
		System.out.println("-------mh_anyCall-------------");
		System.out.println();
	}

	public static void test_call(SSN ssn) {
		System.out.println("12-------mh_call-------------");
		List list = new ArrayList();
		list.add(new Address("0x708678c49fe4e99fb8ccb487b1f667800ee2ecc8"));
		JSONObject callInfo = ssn.call("0xf137f9efe9b6707ea218d1ea9a1697e54b75c82c", "balanceOf", list);
		System.out.println(callInfo);
		System.out.println("-------mh_call-------------");
		System.out.println();
	}

	public static void test_sendRawTransactionTransfer(SSN ssn) {
		System.out.println("13-------mh_sendRawTransaction---sendRawTransactionPrivate-------------");
		String tradeHash = ssn.sendRawTransactionTransfer("0xabd9e6c9951c9377171d2977d0b11383723926f1",
				"0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0", 1, "test",
				"0x8be2254b13b0f00036f68832b561a679f1b5171638b6e9d5e8656baa2d1ceddc");
		System.out.println(tradeHash);
		System.out.println("-------mh_sendRawTransaction---sendRawTransactionPrivate-------------");
		System.out.println();
	}
	
	public static void test_sendRawTransaction(SSN ssn) {
		System.out.println("14-------mh_sendRawTransaction---sendRawTransaction-------------");
		
		String abi = "[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_from\",\"type\":\"address\"},{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"INITIAL_SUPPLY\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"},{\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"name\":\"remaining\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"}]";
		List list = new ArrayList();
		list.add(new Address("0xd92bbb39d372e2b752c6a0c9ea3351cd3c2a3e77"));
		list.add(new Address("0xabd9e6c9951c9377171d2977d0b11383723926f1"));
		list.add(new Utf8String(abi));
		
		String tradeHash = ssn.sendRawTransaction("0xabd9e6c9951c9377171d2977d0b11383723926f1",
				"0xa8195da48ddf8919e7ccf4e4417a57da8d95469d", 1, "registerDapp",list,
				"0x8be2254b13b0f00036f68832b561a679f1b5171638b6e9d5e8656baa2d1ceddc");
		System.out.println(tradeHash);
		System.out.println("-------mh_sendRawTransaction---sendRawTransaction-------------");
		System.out.println();
	}
	
	public static void test_getSignedTx(SSN ssn) {
		System.out.println("14-------mh_getSignedTx-------------");
		
		String abi = "[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_from\",\"type\":\"address\"},{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"INITIAL_SUPPLY\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\"},{\"name\":\"_spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"name\":\"remaining\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_spender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"}]";
		List list = new ArrayList();
		list.add(new Address("0xd92bbb39d372e2b752c6a0c9ea3351cd3c2a3e77"));
		list.add(new Address("0xabd9e6c9951c9377171d2977d0b11383723926f1"));
		list.add(new Utf8String(abi));
		
		String signedTx = ssn.getSignedTx("0xabd9e6c9951c9377171d2977d0b11383723926f1",
				"0xa8195da48ddf8919e7ccf4e4417a57da8d95469d", 1, "registerDapp",list,
				"0x8be2254b13b0f00036f68832b561a679f1b5171638b6e9d5e8656baa2d1ceddc");
		System.out.println(signedTx);
		System.out.println("-------mh_getSignedTx-------------");
		System.out.println();
	}

}
