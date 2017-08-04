package lifeassistant.zk.com.zkframework.framework.taskcontrol.runnable;

import com.jh.app.taskcontrol.JHBaseTask;

/**
 * 实际放入线程池的runnnable
 * @author 099
 * @since 2016-4-5
 */
public  abstract class WorkerRunnable implements Runnable{
	/**task*/
	private JHBaseTask mTask;
	/**是否是放入临时线程池的task*/
	private  boolean mIsTempThreadPool;
	public WorkerRunnable(JHBaseTask task,boolean isTmep){
		setmTask(task);
		setmIsTempThreadPool(isTmep);
	}
	/**
	 * 是否进入临时线程池
	 * @return the mIsTempThreadPool
	 */
	public boolean ismIsTempThreadPool() {
		return mIsTempThreadPool;
	}
	/**
	 * 设置runnable是放入了临时线程池
	 * @param mIsTempThreadPool the mIsTempThreadPool to set
	 */
	public void setmIsTempThreadPool(boolean mIsTempThreadPool) {
		this.mIsTempThreadPool = mIsTempThreadPool;
	}
	public JHBaseTask getmTask() {
		return mTask;
	}
	public void setmTask(JHBaseTask mTask) {
		this.mTask = mTask;
	}
}
