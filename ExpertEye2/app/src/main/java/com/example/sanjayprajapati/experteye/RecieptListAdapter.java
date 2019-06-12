package com.example.sanjayprajapati.experteye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.cart;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.discount;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.netamt;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.total;

/**
 * Created by sanjayprajapati on 6/10/18.
 */

class RecieptListAdapter extends ArrayAdapter<Reciept> {
        private Context mcontext;
        int mResource;
        public static ListView lv;



    public RecieptListAdapter(@NonNull Context context, int resource, ArrayList <Reciept> objects) {
            super(context, resource, objects);
            mcontext = context;
            mResource = resource;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            int id=getItem(position).getSaleID();
            String phone =getItem(position).getPhone();
            String name = getItem(position).getName();
            String date =getItem(position).getDate();
            String time = getItem(position).getTime();
            String location =getItem(position).getLocation();
            String cashier = getItem(position).getCashier();
            String desc =getItem(position).getServiceDescription();
            int total=(getItem(position).getTotal());
            int discount = getItem(position).getDiscount();
            int net = getItem(position).getNetAmount();
            int cash = getItem(position).getCash();
            int card = getItem(position).getCard();


            final Reciept reciept=new Reciept(id,phone,name,date,time,location,cashier,desc,total,discount,net,cash,card);
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mResource,parent,false);

            TextView textView_storephone=(TextView) convertView.findViewById(R.id.tv_phone);
            TextView textView_id=(TextView) convertView.findViewById(R.id.tv_saleid);
            TextView textView_cashier=(TextView) convertView.findViewById(R.id.textView20);
            TextView textView_location=(TextView) convertView.findViewById(R.id.tv_location2);
            TextView textView_date=(TextView) convertView.findViewById(R.id.textView22);
            TextView textView_time=(TextView) convertView.findViewById(R.id.textView21);
            TextView textView_name=(TextView) convertView.findViewById(R.id.textView23);
            TextView textView_phone=(TextView) convertView.findViewById(R.id.textView24);
            TextView textview_desc=(TextView) convertView.findViewById(R.id.textView26);
            TextView textview_total=(TextView) convertView.findViewById(R.id.textView28);
            TextView textview_disc=(TextView) convertView.findViewById(R.id.textView29);
            TextView textview_netamt=(TextView) convertView.findViewById(R.id.textView30);

            if(location=="Malden"){
                textView_storephone.setText("(781) 333-1459");
            }else{
                textView_storephone.setText("(857) 363-7165");
            }


            textView_id.setText("Sale id : "+id);
            textView_cashier.setText("Cashier : "+cashier);
            textView_location.setText("Location : "+location);
            textView_date.setText(date);
            textView_time.setText(time);
            textView_name.setText(name);
            textView_phone.setText(phone);
            textview_desc.setText(desc);
            textview_total.setText("Total : $"+total);
            textview_disc.setText("Discount : -$"+discount);
            textview_netamt.setText("Net Amount : $"+net);



            final View finalConvertView = convertView;
            return  convertView;

        }

}


