package com.example.mycal;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class HistoryCursorAdapter extends CursorAdapter {
    public HistoryCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.fragment_history_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView textViewTanggal = (TextView) view.findViewById(R.id.textViewListTanggal);
        TextView textViewKalori = (TextView) view.findViewById(R.id.textViewListKalori);
        TextView textViewKalori1 = (TextView) view.findViewById(R.id.textViewListKalori1);
        TextView textViewProtein = (TextView) view.findViewById(R.id.textViewListProtein);
        TextView textViewKarbo = (TextView) view.findViewById(R.id.textViewListKarbo);
        TextView textViewLemak = (TextView) view.findViewById(R.id.textViewListLemak);

        int getId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String getTanggal = cursor.getString(cursor.getColumnIndexOrThrow("food_diary_sum_date"));
        String getKalori = cursor.getString(cursor.getColumnIndexOrThrow("food_diary_sum_energy"));
        String getTotalProtein = cursor.getString(cursor.getColumnIndexOrThrow("food_diary_sum_proteins"));
        String getTotalKarbo = cursor.getString(cursor.getColumnIndexOrThrow("food_diary_sum_carbs"));
        String getTotalLemak = cursor.getString(cursor.getColumnIndexOrThrow("food_diary_sum_fat"));

        String TotalKalori = "Total kalori dimakan : " + getKalori + " KCal";
        String TolalProtein = "Total protein dimakan : " + getTotalProtein + "g";
        String TotalKarbo = "Total karbohidrat dimakan : " + getTotalKarbo + "g";
        String TotalLemak = "Total lemak dimakan : " + getTotalLemak + "g";

        textViewTanggal.setText(getTanggal);
        textViewKalori.setText(getKalori);
        textViewKalori1.setText(TotalKalori);
        textViewProtein.setText(TolalProtein);
        textViewKarbo.setText(TotalKarbo);
        textViewLemak.setText(TotalLemak);
    }
}
