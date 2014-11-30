package lite.erp.exception;

public class JSCompressorException extends LiteERPException{

	private static final long serialVersionUID = 1L;

	public JSCompressorException(String msg){
		super(msg);
	}
	public JSCompressorException(String msg,Throwable throwable){
		super(msg,throwable);
	}
	
	public JSCompressorException(String msg,Integer code,Throwable throwable){
		super(msg,code,throwable);
	}
	
	
}
