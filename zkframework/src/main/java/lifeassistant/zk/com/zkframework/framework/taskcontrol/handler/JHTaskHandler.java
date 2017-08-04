package lifeassistant.zk.com.zkframework.framework.taskcontrol.handler;


import android.os.Handler;
import android.os.Looper;

/**
 * 子线程Handler
 * @author 099
 *
 */
public class JHTaskHandler {
	/**task 子线程handler*/
	private static HandlerThreadWrapper taskThread = new HandlerThreadWrapper("subtask");
	/**
	 * 获取taskHanlder
	 * @return Handler
	 */
	public static Handler getTaskHandler(){
		return taskThread.getHandler();
	}
	/***
	 * 获取looper
	 * @return Looper
	 */
	public static Looper getTaskLooper(){
		return taskThread.getLooper();
	}
	/**
	 * 获取子线程Handler
	 * @param threadName
	 * @return
	 */
	public static Handler getSubThreadHandler(String threadName){
		return new HandlerThreadWrapper(threadName).getHandler();
	}
	
	/**
	 * Handler包装类
	 * @author 099
	 *
	 */
	private static class HandlerThreadWrapper {
		/**Handler*/
        private Handler handler = null;
        /**HandlerThread**/
        private android.os.HandlerThread handlerThread;
        /**
         * 构造方法
         * @param name 子线程名字
         */
        private HandlerThreadWrapper(String name) {
            handlerThread = new android.os.HandlerThread(name);
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        /**
         * 获取Handler
         * @return handler
         */
        public Handler getHandler() {
            return handler;
        }
        /**
         * 获取looper
         * @return Looper
         */
        public Looper getLooper(){
        	return handlerThread.getLooper();
        }
    }
}
