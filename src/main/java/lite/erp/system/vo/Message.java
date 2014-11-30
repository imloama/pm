package lite.erp.system.vo;

public class Message implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回结果代码
	 */
	private int code;
	
	/**
	 * 提示语句
	 */
	private String msg;
	
	/**
	 * 返回结果
	 */
	private Object data;

	public int getCode() {
		return code;
	}

	public Message setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Message setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Message setData(Object data) {
		this.data = data;
		return this;
	}
	
	
	
	

}
