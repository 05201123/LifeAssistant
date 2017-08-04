package lifeassistant.zk.com.mylifeassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import lifeassistant.zk.com.mylifeassistant.R;
import lifeassistant.zk.com.mylifeassistant.dao.CostRecordDao;
import lifeassistant.zk.com.mylifeassistant.dto.CostRecordDTO;
import lifeassistant.zk.com.mylifeassistant.utils.DataUtil;
import lifeassistant.zk.com.mylifeassistant.utils.DateUtil;

/**
 * Created by 099 on 2017/8/1.
 */

public class CostRecordActivity extends Activity {

    private EditText breakfastCostET;
    private EditText dinnerCostET;
    private EditText lunchCostET;
    private EditText extraCostET;
    private EditText childCostET;
    private EditText wifeCostET;
    private EditText extraCostdspET;
    private EditText costDateET;
    private Button saveCostBT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_record);
        breakfastCostET = (EditText) findViewById(R.id.breakfast_cost_et);
        dinnerCostET = (EditText) findViewById(R.id.dinner_cost_et);
        lunchCostET = (EditText) findViewById(R.id.lunch_cost_et);
        extraCostET = (EditText) findViewById(R.id.extra_cost_et);
        childCostET = (EditText) findViewById(R.id.child_cost_et);
        wifeCostET = (EditText) findViewById(R.id.wife_cost_et);
        extraCostdspET = (EditText) findViewById(R.id.extra_costdsp_et);
        costDateET = (EditText) findViewById(R.id.cost_data_et);
        saveCostBT = (Button) findViewById(R.id.save_cost_bt);
        saveCostBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CostRecordDTO dto=new CostRecordDTO();
                dto.setBreakfastCost(DataUtil.getDoubleByString(breakfastCostET.getText().toString().trim()));
                dto.setLunch_cost(DataUtil.getDoubleByString(lunchCostET.getText().toString().trim()));
                dto.setDinner_cost(DataUtil.getDoubleByString(dinnerCostET.getText().toString().trim()));
                dto.setChild_cost(DataUtil.getDoubleByString(childCostET.getText().toString().trim()));
                dto.setExtra_cost(DataUtil.getDoubleByString(extraCostET.getText().toString().trim()));
                dto.setExtra_cost_dsp(extraCostdspET.getText().toString().trim());
                dto.setWife_cost(DataUtil.getDoubleByString(wifeCostET.getText().toString().trim()));
                Date date=new Date();
                if(TextUtils.isEmpty(costDateET.getText().toString())){
                    dto.setCost_time(DateUtil.getDay(date));
                }else{
                    dto.setCost_time(costDateET.getText().toString().trim());
                }
                dto.setSumbit_time(date.getTime());
                dto.setTotal_money(CostRecordDTO.getTotalCost(dto));
                CostRecordDao.getInstance().saveData(dto);
                Toast.makeText(CostRecordActivity.this,"保存成功",Toast.LENGTH_SHORT).show();


            }
        });
    }
}
