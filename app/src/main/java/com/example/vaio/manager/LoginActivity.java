package com.example.vaio.manager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.t3h.model.Employee;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;

/**
 * Created by vaio on 11/7/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MY_PREFERENCES = "myPref";
    public static final String USER_NAME = "user name";
    public static final String PASSWORD = "password";
    private static final int REQUEST_CODE = 1;
    public static final String KEY = "key";
    public static final int RESULT_CODE = 1;
    private EditText edtUserName;
    private EditText edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    private ArrayList<Employee> arrEmployee;
    private DatabaseSQLi databaseSQLi;
    private SharedPreferences sharedPreferences;
    private RelativeLayout relativelayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        checkRemember();
        initData();

    }

    private void checkRemember() {
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        if (sharedPreferences != null) {
            String userName = sharedPreferences.getString(USER_NAME, "");
            String password = sharedPreferences.getString(PASSWORD, "");
            if (userName != "" && password != "") {
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else {
            return;
        }

    }



    private void initData() {
        databaseSQLi = new DatabaseSQLi(this);
        arrEmployee = databaseSQLi.getDataEmployee();
    }

    private void initViews() {
        relativelayout = (RelativeLayout) findViewById(R.id.activity_login);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    public boolean checkLogin(String userName, String password) {
        for (int i = 0; i < arrEmployee.size(); i++) {
            if (arrEmployee.get(i).getUserName().equals(userName) && arrEmployee.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                if (checkLogin(userName, password)) {
                    sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cbRemember.isChecked()) {
                        editor.putString(USER_NAME, userName);
                        editor.putString(PASSWORD, password);
                    } else {
                        editor.clear();

                    }
                    editor.commit();
                    AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.anim_login_to_main);
                    relativelayout.startAnimation(set);

                    Handler handler  = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    },900);

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "User name or Password is not correct !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
            if (sharedPreferences != null) {
                String userName = sharedPreferences.getString(USER_NAME, "");
                String password = sharedPreferences.getString(PASSWORD, "");
                edtUserName.setText(userName);
                edtPassword.setText(password);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
            }
        }

    }
}
