package com.example.vaio.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.t3h.model.Customer;
import com.t3h.model.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by vaio on 11/7/2016.
 */

public class DatabaseSQLi {

    private Context context;
    public static final String DB_NAME = "company.sqlite";
    public static final String TB_NAME_EMPLOYEE = "employee";
    public static final String TB_NAME_CUSTOMER = "customer";
    public static final String DB_PATH = Environment.getDataDirectory() + "/data/com.example.vaio.manager/databases/" + DB_NAME;

    public static final String CUSTOMER_CODE = "CODE";
    public static final String CUSTOMER_NAME = "NAME";
    public static final String CUSTOMER_DATE_OF_BIRTH = "DATE_OF_BIRTH";
    public static final String CUSTOMER_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CUSTOMER_COMPANY = "COMPANY";
    public static final String CUSTOMER_POSITION = "POSITION";
    public static final String CUSTOMER_ADDRESS = "ADDRESS";
    public static final String CUSTOMER_TYPE = "TYPE";

    public static final String EMPLOYEE_CODE ="CODE";
    public static final String EMPLOYEE_USER_NAME ="USER_NAME";
    public static final String EMPLOYEE_PASSWORD ="PASSWORD";

    private ArrayList<Employee> arrEmployee = new ArrayList<>();
    private ArrayList<Customer> arrCustomer = new ArrayList<>();
    private SQLiteDatabase database;

    public DatabaseSQLi(Context context) {
        this.context = context;
        copyDatabase(context);
    }

    public void copyDatabase(Context context) {
        try {
            File file = new File(DB_PATH);
            if (file.exists()) {
                return;
            }
            File parent = file.getParentFile();
            parent.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            InputStream inputStream = context.getAssets().open(DB_NAME);
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                count = inputStream.read(b);
            }
            outputStream.close();
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {
        database.close();
    }

    public ArrayList<Customer> getDataCustomer() {
        openDatabase();
        Cursor cursor = database.query(TB_NAME_CUSTOMER, null, null, null, null, null, null);
        int codeIndex = cursor.getColumnIndex(CUSTOMER_CODE);
        int nameIndex = cursor.getColumnIndex(CUSTOMER_NAME);
        int dateOfBirthIndex = cursor.getColumnIndex(CUSTOMER_DATE_OF_BIRTH);
        int phoneNumberIndex = cursor.getColumnIndex(CUSTOMER_PHONE_NUMBER);
        int companyIndex = cursor.getColumnIndex(CUSTOMER_COMPANY);
        int positionIndex = cursor.getColumnIndex(CUSTOMER_POSITION);
        int addressIndex = cursor.getColumnIndex(CUSTOMER_ADDRESS);
        int typeIndex = cursor.getColumnIndex(CUSTOMER_TYPE);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int code = cursor.getInt(codeIndex);
            String name = cursor.getString(nameIndex);
            String dateOfBirth = cursor.getString(dateOfBirthIndex);
            int phoneNumber = cursor.getInt(phoneNumberIndex);
            String company = cursor.getString(companyIndex);
            String position = cursor.getString(positionIndex);
            String address = cursor.getString(addressIndex);
            String type = cursor.getString(typeIndex);
            Customer employee = new Customer(code,name,dateOfBirth,phoneNumber,company,position,address,type);
            arrCustomer.add(employee);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrCustomer;
    }

    public ArrayList<Employee>  getDataEmployee(){
        openDatabase();
        Cursor cursor = database.query(TB_NAME_EMPLOYEE, null, null, null, null, null, null);
        int codeIndex = cursor.getColumnIndex(EMPLOYEE_CODE);
        int userNameIndex = cursor.getColumnIndex(EMPLOYEE_USER_NAME);
        int passwordIndex = cursor.getColumnIndex(EMPLOYEE_PASSWORD);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int code = cursor.getInt(codeIndex);
            String userName = cursor.getString(userNameIndex);
            String password = cursor.getString(passwordIndex);
            Employee employee = new Employee(code,userName,password);
            arrEmployee.add(employee);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrEmployee;
    }

}
