package com.filip.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }

        };


        final EditText productName = (EditText) findViewById(R.id.productName);
        final EditText productQuantity = (EditText) findViewById(R.id.productQuantity);
        productQuantity.setFilters(new InputFilter[] { filter });
        Button addBtn = (Button) findViewById(R.id.add_button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button" ,"Button clicked");
                Toast.makeText(getApplicationContext(),"Product added", Toast.LENGTH_SHORT).show();
                String product_name = productName.getText().toString();
                String product_quantity = productQuantity.getText().toString();
                createProduct(product_name,Integer.parseInt(product_quantity));
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
        long productID = db.insertProduct(productName,quantity);
        ShoppingList product = db.getProduct(productID);
        arrayOfProducts.add(product);
        mAdapter.notifyDataSetChanged();
    }

}
