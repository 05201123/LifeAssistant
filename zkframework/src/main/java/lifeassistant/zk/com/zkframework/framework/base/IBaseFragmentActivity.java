package lifeassistant.zk.com.zkframework.framework.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.jh.eventControler.EventControler;
import com.jh.fragment.JHFragmentActivity;
import com.jh.framework.interfaces.InitMVC;
import com.jh.framework.interfaces.InitViews;

public abstract class  IBaseFragmentActivity extends JHFragmentActivity implements InitViews,InitMVC{
	protected EventControler mEventHandler;
	private IBaseActivityController mController;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initModelController();
		if(mController!=null){
			mController.onActivtyCreateBefore();
		}
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		if(mController!=null){
			mController.onActivityStartBefore();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(mController!=null){
			mController.onActivityResumeBefore();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		if(mController!=null){
			mController.onActivityStopBefore();
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(mController!=null){
			mController.onActivityPouseBefore();
		}
	}
	/**
	 * 初始化
	 */
	private void initModelController() {
		mEventHandler=getEventControler();
		if(mEventHandler!=null){
			mEventHandler.register(this);
		}
		mController=getIBaseController();
		if(mController!=null){
			mController.setmIBaseModel(getIBaseModel());
			mController.setEventHandler(mEventHandler);
		}
		
	}	
	public abstract IBaseModel getIBaseModel();
	public  abstract IBaseActivityController getIBaseController();
	public  abstract EventControler getEventControler();
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}
	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}
	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}
	public void initView(){
		getViews();
		setViews();
		setListeners();
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mEventHandler!=null){
			mEventHandler.unregister(this);
		}
		if(mController!=null){
			mController.onActivityDestroyBefore();
		}
	}
	
}