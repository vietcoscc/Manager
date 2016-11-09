package com.example.vaio.manager;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.t3h.adapter.ListViewCustomerAdapter;
import com.t3h.model.Customer;

import java.util.ArrayList;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ArrayList<Customer> arrCustomer = new ArrayList<>();
    private ArrayList<Customer> arrRoot = new ArrayList<>();
    private ListView lvCustomer;
    private RelativeLayout relativeLayout;
    private DatabaseSQLi database;
    private ListViewCustomerAdapter adapter;
    private ImageView btnLogout;
    private SearchView searchView;
    private SharedPreferences sharedPreferences;
    private String userName;
    private String password;
    private ListView lvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        initData();
        initViews();
    }
    public void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String s[] = {Manifest.permission.CALL_PHONE};
            requestPermissions(s, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            String s[] = {Manifest.permission.SEND_SMS};
            requestPermissions(s,2);
        }
    }
    private void initData() {


        database = new DatabaseSQLi(this);
        arrRoot = database.getDataCustomer();
        arrCustomer.addAll(arrRoot);
        adapter = new ListViewCustomerAdapter(this, arrCustomer);
    }

    private void initViews() {
        lvSearch = (ListView) findViewById(R.id.listViewDrop);
        lvCustomer = (ListView) findViewById(R.id.lvCustomer);
        lvCustomer.setAdapter(adapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar);
        btnLogout = (ImageView) actionBar.getCustomView().findViewById(R.id.btnLogout);
        searchView = (SearchView) actionBar.getCustomView().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> arrTmp = new ArrayList<String>();
                for (int i = 0; i < arrCustomer.size(); i++) {
                    if (arrCustomer.get(i).toString().toLowerCase().contains(newText)) {
                        arrTmp.add(arrCustomer.get(i).getName());
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrTmp);
                lvSearch.setAdapter(arrayAdapter);
                lvSearch.setVisibility(View.VISIBLE);
                if (newText.isEmpty()) {
                    lvSearch.setVisibility(View.GONE);
                }
                lvSearch.setOnItemClickListener(MainActivity.this);
                return false;
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(LoginActivity.RESULT_CODE);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        arrCustomer.clear();
        arrCustomer.addAll(arrRoot);
        adapter.notifyDataSetChanged();
        searchView.onActionViewCollapsed();
//        if (searchView.isIconified()) {
//            setResult(LoginActivity.RESULT_CODE);
//            super.onBackPressed();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        searchView.onActionViewCollapsed();
        TextView tv = (TextView) view;
        String s = tv.getText().toString();
        arrCustomer.clear();
        arrCustomer.addAll(arrRoot);
        for (int i = arrCustomer.size()-1; i >=0; i--) {
            if (!arrCustomer.get(i).getName().contains(s)) {
                arrCustomer.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
