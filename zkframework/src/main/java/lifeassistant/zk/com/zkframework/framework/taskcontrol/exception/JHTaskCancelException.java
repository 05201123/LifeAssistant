package lifeassistant.zk.com.zkframework.framework.taskcontrol.exception;
/**
 * 任务主动取消的异常
 * @author 099
 *
 */
public class JHTaskCancelException  extends JHTaskException{
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**取消异常信息*/
	private static final String CANCEL_TASK_EXCEPTION="canceltaskexcepiton";
	@Override
	/**
	 * 获取错误信息
	 */
	public String getDefaultMessage() {
		return CANCEL_TASK_EXCEPTION;
	}
}
