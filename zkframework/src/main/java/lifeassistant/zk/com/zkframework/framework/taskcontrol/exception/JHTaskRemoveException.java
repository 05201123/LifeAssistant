package lifeassistant.zk.com.zkframework.framework.taskcontrol.exception;
/**
 * 任务主动删除的异常
 * @author 099
 *
 */
public class JHTaskRemoveException  extends JHTaskException{
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**移除异常信息*/
	private static final String REMOVE_TASK_EXCEPTION="removetaskexcepiton";
	@Override
	/**
	 * 获取错误信息
	 */
	public String getDefaultMessage() {
		return REMOVE_TASK_EXCEPTION;
	}
}
