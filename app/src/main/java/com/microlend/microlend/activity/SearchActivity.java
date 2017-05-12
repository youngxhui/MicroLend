package com.microlend.microlend.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.microlend.microlend.R;
import com.microlend.microlend.adpter.LendAdpter;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private List<Lend> lendList = new ArrayList<>();
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.rclv_search_search);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManger);
        LendAdpter adapter = new LendAdpter(lendList);
        recyclerview.setAdapter(adapter);
        initLendList();
    }


    protected void initLendList() {
        lendList.clear();
        List<Lend> cls = DataSupport.where("loadpeoplename = ?", query).find(Lend.class);
        for (Lend lend : cls
                ) {
            Log.e(TAG, "initClassList: id : " + lend.getId() + "  name: " + lend.getLoadPeopleName());
            Lend mLend = new Lend();
            mLend.setId(lend.getId());
            mLend.setLoadPeopleName(lend.getLoadPeopleName());
            mLend.setMoney(lend.getMoney());
            mLend.setRealLoadTime(lend.getRealLoadTime());
            mLend.setTelPhone(lend.getTelPhone());
            mLend.setSumMoney(lend.getSumMoney());
            mLend.setYear(lend.getYear());
            mLend.setMonth(lend.getMonth());
            mLend.setDay(lend.getDay());
            lendList.add(mLend);
        }
    }
}
