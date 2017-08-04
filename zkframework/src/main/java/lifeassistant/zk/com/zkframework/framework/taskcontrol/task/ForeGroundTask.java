package lifeassistant.zk.com.zkframework.framework.taskcontrol.task;

import com.jh.app.taskcontrol.JHBaseTask;
import com.jh.app.taskcontrol.constants.TaskConstants.TaskPriority;
/**
 * 前台执行的task，activity级别
 * @author 099
 *
 */
public abstract class ForeGroundTask extends JHBaseTask {
    @Override
    protected int getPriority() {
    	return TaskPriority.PRIORITY_FOREGROUND;
    }
    
}
