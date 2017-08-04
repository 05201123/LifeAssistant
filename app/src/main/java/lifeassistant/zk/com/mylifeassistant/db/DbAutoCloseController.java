package lifeassistant.zk.com.mylifeassistant.db;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import lifeassistant.zk.com.mylifeassistant.core.CoreApi;

/**
 * 数据库自动关闭控制器
 * @author 099
 *
 */
public class DbAutoCloseController {
	/***
	 * 数据库5分钟不用自动关闭
	 */
	private static final long DB_AUTO_CLOSE_TIME=5*60*1000;
	private static final int DEFALUT_MSGWHAT=101;
	private static DbAutoCloseController controller=new DbAutoCloseController();
	private Handler mHandler;
	private DbAutoCloseController() {
	}
	/**
	 * 系统使用了数据库
	 */
	public void userDb(){
		synchronized (controller) {
			if(mHandler!=null){
				mHandler.removeMessages(DEFALUT_MSGWHAT);
			}else{
				mHandler=new Handler(Looper.getMainLooper()){
					@Override
					public void handleMessage(Message msg) {
						if(msg.what==DEFALUT_MSGWHAT){
							closeDb();
						}
					}
				};
			}
			mHandler.sendEmptyMessageDelayed(DEFALUT_MSGWHAT, DB_AUTO_CLOSE_TIME);
			
			
		}
	};
	/**
	 * 关闭数据库
	 */
	public void closeDb(){
		QGPDBHelper helper=QGPDBHelper.getInstance(CoreApi.getInstance().getApplicationContext());
		synchronized (helper) {
			helper.close();
		}
	}
	public static DbAutoCloseController getInstance(){
		return controller;
	}
}
