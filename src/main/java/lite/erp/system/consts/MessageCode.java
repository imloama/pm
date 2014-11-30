package lite.erp.system.consts;

/**
 * 消息的code标识
 * @author mazhaoyong
 *
 */
public final class MessageCode {

	private MessageCode(){}
	
	public static final String MESSAGE_CODE = "msg_code";
	
	/**
	 * 未授权信息
	 */
	public static final int UNAUTH = -100;
	/**
	 * 发生错误
	 */
	public static final int ERROR = -1;
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	
	
}
