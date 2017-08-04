package lifeassistant.zk.com.zkframework.framework.interfaces;
/**
 * 获取数据回调接口
 * @author 099
 *
 */
public interface IGetDataCallBack <T>{
	/***
	 * 获取数据成功
	 * @param result
	 * @param msg
	 */
	public void getDataSuccess(T result, String flag);
	/***
	 * 获取数据失败
	 * @param errmsg
	 */
	public void getDataFailed(String errmsg, String flag);
}
