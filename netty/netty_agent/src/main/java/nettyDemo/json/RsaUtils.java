package nettyDemo.json;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RsaUtils {
	// 用于加密解密的rsa 模数
	static String n = "677066485833933273683257014062869884601540923033506850039" +
			"8705436463980338380888897716686672677061997194130007965258693347662" +
			"961755646804182634515596584609";
	// 公钥
	static String e = "65537";
	// 加密解密的私钥
	static String d = "604046574610132361551281785178478405828272521306225674275" +
			"3130417391892612013391772133558610312330302882133855766713285511868" +
			"129079320760494366675413820865";

	//签名认证的rsa模数
	static String sign_n = "9912437676341948691266922209965180729575449316456258" +
			"7457146438441712974954405357257382446512931883695366467795204401516" +
			"32820421821638628026744518880343081";
	//签名认证的私钥
	static String sign_d = "3377249811175967351683768039829600386204589892536304" +
			"7062432256953553092394375598086736451018708660607687807331730406587" +
			"94288084100441369702947025962245761";
	
	/**
	 * 根据keyInfo产生公钥和私钥，并且保存到pk.dat和sk.dat文件中
	 * 
	 * @param keyInfo
	 * @throws Exception
	 */
	public static void genKeys(String keyInfo) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom();
		random.setSeed(keyInfo.getBytes());
		// 初始加密，长度为512，必须是大于512才可以的
		keygen.initialize(512, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		// 取得公钥
		PublicKey publicKey = kp.getPublic();
		System.out.println(publicKey);
		// 取得私钥
		PrivateKey privateKey = kp.getPrivate();
		System.out.println(privateKey);
	}

	/**
	 * 根据公钥n、e生成公钥
	 * 
	 * @param modulus
	 *            公钥n串
	 * @param publicExponent
	 *            公钥e串
	 * @return 返回公钥PublicKey
	 * @throws Exception
	 */
	public static PublicKey getPublickKey(String modulus, String publicExponent){
		KeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
				new BigInteger(publicExponent));
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = factory.generatePublic(publicKeySpec);
			return publicKey;
		}catch(Exception e) {
			return null;
		}
	}
	
	public static PublicKey getPublickKey(){
		return getPublickKey(n, e);
	}
	
	public static PublicKey getSignPublicKey() {
		return getPublickKey(sign_n, e);
	}

	/**
	 * 根据公钥n、e生成私钥
	 * 
	 * @param modulus
	 *            私钥n串
	 * @param privatekeyExponent
	 *            私钥e串
	 * @return 返回私钥Key
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String modulus, String privatekeyExponent) {
		KeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
				new BigInteger(privatekeyExponent));
		PrivateKey privateKey = null;
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			privateKey = factory.generatePrivate(privateKeySpec);
		}catch(Exception e) {
			System.err.println("Failed to get private key");
		}
		return privateKey;
	}

	public static PrivateKey getPrivateKey() {
		return getPrivateKey(n, d);
	}
	
	public static PrivateKey getSignPrivateKey() {
		return getPrivateKey(sign_n, sign_d);
	}

	/**
	 * 用私钥证书进行签名
	 * 
	 * @param message
	 *            签名之前的原文
	 * @param privateKey
	 *            私钥
	 * @return byte[] 返回签名
	 * @throws Exception
	 */
	public static byte[] sign(PrivateKey privateKey, byte[] message) {
		try {
			//Signature sign = Signature.getInstance("RSA/ECB/PKCS1Padding");
			Signature sign = Signature.getInstance("MD5withRSA");
			sign.initSign(privateKey);
			sign.update(message);
			byte[] signed = sign.sign();
			return signed;
		}catch(Exception e) {
			System.err.println("Failed to get signature");
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] sign(PrivateKey privateKey, String message) {
		try {
			Signature sign = Signature.getInstance("MD5withRSA");
			sign.initSign(privateKey);
			sign.update(message.getBytes("utf-8"));
			byte[] signed = sign.sign();
			return signed;
		}catch(Exception e) {
			System.err.println("Failed to get signature");
			return null;
		}
	}

	/**
	 * 用公钥证书进行验签
	 * 
	 * @param src
	 *            签名之前的原文
	 * @param cipherText
	 *            签名
	 * @param publicKey
	 *            公钥
	 * @return boolean 验签成功为true,失败为false
	 * @throws Exception
	 */
	public static boolean verify(PublicKey publicKey, byte[] src,
			byte[] cipherText) throws SignatureException,
			NoSuchAlgorithmException, InvalidKeyException {
		Signature sign = Signature.getInstance("MD5withRSA");
		sign.initVerify(publicKey);
		sign.update(src);
		if (sign.verify(cipherText)) {
			return true;
		}
		return false;
	}

	public static boolean verify(PublicKey publicKey, String src,
			byte[] cipherText) throws SignatureException,
			NoSuchAlgorithmException, InvalidKeyException,
			UnsupportedEncodingException {
		Signature sign = Signature.getInstance("MD5withRSA");
		sign.initVerify(publicKey);
		sign.update(src.getBytes("utf-8"));
		if (sign.verify(cipherText)) {
			return true;
		}
		return false;
	}

	/**
	 * 用私钥证书进行加密
	 * 
	 * @param message
	 *            签名之前的原文
	 * @param privateKey
	 *            私钥
	 * @return byte[] 密文
	 * @throws Exception
	 */
	public static byte[] encript(PublicKey publicKey, byte[] message){
		try{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(message);
		}catch (Exception e){
			return null;
		}
	}

	public static byte[] encript(PublicKey publicKey, String message){
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(message.getBytes("utf-8"));
		}catch(Exception e) {
			return null;
		}
	}

	public static byte[] decript(PrivateKey privateKey, byte[] cipherText)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(cipherText);
	}
	
	public static String decript(PrivateKey privateKey, String cipherText)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] message =  cipher.doFinal(cipherText.getBytes());
		return new String(message);
	}

	public static void main(String[] args) throws InvalidKeyException,
			SignatureException, NoSuchAlgorithmException, Exception {
		String src = "hello pautcher";
		PublicKey publicKey = getPublickKey(n, e);
		PrivateKey privateKey = getPrivateKey(n, d);
		
		//System.out.println(publicKey + "---------" + privateKey);
		System.out.println(encript(publicKey, src.getBytes()));
		System.out.println(new String(decript(privateKey,encript(publicKey, src.getBytes()))));
//		byte[] signed = sign(privateKey, src);
//		System.out.println(verify(publicKey, src, signed));
		// genKeys("123456");
	}
}
