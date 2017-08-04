package lifeassistant.zk.com.mylifeassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import lifeassistant.zk.com.mylifeassistant.R;
import lifeassistant.zk.com.mylifeassistant.dao.CostRecordDao;
import lifeassistant.zk.com.mylifeassistant.dto.CostRecordDTO;
import lifeassistant.zk.com.mylifeassistant.utils.DataUtil;
import lifeassistant.zk.com.mylifeassistant.utils.DateUtil;

/**
 * Created by 099 on 2017/8/1.
 * 消费查询Activity
 */

public class CostQureyActivity extends Activity {


    private EditText dateCostET;
    private Button queryCostBT;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_query);
        dateCostET = (EditText) findViewById(R.id.date_cost_et);
        queryCostBT= (Button) findViewById(R.id.query_cost_bt);
        tv = (TextView) findViewById(R.id.details_info_tv);
        queryCostBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=dateCostET.getText().toString().trim();
                if(TextUtils.isEmpty(date)){
                    date=DateUtil.getDay(new Date());
                }
                CostRecordDTO dto=CostRecordDao.getInstance().queryCostInfoBydate(date);
                if(dto!=null){
                    tv.setText(dto.toString());
                }else{
                    tv.setText("暂无数据");
                }
            }
        });

    }
}
