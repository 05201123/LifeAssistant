package lifeassistant.zk.com.zkframework.framework.taskcontrol.callback;

import com.jh.app.taskcontrol.runnable.WorkerRunnable;

/**
 * 线程池策略
 * @author 099
 *
 */
public interface IThreadPoolStrategy {
	/**
	 * 执行runnable
	 * @param runnable workRunnable
	 */
	public void execute(WorkerRunnable runnable);
	/**
	 * 线程池是否空闲
	 * @return true 空闲
	 * 		   false 满了
	 */
	public boolean isFree();
	/**
	 * 是否可以执行紧急任务
	 * @return true 可以强制执行
	 * 		   false 不可强制执行
	 */
	public boolean isCanFroceExec();
	/**
	 * 执行完成
	 * @param mIsTempThreadPool false 常规线程池执行完成
	 * 							true  temp线程池执行完成
	 */
	public void exeFinished(boolean mIsTempThreadPool);
}
