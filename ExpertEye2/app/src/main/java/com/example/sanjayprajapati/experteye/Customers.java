package com.example.sanjayprajapati.experteye;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;



/**
 * Created by sanjayprajapati on 5/26/18.
 */

public class Customers extends Fragment {
    ListView customer_lv;
    InputStream inputStream;
    String line,result=null;
    EditText search;
    String path = "http://dmis.club/dmis_app/getdataexpert.php";
    private ProgressDialog pDialog;
    private EditText et_fname, et_lname, et_emailid, et_phone, et_notes;
    //public static ArrayList<CustomerDetail> details=new ArrayList <>();
    private String fname, lname, emailid, phone, notes;
    private RegisterAsync registerAsync;
    AlertDialog dialog;
    Button btnregister;
    ImageButton add;
    int i=0;



    public Customers(){


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.customers,container,false);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        DownloadTask downloadTask=new DownloadTask();
        downloadTask.execute("");

        add=(ImageButton)rootview.findViewById(R.id.imgbnt_add);
        search=(EditText) rootview.findViewById(R.id.editText_search);
        customer_lv=(ListView) rootview.findViewById(R.id.lv_customerdetail);
        CustomerListAdapter adapter= new CustomerListAdapter(getContext(),R.layout.customerdetail,BottomTabbedActivity.details);
        customer_lv.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View rootview = getLayoutInflater().inflate(R.layout.addcustomer,null);

                et_fname=(EditText)rootview.findViewById(R.id.et_fname);
                et_lname=(EditText)rootview.findViewById(R.id.et_lname);
                et_phone=(EditText)rootview.findViewById(R.id.et_phone);

                btnregister=(Button)rootview.findViewById(R.id.bt_register);
                btnregister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        phone=et_phone.getText().toString();
                        fname=et_fname.getText().toString();
                        lname=et_lname.getText().toString();
                        String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String phonepattern="[0-9]";

                        if (phone.matches(phonepattern) && phone.trim().length() != 10){
                            Toast.makeText(getContext(),"Please enter correct phone",Toast.LENGTH_SHORT).show();

                        }
                        else if(phone.trim().length() == 10) {
                            registerAsync =new Customers.RegisterAsync();
                            emailid="";
                            notes="";
                            registerAsync.execute(phone,fname,lname,emailid,notes);

                        }

                    }
                });

                mBuilder.setView(rootview);
                dialog=mBuilder.create();
                dialog.show();


            }
        });



        return rootview;
    }



    public class DownloadTask extends AsyncTask <String, Integer, String>

    {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            String path = strings[0];
            try {
                URL url = new URL("http://dmis.club/dmis_app/getdataexpert.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                inputStream = new BufferedInputStream(con.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }

            //READ INPUT STREAM CONTENT INto string
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //PARSE JASON ARRAY
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = null;


                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String phone = jsonObject.getString("Phone").trim();
                    String fname = jsonObject.getString("FirstName");
                    String lname = jsonObject.getString("LastName");
                    String email = jsonObject.getString("Email");
                    String notes = jsonObject.getString("Notes");

                    CustomerDetail customer = new CustomerDetail(phone, fname, lname, email, notes);
                    BottomTabbedActivity.details.add(customer);
                    int progress = (int) i;
                    publishProgress(i);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Download complete";



        }


        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(getContext());
            progressDialog.setTitle("Download in Progress ..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.hide();
            Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();

            CustomerListAdapter adapter= new CustomerListAdapter(getContext(),R.layout.customerdetail,BottomTabbedActivity.details);
            customer_lv.setAdapter(adapter);

        }
    }

    public class RegisterAsync extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Uploading info ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String Response = "";
            try {
                String data = URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                data += "&" + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                data += "&" + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                data += "&" + URLEncoder.encode("notes", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");

                URL url = new URL("http://dmis.club/dmis_app/experteye.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(data);
                writer.flush();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String s;
                while ((s = rd.readLine()) != null) {
                    Response += s;
                }
                writer.close();
                rd.close();
                Log.i("Response ", Response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Response;
        }

        protected void onPostExecute(String result) {
            if (pDialog.isShowing())
                Log.d("result",result);
            response(result);
            pDialog.dismiss();
        }
    }

    private void response(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject != null) {
                String response = jsonObject.getString("response");
                String message=jsonObject.getString("message");
                Log.i("response", response);
                if (response.equals("true")){
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(path);
                }
                else {
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}


