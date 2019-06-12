package com.example.sanjayprajapati.experteye;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sanjayprajapati on 5/26/18.
 */

public class Sales extends Fragment {
    InputStream inputStream;
    String line,result;
    public ArrayList<Reciept> recieptview=new ArrayList <>();
    ListView recieptlv;
    Calendar calendar=Calendar.getInstance();
    private String todaydate;
    EditText dateinput;
    TextView count,cash,card,total;
    Button search;
    int gtotal=0;
    int gcash=0;
    int gcard=0;
    ProgressDialog pDialog;
    Spinner place;
    String storelocation;



    public Sales(){

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.sales,container,false);
        dateinput=(EditText)rootview.findViewById(R.id.editTextdate);
        count=(TextView)rootview.findViewById(R.id.count);
        cash=(TextView)rootview.findViewById(R.id.cash);
        card=(TextView)rootview.findViewById(R.id.card);
        total=(TextView)rootview.findViewById(R.id.total);
        search=(Button)rootview.findViewById(R.id.search);
        place=(Spinner)rootview.findViewById(R.id.locationspinner);



        todaydate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        dateinput.setText(todaydate);
        recieptlv=(ListView) rootview.findViewById(R.id.reciept_listview);


        //Spinner for location setup
        place=(Spinner)rootview.findViewById(R.id.locationspinner);
        final String[] location = new String[]{"Everett","Malden"};
        ArrayAdapter<String> locationadapter=new ArrayAdapter <String>(getContext(),R.layout.support_simple_spinner_dropdown_item,location);
        place.setAdapter(locationadapter);
        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                storelocation=location[i].toString();
                Toast.makeText(getContext(),BottomTabbedActivity.location+" selected as store",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {
                storelocation=location[0].toString();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recieptview.clear();
                gtotal=0;
                gcash=0;
                gcard=0;
                GetRecieptTask getRecieptTask=new GetRecieptTask();
                getRecieptTask.execute(dateinput.getText().toString().trim());

            }
        });





        return rootview;
    }

private class GetRecieptTask extends AsyncTask<String,Void,String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String Response = "";
        try {
            String data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
            URL url = new URL("http://dmis.club/dmis_app/getdatereciept.php");
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
                if (response.equals("found")){
                    try {
                        JSONArray jsonArray = new JSONArray(message);
                        JSONObject jsonObject1 = null;


                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            int id=jsonObject1.getInt("SaleID");
                            String phone = jsonObject1.getString("Phone");
                            String fname = jsonObject1.getString("Name");
                            String date = jsonObject1.getString("Date").trim();
                            String time = jsonObject1.getString("Time");
                            String location = jsonObject1.getString("Location").trim();
                            String cashier = jsonObject1.getString("Cashier");
                            String serviceDescription = jsonObject1.getString("ServiceDescription");
                            int total = Integer.parseInt(jsonObject1.getString("Total"));
                            int discount = Integer.parseInt(jsonObject1.getString("Discount"));
                            int net = Integer.parseInt(jsonObject1.getString("NetAmt"));
                            int cash = Integer.parseInt(jsonObject1.getString("Cash"));
                            int card = Integer.parseInt(jsonObject1.getString("Card"));

                            if(date.trim().equals(dateinput.getText().toString().trim()) && location.trim().equals(storelocation)){
                                Reciept reciept=new Reciept(id,phone,fname,date,time,location,cashier,serviceDescription,
                                        total,discount,net,cash,card);
                                gtotal=gtotal+net;
                                gcash=gcash+cash;
                                gcard=gcard+card;
                                recieptview.add(reciept);
                            }


                        }
                        RecieptListAdapter adapter= new RecieptListAdapter(getContext(),R.layout.reciept,recieptview);
                        recieptlv.setAdapter(adapter);
                        count.setText("Total Customer Visit :"+recieptlv.getCount());
                        cash.setText("Cash : $"+gcash);
                        card.setText("Card : $"+gcard);
                        total.setText("Grand Total : $"+gtotal+"  "+"Cash+Card"+gcash+gcard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
                else {



                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}