package com.uebung.maus.getingstarted.pogba.mysimplepro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity {


    private ArrayList<MyData> myList = new ArrayList<>();
    private MyData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        data = (MyData) getIntent().getSerializableExtra(FirstActivity.EXTRA_MSG);
        addData(data);
        //myList.add(data);
        //myList.iterator().hasNext();

        DataAdapter adapter = new DataAdapter(this, 0, myList);
        adapter.notifyDataSetChanged();

        ListView listView = findViewById(R.id.lw);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                MyData data = myList.get(position);

                intent.putExtra("data", data);

                startActivityForResult(intent, 1);
            }
        });


        //this.myData = new MyData();



        //this.myData.setDataName(extra);

       // myList.add(data);

        //this.adapter = new ArrayAdapter<>(this, R.layout.activity_second, myList);

        //listView.setAdapter(adapter);



        //textView.setText(data.getDataName());
    }

    private void addData(MyData data) {
        ArrayList<MyData> list = new ArrayList<>();
        if (!list.contains(data)){
            list.add(data);
            list.iterator().next();
        }else list.add(list.size()+1, data);
        this.myList = list;
    }



   /* private class CustomAdapt extends ArrayAdapter<MyData>{

        public CustomAdapt(final Activity context, ArrayList<MyData> dataList){
            super(context, R.layout.activity_second, dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyData myData = getItem(position);

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.activity_second, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.data);

            textView.setText(myData.getDataName());

            return  convertView;
        }
    }*/
}
