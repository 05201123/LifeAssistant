package lifeassistant.zk.com.zkframework.framework.taskcontrol.task;


import lifeassistant.zk.com.zkframework.framework.taskcontrol.JHBaseTask;
import lifeassistant.zk.com.zkframework.framework.taskcontrol.constants.TaskConstants;

/***
 * 可延迟Task
 * @author 099
 * 等待时间无限制
 */
public abstract class DelayTask extends JHBaseTask {
	@Override
	protected int getPriority() {
		return TaskConstants.TaskPriority.PRIORITY_DELAY;
	}

}
