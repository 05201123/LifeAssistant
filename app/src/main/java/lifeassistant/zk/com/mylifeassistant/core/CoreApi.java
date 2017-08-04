package lifeassistant.zk.com.mylifeassistant.core;

import android.content.Context;

/**
 * Created by 099 on 2017/8/1.
 * 核心api
 */

public class CoreApi {
    private final  static CoreApi mInstance=new CoreApi();
    private Context mContext;
    public static CoreApi getInstance(){
        return mInstance;
    }
    private CoreApi(){

    }
    public  Context getApplicationContext(){
        if (mContext==null){
            throw new RuntimeException("mContext is null");
        }
        return mContext;
    }
    public void setApplicationContext(Context context){
        this.mContext=context;
    }
}
