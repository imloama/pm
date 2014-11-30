package lite.erp.security.shiro;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

import javax.annotation.PostConstruct;

import lite.erp.security.crypto.RSAUtil;
import lite.erp.system.consts.SystemConsts;
import lite.erp.system.entity.Menu;
import lite.erp.system.entity.User;
import lite.erp.system.service.IUserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.Encodes;

import com.alibaba.fastjson.JSON;

public class ShiroDbRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	protected IUserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		logger.info("into doGetAuthenticationInfo");
		try {
			String pwd = decryptPwd(token.getPassword());
			token.setPassword(pwd.toCharArray());
		} catch (Exception e) {
			throw new AccountException("解密密码失败");
		}
		User user = userService.findUserByName(token.getUsername());
		SecurityUtils.getSubject().getSession().removeAttribute("errmsg");
		if (user == null) {
			SecurityUtils.getSubject().getSession().setAttribute("errmsg", "未找到用户！");
			throw new UnknownAccountException();// 没有找到账号
		}

		if (Boolean.TRUE.equals(user.isLocked())) {
			SecurityUtils.getSubject().getSession().setAttribute("errmsg", "账号锁定！");
			throw new LockedAccountException(); // 帐号锁定
		}
		byte[] salt = Encodes.decodeHex(user.getSalt());
		ShiroUser shiroUser = new ShiroUser(user.getId(), user.getNickname(), user.getName());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser,
				user.getPassword(), ByteSource.Util.bytes(salt), getName());
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(SystemConsts.CURRENT_USER_SESSION_NAME, shiroUser);
		session.setAttribute(SystemConsts.ORIGIN_CURRENT_USER_NAME, user);
		//添加菜单
		List<Menu> menus = getUserService().findMenu(user);
		session.setAttribute(SystemConsts.SESSION_ATTR_MENUS,menus);
		session.setAttribute(SystemConsts.SESSION_ATTR_MENUS_JSON,JSON.toJSONString(menus));
		System.out.println(JSON.toJSONString(menus));
		return info;
	}
	@Override
	protected AuthorizationInfo getAuthorizationInfo(
			PrincipalCollection principals) {
		return super.getAuthorizationInfo(principals);
	}
	
	private String decryptPwd(char[] pwds) throws Exception{
		String pwd1 = new String(pwds);
		String pwd = null;
		KeyPair key = (KeyPair)SecurityUtils.getSubject().getSession().getAttribute("key");
		RSAPrivateKey rsap = (RSAPrivateKey) key.getPrivate();
		byte[] en_result = new BigInteger(pwd1, 16).toByteArray();
		byte[] bs = RSAUtil.decrypt(rsap,en_result);
		String de_orig = new String(bs);
		StringBuffer sb = new StringBuffer();
		sb.append(de_orig);
		String uri_orig = sb.reverse().toString().trim();
		//接收的数据做过编码处理，所以要还原
		pwd =URLDecoder.decode(uri_orig,"UTF-8");//
		return pwd;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principals);
			SecurityUtils.getSubject().logout();
			return null;
		}
		
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.findUserByName(shiroUser.getLoginName());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRolesCode());
		info.addStringPermissions(user.getPermissions());
		
		//info.setRoles(this.getUserService().findRolesByUsername(username));
//		info.setStringPermissions(this.getUserService().findPermissionsByUsername(username));
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(IUserService.HASH_ALGORITHM);
		matcher.setHashIterations(IUserService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
}
