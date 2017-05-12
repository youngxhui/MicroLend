package com.microlend.microlend.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.model.Lend;
import com.microlend.microlend.util.Sum;

import org.litepal.crud.DataSupport;

public class AddLendItemActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_lendName;
    private TextView tv_IdCard;
    private TextView tv_tel;
    private TextView tv_money;
    private TextView tv_rate;
    private TextView tv_startDate;
    private TextView tv_sum;

    private Button btn_chooseDate;
    private Button btn_submit;

    private boolean flag;

    private int lDay;
    private int lMonth;
    private int lYear;

    private int mDay;
    private int mMonth;
    private int myear;

    private static final String TAG = "AddLendItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lend_item);
        initView();

        btn_chooseDate.setOnClickListener(this);
        btn_submit.setOnClickListener(this);


    }

    protected void initView() {
        tv_lendName = (TextView) findViewById(R.id.tv_lendname_addlend);
        tv_IdCard = (TextView) findViewById(R.id.tv_lendid_addlend);
        tv_money = (TextView) findViewById(R.id.tv_money_addlend);
        tv_tel = (TextView) findViewById(R.id.tv_tel_addlend);
        tv_rate = (TextView) findViewById(R.id.tv_reta_addlend);
        tv_startDate = (TextView) findViewById(R.id.tv_startdate_addlend);
//        tv_sum = (TextView) findViewById(R.id.tv_sum_addlend);
        btn_chooseDate = (Button) findViewById(R.id.btn_addtime_addlend);
        btn_submit = (Button) findViewById(R.id.btn_submit_addlend);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addtime_addlend:
                getDate();
                try {
                    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            Log.w(TAG, "onDateSet:  year" + year + " month " + month + " day " + day);
                            mMonth = month + 1;
                            mDay = day;
                            myear = year;
                            tv_startDate.setText(myear + "/" + mMonth + "/" + day);
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(AddLendItemActivity.this, 0, listener, lYear, lMonth, lDay);
                    if (myear < lYear && mMonth < lMonth && mDay < lDay) {
                        flag = false;
                    }
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_submit_addlend:

                setInfo();
                startActivity(new Intent(AddLendItemActivity.this, MainActivity.class));
                finish();

                break;
        }
    }


    private void setInfo() {
        int f = 0;
        if (!flag) {
            f = 1;
        }
        float money = 0f;
        if (tv_money.getText().toString().trim().isEmpty()) {
            f = 1;
        } else {
            money = Float.parseFloat(tv_money.getText().toString());
        }
        float reta = 0f;
        if (tv_rate.getText().toString().trim().isEmpty()) {
            f = 1;
        } else {
            reta = Float.parseFloat(tv_rate.getText().toString().trim());
        }
        if (f == 1) {
            Toast.makeText(AddLendItemActivity.this, "0", Toast.LENGTH_SHORT).show();
            return;
        }

        float sum = Sum.getSum(money, reta, myear, mMonth, lYear, lMonth);
        Lend lend = new Lend();
        lend.setLoadPeopleName(tv_lendName.getText().toString());
        lend.setTelPhone(tv_tel.getText().toString());
        lend.setLoadPeopleIDCard(tv_IdCard.toString());
        lend.setMoney(money);
        lend.setLoadPeopleIDCard(tv_IdCard.getText().toString());
        String date = tv_startDate.getText().toString();
        lend.setRate(reta);
        lend.setMonth(mMonth);
        lend.setYear(myear);
        lend.setDay(mDay);
//        lend.setStartDate(date);
        lend.setSumMoney(sum);
        Toast.makeText(AddLendItemActivity.this, "利息是：" + sum, Toast.LENGTH_LONG).show();
        try {
            lend.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(AddLendItemActivity.this, MainActivity.class));
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void getDate() {
        Time t = new Time();
        t.setToNow();
        lYear = t.year;
        lMonth = t.month;
        lDay = t.monthDay;
        Log.w(TAG, "getDate: year" + lYear);
        Log.w(TAG, "getDate: month" + lMonth);
        Log.w(TAG, "getDate: day" + lDay);

    }

}
