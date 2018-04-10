package com.demo.bs.demoapp2.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.bs.demoapp2.DBBean.DBLuRuShuJvBean;
import com.demo.bs.demoapp2.DBBean.DBUserInfoBean;
import com.demo.bs.demoapp2.DBBeanUtils.DBLuRuShuJvBeanUtils;
import com.demo.bs.demoapp2.DBBeanUtils.DBUserInfoBeanUtils;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.demo.bs.demoapp2.xl.xinlv.activity.XLMainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JBTZActivity extends BaseActivity implements View.OnClickListener {
    String[] items = {"血压", "心率", "体温", "血糖","体重","步数","体脂率"};

    private Spinner sp;
    private TextView tv_data;
    private TextView tv_date;
    private AppCompatButton btn_submit;
    private AppCompatButton btn_jc;
    private DBUserInfoBean dbUserInfoBean;
    private String dbSex;
    private String dbOld;
    private String shenGao;
    private TextView tvRemind;
    private TextView tvDengJiResult;
    private Date now ;
    private  String value;
    private  String kind;
    private int dbOldInt;
    private int shenGaoInt;
    private int valueInt;
    private int HealState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jbtz);
        initView();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       //绑定 Adapter到控件
        sp.setAdapter(adapter);
        setToolbar("基本体征");
        dbUserInfoBean = DBUserInfoBeanUtils.getInstance().queryOneData(1);
        if (dbUserInfoBean!= null){
            dbSex =  dbUserInfoBean.getSex();
            dbOld = dbUserInfoBean.getNianLing();
            shenGao = dbUserInfoBean.getShenGao();
            dbOldInt = Integer.parseInt(dbOld);//String待转换的字符串
            shenGaoInt = Integer.parseInt(shenGao);
            Log.i("aaa","  年龄为：  "+dbOld + ";     性别为：   "+dbSex);
        }

    }

    private void initView() {

        sp = (Spinner) findViewById(R.id.sp);
        tv_data = (TextView) findViewById(R.id.tv_data);
        tv_date = (TextView) findViewById(R.id.tv_date);
        btn_submit = (AppCompatButton) findViewById(R.id.btn_submit);
        btn_jc = (AppCompatButton) findViewById(R.id.btn_jc);
        tvRemind = (TextView) findViewById(R.id.tvRemind);
        tvDengJiResult = (TextView) findViewById(R.id.tvDengJiResult);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");//可以方便地修改日期格式
        String time = dateFormat.format( now );
        tv_date.setText(time);
        btn_submit.setOnClickListener(this);
        btn_jc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                kind =  (String) sp.getSelectedItem();//数据种类
                value = tv_data.getText().toString();//输入的内容
                valueInt = Integer.parseInt(value);
                setRemind();
                saveData();
                add();
                Log.i("aaa","选项为：  "+(String) sp.getSelectedItem());
                break;
            case R.id.btn_jc:
//                if (sp.getSelectedItemPosition()==1){
//                    Intent intent =new Intent(JBTZActivity.this,XLMainActivity.class);
//                    startActivityForResult(intent,1);
//                }else {
                    final EditText e =new EditText(JBTZActivity.this);
                    new AlertDialog.Builder(JBTZActivity.this).setView(e).setTitle("输入检测结果").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (e.getText().toString().equals("")){
                                showToast("内容不能为空");
                                return;
                            }
                            tv_data.setText(e.getText().toString());
                            add();
                        }
                    }).setNegativeButton("取消",null).show();
              //  }
                break;
        }
    }
    private void add() {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "AddData") {
            @Override
            protected void onCallback(String json) {
              showToast("提交成功");
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.addParams("type", (String) sp.getSelectedItem());
        httpUtil.addParams("value",tv_data.getText().toString());
        httpUtil.addParams("date",tv_date.getText().toString());
        httpUtil.sendGetRequest();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            if (requestCode == 1) {
                showToast("检测到心率均值:"+ data.getStringExtra("data"));
                tv_data.setText(data.getStringExtra("data"));
            }
        }
    }


    public void setRemind(){

        if (!TextUtils.isEmpty(tv_date.getText().toString().trim())){
            if (TextUtils.isEmpty(dbSex)||TextUtils.isEmpty(dbOld)){
                Toast.makeText(getApplicationContext(), "请先进入个人档案页完善个人信息再提交", Toast.LENGTH_SHORT).show();
            }else {
                switch (kind){
                    case "血压":
                        setXueYaRemind();
                        break;
                    case "心率":
                        setXinLvRemind();
                        break;
                    case "体温":
                        setTiWenRemind();
                        break;
                    case "血糖":
                        setXueTangRemind();
                        break;
                    case "体重":
                        setTiZhongRemind();
                        break;
                    case "步数":
                        setBuShuRemind();
                        break;
                    case "体脂率":
                        setTiZhiRemind();
                        break;

                }
            }
        }else {
            Toast.makeText(getApplicationContext(), "请输入检测结果后再提交", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTiZhiRemind(){
        if (valueInt>=18&&valueInt<24){
            tvRemind.setText("体脂正常值为18≤BMI<24 您的体脂正常");
            tvDengJiResult.setText("体脂正常");
            HealState = 1;
        }else if (valueInt>=24&&valueInt<27){
            tvRemind.setText("体脂正常值为18≤BMI<24 您的体脂过多");
            tvDengJiResult.setText("体脂过多");
            HealState = 2;
        }
        else if (valueInt>=27){
            tvRemind.setText("体脂正常值为18≤BMI<24 您的重度肥胖");
            tvDengJiResult.setText("重度肥胖");
            HealState = 2;
        }else {
            tvRemind.setText("体脂正常值为18≤BMI<24 您太瘦");
            tvDengJiResult.setText("太瘦");
            HealState = 2;
        }
    }

    public void setBuShuRemind(){
        if (valueInt>10000){
            tvRemind.setText("今天锻炼已达标。不错哦，再接再厉");
            tvDengJiResult.setText("锻炼已达标");
            HealState = 1;
        }else {
            tvRemind.setText("今天缺乏锻炼");
            tvDengJiResult.setText("缺乏锻炼");
            HealState = 2;
        }
    }

    public void setTiZhongRemind(){
        float BiaoZhunTiZhong = (float) ((shenGaoInt-100)*0.9);
        if ((((valueInt-BiaoZhunTiZhong)/BiaoZhunTiZhong)*100)>10){
            tvRemind.setText("您这个身高的标准体重为："+BiaoZhunTiZhong+",您现在的体重有所超重");
            tvDengJiResult.setText("超重");
            HealState = 2;
        }else if((((BiaoZhunTiZhong-valueInt)/BiaoZhunTiZhong)*100)>10){
            tvRemind.setText("您这个身高的标准体重为："+BiaoZhunTiZhong+",您现在偏瘦");
            tvDengJiResult.setText("偏瘦");
            HealState =2;
        }else if ((((valueInt-BiaoZhunTiZhong)/BiaoZhunTiZhong)*100)<=10||(((BiaoZhunTiZhong-valueInt)/BiaoZhunTiZhong)*100)<=10){
            tvRemind.setText("您这个身高的标准体重为："+BiaoZhunTiZhong+",您现在体重正常");
            tvDengJiResult.setText("体重正常");
            HealState = 1;
        }

    }

    public void setXueTangRemind(){
        if (valueInt>6){
            tvRemind.setText("血糖正常值为4.0-6.1mmol/L 您的血糖有点高");
            tvDengJiResult.setText("血糖偏高");
            HealState = 2;
        }else if (valueInt<4){
            tvRemind.setText("血糖正常值为4.0-6.1mmol/L 您的血糖有点低");
            tvDengJiResult.setText("血糖偏低");
            HealState = 2;
        }else {
            tvRemind.setText("血糖正常值为4.0-6.1mmol/L 您的血糖正常");
            tvDengJiResult.setText("血糖正常");
            HealState = 1;
        }
    }

    public void setTiWenRemind(){
        if (valueInt>41){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在超超高热");
            tvDengJiResult.setText("超超高热");
            HealState = 2;
        }else if (valueInt<=41&&valueInt>39){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在超高热");
            tvDengJiResult.setText("超高热");
            HealState = 2;
        } else if (valueInt<=39&&valueInt>38){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在高热");
            tvDengJiResult.setText("高热");
            HealState = 2;
        } else if (valueInt<=38&&valueInt>37){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在中度热");
            tvDengJiResult.setText("中度热");
            HealState = 3;
        } else if (valueInt<=37&&valueInt>36){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在体温正常");
            tvDengJiResult.setText("体温正常");
            HealState = 1;
        } else if (valueInt<36){
            tvRemind.setText("正常人的体温为：36.0-37.0 您现在低温");
            tvDengJiResult.setText("低温");
            HealState = 2;
        }
    }


    public void setXinLvRemind(){
        if (valueInt>100){
            tvRemind.setText("正常人的心率为60次~100次/分钟 您的心率有点高");
            tvDengJiResult.setText("心率偏高");
            HealState = 2;
        }else if (valueInt<60){
            tvRemind.setText("正常人的心率为60次~100次/分钟 您的心率有点低");
            tvDengJiResult.setText("心率偏低");
            HealState = 2;
        }else {
            tvRemind.setText("正常人的心率为60次~100次/分钟 您的心率正常");
            tvDengJiResult.setText("心率正常");
            HealState = 1;
        }
    }

    public void setXueYaRemind(){
        switch (dbSex){
            case "男":
                switch (dbOldInt){
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        if (valueInt>115){
                            tvRemind.setText("年龄16~22  男的正常血压为:73~115 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<73){
                            tvRemind.setText("年龄16~22  男的正常血压为:73~115 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄16~22 男的正常血压为:73~115 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        if (valueInt>115){
                            tvRemind.setText("年龄21~25  男的正常血压为:73~115 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<73){
                            tvRemind.setText("年龄21~25  男的正常血压为:73~115 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄21~25 男的正常血压为:73~115 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        if (valueInt>115){
                            tvRemind.setText("年龄26~30  男的正常血压为:75~115 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<75){
                            tvRemind.setText("年龄26~30  男的正常血压为:75~115 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄26~30 男的正常血压为:75~115 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 3;
                        }
                        break;
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                        if (valueInt>117){
                            tvRemind.setText("年龄31~35  男的正常血压为:76~117 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<76){
                            tvRemind.setText("年龄31~35  男的正常血压为:76~117 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄31~35 男的正常血压为:76~117 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                        if (valueInt>120){
                            tvRemind.setText("年龄36~40  男的正常血压为:80~120 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<80){
                            tvRemind.setText("年龄36~40  男的正常血压为:80~120 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄36~40 男的正常血压为:80~120 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                        if (valueInt>124){
                            tvRemind.setText("年龄41~45  男的正常血压为:81~124 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<81){
                            tvRemind.setText("年龄41~45  男的正常血压为:81~124 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄41~45 男的正常血压为:81~124 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                        if (valueInt>128){
                            tvRemind.setText("年龄46~50  男的正常血压为:82~128 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<82){
                            tvRemind.setText("年龄46~50  男的正常血压为:82~128 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄46~50 男的正常血压为:82~128 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                        if (valueInt>134){
                            tvRemind.setText("年龄51~55  男的正常血压为:84~134 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<84){
                            tvRemind.setText("年龄51~55  男的正常血压为:84~134 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄51~55 男的正常血压为:84~134 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                        if (valueInt>137){
                            tvRemind.setText("年龄56~60  男的正常血压为:84~137 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<84){
                            tvRemind.setText("年龄56~60  男的正常血压为:84~137 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄56~60 男的正常血压为:84~137 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                        if (valueInt>148){
                            tvRemind.setText("年龄61~65  男的正常血压为:86~148 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<86){
                            tvRemind.setText("年龄61~65  男的正常血压为:86~148 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄61~65 男的正常血压为:86~148 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                }

                break;
            case "女":
                Log.i("aaa","走女生的路");
                switch (dbOldInt){
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        if (valueInt>110){
                            tvRemind.setText("年龄16~22  女生的正常血压为:70~110 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<70){
                            tvRemind.setText("年龄16~22  女生的正常血压为:70~110 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄16~22  女生的正常血压为:70~110 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        if (valueInt>110){
                            tvRemind.setText("年龄21~25  女生的正常血压为:70~110 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<70){
                            tvRemind.setText("年龄21~25  女生的正常血压为:70~110 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄21~25 女生的正常血压为:70~110 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        if (valueInt>112){
                            tvRemind.setText("年龄26~30  女生的正常血压为:73~112 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<73){
                            tvRemind.setText("年龄26~30  女生的正常血压为:73~112 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄26~30 女生的正常血压为:73~112 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                        if (valueInt>114){
                            tvRemind.setText("年龄31~35  女生的正常血压为:74~114 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<74){
                            tvRemind.setText("年龄31~35  女生的正常血压为:74~114 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄31~35 女生的正常血压为:74~114 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                        if (valueInt>116){
                            tvRemind.setText("年龄36~40  女生的正常血压为:77~116 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<77){
                            tvRemind.setText("年龄36~40  女生的正常血压为:77~116 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄36~40 女生的正常血压为:77~116 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                        if (valueInt>122){
                            tvRemind.setText("年龄41~45  女生的正常血压为:78~122 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<78){
                            tvRemind.setText("年龄41~45  女生的正常血压为:78~122 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄41~45 女生的正常血压为:78~122 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                        if (valueInt>128){
                            tvRemind.setText("年龄46~50  女生的正常血压为:79~128 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<79){
                            tvRemind.setText("年龄46~50  女生的正常血压为:79~128 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄46~50 女生的正常血压为:79~128 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                        if (valueInt>134){
                            tvRemind.setText("年龄51~55  女生的正常血压为:80~134 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<80){
                            tvRemind.setText("年龄51~55  女生的正常血压为:80~134 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 1;
                        }else {
                            tvRemind.setText("年龄51~55 女生的正常血压为:80~134 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;

                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                        if (valueInt>139){
                            tvRemind.setText("年龄56~60  女生的正常血压为:82~139 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<82){
                            tvRemind.setText("年龄56~60  女生的正常血压为:82~139 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄56~60 女生的正常血压为:82~139 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                        if (valueInt>145){
                            tvRemind.setText("年龄61~65  女生的正常血压为:83~145 您可能有高血压，建议：平常饮食清淡");
                            tvDengJiResult.setText("高血压正常");
                            HealState = 1;
                        }else if (valueInt<83){
                            tvRemind.setText("年龄61~65  女生的正常血压为:83~145 您可能有低血压，建议：平常多吃营养品");
                            tvDengJiResult.setText("低血压");
                            HealState = 2;
                        }else {
                            tvRemind.setText("年龄61~65 女生的正常血压为:83~145 您血压正常");
                            tvDengJiResult.setText("血压正常");
                            HealState = 2;
                        }
                        break;
                }
                break;
        }
    }

    public void saveData(){
        if (!TextUtils.isEmpty(tv_date.getText().toString())){
            String kind =  (String) sp.getSelectedItem();
            DBLuRuShuJvBean dbLuRuShuJvBean = new DBLuRuShuJvBean();
            dbLuRuShuJvBean.setCreatTimeAsId(getTime());
            dbLuRuShuJvBean.setDataKind(kind);
            dbLuRuShuJvBean.setRusultData(tv_date.getText().toString());
            dbLuRuShuJvBean.setHealState(HealState+"");
            DBLuRuShuJvBeanUtils.getInstance().insertOneData(dbLuRuShuJvBean);
            Toast.makeText(getApplicationContext(), "保存基本特征数据成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "请输入检测结果后再提交", Toast.LENGTH_SHORT).show();
        }

    }




    public long getTime() {
        return System.currentTimeMillis();//获取系统时间戳
    }




}
