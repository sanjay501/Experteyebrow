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

class CustomerListAdapter extends ArrayAdapter<CustomerDetail> {
        private Context mcontext;
        int mResource;
        public static ListView lv;



    public CustomerListAdapter(@NonNull Context context, int resource, ArrayList <CustomerDetail> objects) {
            super(context, resource, objects);
            mcontext = context;
            mResource = resource;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            String phone =getItem(position).getPhone();
            String fname=getItem(position).getFirstName();
            String lname=getItem(position).getLastName();
            String email=getItem(position).getEmail_address();
            String notes=getItem(position).getNotes();


            final CustomerDetail ss = new CustomerDetail(phone,fname,lname,email,notes);
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mResource,parent,false);

            TextView textView_name=(TextView) convertView.findViewById(R.id.textView_detailname);
            TextView textView_phone=(TextView) convertView.findViewById(R.id.textView_detailphone);
            //TextView textView_email=(TextView) convertView.findViewById(R.id.textView_detailemail);
            //TextView textView_notes=(TextView) convertView.findViewById(R.id.textView16);

            textView_name.setText(ss.getFirstName()+" "+ss.getLastName());
            //textView_email.setText(ss.getEmail_address());
            textView_phone.setText(ss.getPhone());
            //textView_notes.setText(ss.getNotes());
            final View finalConvertView = convertView;

            return  convertView;

        }


}


