package lifeassistant.zk.com.zkframework.framework.taskcontrol;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.util.Log;

import com.jh.app.taskcontrol.callback.IThreadPoolStrategy;
import com.jh.app.taskcontrol.handler.JHTaskHandler;
import com.jh.app.taskcontrol.runnable.WorkerRunnable;
/**
 * 任务线程池
 * @author 099
 * @since 2016-3-31
 * temp线程池，打算用延迟销毁的方式，以免频繁的校验
 * //如果以后有机会，可以根据手机性能，型号，来决定使用哪种线程策略
 */
public class JHTaskThreadPool {
    /**金和线程池选择策略**/
    private IThreadPoolStrategy iThreadPoolStrategy;
    /**
     * 构造
     * @param corePoolSize 线程池线程数量
     * @param maximumPoolSize 最大数量
     * @param iThreadPoolStrategy 线程池策略
     * @param handler handler
     */
	public JHTaskThreadPool(int corePoolSize,
            int maximumPoolSize,IThreadPoolStrategy iThreadPoolStrategy,Handler handler){
		if (corePoolSize <= 0 || maximumPoolSize <= 0 ||maximumPoolSize <= corePoolSize){
			throw new IllegalArgumentException();
		}
        if(iThreadPoolStrategy==null){
        	this.iThreadPoolStrategy=new JHDefaultThreadPool(corePoolSize,maximumPoolSize-corePoolSize);
		}
	}
	/**
	 * 执行runnalbe
	 * @param runnable runnable
	 */
	public  void executeRunnable(WorkerRunnable runnable){
		if(runnable==null){
			 throw new NullPointerException();
		}
		iThreadPoolStrategy.execute(runnable);
	}
	/**
	 * 是否可以执行runnalbe
	 * @return 	true 有空闲
	 * 			false 满栈
	 */
	public  boolean isCanExecRunnable(){
		
		return iThreadPoolStrategy.isFree();
	
	}
	/**
	 * 是否可以强制执行runnalbe
	 * @return 	true 有空闲
	 * 			false 满栈
	 */
	public  boolean isCanForceExecRunnable(){
		
		return iThreadPoolStrategy.isCanFroceExec();
	}
	/***
	 * 线程完成执行
	 * @param mIsTempThreadPool 是否是用了临时线程池
	 */
	public  void exeFinished(boolean mIsTempThreadPool) {
		
		iThreadPoolStrategy.exeFinished(mIsTempThreadPool);
	}
	
	/***
	 * 金和默认线程池
	 * @author 099
	 * @since 2016-3-31
	 */
	private class JHDefaultThreadPool implements IThreadPoolStrategy{
		/**锁*/
		private Object mainLock = new Object();
		 /**子线程Handler*/
	    private Handler mHandler;
		/**常规线程池**/
		private ThreadPoolExecutor executorService;
		/**临时线程池**/
		private ThreadPoolExecutor tempExecutorService;
		/**临时线程池数量**/
		private int mTempTreadPoolCount;
		/**临时线程池数量**/
		private int mCoreTreadPoolCount;
		/**临时线程池一分钟之内无任务，直接释放*/
		private static final long OVERLOAD_FREETIMEOUT=1000*60;
		/**常规线程池，当前执行的数量*/
		private volatile int curExeCoreCount=0;
		/**临时线程池，当前执行的数量**/
		private volatile int curExeTempCount=0;
		
		/**过载空闲超时的runnable**/
		private Runnable mOverLoadRunnable=new Runnable() {
			@Override
			public void run() {
				synchronized(mainLock){
					if(tempExecutorService!=null){
						tempExecutorService.shutdown();
						tempExecutorService=null;
						//TODO print debug
					}
				}
			}
		};
		/***
		 * 构造
		 * @param corePoolSize 当前线程池的size
		 * @param tempPoolSize 临时线程池的size
		 */
		private JHDefaultThreadPool(int corePoolSize,int tempPoolSize){
			mHandler=JHTaskHandler.getTaskHandler();
			mCoreTreadPoolCount=corePoolSize;
			executorService=newFixedThreadPool(corePoolSize);
			mTempTreadPoolCount=tempPoolSize;
		}
		/**
		 * 生成线程池{@link Executors #newFixedThreadPool(int)}
		 * @param nThreads 线程数量
		 * @return ThreadPoolExecutor 线程池
		 */
		private ThreadPoolExecutor newFixedThreadPool(int nThreads){
			return new ThreadPoolExecutor(nThreads, nThreads,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
		}
		@Override
		public void execute(WorkerRunnable runnable) {
			if(!runnable.ismIsTempThreadPool()){
				synchronized (JHDefaultThreadPool.class) {
					curExeCoreCount++;
				}
//				Log.e("bbbbbbbbb", "curExeCoreCount ="+curExeCoreCount);//TODO print debug
				executorService.execute(runnable);
			}else{
				synchronized(mainLock){
					if (tempExecutorService == null) {
						tempExecutorService = newFixedThreadPool(mTempTreadPoolCount);
					}
					synchronized (JHDefaultThreadPool.class) {
						curExeTempCount++;// TODO print debug
					}
					tempExecutorService.execute(runnable);
					mHandler.removeCallbacks(mOverLoadRunnable);
					mHandler.postDelayed(mOverLoadRunnable,
							OVERLOAD_FREETIMEOUT);
				}
			}
			
		}
		@Override
		public boolean isFree() {
			return curExeCoreCount<mCoreTreadPoolCount;
		}
		@Override
		public boolean isCanFroceExec() {
			/**临时线程池满栈，不执行**/
			boolean temp=true;
			synchronized(mainLock){
				if(tempExecutorService!=null&&curExeTempCount==mTempTreadPoolCount){
					temp= false;
				}
			}
			
			return temp;
		}
		@Override
		public void exeFinished(boolean mIsTempThreadPool) {
			if(mIsTempThreadPool){
				synchronized (JHDefaultThreadPool.class) {
					curExeTempCount--;
				}
//				Log.e("cccccccccc", "curExeTempCount ="+curExeTempCount);//TODO print debug
			}else{
				synchronized (JHDefaultThreadPool.class) {
					curExeCoreCount--;
				}
//				Log.e("aaaaaaaaaa", "curExeCoreCount ="+curExeCoreCount);//TODO print debug
			}
			
			
		}
	}

	
}
