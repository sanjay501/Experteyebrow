package com.example.sanjayprajapati.experteye;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.cart;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.details;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.discount;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.netamt;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.total;

/**
 * Created by sanjayprajapati on 5/26/18.
 */

public class Cart extends Fragment{

    private CheckBox guest;
    private TextView title, displayname,tv_count;
    public  static TextView cartcount;
    public static ListView listView;
    public static TextView location;
    public static int totalamt, discountamt, netamount;
    private Button coupen_button;
    public EditText discount_et;
    public static AutoCompleteTextView customer;
    private ArrayList <Customer> customer_list;
    AlertDialog dialog,confirmation_dialog;
    Spinner cashier,place;
    String[] employee;
    Calendar calendar = Calendar.getInstance();
    TextView cash,card;
    ProgressDialog pDialog;
    private UploadTransactionAsync uploadTransactionAsync;
    Date date;
    DownloadCustomerSales downloadCustomerSales;
    private int count_sales=0;
    private DeleteRecords deleteRecords;
    public static ListView servicesonly;
    ListView customer_lv;
    InputStream inputStream;
    String line,result=null;
    EditText search;
    String path = "http://dmis.club/dmis_app/getdataexpert.php";
    private EditText et_fname, et_lname, et_emailid, et_phone, et_notes;
    //public static ArrayList<CustomerDetail> details=new ArrayList <>();
    private String fname, lname, emailid, phone, notes;
    private Customers.RegisterAsync registerAsync;
    ProgressDialog pDialogdelete;

    Button btnregister;
    ImageButton add;
    int i=0;




    public Cart() {
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.cart, container, false);
        location=(TextView)rootview.findViewById(R.id.location);
        location.setText(BottomTabbedActivity.location);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        if(details.isEmpty()){
            DownloadTask downloadTask=new DownloadTask();
            downloadTask.execute(path);
        }


        final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        DateFormat format = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        try {
            date = format.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_count=(TextView) rootview.findViewById(R.id.textView11);
        discount = (TextView) rootview.findViewById(R.id.textview_discount);
        guest = (CheckBox) rootview.findViewById(R.id.checkBox2);
        discount.setText("Discount : -$" + BottomTabbedActivity.discountamount);
        Button checkout= (Button)rootview.findViewById(R.id.btn_checkout) ;
        cash=(TextView)rootview.findViewById(R.id.editText_cash) ;
        card=(TextView)rootview.findViewById(R.id.edittext_card) ;
        //spinner for cashier setup
        cashier=(Spinner)rootview.findViewById(R.id.spinner4);
        employee = new String[]{"Jana Prajapati","Roshana Manandhar","Dikshya","Uma Aryal","sapana"};
        ArrayAdapter<String> adapter=new ArrayAdapter <String>(getContext(),R.layout.support_simple_spinner_dropdown_item,employee);
        cashier.setAdapter(adapter);
        cashier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                BottomTabbedActivity.cashier=employee[i].toString();
                //Toast.makeText(getContext(),BottomTabbedActivity.cashier+"selected as cashier",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });


        final String[] location = new String[]{"Everett","Malden"};
        customer = (AutoCompleteTextView) rootview.findViewById(R.id.autoCompleteTextView);
        displayname=(TextView)rootview.findViewById(R.id.tv_displayname) ;
        CustomerModel customerModel = new CustomerModel();
        AutoCompleteNameAdapter autoCompleteNameAdapter=new AutoCompleteNameAdapter(getContext(),
                customerModel.findAll());
        customer.setAdapter(autoCompleteNameAdapter);
        customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                CustomerDetail customerobj = (CustomerDetail) parent.getItemAtPosition(position);
                String name= customerobj.getFirstName()+" "+customerobj.getLastName();
                displayname.setText(name);
                downloadCustomerSales=new Cart.DownloadCustomerSales();
                downloadCustomerSales.execute(customer.getText().toString().trim());
                Log.d("passed ",customer.getText().toString().trim() );


            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(guest.isChecked()){
                    customer.setText("GUEST");
                    customer.dismissDropDown();
                    customer.setEnabled(false);
                    displayname.setText("GUEST");
                }else{
                    displayname.setText("");
                    customer.setEnabled(true);
                    customer.setText("");
                }
            }
        });
        listView = (ListView) rootview.findViewById(R.id.lv_servicescart);
        servicesonly = (ListView) rootview.findViewById(R.id.lv_servicesonly);
        total = (TextView) rootview.findViewById(R.id.textView8);
        total.setText("Total : $" + getTotal(cart));

        coupen_button = (Button) rootview.findViewById(R.id.Applycoupen);
        discount_et = (EditText) rootview.findViewById(R.id.EnterCoupen);
        netamt = (TextView) rootview.findViewById(R.id.textView10);
        netamount = getTotal(cart) - discountamt;
        netamt.setText("Net Amt : $" + netamount);
        coupen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (discount_et.getText().toString().isEmpty()) {
                    BottomTabbedActivity.discountamount = 0;
                    discount.setText("Discount : -$" + BottomTabbedActivity.discountamount);
                    netamount = getTotal(cart) - BottomTabbedActivity.discountamount;
                    netamt.setText("Net Amt : $" + netamount);
                } else {
                    BottomTabbedActivity.discountamount = Integer.parseInt(discount_et.getText().toString());
                    discount.setText("Discount : -$" + BottomTabbedActivity.discountamount);
                    netamount = getTotal(cart) - BottomTabbedActivity.discountamount;
                    netamt.setText("Net Amt : $" + netamount);
                }

            }
        });

        netamt = (TextView) rootview.findViewById(R.id.textView10);
        if (getContext() == null) {
            Toast.makeText(getContext(), "context is null", Toast.LENGTH_LONG);
        } else {
            CartListAdapter adaptercart = new CartListAdapter(getContext(), R.layout.custom_cart, BottomTabbedActivity.cart);
            listView.setAdapter(adaptercart);

            QuickServicesListAdapter quickServicesListAdapter=new QuickServicesListAdapter(
                    getContext(),R.layout.sideservices,new CustomerModel().LoadAll());
            servicesonly.setAdapter(quickServicesListAdapter);
        }

        //get ready for all required value to be checked out
        checkout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String phone=customer.getText().toString();

                String fullname =displayname.getText().toString();


                String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String ftime = sdf.format(timestamp);


                String final_location=BottomTabbedActivity.location;
                String final_cashier=BottomTabbedActivity.cashier;

                //get description
                String desc="";
                int total=0;
                String ftotal="";
                for(int i=0;i<BottomTabbedActivity.cart.size();i++){
                    String title=BottomTabbedActivity.cart.get(i).getTitle();
                    int price=BottomTabbedActivity.cart.get(i).getPrice();
                    String line = (i+1)+"."+" "+title+"\t\t$"+price+"\n";
                    desc= desc+line;
                    total=total+price;
                }
                ftotal=total+"";
                String fdiscount= BottomTabbedActivity.discountamount+"";

                String fnetamount = getTotal(cart) - BottomTabbedActivity.discountamount+"";
                String cashamount = cash.getText().toString();
                if (cashamount.isEmpty()){
                    cashamount="0";
                }

                String cardAmount = card.getText().toString();
                if(cardAmount.isEmpty()){
                    cardAmount="0";
                }
                Log.i("phone",phone);
                Log.i("fname",fullname);
                Log.i("date",date);
                Log.i("time",ftime);
                Log.i("flocation",final_location);
                Log.i("f cashier",final_cashier);
                Log.i("f description",desc);
                Log.i("f discount",fdiscount);
                Log.i("f net amount",fnetamount);
                Log.i("cash",cashamount);
                Log.i("card",cardAmount);
                if(!customer.getText().toString().isEmpty() &&
                            (BottomTabbedActivity.cart.size() != 0) ) {
                    if(customer.getText().toString().trim().length()==10 || guest.isChecked()){
                        uploadTransactionAsync =new Cart.UploadTransactionAsync();
                        uploadTransactionAsync.execute(phone,fullname,date,ftime,final_location,
                                final_cashier,desc,ftotal,fdiscount,fnetamount,
                               cashamount,cardAmount);
                        Toast.makeText(getContext(),"Test Sucessful",Toast.LENGTH_LONG).show();


                    }else if(customer.getText().toString().trim().length()!=10){
                        customer.showDropDown();

                    }else{
                        Toast.makeText(getContext(),"Check Phone Number",Toast.LENGTH_LONG).show();
                    }

                    }else {
                        Toast.makeText(getContext(),"Check Name or Check Cart Please",Toast.LENGTH_LONG).show();
                    }










            }
        });

        TextView add=(TextView) rootview.findViewById(R.id.newcustomer);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View rootview = getLayoutInflater().inflate(R.layout.addcustomer, null);

                et_fname = (EditText) rootview.findViewById(R.id.et_fname);
                et_lname = (EditText) rootview.findViewById(R.id.et_lname);
                et_phone = (EditText) rootview.findViewById(R.id.et_phone);

                btnregister = (Button) rootview.findViewById(R.id.bt_register);
                btnregister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        phone = et_phone.getText().toString();
                        fname = et_fname.getText().toString();
                        lname = et_lname.getText().toString();
                        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String phonepattern = "[0-9]";

                        if (phone.matches(phonepattern)) {
                            Toast.makeText(getContext(), "Please enter correct phone", Toast.LENGTH_LONG).show();

                        } else {
                            details.add(new CustomerDetail(phone, fname, lname, emailid, notes));
                            RegisterAsync registerAsync = new RegisterAsync();
                            emailid = "";
                            notes = "";
                            registerAsync.execute(phone, fname, lname, emailid, notes);

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

    @Override
    public void onResume() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static int getTotal(ArrayList <Services> cartitem) {
        totalamt = 0;
        for (int i = 0; i < cartitem.size(); i++) {
            totalamt = totalamt + cartitem.get(i).getPrice();

        }
        return totalamt;
    }



    public class UploadTransactionAsync extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Uploading transaction info ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String Response = "";
            try {
                String data = URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                data += "&" + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                data += "&" + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                data += "&" + URLEncoder.encode("Location", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                data += "&" + URLEncoder.encode("Cashier", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8");
                data += "&" + URLEncoder.encode("ServiceDescription", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8");
                data += "&" + URLEncoder.encode("Total", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8");
                data += "&" + URLEncoder.encode("Discount", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8");
                data += "&" + URLEncoder.encode("NetAmt", "UTF-8") + "=" + URLEncoder.encode(params[9], "UTF-8");
                data += "&" + URLEncoder.encode("Cash", "UTF-8") + "=" + URLEncoder.encode(params[10], "UTF-8");
                data += "&" + URLEncoder.encode("Card", "UTF-8") + "=" + URLEncoder.encode(params[11], "UTF-8");

                URL url = new URL("http://dmis.club/dmis_app/writesalesexpert.php");
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
                int[] confirmimage={R.drawable.confirm,R.drawable.error};
                if (response.equals("true")){
                    //Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                    BottomTabbedActivity.cart.clear();
                    reset();
                    if(count_sales==6 || count_sales > 6){
                        pDialog.dismiss();
                        deleteRecords=new Cart.DeleteRecords();
                        deleteRecords.execute(customer.getText().toString().trim());
                    }else{
                        pDialog.dismiss();
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                            View rootview = getLayoutInflater().inflate(R.layout.confirmation, null);

                            TextView cashierkoname = (TextView) rootview.findViewById(R.id.cashierkoname);
                            TextView confirmtext = (TextView) rootview.findViewById(R.id.confirmtext);
                            ImageView imageViewconfirm = (ImageView)rootview.findViewById(R.id.imageView_confirmation);
                            imageViewconfirm.setImageResource(confirmimage[0]);
                            cashierkoname.setText("Thankyou "+BottomTabbedActivity.cashier+",");
                            confirmtext.setText(message);

                            Button dismiss = (Button) rootview.findViewById(R.id.btn_dismiss);
                            dismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    confirmation_dialog.dismiss();
                                }
                            });




                            mBuilder.setView(rootview);
                            confirmation_dialog=mBuilder.create();
                            confirmation_dialog.show();



                    }

                }
                else {
                    pDialog.dismiss();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View rootview = getLayoutInflater().inflate(R.layout.addcustomer, null);

                    TextView cashierkoname = (EditText) rootview.findViewById(R.id.cashierkoname);
                    TextView confirmtext = (EditText) rootview.findViewById(R.id.confirmtext);
                    ImageView imageViewconfirm = (ImageView)rootview.findViewById(R.id.imageView_confirmation);
                    imageViewconfirm.setImageResource(confirmimage[1]);
                    cashierkoname.setText("Thankyou "+BottomTabbedActivity.cashier+",");
                    confirmtext.setText(message);

                    Button dismiss = (Button) rootview.findViewById(R.id.btn_dismiss);
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmation_dialog.dismiss();
                        }
                    });




                    mBuilder.setView(rootview);
                    confirmation_dialog=mBuilder.create();
                    confirmation_dialog.show();



                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void reset(){
        BottomTabbedActivity.discountamount=0;
        total.setText("Total : $"+getTotal(cart)+"");
        displayname.setText("Customer Name Here ");
        tv_count.setText("Customer Info here");
        discount.setText("Discount : -$" + BottomTabbedActivity.discountamount);
        netamount = getTotal(cart) - BottomTabbedActivity.discountamount;
        netamt.setText("Net Amt : $" + netamount);
        cash.setText("");
        card.setText("");
        listView.invalidateViews();
    }


    /////////////////////////////////////////

    public class DownloadCustomerSales extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Downloloading info ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String Response = "";
            try {
                String data = URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                URL url = new URL("http://dmis.club/dmis_app/getcount.php");
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
            customer_response(result);
            pDialog.dismiss();
        }
    }

    private void customer_response(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject != null) {
                String response = jsonObject.getString("response");
                String message=jsonObject.getString("message").trim();
                count_sales=Integer.parseInt(message);
                if (response.equals("true")){
                    if(count_sales==6){
                        BottomTabbedActivity.discountamount = 8;
                        discount.setText("Discount : -$" + BottomTabbedActivity.discountamount);
                        netamount = getTotal(cart) - BottomTabbedActivity.discountamount;
                        netamt.setText("Net Amt : $" + netamount);
                        tv_count.setText("This is "+displayname.getText()+" "+(count_sales+1)+" visit. 50 % Discount Applied");

                    }else{
                        tv_count.setText("This is "+displayname.getText()+" "+(count_sales+1)+" visit.");
                    }
                }else{
                    tv_count.setText("record not found");
                    Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class DeleteRecords extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogdelete = new ProgressDialog(getContext());
            pDialogdelete.setMessage("Deleting records ...");
            pDialogdelete.setCancelable(false);
            pDialogdelete.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String Response = "";
            try {
                String data = URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                URL url = new URL("http://dmis.club/dmis_app/deleterecord.php");
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
            if (pDialogdelete.isShowing())
                Log.d("result",result);
            delete_response(result);
            pDialog.dismiss();
        }
    }

    private void delete_response(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject != null) {
                String response = jsonObject.getString("response");
                String message=jsonObject.getString("message").trim();
                if (response.equals("true")){
                    Toast.makeText(getContext(),"Records Deleted Sucessfully",Toast.LENGTH_SHORT).show();
                    customer.setText("");
                    pDialogdelete.dismiss();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Records delete unsucessful",Toast.LENGTH_SHORT).show();
                    pDialogdelete.dismiss();
                    dialog.dismiss();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                BottomTabbedActivity.details.clear();


                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String phone = jsonObject.getString("Phone");
                    String fname = jsonObject.getString("FirstName");
                    String lname = jsonObject.getString("LastName");
                    String email = jsonObject.getString("Email");
                    String notes = jsonObject.getString("Notes");

                    CustomerDetail customer = new CustomerDetail(phone, fname, lname, email, notes);
                    BottomTabbedActivity.details.add(customer);
                    int progress = (int) i;
                    publishProgress(i);


                }
                CustomerListAdapter adapter= new CustomerListAdapter(getContext(),R.layout.customerdetail,BottomTabbedActivity.details);
                customer_lv.setAdapter(adapter);
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

            //CustomerListAdapter adapter= new CustomerListAdapter(getContext(),R.layout.customerdetail,BottomTabbedActivity.details);
            //customer_lv.setAdapter(adapter);

        }
    }

    //////register custoimer code

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
            register_response(result);
            pDialog.dismiss();
        }
    }

    private void register_response(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject != null) {
                String response = jsonObject.getString("response");
                String message=jsonObject.getString("message");
                Log.i("response", response);
                if (response.equals("true")){
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    details.add(new CustomerDetail(phone,fname,lname,emailid,notes));
                    AutoCompleteNameAdapter autoCompleteNameAdapter=new AutoCompleteNameAdapter(getContext(),new CustomerModel().findAll());
                    customer.setAdapter(autoCompleteNameAdapter);
                    customer.setText(phone);
                    customer.showDropDown();

                }
                else if (message.equals("Account Found !") && response.equals("found")){
                    dialog.dismiss();
                    customer.setText(phone);
                    customer.showDropDown();




                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //end or register customer code.


}











