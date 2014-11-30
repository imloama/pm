package lite.erp.security.crypto;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 3DS加密,代码来源于网络
 * 
 * 
 * @author mazhaoyong
 *
 */
public class ThreeDES {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Key key; // 密钥
	public static String generateKeyStr="rstuvwxyz123456abcdefghigklmnopq";
	private String encryptAlgorithm="DES";

	public ThreeDES(){
		this(generateKeyStr);
	}
	
	public ThreeDES(String strKey){
		getKey(strKey);
	}
	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 *            密钥字符串
	 */
	private void getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance(encryptAlgorithm);
			_generator.init(new SecureRandom(strKey.getBytes()));
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			logger.error("生成key错误!", e);
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 *            String明文
	 * @return String密文
	 */
	public String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = this.getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			logger.error("加密错误!", e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 *            String密文
	 * @return String明文
	 */
	public String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			logger.error("解密错误!", e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 *  为getEncString方法提供服务
	 *  
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 *            byte[]明文
	 * @return byte[]密文
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(encryptAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			logger.error("加密错误!", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 为getDesString方法提供服务
	 * 
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 *            byte[]密文
	 * @return byte[]明文
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(encryptAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			logger.error("解密错误!", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
}
