package lite.erp.exception;

public class LiteERPException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;
	private Integer code;
	private Throwable throwable;
	public LiteERPException(String msg){
		this.msg = msg;
	}
	public LiteERPException(String msg,Throwable throwable){
		this.msg= msg;
		this.throwable = throwable;
	}
	
	public LiteERPException(String msg,Integer code,Throwable throwable){
		this.msg = msg;
		this.code = code;
		this.throwable = throwable;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage()+this.msg;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
	
	
	
}
