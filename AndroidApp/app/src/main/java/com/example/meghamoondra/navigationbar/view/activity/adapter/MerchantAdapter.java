package com.example.meghamoondra.navigationbar.view.activity.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.Stock;

import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder>{

    private List<Stock> mProduct;
    private String mimageUrl;

    private MerchantAdapter.IAdapterCommunicator iAdapterCommunicator;

    public MerchantAdapter(List<Stock> products,String imageUrl, IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        mimageUrl = imageUrl;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }


    @NonNull
    @Override
    public MerchantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_list_layout, parent, false);
        return new MerchantAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Stock product = mProduct.get(position);
        Log.d("Check", "onBindViewHolder: " + product);
        holder.merchantName.setText(product.getMerchantName());
        holder.productRating.setText(product.getRating() + "");
        holder.noOfItems.setText(product.getNoOfItems() + "");
        holder.productPrice.setText(product.getProductPrice() + "");
        Log.d("imageHolder", ""+mimageUrl);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCommunicator.navigateDetail(product.getProductId().toString(),product.getMerchantId());
            }
        });

            Glide.with(holder.imageView.getContext())
                    .load(mimageUrl)
                    .into(holder.imageView);


    }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout cardView;
            TextView merchantName;
            TextView productRating;
            TextView noOfItems;
            TextView productPrice;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView =itemView.findViewById(R.id.productCard_merchant);
                merchantName = itemView.findViewById(R.id.textViewTitle_merchant);
                productRating = itemView.findViewById(R.id.text_merchant);
                noOfItems = itemView.findViewById(R.id.Rating_merchant);
                productPrice = itemView.findViewById(R.id.Price_merchant);
                imageView = itemView.findViewById(R.id.imageView_merchant);
            }
        }

        public interface IAdapterCommunicator {
            void navigateDetail(String productId, String merchantId);
        }
    }



