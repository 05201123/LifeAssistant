package lifeassistant.zk.com.zkframework.framework.taskcontrol.exception;


import lifeassistant.zk.com.zkframework.framework.exception.JHException;

/***
 * 金和task异常类
 * @author 099
 *
 */
public class JHTaskException extends JHException {
	/**任务异常信息*/
	private static final String JH_TASK_EXCEPTION="jhtaskexcepiton";
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	
	@Override
	/**
	 * 获取错误信息
	 */
	public String getDefaultMessage() {
		return JH_TASK_EXCEPTION;
	}
}
