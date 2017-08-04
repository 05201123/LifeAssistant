package lifeassistant.zk.com.zkframework.framework.taskcontrol.exception;
/**
 * 任务执行时间超长异常
 * @author 099
 */
public class JHTaskRunningTimeOutException extends JHTaskException {
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**运行超时异常信息*/
	private static final String TASK_RUNNING_TIMEOUT_EXCEPTION="taskrunningtimeoutexception";
	@Override
	/**
	 * 获取错误信息
	 */
	public String getDefaultMessage() {
		return TASK_RUNNING_TIMEOUT_EXCEPTION;
	}
	

}
