package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.bs.demoapp2.DBBean.DBLuRuShuJvBean;
import com.demo.bs.demoapp2.DBBean.DataSaveEvent;
import com.demo.bs.demoapp2.DBBeanUtils.ConstKey;
import com.demo.bs.demoapp2.DBBeanUtils.DBLuRuShuJvBeanUtils;
import com.demo.bs.demoapp2.DBBeanUtils.RxBus;
import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.share.AndroidShare;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class JianKangZhiShuActivity extends AppCompatActivity {

    private TextView tvJianKangZhiShuActivityResult;
    private ListView lvJianKangZhiShuActivity;
    private List<DBLuRuShuJvBean> resultDaoList = new ArrayList<>();
    private List<DBLuRuShuJvBean> resultDaoList2 = new ArrayList<>();
    private MyKindAdapter myAdapter;
    private Subscription rxSubscription;
    private int status;
    private int status2 = 1;
    private int status3 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_kang_zhi_shu);

        tvJianKangZhiShuActivityResult = (TextView) findViewById(R.id.tvJianKangZhiShuActivityResult);
        lvJianKangZhiShuActivity = (ListView) findViewById(R.id.lvJianKangZhiShuActivity);

        resultDaoList = DBLuRuShuJvBeanUtils.getInstance().queryData();

        if (resultDaoList != null && resultDaoList.size() > 0 && resultDaoList.size() <= 10) {
            status = 1;
            myAdapter = new MyKindAdapter(resultDaoList);
            lvJianKangZhiShuActivity.setAdapter(myAdapter);
        } else if (resultDaoList != null && resultDaoList.size() > 10) {
            for (int i = 0; i < 10; i++) {
                resultDaoList2.add(resultDaoList.get(i));
            }
            status = 2;
            myAdapter = new MyKindAdapter(resultDaoList2);
            lvJianKangZhiShuActivity.setAdapter(myAdapter);
        }
        refreshMediaUpdateEvent();

    }


    public void refreshMediaUpdateEvent() {
        rxSubscription = RxBus.getDefault()
                .toObservable(DataSaveEvent.class)
                .subscribe(new Action1<DataSaveEvent>() {
                    @Override
                    public void call(DataSaveEvent dataSaveEvent) {
                        if (ConstKey.SAVE_DATA_SUCCESS.equals(dataSaveEvent.getDataSaveEvent())) {
                            if (status2 == 1 && status3 == 1) {
                                tvJianKangZhiShuActivityResult.setText("从近记录的数据来看，身体各项指标都很健康，继续保持，加油");
                            } else if (status2 == 1 && status3 == 3) {
                                tvJianKangZhiShuActivityResult.setText("从近记录的数据来看，身体各项指标都还可以，但是有指标显示亚健康，希望多多注意！！");
                            } else if (status2 == 2 && status3 == 1) {
                                tvJianKangZhiShuActivityResult.setText("从近记录的数据来看，身体各项指标中存在不健康的指标，希望多多注意身体！！");
                            } else if (status2 == 2 && status3 == 3) {
                                tvJianKangZhiShuActivityResult.setText("从近记录的数据来看，身体各项指标中存在不健康和亚健康的指标，应该引起注意了，！！");
                            }
                        }
                    }
                });
    }

    class MyKindAdapter extends BaseAdapter {

        private List<DBLuRuShuJvBean> dbUserInvestmentList;
        private LayoutInflater inflater;
        private MyVidewHolder myViewHolder;

        public MyKindAdapter(List<DBLuRuShuJvBean> dbUserInvestmentList) {
            this.dbUserInvestmentList = dbUserInvestmentList;
            inflater = LayoutInflater.from(JianKangZhiShuActivity.this);
        }


        @Override
        public int getCount() {
            return dbUserInvestmentList.size();
        }

        @Override
        public Object getItem(int position) {
            return dbUserInvestmentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_db_chu_xu_activity, null);
                myViewHolder = new MyVidewHolder(convertView);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyVidewHolder) convertView.getTag();
            }

            myViewHolder.tvItemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
            myViewHolder.tvItemCount = (TextView) convertView.findViewById(R.id.tvItemCount);
            myViewHolder.tvItemTime = (TextView) convertView.findViewById(R.id.tvItemTime);
            myViewHolder.tvItemSign = (TextView) convertView.findViewById(R.id.tvItemSign);
            myViewHolder.btnShare = convertView.findViewById(R.id.btn_share);

            SimpleDateFormat sdr1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            String CreatedTime1 = sdr1.format(new Date(dbUserInvestmentList.get(position).getCreatTimeAsId()));
            myViewHolder.tvItemTime.setText("记录数据的时间:" + CreatedTime1);
            myViewHolder.tvItemTitle.setText("记录项目类型:" + dbUserInvestmentList.get(position).getDataKind());
            myViewHolder.tvItemCount.setText("记录的该项目的数据：" + dbUserInvestmentList.get(position).getRusultData());

            if (Integer.parseInt(dbUserInvestmentList.get(position).getHealState()) == 1) {
                myViewHolder.tvItemSign.setText("该条身体健康数据的结果为： 健康");
            } else if (Integer.parseInt(dbUserInvestmentList.get(position).getHealState()) == 2) {
                myViewHolder.tvItemSign.setText("该条身体健康数据的结果为： 不健康");
                status2 = 2;
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
            } else if (Integer.parseInt(dbUserInvestmentList.get(position).getHealState()) == 3) {
                myViewHolder.tvItemSign.setText("该条身体健康数据的结果为： 亚健康");
                status3 = 3;
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
            }

            myViewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidShare as = new AndroidShare(JianKangZhiShuActivity.this, "分享健康数据 :  "
                            + myViewHolder.tvItemTime.getText().toString() + "\n"
                            + myViewHolder.tvItemTitle.getText().toString() + "\n"
                            + myViewHolder.tvItemCount.getText().toString() + "\n", "");
                    as.show();
                }
            });
            return convertView;
        }

        class MyVidewHolder {
            private TextView tvItemTitle;
            private TextView tvItemCount;
            private TextView tvItemTime;
            private TextView tvItemSign;
            private Button btnShare;

            MyVidewHolder(View view) {

            }
        }
    }


}
