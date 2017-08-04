package lifeassistant.zk.com.zkframework.framework.taskcontrol.constants;
/**
 * task中一些常量
 * @author 099
 * @since 2016-3-30
 */
public interface TaskConstants {
	/**全部任务执行完**/
	String ALL_TASK="all_task";
	/**
	 * 任务权重静态类
	 * @author 099
	 * 1----可延迟
	 * 2----普通
	 * 3----重要
	 * 4----立即执行
	 */
	public static  final class TaskPriority{
		/**立即执行**/
		public static final int PRIORITY_IMMEDIATE=4;
		/**重要**/
		public static final int PRIORITY_FOREGROUND=3;
		/**普通**/
		public static final int PRIORITY_NORMAL=2;
		/**可延迟**/
		public static final int PRIORITY_DELAY=1;
	}
}
