package lifeassistant.zk.com.mylifeassistant.application;

import android.app.Application;

import lifeassistant.zk.com.mylifeassistant.core.CoreApi;

/**
 * Created by 099 on 2017/8/2.
 */

public class ZKApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CoreApi.getInstance().setApplicationContext(this);
    }
}
