package com.microlend.microlend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MonthGraphActivity extends AppCompatActivity {

    private static final String TAG = "MonthGraphActivity";
    private LineChartView lineChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChartView= (LineChartView) findViewById(R.id.linechart);
        lineChartView.setInteractive(true);
        lineChartView.setZoomEnabled(true);

        List<PointValue> values = new ArrayList<PointValue>();
        List<Line> lines = new ArrayList<Line>();

        for (int i = 1; i < 13; i++) {
            List<Lend> cls =DataSupport.select("summoney","back")
                    .where("month=?", String.valueOf(i))
                    .find(Lend.class);
            List<Float> m=new ArrayList<>();
            for (Lend l:cls
                 ) {
                if (!l.isBack()){
                    m.add(l.getSumMoney());
                }
            }
            values.add(new PointValue(i,sum(m)));
        }
        Line line = new Line(values).setColor(Color.BLUE);//声明线并设置颜色
        line.setCubic(true);//设置是平滑的还是直的

        line.setHasLabelsOnlyForSelected(false);
        line.setHasLabels(true);
        lines.add(line);
        lineChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);//设置缩放方向

        LineChartValueFormatter chartValueFormatter=new SimpleLineChartValueFormatter(2);
        line.setFormatter(chartValueFormatter);

        LineChartData data = new LineChartData();
        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴
        axisY.setName("资金").setTextColor(Color.BLACK).setTextSize(14);
        axisX.setName("月份").setTextColor(Color.BLACK).setTextSize(14);
        axisX.setHasLines(true);
        axisY.setHasLines(true);
        axisX.setTypeface(Typeface.MONOSPACE);


        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lines);
        lineChartView.setLineChartData(data);//给图表设置数据


    }

    private static float sum(List<Float> list){
        float a = 0;
        for (int i = 0; i < list.size(); i++) {
            a+=list.get(i);
        }
        return a;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startActivity(new Intent(MonthGraphActivity.this,MainActivity.class));
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
