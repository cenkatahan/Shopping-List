package com.example.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList ids, names, amounts;

    public ProductAdapter(Context context, Activity activity, ArrayList ids, ArrayList names, ArrayList amounts) {
        this.context = context;
        this.activity = activity;
        this.ids = ids;
        this.names = names;
        this.amounts = amounts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_cardview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.cardViewName.setText(String.valueOf(names.get(position)));
        holder.cardViewAmount.setText(String.valueOf(amounts.get(position)));
        holder.itemView.setTag(ids.get(position));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView cardViewName, cardViewAmount;
        private LinearLayout cardView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_layout);
            cardViewName = itemView.findViewById(R.id.cardView_name);
            cardViewAmount = itemView.findViewById(R.id.cardView_amount);
        }
    }
}








































