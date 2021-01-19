package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText productName;
    private TextView productAmount;
    private Button button_increase, button_decrease, button_add;
    private RecyclerView recyclerView;

    private ArrayList<String> ids, names, amounts;
    private ProductAdapter adapter;
    private ProductDatabaseHelper productDb;

    private int amount = 0;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        productName = findViewById(R.id.editText_productName);
        productAmount = findViewById(R.id.textView_amount);
        button_decrease = findViewById(R.id.button_decreaseAmount);
        button_increase = findViewById(R.id.button_increaseAmount);
        button_add = findViewById(R.id.button_add);

        recyclerView = findViewById(R.id.recyclerView);
        productDb = new ProductDatabaseHelper(MainActivity.this);
        ids = new ArrayList<>();
        names = new ArrayList<>();
        amounts = new ArrayList<>();


        displayProducts();

        adapter = new ProductAdapter(MainActivity.this, this, ids, names, amounts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                productDb.deleteTheProduct((String) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        button_increase.setOnClickListener(v -> {
            amount++;
            productAmount.setText(String.valueOf(amount));
        });

        button_decrease.setOnClickListener(v -> {
            if(amount <= 0){
                amount--;
                productAmount.setText(String.valueOf(amount));
            }
        });

        button_add.setOnClickListener(v -> {

            new AddProductTask().execute(productName.getText().toString().trim(), productAmount.getText().toString().trim());

        });


    }


    public void displayProducts(){
        Cursor cursor =productDb.readAllProducts();

        while (cursor.moveToNext()){
            ids.add(cursor.getString(0));
            names.add(cursor.getString(1));
            amounts.add(cursor.getString(2));
        }
    }



    private class AddProductTask extends AsyncTask<String, Integer, String>{


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Adding");
            progressDialog.setMessage("Adding Record... Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String name = strings[0];
            String amount = strings[1];
            productDb.addProduct(name, amount);
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return name + " "+ amount;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            MainActivity.this.recreate();
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
        }
    }
}


































