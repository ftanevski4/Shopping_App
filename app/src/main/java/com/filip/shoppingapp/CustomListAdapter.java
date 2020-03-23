package com.filip.shoppingapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.filip.shoppingapp.database.model.ShoppingList;

import java.util.ArrayList;


public class CustomListAdapter extends ArrayAdapter<ShoppingList> {

    private ArrayList<ShoppingList> productList;

    public CustomListAdapter(MainActivity context, ArrayList<ShoppingList> products) {
        super(context, 0, products);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShoppingList product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.nameView);
        TextView quantityView = (TextView) convertView.findViewById(R.id.quantityView);


        nameView.setText(product.getName());
        quantityView.setText(String.valueOf(product.getQuantity()));

        return convertView;
    }
}
