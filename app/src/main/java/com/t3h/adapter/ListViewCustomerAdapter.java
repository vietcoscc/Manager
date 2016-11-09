package com.t3h.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaio.manager.InfoActivity;
import com.example.vaio.manager.MainActivity;
import com.example.vaio.manager.R;
import com.t3h.model.Customer;

import java.util.ArrayList;

/**
 * Created by vaio on 11/8/2016.
 */

public class ListViewCustomerAdapter extends ArrayAdapter {
    public static final String KEY = "key";
    private ArrayList<Customer> arrCustomer;
    private LayoutInflater inflater;

    public ListViewCustomerAdapter(Context context, ArrayList<Customer> arrCustomer) {
        super(context, android.R.layout.simple_list_item_1, arrCustomer);
        inflater = LayoutInflater.from(context);
        this.arrCustomer = arrCustomer;
    }

    @NonNull
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.lv_customer_item, parent, false);
            viewHolder.tvName = (TextView) v.findViewById(R.id.tvName);
            viewHolder.tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhoneNumber);
            viewHolder.btnCall = (ImageButton) v.findViewById(R.id.btnCall);
            viewHolder.btnSMS = (ImageButton) v.findViewById(R.id.btnSMS);
            viewHolder.btnInfo = (ImageButton) v.findViewById(R.id.btnInfo);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        final Customer customer = arrCustomer.get(position);
        viewHolder.tvName.setText(position + "_" + customer.getName() + "_" + customer.getCompany());
        viewHolder.tvPhoneNumber.setText("0" + customer.getPhoneNumber() + "");
        viewHolder.btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getContext());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setNegativeButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = editText.getText().toString();
                        SmsManager manager = SmsManager.getDefault();
                        manager.sendTextMessage(viewHolder.tvPhoneNumber.getText().toString(), null, content, null, null);
                    }
                });
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Messenger");
                dialog.setView(editText);
                dialog.create().show();
            }
        });
        viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:" + viewHolder.tvPhoneNumber.getText().toString());
                intent.setData(uri);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getContext().startActivity(intent);
            }
        });
        viewHolder.btnInfo.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                String data[] = {customer.getCode() + "", customer.getName(), customer.getDateOfBirth(), customer.getPhoneNumber() + "", customer.getCompany(),
                        customer.getPosition(), customer.getAddress(), customer.getType()};
                intent.putExtra(KEY, data);
                getContext().startActivity(intent);
            }
        });
        return v;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvPhoneNumber;
        ImageButton btnCall;
        ImageButton btnSMS;
        ImageButton btnInfo;
    }
}
