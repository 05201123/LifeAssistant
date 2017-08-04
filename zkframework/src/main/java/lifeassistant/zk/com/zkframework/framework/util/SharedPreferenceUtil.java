package lifeassistant.zk.com.zkframework.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharePreferenced工具类
 *  shared_prefs
 *  name.xml或者activityName.xml
 * @author 099
 * 
 */
public class SharedPreferenceUtil {
	/** SharedPreferences 成员变量 **/
	private SharedPreferences sp;
	/** Editor 编辑器 **/
	private Editor editor;
	/**
	 * 
	 * @param context
	 * @param name
	 * @param mode
	 */
	public SharedPreferenceUtil(Context context, String name, int mode){
		this(context,name,mode,true);
	}
	/**
	 * 
	 * @param activity
	 * @param mode
	 */
	public SharedPreferenceUtil(Activity activity, int mode){
		this(activity,mode,true);
	}
	
	/**
	 * 
	 * @param context
	 * @param name
	 * @param mode
	 * @param isInitEditor 是否编辑
	 */
	public SharedPreferenceUtil(Context context, String name, int mode,
			boolean isInitEditor) {
		sp = context.getSharedPreferences(name, mode);
		initEditor(isInitEditor);
	}
	/**
	 * 
	 * @param activity
	 * @param mode
	 * @param isInitEditor 是否编辑
	 */
	public SharedPreferenceUtil(Activity activity, int mode,
			boolean isInitEditor) {
		sp = activity.getPreferences(mode);
		initEditor(isInitEditor);
	}

	/** 初始化编辑器 **/
	public void initEditor(boolean isInitEditor) {
		if (isInitEditor) {
			editor = sp.edit();
		}
	}

	/***
	 * 存储Boolean类型的数据(默认直接提交)
	 * 
	 * @param key
	 * @param value
	 */
	public void saveBoolKey(String key, boolean value) {
		saveBoolKey(key,value,true);
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @param isCommit
	 */
	public void saveBoolKey(String key, boolean value,boolean isCommit) {
		editor.putBoolean(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取bool型的值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean loadBoolKey(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}
	/***
	 * 存储Boolean类型的数据(默认直接提交)
	 * 
	 * @param key
	 * @param value
	 */
	public void saveIntKey(String key, int value) {
		saveIntKey(key,value,true);
	}
	/**
	 * 存储Boolean类型的数据
	 * @param key
	 * @param value
	 * @param isCommit
	 */
	public void saveIntKey(String key, int value,boolean isCommit) {
		editor.putInt(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取Int型的值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int loadIntKey(String key, int defValue) {
		return sp.getInt(key, defValue);
	}
	/***
	 * 存储long类型的数据(默认直接提交)
	 * 
	 * @param key
	 * @param value
	 */
	public void saveLongKey(String key, long value) {
		saveLongKey(key,value,true);
	}
	/**
	 * 存储long类型的数据
	 * @param key
	 * @param value
	 * @param isCommit
	 */
	public void saveLongKey(String key, long value,boolean isCommit) {
		editor.putLong(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取long型的值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long loadLongKey(String key, long defValue) {
		return sp.getLong(key, defValue);
	}
	/***
	 * 存储Float类型的数据(默认直接提交)
	 * 
	 * @param key
	 * @param value
	 */
	public void saveFloatKey(String key, float value) {
		saveFloatKey(key,value,true);
	}
	/**
	 * 存储Float类型的数据
	 * @param key
	 * @param value
	 * @param isCommit
	 */
	public void saveFloatKey(String key, float value,boolean isCommit) {
		editor.putFloat(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取float型的值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float loadFloatKey(String key, float defValue) {
		return sp.getFloat(key, defValue);
	}
	/***
	 * 存储String类型的数据(默认直接提交)
	 * 
	 * @param key
	 * @param value
	 */
	public void saveStringKey(String key, String value) {
		saveStringKey(key,value,true);
	}
	/**
	 * 存储String类型的数据
	 * @param key
	 * @param value
	 * @param isCommit
	 */
	public void saveStringKey(String key, String value,boolean isCommit) {
		editor.putString(key, value);
		commitEditor(isCommit);
		
	}
	/**
	 * 获取String型的值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String loadStringKey(String key, String defValue) {
		return sp.getString(key, defValue);
	}
	
	
	
	/**
	 * 是否提交，储存数据
	 * @param isCommit
	 */
	private void commitEditor(boolean isCommit) {
		if(isCommit){
			editor.commit();
		}
	}
	/***
	 * 提交数据（用于单独提交数据）
	 */
	public void commitData(){
		commitEditor(true);
	}

	/***
	 * 清空编辑器
	 */
	public void clearEditor() {
		editor.clear();
		editor.commit();

	}

}
