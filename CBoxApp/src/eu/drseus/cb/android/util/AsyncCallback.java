package eu.drseus.cb.android.util;

public abstract class AsyncCallback<T> {
	
	protected int timeout;
	
	public AsyncCallback(int timeout){
		this.timeout = timeout;
	}
	
	public int getTimeout(){
		return timeout;
	}
	
	public abstract void onResult(T result);
	public abstract void onError(CallbackException e);
	
	public static class CallbackException extends Exception {
				
		/**
		 * 
		 */
		private static final long serialVersionUID = -8877873446805184679L;
		
		private Exception nestedException;
		
		public CallbackException(String message){
			super(message);
		}
		
		public CallbackException(Exception nestedException){
			super(nestedException.getMessage());
			this.nestedException = nestedException;
		}
		
		public CallbackException(String message, Exception nestedException){
			super(message);
			this.nestedException = nestedException;
		}
		
		public Exception getNestedException(){
			return nestedException;
		}
		
	}
}