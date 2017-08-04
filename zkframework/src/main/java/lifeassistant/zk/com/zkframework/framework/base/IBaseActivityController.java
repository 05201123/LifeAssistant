package lifeassistant.zk.com.zkframework.framework.base;
import android.content.Context;
/**
 * 基础控制类
 * @author 099
 *
 */
public abstract class IBaseActivityController extends IBaseController{

	public IBaseActivityController(Context context) {
		super(context);
	}
	/**用于初始化一些信息，比如oncreate中调用，初始化一些信息**/
	public void onActivtyCreateBefore(){
		
	};
	
	public void onActivityStartBefore(){
		
	};
	public void onActivityResumeBefore(){
		
	};
	public void onActivityPouseBefore(){
		
	};
	public void onActivityStopBefore(){
		
	};
	
	/**用于销毁一些信息，比如destroy中调用，关闭数据库等**/
	public void onActivityDestroyBefore(){
		
	}
	
}