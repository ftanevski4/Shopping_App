package com.filip.shoppingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] nameArray;
    private final int[] quantityArray;

    public CustomListAdapter(Activity context, String[] nameArrayParam, int[] quantityArrayParam){

        super(context,R.layout.activity_listview , nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.quantityArray = quantityArrayParam;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_listview, null,true);


        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        TextView quantityTextField = (TextView) rowView.findViewById(R.id.quantityView);

        nameTextField.setText(nameArray[position]);
        quantityTextField.setText(String.valueOf(quantityArray[position]));

        return rowView;

    };
}
