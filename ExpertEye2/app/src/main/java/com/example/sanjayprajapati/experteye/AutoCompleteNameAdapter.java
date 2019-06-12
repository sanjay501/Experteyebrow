package com.example.sanjayprajapati.experteye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayprajapati on 6/30/18.
 */

public class AutoCompleteNameAdapter extends ArrayAdapter<CustomerDetail> {
    private List<CustomerDetail> CustomerListFull;


    public AutoCompleteNameAdapter(@NonNull Context context,  @NonNull List<CustomerDetail> CustomerList) {
        super(context, 0, CustomerList);
        CustomerListFull=new ArrayList <>(CustomerList);
    }

    public Filter getFilter(){
        return CustomerFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.spinnerview,parent,false
            );
        }
        TextView tv_name=(TextView) convertView.findViewById(R.id.textView12);
        TextView tv_phone=(TextView) convertView.findViewById(R.id.textView13);

        CustomerDetail customer=getItem(position);
        if(customer!=null){
            tv_name.setText(customer.getFirstName()+" "+customer.getLastName());
            tv_phone.setText(customer.getPhone());
        }

        return convertView;
    }

    private Filter CustomerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CustomerDetail> suggestion=new ArrayList <>();

            if (constraint==null || constraint.length()==0){
                suggestion.addAll(CustomerListFull);
            }else {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for(CustomerDetail item:CustomerListFull){
                    if(item.getFirstName().toLowerCase().contains(filterpattern) ||
                            item.getLastName().toLowerCase().contains(filterpattern) ||
                            item.getPhone().toLowerCase().contains(filterpattern)
                            ){
                        suggestion.add(item);
                    }
                }

            }
            results.values=suggestion;
            results.count=suggestion.size();
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return  ((CustomerDetail) resultValue).getPhone();
        }
    };



}
