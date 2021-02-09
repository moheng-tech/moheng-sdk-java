package tech.moheng.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.alibaba.fastjson.JSONObject;

import tech.moheng.chain.crypto.CipherException;
import tech.moheng.lib.Account;

public class AccountTest {

	 public static void main(String[] args) throws URISyntaxException, CipherException, IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		
		 Account account = new Account();
		 
		 //注册
//		 test_register(account);
		 
		 //登录
//		 test_login(account);
		 
	}
	 
	 public static void test_register(Account account) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException{
		 System.out.println("------------register-----------------");
		 String password = "123";
		 JSONObject accountRes = account.register(password);
		 System.out.println(accountRes);
		 System.out.println("------------register-----------------");
	 }
	 
	 public static void test_login(Account account) throws CipherException, IOException{
		 System.out.println("------------login-----------------");
		//登录
		 String keyStore = "{\"address\":\"708678c49fe4e99fb8ccb487b1f667800ee2ecc8\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"868b5ddcde564bd636f5d1855cf6638598decdbae364b1d5d33aa3c47149f749\",\"cipherparams\":{\"iv\":\"d84c05f72cddea7fca4d41d600fbf0f5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"4dcfd8a6db510fe2ab5f99c60a8e875d39b9eaf4f4047ab81da2be72eb5ff232\"},\"mac\":\"109dfda2a0c02923b3e016241b096422bf0268fe32b1bff50c47c8683d60bd47\"},\"id\":\"407f641a-35c7-41fe-8063-c40f0b9f8a7f\",\"version\":3}";
		 String password = "ssndefaultpwd";
		 String privateKey = account.login(password, keyStore);
		 System.out.println(privateKey);
		 System.out.println("------------login-----------------");
	 }
}
