package com.example.meghamoondra.navigationbar.view.activity.adapter;
//package com.example.meghamoondra.navigationbar.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.CartDetails;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private static final String TAG = "CartAdapter";

    private List<CartDetails> mProduct;


    private CartAdapter.IAdapterCommunicator iAdapterCommunicator;
    public CartAdapter(List<CartDetails> products, CartAdapter.IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_listlayout, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final CartDetails productList= mProduct.get(position);
        Log.d(TAG, "data1: " + productList);
        holder.productName.setText("Product Name :" + productList.getProductName());
        holder.merchantName.setText("Merchant Name :" + productList.getMerchantName());
       // holder.date.setText("Date:" + product.getDate());
        holder.quantity.setText("Quantity:" + productList.getQuantity());
        holder.productPrice.setText("Price :" + productList.getPrice());

        Glide.with(holder.imageView.getContext())
                .load(productList.getImageUrl())
                .into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCommunicator.deleteItem(holder.getAdapterPosition());
            }
        });


    }




    @Override
    public int getItemCount() {
        // Set the list count
        return mProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView merchantName;
        private Button delete;
        private TextView quantity;
        private TextView productPrice;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_name);
            merchantName = itemView.findViewById(R.id.cart_merchant);
            delete = itemView.findViewById(R.id.c_del);
            quantity = itemView.findViewById(R.id.cart_quantity);
            productPrice = itemView.findViewById(R.id.cart_price);
            imageView = itemView.findViewById(R.id.cart_View);
        }
    }

    public interface IAdapterCommunicator {
        void deleteItem(int position);
    }
}



