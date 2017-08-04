package lifeassistant.zk.com.zkframework.framework.taskcontrol.exception;
/**
 * 任务等待时间超长异常
 * @author 099
 * 对targettask，操作异常的命名
 */
public class JHTaskWaitTimeOutException extends JHTaskException {
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**等待超时异常信息*/
	private static final String TASK_WAIT_TIMEOUT_EXCEPTION="taskwaittimeoutexception";
	@Override
	/**
	 * 获取错误信息
	 */
	public String getDefaultMessage() {
		return TASK_WAIT_TIMEOUT_EXCEPTION;
	}
	

}
