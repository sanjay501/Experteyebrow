package com.example.sanjayprajapati.experteye;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sanjayprajapati on 6/8/18.
 */

public class Threading extends Fragment {
    TextView title;
    ListView listView;

    public Threading() {


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View threadingview = inflater.inflate(R.layout.services, null);
        Services chin=new Services("Chin",5,"drawable://"+R.drawable.facial);
        Services eyebrow=new Services("EyeBrows",5,"drawable://"+R.drawable.deal);
        Services upperlips=new Services("Upper Lips",5,"drawable://"+R.drawable.heena);
        Services lowerlips=new Services("Lower Lips",5,"drawable://"+R.drawable.facial);
        Services neck=new Services("Neck",5,"drawable://"+R.drawable.deal);
        Services SideBurns=new Services("SideBurns",5,"drawable://"+R.drawable.heena);

        ArrayList<Services> services=new ArrayList<>();
        services.add(eyebrow);
        services.add(upperlips);
        services.add(lowerlips);
        services.add(neck);
        services.add(SideBurns);
        String[] servicess = {"sanjay", "salma", "ram"};



        title = (TextView) threadingview.findViewById(R.id.tv_title);
        title.setText("THREADING");
        listView = (ListView) threadingview.findViewById(R.id.lv_services);

        if (getContext() == null) {
            Toast.makeText(getContext(), "context is null", Toast.LENGTH_LONG);
        } else {

        }

        return threadingview;
    }
}










