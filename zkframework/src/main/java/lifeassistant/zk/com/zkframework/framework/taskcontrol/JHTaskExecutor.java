package lifeassistant.zk.com.zkframework.framework.taskcontrol;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import android.os.SystemClock;
import com.jh.app.taskcontrol.callback.ITaskFinishLinsener;
import com.jh.app.taskcontrol.constants.TaskConstants;
import com.jh.app.taskcontrol.constants.TaskConstants.TaskPriority;
import com.jh.app.taskcontrol.runnable.WorkerRunnable;
import com.jh.exception.JHException;
 /**
  * JH任务控制器
  * @author 099
  * @since 2016-3-11
  */
public class JHTaskExecutor {
	/**单例**/
	private static JHTaskExecutor excutor=new JHTaskExecutor();
	/**CPU数量*/
//	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	/**线程池tread数量**/
	private int corePoolSize=8;	

	/**任务队列*/
	private JHTaskQueue mTaskQueue;
	/**任务线程池*/
	private JHTaskThreadPool mTaskThreadPool;
	/**上一次满栈的时间**/
	private volatile long lastThredPoolFullTime;
	/**满栈一分钟前台任务可以运行**/
	private static final int TASKPOOLS_FULL_TIMEOUT=1000*60;
	 /**有特殊Target的task队列**/
	private final Map<String, HashSet<ITaskFinishLinsener>> mTargetFinishLinseners 
		= new HashMap<String, HashSet<ITaskFinishLinsener>>();
	/**构造*/
	private JHTaskExecutor(){
		mTaskQueue=new JHTaskQueue();
		mTaskThreadPool=new JHTaskThreadPool(corePoolSize, corePoolSize+5, null, null);
	}
	/**
	 * 获取实例
	 * @return JHTaskExecutor
	 */
	public static JHTaskExecutor getInstance(){
		return excutor;
	}
	
	/**
	 * 将task添加到同Priority的队列
	 * @param baseTask task
	 */
	public  void addTask(JHBaseTask baseTask){
		addTaskFirst(baseTask, false);
	}
	/**
	 * 将task添加到同Priority的队列第一位上
	 * @param baseTask task
	 */
	public  void addTaskFirst(JHBaseTask baseTask){
		addTaskFirst(baseTask, true);
	}
	/**
	 * 将task添加到同Priority的队列第一位上
	 * @param baseTask task
	 * @param isSetFirst true 放在第一位
	 * 					 false 放在最后一位
	 */
	public  void addTaskFirst(JHBaseTask baseTask,boolean isSetFirst){
		if(baseTask!=null){
			if(mTaskQueue.enqueueTask(baseTask, isSetFirst)){
				executeTask();
			}
		}else{
			throw new NullPointerException();
		}
	}
	/**
	 * 执行task
	 */
	public synchronized void executeTask() {
		JHBaseTask task=mTaskQueue.getFirstTask();
		if(task==null){
			return;
		}
		if(mTaskThreadPool.isCanExecRunnable()){
			lastThredPoolFullTime=0;
			
			prepare(task,false);
			return;
		}else{
			if(lastThredPoolFullTime!=0){
				lastThredPoolFullTime=SystemClock.elapsedRealtime();
			}
			if(mTaskThreadPool.isCanForceExecRunnable()){
				if(task.getPriority()==TaskPriority.PRIORITY_IMMEDIATE){
					prepare(task,true);
					return ;
				}else if(task.getPriority()==TaskPriority.PRIORITY_IMMEDIATE&&SystemClock.elapsedRealtime()-lastThredPoolFullTime>=TASKPOOLS_FULL_TIMEOUT){
					prepare(task,true);
					return;
				} 
			}
		}
		
		mTaskQueue.reAddTaskQueue(task);
	}
	/**
	 * 执行task
	 * @param task 任务
	 * @param isForce true 强制执行
	 * 			 	  false 非强制执行
	 */
	private void prepare(JHBaseTask task,boolean isForce) {
		
		if(task.getException()==null){
			task.notifyPre();
			mTaskThreadPool.executeRunnable(new WorkerRunnable(task,isForce) {
				@Override
				public void run() {
					try {
						android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
						if(getmTask().getException()!=null){
							 getmTask().notifyFailed();
							 return;
						}
						getmTask().doTask();//耗时操作，尽量少执行
						
						if(getmTask().getException()!=null){
							 getmTask().notifyFailed();
							 return;
						}else{
							getmTask().notifySuccess();
						}
					}catch ( JHException e) {
						e.printStackTrace();
						getmTask().setException(e);
						getmTask().notifyFailed();
					}
					 catch ( Exception e) {
						 e.printStackTrace();
						 getmTask().setException(new JHException(e));
						 getmTask().notifyFailed();
					}finally{
						removeRunningTask(getmTask());
						mTaskThreadPool.exeFinished(ismIsTempThreadPool());
						executeTask();
						
					}
				}
			});
			
			
		}else{
			task.notifyFailed();
			removeRunningTask(task);
			executeTask();
			
		}
		
		
	}
	/**
	 * 删除正在running队列中task
	 * @param currentTask 任务
	 */
	private void removeRunningTask(JHBaseTask currentTask){
		mTaskQueue.removeRunningTask(currentTask);
		notifyTaskExeRunningListener(currentTask);
		
	}

	/**
	 * 从等待队列中删除task
	 * @param baseTask 任务
	 */
//	public void removeWaitTask(JHBaseTask baseTask){
//		if(baseTask==null){
//			throw new NullPointerException();
//		}
//		if(baseTask.isWaiting()){
//			if(mTaskQueue.removeWaitTask(baseTask)){
//				baseTask.setException(new JHTaskRemoveException());
//				baseTask.notifyFailed();
//			}
//		}
//	}
	
	/**
	 * 从等待队列中删除有traget标记的Task
	 * @param taskTraget 标记
	 */
//	public void removeWaitTaskByTraget(String taskTraget){
//		 HashSet<JHBaseTask> removeTasks=new HashSet<JHBaseTask>(mTaskQueue.getTaskByTraget(taskTraget));
//		for(JHBaseTask task:removeTasks){
//			removeWaitTask(task);
//		}
//	}
	/**
	 * 取消task，从等待队列中删除，若在执行中，则只是标记task为canceled
	 * @param baseTask 任务
	 */
	public void cancelTask(JHBaseTask baseTask){
		if(baseTask==null){
			throw new NullPointerException();
		}
		baseTask.cancel(false);
	}
	/**
	 * 通过taskTrager取消task，从等待队列中删除，若在执行中，则只是标记task为canceled
	 * @param taskTraget 标记
	 */
	public void cancelTaskByTraget(String taskTraget){
		HashSet<JHBaseTask>  set=mTaskQueue.getTaskByTraget(taskTraget);
		if(set==null){
			return;
		}
		HashSet<JHBaseTask> removeTasks=new HashSet<JHBaseTask>(set);
		for(JHBaseTask task:removeTasks){
			cancelTask(task);
		}
		
	}
	/**
	 * 是否存在task（等待中，执行中的task）
	 * @param task 任务
	 * @return false 无
	 * 		   true  有
	 */
	public boolean hasTask(JHBaseTask task){
		return mTaskQueue.contains(task);
	}
	/**
	 * 是否存在标记为traget的task（等待中，执行中的task）
	 * @param taskTraget 标记
	 * @return false 无
	 * 		   true  有
	 */
	public boolean hasTask(String taskTraget){
		return mTaskQueue.contains(taskTraget);
	}
	/**
	 * 将task任务滞后
	 * @param task 任务 
	 */
	public void waitTask(JHBaseTask task){
		if(task!=null){
			if(mTaskQueue.contains(task)){
				task.setActive(false);
			}else{
				task.setActive(false);
				addTask(task);
			}
			
		}
	}
	/**
	 * 将task任务滞后通过Traget
	 * @param taskTraget 标记 
	 */
	public void waitTask(String taskTraget){
		if(taskTraget!=null){
			HashSet<JHBaseTask> taskSets=mTaskQueue.getTaskByTraget(taskTraget);
			for(JHBaseTask task:taskSets){
				if(task.isWaiting()){
					task.setActive(false);
				}
			}
		}
	}
	/**
	 * 重新激活task
	 * @param task 任务
	 */
	public void reActiveTask(JHBaseTask task){
		if(task!=null){
			if(mTaskQueue.contains(task)){
				task.setActive(true);
			}else{
				task.setActive(true);
				addTask(task);
			}
			
		}
	}
	/**
	 * 重新激活taskByTraget
	 * @param taskTraget 标记
	 */
	public void reActiveTask(String taskTraget){
		if(taskTraget!=null){
			HashSet<JHBaseTask> taskSets=mTaskQueue.getTaskByTraget(taskTraget);
			for(JHBaseTask task:taskSets){
				if(task.isWaiting()){
					task.setActive(true);
				}
			}
		}
	}
	/**
	 * 通知task执行监听
	 * @param currentTask 任务
	 */
	private void notifyTaskExeRunningListener(JHBaseTask currentTask) {
		//notify allTask
		if(mTaskQueue.isEmpty()){
				HashSet<ITaskFinishLinsener> set = mTargetFinishLinseners
						.get(TaskConstants.ALL_TASK);
				if(set!=null){
					for(ITaskFinishLinsener listener:set){
						listener.notifyGroupTagFinish(TaskConstants.ALL_TASK);
					}
				}
		}
		// notify traget task
		if(currentTask.getmTaskTraget()!=null&&mTaskQueue.isTragetEmpty(currentTask.getmTaskTraget())){
				HashSet<ITaskFinishLinsener> set = mTargetFinishLinseners
						.get(currentTask.getmTaskTraget());
				if(set!=null){
					for(ITaskFinishLinsener listener:set){
						listener.notifyGroupTagFinish(currentTask.getmTaskTraget());
					}
				}
		}
		
		
	}
	/***
	 * 添加任务执行完成的listener
	 * @param linsener ITaskFinishLinsener
	 */
	public void addTaskFinishLinsener(ITaskFinishLinsener linsener){
		addTragetTaskFinishLinsener(TaskConstants.ALL_TASK,linsener);
	}
	/***
	 * 添加任务执行完成的listener
	 * @param linsener ITaskFinishLinsener
	 * @param traget 标记
	 */
	public void addTragetTaskFinishLinsener(String traget,ITaskFinishLinsener linsener){
		  String tempTraget=traget;
		 if(tempTraget==null){
			 tempTraget=TaskConstants.ALL_TASK;
		 }
		synchronized (mTargetFinishLinseners) {
			HashSet<ITaskFinishLinsener> set = mTargetFinishLinseners
					.get(tempTraget);
			if (set == null) {
				set = new HashSet<ITaskFinishLinsener>();
				set.add(linsener);
				mTargetFinishLinseners.put(tempTraget, set);
			} else {
				set.add(linsener);
			}
		}
		  
	}
	/**
	 * 移除任务执行完成的listener
	 * @param linsener ITaskFinishLinsener
	 * @param traget 标记
	 */
	public void removeTaskFinishLinsener(String traget,ITaskFinishLinsener linsener){
		 String tempTraget=traget;
		 if(tempTraget==null){
			 tempTraget=TaskConstants.ALL_TASK;
		 }
		synchronized (mTargetFinishLinseners) {
			HashSet<ITaskFinishLinsener> set = mTargetFinishLinseners
					.get(tempTraget);
			if (set != null) {
				set.remove(linsener);
			}
		}
		 
		 
		 
	}
	
}
