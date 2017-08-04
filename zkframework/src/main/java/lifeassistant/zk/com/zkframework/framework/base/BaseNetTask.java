/*
package lifeassistant.zk.com.zkframework.framework.base;

import android.content.Context;
import android.text.TextUtils;

import lifeassistant.zk.com.zkframework.framework.exception.JHException;
import lifeassistant.zk.com.zkframework.framework.taskcontrol.task.ForeGroundTask;


*/
/**
 * Task从服务器请求数据并接收
 * 
 * @author 099
 * @since 2014-12-12
 *//*

public abstract class BaseNetTask<T,Q> extends ForeGroundTask {
	*/
/***
	 * 上下文
	 *//*

	private Context mContext;
	*/
/** 回调 **//*

	private IGetDataCallBack<Q> callback;
	*/
/** 默认错误提示语 *//*

	private String errorMsg;
	*/
/** 请求httpurl *//*

	private String reqUrl;
	*/
/** 请求dto **//*

	private T reqDto;
	*/
/** 解析的res的class值 *//*

	private Class<?> transformClass;
	*/
/** 是否解析的list数据 *//*

	private boolean isPraseList = false;
	private String flag;
	private Q returnInfo;

	public BaseNetTask(Context context, IGetDataCallBack<Q> callback,
			String reqUrl, String errMsg, Class<?> transformClass) {
		this.mContext = context;
		this.callback = callback;
		this.errorMsg = errMsg;
		this.reqUrl = reqUrl;
		this.transformClass = transformClass;
	}

	public BaseNetTask(Context context, IGetDataCallBack<Q> callback,
			String reqUrl, String errMsg, Class<?> transformClass,
			boolean isPraseList) {
		this.mContext = context;
		this.callback = callback;
		this.errorMsg = errMsg;
		this.reqUrl = reqUrl;
		this.transformClass = transformClass;
		this.isPraseList = isPraseList;
	}
	public BaseNetTask(Context context, IGetDataCallBack<Q> callback,
			String reqUrl, String errMsg, Class<?> transformClass,
			boolean isPraseList,String flag) {
		this.mContext = context;
		this.callback = callback;
		this.errorMsg = errMsg;
		this.reqUrl = reqUrl;
		this.transformClass = transformClass;
		this.isPraseList = isPraseList;
		this.flag=flag;
	}
	*/
/**初始化请求dto*//*

	public abstract T initReqDto();
	
	@Override
	public void doTask() throws JHException {
		if (!NetStatus.hasNet(mContext)) {
			throw new JHException("无网络");
		}
		reqDto = initReqDto();
		try {
			JHHttpClient client = ContextDTO.getWebClient();
			if (reqDto == null) {
				// 等待加入string
				throw new JHException(errorMsg);
			}
			String result = client
					.request(this.reqUrl, GsonUtil.format(reqDto));
			if (result == null) {
				throw new JHException(errorMsg);
			} else {

				if (isPraseList) {
					returnInfo  = (Q) GsonUtil.parseList(result, transformClass);

				} else {
					returnInfo = (Q) GsonUtil.parseMessage(result, transformClass);
				}

				if (returnInfo == null) {
					throw new JHException(errorMsg);
				}
			}
		} catch (UnInitiateException e) {
			throw new JHException(errorMsg);
		}catch (Exception e) {
			throw new JHException(errorMsg);
		}

	}

	@Override
	public void success() {
		super.success();
		if(callback!=null){
			callback.getDataSuccess(returnInfo,flag);
			
		}
	}

	@Override
	public void fail(String errorMessage) {
		super.fail(errorMessage);
		if(callback!=null){
			if(TextUtils.isEmpty(errorMessage)){
				errorMessage=errorMsg;
			}
			callback.getDataFailed(errorMessage,flag);
		}
	}	

}
*/
