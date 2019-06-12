package com.example.sanjayprajapati.experteye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.cart;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.discount;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.netamt;
import static com.example.sanjayprajapati.experteye.BottomTabbedActivity.total;
import static com.example.sanjayprajapati.experteye.Cart.discountamt;
import static com.example.sanjayprajapati.experteye.Cart.netamount;
import static com.example.sanjayprajapati.experteye.Cart.totalamt;



/**
 * Created by sanjayprajapati on 6/10/18.
 */

class CartListAdapter extends ArrayAdapter<Services> {
        private Context mcontext;
        int mResource;
        public static ListView lv;
        ViewHolder holder;



    public CartListAdapter(@NonNull Context context, int resource, ArrayList <Services> objects) {
            super(context, resource, objects);
            mcontext = context;
            mResource = resource;

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

            TextView textView_title=(TextView) convertView.findViewById(R.id.textView41);
            TextView textView_price=(TextView) convertView.findViewById(R.id.textView51);
            ImageView imageView =(ImageView) convertView.findViewById(R.id.imageView3);
            textView_price.setText("$"+price);
            textView_title.setText(title);

            ImageButton delete =(ImageButton) convertView.findViewById(R.id.imageButton_add1);
            final View finalConvertView = convertView;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),ss.getTitle()+" deleted from cart",Toast.LENGTH_SHORT);
                    cart.remove(position);
                    Cart.listView.invalidateViews();
                    total.setText("Total : $"+getTotal(cart));
                    discount.setText("Discount : -$"+discountamt);
                    netamount=getTotal(cart)-discountamt;
                    netamt.setText("Net Amt : $"+netamount);
                    //Home.cartcount.setText(""+cart.size());
                }
            });
            return  convertView;

        }
    public static int getTotal(ArrayList<Services> cartitem){
        totalamt=0;
        for(int i=0;i<cartitem.size();i++){
            totalamt = totalamt+cartitem.get(i).getPrice();

        }
        return totalamt;
    }




}


