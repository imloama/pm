package lite.erp.ext.spring;

import java.util.Properties;

import lite.erp.security.crypto.ThreeDES;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 用于实现properties文件的加密处理
 * 加密采用rsa的加密处理
 * 
 * @author mazhaoyong
 */
public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer{

	public static final String KEY_PASSWORD = "jdbc.password";
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		 String password = props.getProperty(KEY_PASSWORD); 
		 if(null!=password){
			 //解密
			 ThreeDES des = new ThreeDES();
			 props.setProperty(KEY_PASSWORD,des.getDesString(password));
		 }
		super.processProperties(beanFactoryToProcess, props);
	}
	
	
}
