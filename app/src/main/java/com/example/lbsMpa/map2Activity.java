package com.example.lbsMpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class map2Activity extends AppCompatActivity {
    private Button m2Btn1,m2Btn2;
    private ListView mListView;
    private MapView mMapView;
  //  private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        Toast.makeText(map2Activity.this,"map2Activity",Toast.LENGTH_SHORT).show();
     //   mMapView = (MapView) findViewById(R.id.map2MV1);
     //   baiduMap = mMapView.getMap();

        m2Btn1 = (Button) findViewById(R.id.map2_btn1);
        m2Btn2 = (Button) findViewById(R.id.map2_btn2);
        m2Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        m2Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mListView = (ListView) findViewById(R.id.LV_1);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getdata()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Toast.makeText(map2Activity.this, "地点1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(map2Activity.this, "地点2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(map2Activity.this, "地点3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(map2Activity.this, "地点4", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(map2Activity.this, "地点5", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private List<String> getdata(){
        List<String> data  =  new ArrayList<String>();
        data.add("地点1");
        data.add("地点2");
        data.add("地点3");
        data.add("地点4");
        return data;
    }
}
