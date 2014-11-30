package lite.erp.api.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lite.erp.security.crypto.RSAKeyFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 生成加密内容
 * @author mazhaoyong
 *
 */
@Controller
@RequestMapping("/api")
public class KeyPairController {

	/**
	 * 生成当前的keypair内容,用于对数据做加密处理,当前的keypair会存储存session中
	 * @return
	 */
	@RequestMapping("/keypair")
	@ResponseBody
	public Map<String,String> keypair(HttpSession session){
		Map<String,String> result = new HashMap<String,String>();
		KeyPair key = RSAKeyFactory.getInstance().getKeyPair();
		session.setAttribute("key", key);
		RSAPublicKey pkey = (RSAPublicKey) key.getPublic();
		String module = pkey.getModulus().toString(16);
		result.put("module", module);
		String empoent = pkey.getPublicExponent().toString(16);
		result.put("empoent",empoent);
		return result;
	}
	
	
}
