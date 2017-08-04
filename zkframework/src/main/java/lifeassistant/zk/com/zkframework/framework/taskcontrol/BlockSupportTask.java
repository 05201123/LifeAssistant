package lifeassistant.zk.com.zkframework.framework.taskcontrol;

/***
 * 支持阻塞方法的task
 * @author 099
 * 等待时间无限制
 */
public abstract class BlockSupportTask extends JHBaseTask {
	/**是否执行完*/
	 boolean  isFinished;
	 /**是否等待*/
	 boolean  isWaited;
	@Override
	void notifyFailed() {
		super.notifyFailed();
		notifyUnLock();
		
	}
	@Override
	void notifySuccess() {
		super.notifySuccess();
		notifyUnLock();
		
	}
	/**
	 * 通知解锁
	 */
	private void notifyUnLock() {
		synchronized (BlockSupportTask.this) {
			if(isFinished){
				return;
			}
			isFinished=true;
			if(isWaited){
				notifyAll();
			}
		}
	}
	/***
	 * 执行未完成阻塞到执行完成
	 * 如果未执行完，则阻塞当前线程
	 * @throws InterruptedException 
	 */
	public void unFinishedBlock() {
		synchronized (BlockSupportTask.this) {
			if(!isFinished){
				isWaited=true;
				try {
					wait(2000);
					isWaited=false;
				} catch (InterruptedException e) {
					isWaited=false;
				}
			}
		}
		
	}

}
