package com.filip.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.filip.shoppingapp.database.DatabaseHelper;
import com.filip.shoppingapp.database.model.ShoppingList;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<ShoppingList> arrayOfProducts;
    CustomListAdapter mAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        arrayOfProducts = db.getAllProducts();
        mAdapter = new CustomListAdapter(this,arrayOfProducts);
        listView = (ListView) findViewById(R.id.shopping_list);

        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final EditText productName = (EditText) findViewById(R.id.productName);
        final EditText productQuantity = (EditText) findViewById(R.id.productQuantity);
        Button addBtn = (Button) findViewById(R.id.add_button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button" ,"Button clicked");
                Toast.makeText(getApplicationContext(),"Product added", Toast.LENGTH_SHORT).show();
                String product_name = productName.getText().toString();
                int product_quantity = Integer.parseInt(productQuantity.getText().toString());
                createProduct(product_name,product_quantity);
                productName.setText("");
                productQuantity.setText("");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createProduct(String productName, int quantity){
        db.insertProduct(productName,quantity);
        arrayOfProducts.add(db.getProduct(db.insertProduct(productName,quantity)));
        mAdapter.notifyDataSetChanged();
    }

}
