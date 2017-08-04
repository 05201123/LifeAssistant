package lifeassistant.zk.com.zkframework.framework.base;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 基础控制类
 * @author 099
 * @param <T>
 *
 */
public abstract class IBaseFragmentController extends IBaseController{

	public IBaseFragmentController(Context context) {
		super(context);
	}
	/**用于初始化一些信息，比如oncreate中调用，初始化一些信息**/
	public void onFragmentCreateBefore(Bundle savedInstanceState){
		
	};
	public void onFragmentCreateViewBefore(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
	}
	public void onFragmentViewCreatedBefore(View view, Bundle savedInstanceState){
		
	}
	public void onFragmentDestroyViewBefore() {
		
	}
	public void onFragmentStartBefore(){
		
	};
	public void onFragmentResumeBefore(){
		
	};
	public void onFragmentPouseBefore(){
		
	};
	public void onFragmentStopBefore(){
		
	};
	
	/**用于销毁一些信息，比如destroy中调用，关闭数据库等**/
	public void onFragmentDestroyBefore(){
		
	}
	
}