package com.example.vaio.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.t3h.adapter.ListViewCustomerAdapter;

/**
 * Created by vaio on 11/8/2016.
 */

public class InfoActivity extends AppCompatActivity {
    private TextView tvCode;
    private TextView tvName;
    private TextView tvDateOfbirth;
    private TextView tvPhoneNumber;
    private TextView tvCompany;
    private TextView tvPosition;
    private TextView tvAddress;
    private TextView tvType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String data[] = (String[]) intent.getExtras().getCharSequenceArray(ListViewCustomerAdapter.KEY);
        tvCode.setText(data[0]);
        tvName.setText(data[1]);
        tvDateOfbirth.setText(data[2]);
        tvPhoneNumber.setText(data[3]);
        tvCompany.setText(data[4]);
        tvPosition.setText(data[5]);
        tvAddress.setText(data[6]);
        tvType.setText(data[7]);
    }

    private void initViews() {
        tvCode = (TextView) findViewById(R.id.tvCode);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDateOfbirth = (TextView) findViewById(R.id.tvDateOfBirth);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvType = (TextView) findViewById(R.id.tvType);

    }
}
