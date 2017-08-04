package lifeassistant.zk.com.zkframework.framework.base;

import android.content.Context;

import com.jh.app.taskcontrol.JHBaseTask;
import com.jh.app.taskcontrol.JHTaskExecutor;
import com.jh.app.util.ConcurrenceExcutor;
import com.jh.eventControler.EventControler;

/**
 * 基础控制类
 * 
 * @author 099
 * 
 */
public abstract class IBaseController {
	
	
	
	/**线程执行器
	 * 过期，请用新的方法{@link }
	 * */
//	@Deprecated
//	protected ConcurrenceExcutor excutor;
	/**event 执行器**/
	protected EventControler mEventHandler;

//	/** 上下文 */
//	protected Context mContext;

	public IBaseController(Context context) {
//		this.mContext = context;
//		excutor = getConcurrenceExcutor();
	}
	public abstract void setmIBaseModel(IBaseModel mIBaseModel);
	/** 初始化线程执行器
	 * 过期{link }
	 *  */
//	@Deprecated
//	protected abstract ConcurrenceExcutor getConcurrenceExcutor();
	/***
	 * 添加task
	 * @param task
	 * @param isFirst
	 */
	public void addTask(JHBaseTask task ,boolean isFirst){
		JHTaskExecutor.getInstance().addTaskFirst(task, isFirst);
	}
	/***
	 * 添加task
	 * @param task
	 */
	public void addTask(JHBaseTask task ){
		JHTaskExecutor.getInstance().addTaskFirst(task, false);
	}
	/***
	 * 设置eventHandler
	 * @param eventHandler
	 */
	public void setEventHandler(EventControler eventHandler) {
		mEventHandler = eventHandler;

	};

}