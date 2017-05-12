package com.microlend.microlend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.microlend.microlend.R;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;

public class PersonGraphActivity extends AppCompatActivity {

    private static final String TAG = "PersonGraphActivity";
    private BubbleChartView chart;
    private String[] person=new String[]{"aa","bb","xx","cc","dd"};
    private BubbleChartData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_graph);

        chart= (BubbleChartView) findViewById(R.id.bubblechart);
        chart.setZoomEnabled(true);
        chart.setInteractive(true);

        List<Float> money=new ArrayList<>();
        List<String> name=new ArrayList<>();



        List<AxisValue> axisValues = new ArrayList<AxisValue>();

        List<Lend> cls = DataSupport.select("loadpeoplename","summoney")
                .find(Lend.class);
        for (Lend l :
                cls) {
            money.add(l.getSumMoney());
            name.add(l.getLoadPeopleName());
            Log.w(TAG, "money is: "+l.getSumMoney()+"name is"+l.getLoadPeopleName());
        }

        List<BubbleValue> values=new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            BubbleValue value=new BubbleValue(i,money.get(i),money.get(i));
            value.setColor(ChartUtils.pickColor());
            value.setShape(ValueShape.CIRCLE);
            values.add(value);
            axisValues.add(new AxisValue(i, name.get(i).toCharArray()));
        }


        data=new BubbleChartData(values);



        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴
        axisY.setName("资金").setTextColor(Color.BLACK).setTextSize(14);
        axisX.setName("借贷人").setTextColor(Color.BLACK).setTextSize(14);
        axisX.setHasLines(true);
        axisY.setHasLines(true);
        axisX.setTypeface(Typeface.MONOSPACE);
        axisX.setValues(axisValues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(false);
        chart.setBubbleChartData(data);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startActivity(new Intent(PersonGraphActivity.this,MainActivity.class));
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
