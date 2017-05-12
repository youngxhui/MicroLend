package com.microlend.microlend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.microlend.microlend.R;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class YearGraphActivity extends AppCompatActivity {

    private static final String TAG = "YearGraphActivity";
    private ColumnChartView chart;
    private ColumnChartData data;
    public final String[] years = new String[]{"2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"
            , "2025", "2026", "2027"};
    private List<Float> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_graph);

        chart = (ColumnChartView) findViewById(R.id.columnchart);
        chart.setZoomEnabled(true);

        int numColumns = 10;

        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();
        //子列数据集合
        List<SubcolumnValue> values = null;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();

        List<Float> yearmoney = new ArrayList<Float>();
        for (int i = 2017; i < 2028; i++) {
            List<Lend> cls = DataSupport.select("summoney")
                    .where("year=?", String.valueOf(i))
                    .find(Lend.class);
            List<Float> m = new ArrayList<>();
            for (Lend l : cls
                    ) {
                m.add(l.getSumMoney());

                Log.w(TAG, "money is "+l.getSumMoney());
            }
            yearmoney.add(sum(m));
        }



        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();

            //为每一柱图添加颜色和数值
            float f = yearmoney.get(i);
            values.add(new SubcolumnValue(f, ChartUtils.pickColor()));

            //创建Column对象
            Column column = new Column(values);
            //这一步是能让圆柱标注数据显示带小数的重要一步
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
            column.setFormatter(chartValueFormatter);
            //是否有数据标注
            column.setHasLabels(true);
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(years[i]));
        }


        //创建一个带有之前圆柱对象column集合的ColumnChartData
        data = new ColumnChartData(columns);

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("还款额");
        axisY.hasLines();
        axisY.setTextColor(Color.BLACK);

        axisX.hasLines();
        axisX.setTextColor(Color.BLACK);
        axisX.setValues(axisValues);
        //把X轴Y轴数据设置到ColumnChartData 对象中
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        //给表填充数据，显示出来
        chart.setColumnChartData(data);

    }

    private static float sum(List<Float> list) {
        float a = 0;
        for (int i = 0; i < list.size(); i++) {
            a += list.get(i);
        }
        return a;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startActivity(new Intent(YearGraphActivity.this, MainActivity.class));
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
