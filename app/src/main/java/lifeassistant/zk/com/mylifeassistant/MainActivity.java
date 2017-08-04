package lifeassistant.zk.com.mylifeassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import lifeassistant.zk.com.mylifeassistant.activity.CostQureyActivity;
import lifeassistant.zk.com.mylifeassistant.activity.CostRecordActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sava_cost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CostRecordActivity.class));
            }
        });

        findViewById(R.id.query_cost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CostQureyActivity.class));
            }
        });
    }
}
