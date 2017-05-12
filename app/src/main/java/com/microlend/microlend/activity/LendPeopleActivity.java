package com.microlend.microlend.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.microlend.microlend.R;
import com.microlend.microlend.adpter.PeopleAdpter;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LendPeopleActivity extends AppCompatActivity {

    private List<Lend> lendList = new ArrayList<>();
    private PeopleAdpter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_people);
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.rclv_list_people);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_people);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManger);
        adapter = new PeopleAdpter(lendList);
        recyclerview.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        initPeopleList();
    }


    private void refreshList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lendList.clear();
                        initPeopleList();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initPeopleList() {
        lendList.clear();
        List<Lend> cls = DataSupport.findAll(Lend.class);

        for (Lend lend : cls
                ) {
            Lend mLend = new Lend();
            mLend.setId(lend.getId());
            mLend.setLoadPeopleName(lend.getLoadPeopleName());
            mLend.setLoadPeopleIDCard(lend.getLoadPeopleIDCard());
            mLend.setTelPhone(lend.getTelPhone());
            lendList.add(mLend);
        }
    }
}
