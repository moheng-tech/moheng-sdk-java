package tech.moheng.lib;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.moheng.chain.crypto.CipherException;
import tech.moheng.chain.crypto.Credentials;
import tech.moheng.chain.crypto.ECKeyPair;
import tech.moheng.chain.crypto.Keys;
import tech.moheng.chain.crypto.Wallet;
import tech.moheng.chain.crypto.WalletFile;

/**
 * 账户类
 * 
 * @author yyl
 * @date 2021年1月26日
 */
public class Account {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 注册
	 * 
	 * @param password
	 * @return address privateKey keyStory
	 * @throws CipherException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 */
	public JSONObject register(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException{
		ECKeyPair ecKeyPair = Keys.createEcKeyPair();
		WalletFile walletFile = Wallet.createLight(password, ecKeyPair);
		net.sf.json.JSONObject keyStore = net.sf.json.JSONObject.fromObject(walletFile);

		JSONObject newWallet = new JSONObject();
		newWallet.put("address", "0x" + walletFile.getAddress());
		newWallet.put("privateKey", "0x" + ecKeyPair.getPrivateKey().toString(16));
		newWallet.put("keyStore", keyStore);
		return newWallet;
	}
	
	/**
	 * 注册国密
	 * 
	 * @param password
	 * @return address privateKey keyStory
	 * @throws CipherException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException 
	 */
	public JSONObject registerSM(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException{
		ECKeyPair ecKeyPair = Keys.createEcSM2KeyPair();
		WalletFile walletFile = Wallet.createLightSM(password, ecKeyPair);
		net.sf.json.JSONObject keyStore = net.sf.json.JSONObject.fromObject(walletFile);

		JSONObject newWallet = new JSONObject();
		newWallet.put("address", "0x" + walletFile.getAddress());
		newWallet.put("privateKey", "0x" + ecKeyPair.getPrivateKey().toString(16));
		newWallet.put("keyStore", keyStore);
		return newWallet;
	}

	/**
	 * 登录 根据keystore和密码获取私钥
	 * 
	 * @param password
	 * @param keyStore
	 * @return
	 * @throws CipherException
	 * @throws IOException
	 */
	public String login(String password, String keyStore) throws CipherException, IOException {
		WalletFile walletFile = objectMapper.readValue(keyStore, WalletFile.class);
		Credentials credentials = Credentials.create(Wallet.decrypt(password, walletFile));
		ECKeyPair ecKeyPair = credentials.getEcKeyPair();
		String privateKey = "0x" + ecKeyPair.getPrivateKey().toString(16);
		return privateKey;

	}
	
	/**
	 * 登录国密 根据keystore和密码获取私钥
	 * 
	 * @param password
	 * @param keyStore
	 * @return
	 * @throws CipherException
	 * @throws IOException
	 */
	public String loginSM(String password, String keyStore) throws CipherException, IOException {
		WalletFile walletFile = objectMapper.readValue(keyStore, WalletFile.class);
		Credentials credentials = Credentials.create(Wallet.decryptSM(password, walletFile));
		ECKeyPair ecKeyPair = credentials.getEcKeyPair();
		System.out.println("public:"+ecKeyPair.getPublicKey().toString(16));
		String privateKey = "0x" + ecKeyPair.getPrivateKey().toString(16);
		return privateKey;

	}
}
