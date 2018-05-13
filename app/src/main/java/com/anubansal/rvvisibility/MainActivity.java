package com.anubansal.rvvisibility;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.anubansal.rvvisibility.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;
    MyAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ArrayList<String> dataList = initializeArrayList();
        adapter = new MyAdapter(dataList);
        layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(adapter);
        layoutManager.findFirstVisibleItemPosition();
        layoutManager.findLastVisibleItemPosition();
//        for now, making it work on ver. >= 23
        if (Build.VERSION.SDK_INT >= 23)
            mBinding.recyclerView.setOnScrollChangeListener(onScrollChangeListener);
    }

    private View.OnScrollChangeListener onScrollChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3) {
            Log.d(TAG, "first visible position : " + layoutManager.findFirstVisibleItemPosition());
            Log.d(TAG, "last visible position : " + layoutManager.findLastVisibleItemPosition());
        }
    };

    private ArrayList<String> initializeArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        list.add("29");
        list.add("30");
        return list;
    }
}
