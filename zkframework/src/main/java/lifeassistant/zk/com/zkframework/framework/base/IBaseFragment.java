package lifeassistant.zk.com.zkframework.framework.base;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.common.collect.BaseCollectFragment;
import com.jh.eventControler.EventControler;
import com.jh.framework.interfaces.InitMVC;
import com.jh.framework.interfaces.InitViews;
/***
 * Fragment(inti Mvc)
 * @author 099
 *
 */
public abstract class  IBaseFragment extends BaseCollectFragment implements InitViews,InitMVC{
	protected EventControler mEventHandler;
	private IBaseFragmentController mController;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initModelController();
		if(mController!=null){
			mController.onFragmentCreateBefore(savedInstanceState);
		}
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mController!=null){
			mController.onFragmentCreateViewBefore(inflater, container, savedInstanceState);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(mController!=null){
			mController.onFragmentViewCreatedBefore(view, savedInstanceState);
		}
		initView();
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(mController!=null){
			mController.onFragmentDestroyViewBefore();
		}
	}
	@Override
	public void onStart() {
		super.onStart();
		if(mController!=null){
			mController.onFragmentStartBefore();
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		if(mController!=null){
			mController.onFragmentResumeBefore();
		}
	}
	@Override
	public void onStop() {
		super.onStop();
		if(mController!=null){
			mController.onFragmentStopBefore();
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if(mController!=null){
			mController.onFragmentPouseBefore();
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
	public  abstract IBaseFragmentController getIBaseController();
	public  abstract EventControler getEventControler();
	public void initView(){
		getViews();
		setViews();
		setListeners();
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mEventHandler!=null){
			mEventHandler.unregister(this);
		}
		if(mController!=null){
			mController.onFragmentDestroyBefore();
		}
	}
}