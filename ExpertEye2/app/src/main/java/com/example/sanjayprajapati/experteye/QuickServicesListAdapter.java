package com.example.sanjayprajapati.experteye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.cart;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.discount;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.netamt;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.total;
import static com.example.sanjayprajapati.experteye.Cart.discountamt;
import static com.example.sanjayprajapati.experteye.Cart.netamount;


/**
 * Created by sanjayprajapati on 6/10/18.
 */

class QuickServicesListAdapter extends ArrayAdapter<Services> {
    private Context mcontext;
    int mResource;
    public ArrayList<Services> servicesArrayList;


    public QuickServicesListAdapter(@NonNull Context context, int resource, ArrayList <Services> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
        servicesArrayList=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int price=getItem(position).getPrice();
        String title =getItem(position).getTitle();
        String url = getItem(position).getImgURL();

        final Services ss = new Services(title,price,url);
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView textView_title=(TextView) convertView.findViewById(R.id.label);
        TextView textView_add=(TextView) convertView.findViewById(R.id.add);
        textView_title.setText(servicesArrayList.get(position).getTitle());
        textView_add.setText("$"+servicesArrayList.get(position).getPrice());
        textView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Services services=servicesArrayList.get(position);
                cart.add(services);
                Cart.listView.invalidateViews();
                total.setText("Total : $"+CartListAdapter.getTotal(cart));
                discount.setText("Discount : -$"+discountamt);
                netamount=CartListAdapter.getTotal(cart)-discountamt;
                netamt.setText("Net Amt : $"+netamount);


            }
        });
        return  convertView;

    }

}


