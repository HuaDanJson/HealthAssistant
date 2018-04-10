package com.demo.bs.demoapp2.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.DateInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

//健康指数
public class ZSActivity extends BaseActivity {
    String[] items = {"体温", "血压", "步数", "血糖", "体重"};
    private Spinner sp;
    private List<DateInfo> infos;
    private LineChartView clcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zs);
        initView();
        setToolbar("健康指数");
        // getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getData() {

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetDataList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos = gson.fromJson(json, new TypeToken<List<DateInfo>>() {
                    }.getType());
                    List<String> x_label = new ArrayList<>();
                    List<Integer> integers = new ArrayList<>();
                    for (int i = 0; i < infos.size(); i++) {
                        x_label.add(infos.get(i).getDate());
                        integers.add(Integer.parseInt(infos.get(i).getValue()));
                    }
                    initLcv(x_label, integers);
                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.addParams("type", (String) sp.getSelectedItem());
        httpUtil.sendGetRequest();
    }

    private void initView() {
        clcv = (LineChartView) findViewById(R.id.lcv);
        sp = (Spinner) findViewById(R.id.sp);
    }

    private void initLcv(List<String> x_label, List<Integer> date) {

        List<PointValue> pointValues = new ArrayList<PointValue>();// 节点数据结合
        Axis axisY = new Axis().setHasLines(true);// Y轴属性
        Axis axisX = new Axis().setHasLines(true);// X轴属性
        ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
        ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合

        axisX.setLineColor(getResources().getColor(R.color.line_color));// 设置X轴轴线颜色
        axisY.setLineColor(getResources().getColor(R.color.line_color));// 设置Y轴轴线颜色
        axisX.setTextColor(getResources().getColor(R.color.text_bec3d4));// 设置X轴文字颜色
        axisY.setTextColor(getResources().getColor(R.color.text_bec3d4));// 设置Y轴文字颜色
        axisX.setTextSize(ChartUtils.px2dp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.textSize_11)));// 设置X轴文字大小
        axisY.setTextSize(ChartUtils.px2dp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.textSize_11)));// 设置Y轴文字大小
        axisX.setHasTiltedLabels(true);// 设置X轴文字向左旋转45度

        for (int j = 0; j < x_label.size(); j++) {//循环为节点、X、Y轴添加数据
            pointValues.add(new PointValue(j, date.get(j)));// 添加节点数据
            axisValuesY.add(new AxisValue(j).setValue(j).setLabel(date.get(j) + ""));// 添加Y轴显示的刻度值
            axisValuesX.add(new AxisValue(j).setValue(j).setLabel(x_label.get(j)));// 添加X轴显示的刻度值
        }
        axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
        List<Line> lines = new ArrayList<Line>();//定义线的集合
        Line line = new Line(pointValues);//将值设置给折线
        line.setColor(getResources().getColor(R.color.text_b28055));// 设置折线颜色
        line.setStrokeWidth(1);// 设置折线宽度
        line.setFilled(false);// 设置折线覆盖区域是否填充
        line.setCubic(true);// 是否设置为曲线的
        line.setPointColor(getResources().getColor(R.color.text_b28055));// 设置节点颜色
        line.setPointRadius(2);// 设置节点半径
        line.setHasLabels(false);// 是否显示节点数据
        line.setHasLines(true);// 是否显示折线
        line.setHasPoints(true);// 是否显示节点
        line.setShape(ValueShape.CIRCLE);// 节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
        line.setHasLabelsOnlyForSelected(false);// 隐藏数据，触摸可以显示
        lines.add(line);// 将数据集合添加线
        LineChartData chartData = new LineChartData(lines);//将线的集合设置为折线图的数据

        chartData.setAxisYLeft(axisY);// 将Y轴属性设置到左边
        chartData.setAxisXBottom(axisX);// 将X轴属性设置到底部

        chartData.setValueLabelBackgroundAuto(true);// 设置数据背景是否跟随节点颜色
        chartData.setValueLabelBackgroundEnabled(true);// 设置是否有数据背景
        chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式

        clcv.setZoomEnabled(false);//设置是否支持缩放
        clcv.setInteractive(false);//设置图表是否可以与用户互动
        clcv.setValueSelectionEnabled(false);//设置图表数据是否选中进行显示
        clcv.setLineChartData(chartData);//最后为图表设置数据，数据类型为LineChartData
    }

}
