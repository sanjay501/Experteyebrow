package com.example.sanjayprajapati.experteye;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayprajapati on 6/13/18.
 */

public class CustomerModel {

    public List<CustomerDetail> findAll(){
        List<CustomerDetail> customer_list=BottomTabbedActivity.details;
        return customer_list;

    }

    public ArrayList <Services> loadThreading(){
        ArrayList <Services> services=new ArrayList <>();
        Services chin = new Services("Chin", 10, "drawable://" + R.drawable.facial);
        Services eyebrow = new Services("EyeBrows", 12, "drawable://" + R.drawable.deal);
        Services upperlips = new Services("Upper Lips", 10, "drawable://" + R.drawable.heena);
        Services forehead = new Services("Forehead", 10, "drawable://" + R.drawable.facial);
        services.add(eyebrow);
        services.add(upperlips);
        services.add(chin);
        services.add(forehead);
        return services;

    }

    public ArrayList <Services> loadDeals(){
        ArrayList <Services> services=new ArrayList <>();
        Services deal1 = new Services("Threading and Tinting",30, "drawable://" + R.drawable.facial);
        Services deal2 = new Services("Threading and Heena", 20, "drawable://" + R.drawable.deal);
        Services deal3 = new Services("Threading and Facial", 50, "drawable://" + R.drawable.heena);
        services.add(deal1);
        services.add(deal2);
        services.add(deal3);

        return services;

    }

    public ArrayList <Services> loadFacial(){
        ArrayList <Services> services=new ArrayList <>();
        Services fullface = new Services("Full Face", 40, "drawable://" + R.drawable.deal);
        Services deal3 = new Services("Eyebrow Tinting", 18, "drawable://" + R.drawable.heena);
        Services deal4 = new Services("Eyelash Extension", 35, "drawable://" + R.drawable.heena);
        Services deal5 = new Services("Facila", 50, "drawable://" + R.drawable.heena);
        services.add(fullface);
        services.add(deal3);
        services.add(deal4);
        services.add(deal5);
        return services;

    }

    public ArrayList <Services> loadHeena(){
        ArrayList <Services> services=new ArrayList <>();
        Services deal2 = new Services("Heena full", 15, "drawable://" + R.drawable.deal);
        Services deal3 = new Services("Heena half", 30, "drawable://" + R.drawable.deal);
        services.add(deal2);
        services.add(deal3);

        return services;

    }
    public ArrayList <Services> LoadAll(){
        ArrayList <Services> services=new ArrayList <>();
        Services eyebrow = new Services("EyeBrows", 15, "drawable://" + R.drawable.deal);
        Services fullface = new Services("Full Face", 40, "drawable://" + R.drawable.deal);
        Services upperlips = new Services("Upper Lips", 10, "drawable://" + R.drawable.heena);
        Services forehead = new Services("Forehead", 10, "drawable://" + R.drawable.facial);
        Services chin = new Services("Chin", 10, "drawable://" + R.drawable.facial);
        Services tinting = new Services("Eyebrow Tinting", 18, "drawable://" + R.drawable.heena);
        Services eyelash_extension = new Services("Eyelash Extension", 35, "drawable://" + R.drawable.heena);
        Services facilia = new Services("Facila", 50, "drawable://" + R.drawable.heena);
        Services threading_and_tinting = new Services("Threading and Tinting",30, "drawable://" + R.drawable.facial);
        Services threading_and_heena = new Services("Threading and Heena", 20, "drawable://" + R.drawable.deal);
        Services threading_and_facial = new Services("Threading and Facial", 50, "drawable://" + R.drawable.heena);
        Services heena_full = new Services("Heena full", 15, "drawable://" + R.drawable.deal);
        Services heena_half = new Services("Heena half", 30, "drawable://" + R.drawable.deal);
        Services free_eyebrow = new Services("Free Eyebrow", 0, "drawable://" + R.drawable.deal);
        services.add(eyebrow);
        services.add(tinting);
        services.add(fullface);
        services.add(upperlips);
        services.add(forehead);
        services.add(chin);
        services.add(eyelash_extension);
        services.add(facilia);
        services.add(threading_and_tinting);
        services.add(threading_and_heena);
        services.add(threading_and_facial);
        services.add(heena_full);
        services.add(heena_half);
        services.add(free_eyebrow);


        return services;

    }


}
