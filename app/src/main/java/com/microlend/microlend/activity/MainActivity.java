package com.microlend.microlend.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.adpter.LendAdpter;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int userID;
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Lend> lendList = new ArrayList<>();
    private TextView uname;

    private LendAdpter adapter;
    private int flag = 0;
    private int lYear, lMonth, lDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_main);
        ActionBar actionBar = getSupportActionBar();
        Connector.getDatabase();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
        navigationView.setCheckedItem(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_lend:
                        startActivity(new Intent(MainActivity.this, LendPeopleActivity.class));
                        break;
                    case R.id.nav_yearStati:
                        startActivity(new Intent(MainActivity.this, YearGraphActivity.class));
                        break;
                    case R.id.nav_monthStati:
                        startActivity(new Intent(MainActivity.this, MonthGraphActivity.class));
                        break;
                    case R.id.nav_peopleStati:
                        startActivity(new Intent(MainActivity.this,PersonGraphActivity.class));
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(MainActivity.this, ResetActivity.class));
                }
                return true;
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });


        initClassList();
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.rclv_list);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManger);
        adapter = new LendAdpter(lendList);
        recyclerview.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddLendItemActivity.class));
                // finish();
            }
        });
        Intent intent = getIntent();
        final String message = intent.getStringExtra("name");
        View headerView = navigationView.getHeaderView(0);
        uname = (TextView) headerView.findViewById(R.id.icon_username);
        uname.setText(message);
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
                        flag = 1;
                        initClassList();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("按照姓名查询");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                Log.w(TAG, "onQueryTextSubmit: query" + query.toString());
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                // drawerLayout.openDrawer(GravityCompat.START);
                //startActivity(new Intent(MainActivity.this, ResetActivity.class));
                //finish();
                break;

        }

        return true;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        int a = KeyEvent.KEYCODE_SEARCH || KeyEvent.KEYCODE_SEARCH;
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_ENTER:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                Toast.makeText(MainActivity.this, "enter", Toast.LENGTH_SHORT).show();

                break;
            case KeyEvent.KEYCODE_SEARCH:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                break;
//            case KeyEvent.
        }

        return super.dispatchKeyEvent(event);
    }

    protected void initClassList() {
        int n = 1;
        lendList.clear();
        getDate();
        List<Lend> cls = DataSupport.findAll(Lend.class);
        int size = cls.size();
        Log.w(TAG, "initClassList: " + size);
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
            mLend.setBack(lend.isBack());
            mLend.setBackDate(lend.getBackDate());
            int mYear = lend.getYear();
            int mMonth = lend.getMonth();
            int mDay = lend.getDay();
            if ((mYear - lYear) == 0) {
                if ((mMonth - lMonth - 1) == 0) {
                    int days = Math.abs(mDay - lDay);
                    if (days <= 3) {
                        if (!lend.isBack()) {
                            if (flag == 0) {
                                sendNotice(lend.getLoadPeopleName(), days, n);
                                n = n + 1;
                            }
                        }
                    }
                }
            }
            Log.w(TAG, "Main: id " + mLend.getId() + "  name " + mLend.getLoadPeopleName() + " money " + lend.getSumMoney());
            lendList.add(mLend);

        }
    }


    private void sendNotice(String name, int day, int n) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setContentTitle("还款提醒")
                .setContentText(name + "距离还款还剩" + day + "天")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_event_available_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_adb_black_24dp))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        manager.notify(n, notification);
    }

    public void getDate() {
        Time t = new Time();
        t.setToNow();
        lYear = t.year;
        lMonth = t.month;
        lDay = t.monthDay;
    }
}
