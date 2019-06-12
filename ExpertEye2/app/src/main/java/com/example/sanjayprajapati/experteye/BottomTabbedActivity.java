package com.example.sanjayprajapati.experteye;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BottomTabbedActivity extends AppCompatActivity implements
BottomNavigationView.OnNavigationItemSelectedListener {
    private static BottomTabbedActivity instance;
    public static ArrayList<Services> cart;
    public static TextView total, discount, netamt;
    public static String cashier, location,selected_date;
    public static int discountamount = 0;
    public static ArrayList<CustomerDetail> details = new ArrayList<>();
    ListView customer_lv;
    InputStream inputStream;
    String line, result = null;
    EditText search;

    String path = "http://dmis.club/dmis_app/getdataexpert.php";
    private ProgressDialog pDialog;
    private EditText et_fname, et_lname, et_emailid, et_phone, et_notes;
    //public static ArrayList<CustomerDetail> details=new ArrayList <>();
    private String fname, lname, emailid, phone, notes;
    private Customers.RegisterAsync registerAsync;
    AlertDialog dialog;
    Button btnregister;
    ImageButton add;
    int i = 0;
    Fragment fragmentHome= null;
    Fragment fragmentC= null;
    Fragment fragmentS= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_bottom_tabbed);
        cart = new ArrayList<>();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        fragmentHome = new Cart();
        fragmentC = new Customers();
        fragmentS = new Sales();
        BottomTabbedActivity.location="Everett";
        //Everett

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(fragmentC);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                return loadFragment(fragmentHome);

            case R.id.navigation_customers:
                return loadFragment(fragmentC);


            case R.id.navigation_sales:
                return loadFragment(fragmentS);

        }
        return false;

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null ) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;

    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}






