package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.pedometerlibrary.common.PedometerClient;
import com.pedometerlibrary.service.PedometerService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PedometerActivity extends BaseActivity {

    @BindView(R.id.tv_bu_shu_pedometer_activity) TextView mBuShu;
    private PedometerClient pedometerClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        ButterKnife.bind(this);
        pedometerClient = PedometerClient.add(this, new PedometerService.CallBack() {
            @Override
            public void onStep(int step) {
                mBuShu.setText(getString(R.string.activity_main_step, step));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pedometerClient != null && pedometerClient.isConnect()) {
            pedometerClient.remove();
        }
    }
}
