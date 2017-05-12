package com.microlend.microlend.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.model.Lend;
import com.microlend.microlend.util.Sum;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LendContentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText cardID;
    private EditText tel;
    private EditText reta;
    private EditText money;
    private EditText sum;
    private TextView date;
    private Button chooseDate;
    private FloatingActionButton fab_edit;
    private int flag = 0;
    private int id;
    private int lDay, lYear, lMonth,mYear,mDay,mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_content);
        initView();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        displayPlan(id);


        fab_edit.setOnClickListener(this);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.ed_name_content);
        cardID = (EditText) findViewById(R.id.ed_cardid_content);
        tel = (EditText) findViewById(R.id.ed_tel_content);
        reta = (EditText) findViewById(R.id.ed_reta_content);
        money = (EditText) findViewById(R.id.ed_money_content);
        sum = (EditText) findViewById(R.id.ed_sum_content);
        date = (TextView) findViewById(R.id.tv_date_content);
        chooseDate = (Button) findViewById(R.id.btn_addtime_content);
        fab_edit = (FloatingActionButton) findViewById(R.id.fab_edit_lendcontent);
        name.setEnabled(false);
        sum.setEnabled(false);
        money.setEnabled(false);
        reta.setEnabled(false);
        tel.setEnabled(false);
        cardID.setEnabled(false);
        chooseDate.setVisibility(View.INVISIBLE);
    }

    private void displayPlan(int id) {
        if (id == -1) {
            Toast.makeText(LendContentActivity.this, "无法获取数据", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Lend> lendList = DataSupport.where("id=" + id).find(Lend.class);
        for (Lend l : lendList
                ) {
            name.setText(l.getLoadPeopleName());
            cardID.setText(l.getLoadPeopleIDCard());
            tel.setText(l.getTelPhone());
            int year = l.getYear();
            int month = l.getMonth();
            int day = l.getDay();
            String retaS = String.valueOf(l.getRate());
            reta.setText(retaS);
            String moneyS = String.valueOf(l.getMoney());
            money.setText(moneyS);
            String sumS = String.valueOf(l.getSumMoney());
            sum.setText(sumS);
            date.setText(l.getYear() + "/" + l.getMonth() + "/" + l.getDay());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit_lendcontent:
                if (flag == 0) {
                    name.setEnabled(true);
                    sum.setEnabled(true);
                    money.setEnabled(true);
                    reta.setEnabled(true);
                    tel.setEnabled(true);
                    cardID.setEnabled(true);
                    chooseDate.setVisibility(View.VISIBLE);
                    chooseDate.setOnClickListener(this);
                    fab_edit.setImageResource(R.drawable.ic_check_black_24dp);
                    flag = 1;
                } else {
                    float mMoney= Float.parseFloat(money.getText().toString());
                    float mReta = Float.parseFloat(reta.getText().toString());
                    float mSun = Sum.getSum(mMoney, mReta, mYear, mMonth, lYear, lMonth);
                    String sumT = String.valueOf(mSun);
                    sum.setText(sumT);
                    name.setEnabled(false);
                    sum.setEnabled(false);
                    money.setEnabled(false);
                    reta.setEnabled(false);
                    tel.setEnabled(false);
                    cardID.setEnabled(false);
                    chooseDate.setVisibility(View.INVISIBLE);
                    fab_edit.setImageResource(R.drawable.ic_edit_black_24dp);
                    flag = 0;
                    //

                    Lend lend=new Lend();
                    lend.setLoadPeopleName(name.getText().toString());
                    lend.setLoadPeopleIDCard(cardID.getText().toString());
                    lend.setTelPhone(tel.getText().toString());
                    lend.setYear(mYear);
                    lend.setMonth(mMonth);
                    lend.setDay(mDay);
                    lend.setTelPhone(tel.getText().toString());
                    lend.setMoney(mMoney);
                    lend.setRate(mReta);
                    lend.setSumMoney(mSun);
                    lend.update(id);
                }
                break;
            case R.id.btn_addtime_content:
                getDate();
                try {
                    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            mMonth = month + 1;
                            mYear=year;
                            mDay = day;
                            date.setText(year + "/" + mMonth + "/" + day);
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(LendContentActivity.this, 0, listener, lYear, lMonth, lDay);
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }



    private void getDate() {
        Time t = new Time();
        t.setToNow();
        lYear = t.year;
        lMonth = t.month;
        lDay = t.monthDay;
    }
}
