package com.filip.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] nameArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    int[] quantityArray = {
            8,
            6,
            5,
            2,
            3,
            8,
            29,
            22
    };
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.shopping_list);
        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, quantityArray);


        listView.setAdapter(adapter);
    }
}
