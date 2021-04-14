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
		 
		//注册国密SM2
//		 test_registerSM(account);
		 
		 //登录
//		 test_login(account);
		 
		 //登录国密
		 test_loginSM(account);
		 
	}
	 
	 public static void test_register(Account account) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException{
		 System.out.println("------------register-----------------");
		 String password = "123";
		 JSONObject accountRes = account.register(password);
		 System.out.println(accountRes);
		 System.out.println("------------register-----------------");
	 }
	 
	 public static void test_registerSM(Account account) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException{
		 System.out.println("------------register-----------------");
		 String password = "123";
		 JSONObject accountRes = account.registerSM(password);
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
	 
	 public static void test_loginSM(Account account) throws CipherException, IOException{
		 System.out.println("------------login-----------------");
		//登录国密
		 String keyStore = "{\"address\":\"7d531f6fe6dae9c36dd6d925bf7d3354796d086e\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"47ffff9e94f7fdaae66a98cabee4a42a\"},\"ciphertext\":\"293bee25f7575d2fefe9d725cfac072acbfb0c168d9d1e9d9c24b2f019e0a7a3\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"8ae304b35bd267d8ebd1b175bfd336e6bbb820e7939ff5f74b29206c45467e09\"},\"mac\":\"c7d63a85952823355b0713505618e323e7e6f520a23e9504d3d70febaa878688\"},\"id\":\"e5e53f64-2f08-4ab8-a727-e7c031a7c1ee\",\"version\":3}";
		 String password = "123";
		 String privateKey = account.loginSM(password, keyStore);
		 System.out.println(privateKey);
		 System.out.println("------------login-----------------");
	 }
}
