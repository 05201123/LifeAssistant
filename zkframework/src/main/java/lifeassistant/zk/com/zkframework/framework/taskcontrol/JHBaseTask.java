package lifeassistant.zk.com.zkframework.framework.taskcontrol;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import lifeassistant.zk.com.zkframework.framework.exception.JHException;
import lifeassistant.zk.com.zkframework.framework.taskcontrol.callback.ITaskLifeCycle;
import lifeassistant.zk.com.zkframework.framework.taskcontrol.constants.TaskConstants;
import lifeassistant.zk.com.zkframework.framework.taskcontrol.exception.JHTaskCancelException;

/**
 * JH基础任务类
 * @author 099
 * @since 2016-3-11
 */
public abstract class JHBaseTask implements ITaskLifeCycle, Comparable<JHBaseTask>{
	/**超时时间未设置，认为是无限大**/
	public static final long WAIT_TIMEOUT_NONE=-1;
	/**执行超时时间未设置，任务是无限大**/
	public static final long RUNNING_TIMEOUT_NONE=-1;
	/**task的状态值*/
	private  int mStatus=TaskStatus.NONE;
	/**task是否已经取消*/
	private final AtomicBoolean mCancelled = new AtomicBoolean();
	/**等待超时时间**/
	private long waitTimeOut=WAIT_TIMEOUT_NONE;
	/**执行超时时间**/
	private long runningTimeOut=RUNNING_TIMEOUT_NONE;
	/**用于特有标记比如上传，下载，activity等特殊任务**/
	private String mTaskTraget;
	/**tag 的数量*/
	private int mTaskTragetCount;
	/**task执行顺序，先进先出 */
    private Integer mSequence;
    /**是否激活*/
    private volatile boolean isActive=true;
	/**主线程Handler**/
	private static final InternalHandler sHandler = new InternalHandler(
			Looper.getMainLooper());
    /**task 执行进度的消息值*/
    private static final int MESSAGE_POST_PROGRESS = 103;
    /**task 执行之前的消息值*/
    private static final int MESSAGE_POST_PRE = 104;
    /**task 执行是吧的消息值*/
    private static final int MESSAGE_POST_FAILED = 105;
    /**task 执行成功的消息值*/
    private static final int MESSAGE_POST_SUCCESS = 106;
	/**运行过程中发现的异常*/
	private volatile JHException mExc;
	/**
	 * 重置初始状态
	 */
	void reSetInitStatus() {
		if(isCanMulInvoked()){
			isActive=true;
			mStatus=TaskStatus.NONE;
			mExc=null;
			mCancelled.set(false);
		}
	}
	/****
	 * 任务cancel监听器
	 */
	private ITaskCancel cancelListener;
    
    /***
     * 获取task等待超时时间
     * @return 等待超时时间
     */
    protected long getTaskWaitTimeOut(){
    	return waitTimeOut;
    }
    /**
     * 获取task执行超时时间
     * @return 执行超时时间
     */
    protected long getTaskRunningTimeOut(){
    	return runningTimeOut;
    }
    
	/**
	 * 判断任务是否已经取消
	 * @return true 取消
	 * 		   false 未取消
	 */
	public final boolean isCancelled() {
        return mCancelled.get();      
    }
	/**
	 * 取消task
	 * @param mayInterruptIfRunning 是否终止线程
	 */
	 protected void cancel(boolean mayInterruptIfRunning) {
        mCancelled.set(true);
        setException(new JHTaskCancelException());
        notifyTaskCancel();
    }
	 /**
	  * 通知taskCancelListener
	  */
	protected  void notifyTaskCancel() {
		if(cancelListener!=null){
        	cancelListener.cancel(this);
        }
	}
	/**
	 * 判断是否需要等待
	 * @return true  将task压入线程池中
	 * 		   false 不将task压入线程池中，而是压入等待队列中
	 */
	protected boolean  isNeedWait() {
		return false;
	}
	/***
	 * 获取Task权重
	 * @return 权重
	 */
	protected int getPriority() {
	    return TaskConstants.TaskPriority.PRIORITY_NORMAL;
	}
	/**
	 * 调用在dotask方法中，用于通知更新UI进度条，{@link ITaskLifeCycle #onProgressChanged(Object, int)}
	 * @param bussiness 业务实体
	 * @param newProgress 进度值
	 */
	 protected final void publishProgress(Object bussiness,int newProgress) {
	        if (mExc==null) {
			sHandler.obtainMessage(MESSAGE_POST_PROGRESS, newProgress, 0,
					new TaskResult(this, bussiness)).sendToTarget();
	        }
	    }
	
	@Override
	/**doSomething before doTask(); in UIthread**/
	public void onPreExecute() {
	
	}
	@Override
	/**notify progress in backgroundThread*/
	public abstract void doTask() throws JHException;
	@Override
	/**notify progress in UIThread*/
	public void onProgressChanged(Object bussinessData, int newProgress) {
	
	}
	@Override
	/**notify success in UIThread*/
	public void success() {
		
	}
	@Override
	/**notify fail in UIThread*/
	public void fail(String errorMessage) {
		
		
	}
	/***
	 * 是否已经被调用
	 * @return true 已经执行过
	 * 		   false 未执行过
	 * 一个task应该只能执行一次
	 */
	public boolean isInvoked() {
		return mStatus!=TaskStatus.NONE;
	}
	/**
	 * 是否处于等待状态
	 * @return true  处于等待状态
	 * 		   false 处于其他状态
	 */
	public boolean isWaiting() {
		return mStatus==TaskStatus.PENDING;
	}
	/**
	 * 是否处于运行状态
	 * @return  true  处于等待状态
	 * 			false 处于其他状态
	 */
	public boolean isRunning(){
		return mStatus==TaskStatus.RUNNING;
	}
	/**
	 * 是否处于完成状态
	 * @return  true  处于等待状态
	 * 			false 处于其他状态
	 */
	public boolean isFinished(){
		return mStatus==TaskStatus.FINISHED;
	}
	/**
	 * 设置task状态值
	 * @param taskStatus 任务状态
	 */
	 void setTaskStatus(int taskStatus){
		mStatus=taskStatus;
	}
	/**
	 * 设置顺序
	 * @param sequence 顺序值
	 */
	final void setSequence(int sequence) {
        mSequence = sequence;
    }
	@Override
	/**
	 * 比较
	 */
	public int compareTo(JHBaseTask another) {
		int left = this.getPriority();
        int right = another.getPriority();
        return left == right ?
                this.mSequence - another.mSequence :
                	right-left;
	}
	
	
	/**
	 * 获取task的标识
	 * @return String mTaskTraget
	 */
	protected String getmTaskTraget() {
		return mTaskTraget;
	}

	/**
	 * 获取task标识数量
	 * @return
	 */
	protected int getmTaskTragetCount() {
		return 5;
	}
	/**
	 * 是否可以多次调用
	 * @return
	 */
	protected boolean isCanMulInvoked(){
		return true;
	}

	/**
	 * 设置金和taskException
	 * @param jhException 异常 
	 */
	public void setException(JHException jhException) {
		mExc=jhException;
	}
	/**
	 * 获取task运行时异常
	 * @return mExc
	 */
	public JHException getException(){
		return mExc;
	}
	
	/**
	 * 是否是活跃的
	 * @return the isActive
	 */
	 boolean isActive() {
		return isActive&&!isNeedWait();
	}
	/**
	 * 设置活跃
	 * @param isActive the isActive to set
	 */
	 void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	 /**
	  * 通知task执行失败
	  */
	 void notifyFailed() {
		 if (mExc != null) {
			sHandler.obtainMessage(MESSAGE_POST_FAILED, new TaskResult(this))
					.sendToTarget();
		}
	}
	 /**
	  * 通知task执行成功
	  */
	 void notifySuccess(){
		 if (mExc == null) {
			sHandler.obtainMessage(MESSAGE_POST_SUCCESS, new TaskResult(this))
					.sendToTarget();
		}
	 }
	 /***
	  * 通知task准备执行
	  */
	void notifyPre() {
		if (mExc == null) {
			sHandler.obtainMessage(MESSAGE_POST_PRE, new TaskResult(this))
					.sendToTarget();
		}
	}

	/**
	 * 获取取消的listener
	 * @return ITaskCancel 
	 */
	ITaskCancel getCancelListener() {
		return cancelListener;
	}
	/**
	 * 设置取消监听
	 * @param cancelListener 取消监听
	 */
	void setCancelListener(ITaskCancel cancelListener) {
		this.cancelListener = cancelListener;
	}



	/**
	 * 任务状态静态类
	 * @author 099
	 * 0----排队中
	 * 1----执行中
	 * 2----完成
	 */
	public static final class TaskStatus{
		/**初始状态未放到等待队列之中**/
		static final int NONE=-1;
		/**等待中**/
		static final int PENDING=0;
		/**执行中**/
		static final int RUNNING=1;
		/**结束中**/
		static final int FINISHED=2;
	}
	/***
	 * 内部Handler
	 * @author 099
	 *
	 */
	private static class InternalHandler extends Handler {
		/**
		 * 构造
		 * @param mainLooper 主线程Looper
		 */
        public InternalHandler(Looper mainLooper) {
			super(mainLooper);
		}
		@Override
        public void handleMessage(Message msg) {
			TaskResult result=(TaskResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_PROGRESS:
                	if(result.mTask.getException()==null){
                    	result.mTask.onProgressChanged(result.mData, msg.arg1);
                	}
                    break;
                case MESSAGE_POST_PRE:
                	if(result.mTask.getException()==null){
                		result.mTask.onPreExecute();
                	}
                	break;
                case MESSAGE_POST_SUCCESS:
                	if(result.mTask.getException()==null){
                		result.mTask.success();
                	}else{
                		result.mTask.fail(result.mTask.getException().getMessage());
                	}
                	break;
                case MESSAGE_POST_FAILED:
                	if(result.mTask.getException()!=null){
                		result.mTask.fail(result.mTask.getException().getMessage());
                	}
                	break;
            }
        }
    }
	/**
	 * Task结果
	 * @author 099
	 *
	 */
	private static class TaskResult {
		/**task*/
        final JHBaseTask mTask;
        /**data*/
        final Object mData;
        /**
         * 构造
         * @param task 任务
         */
        TaskResult(JHBaseTask task){
        	this(task,null);
        }
        /**
         * 构造
         * @param task 任务
         * @param data 数据
         */
        TaskResult(JHBaseTask task, Object data) {
            mTask = task;
            mData = data;
        }
    }
	/**
	 * 取消task的监听
	 * @author 099
	 *
	 */
	public interface ITaskCancel{
		/**
		 * 取消task
		 * @param task 任务
		 */
		 void cancel(JHBaseTask task);
	}
	 
}
