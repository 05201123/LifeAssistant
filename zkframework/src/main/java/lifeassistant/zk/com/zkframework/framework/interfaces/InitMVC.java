package lifeassistant.zk.com.zkframework.framework.interfaces;

import com.jh.eventControler.EventControler;
import com.jh.framework.base.IBaseController;
import com.jh.framework.base.IBaseModel;

/***
 * MVC初始化接口
 * @author 099
 *
 */
public interface InitMVC {
	/**
	 * 初始化Model
	 * 
	 * @return
	 */
	IBaseModel getIBaseModel();

	/***
	 * 初始化控制器
	 * 
	 * @return
	 */
	IBaseController getIBaseController();

	/***
	 * 初始化事件控制器
	 * 
	 * @return
	 */
	EventControler getEventControler();

}
