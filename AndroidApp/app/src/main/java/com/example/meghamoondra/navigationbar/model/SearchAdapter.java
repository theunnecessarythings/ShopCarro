package com.example.meghamoondra.navigationbar.model;

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
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.meghamoondra.navigationbar.controller.Urls.productUrl;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static IAccount iAccount;
    Product productDetails = new Product();

    private List<SearchProduct> mProduct;
    private IAdapterCommunicator iAdapterCommunicator;
    public SearchAdapter(List<SearchProduct> products, IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Input the layout id
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SearchProduct product = mProduct.get(position);
        holder.productName.setText(product.getProductName());
        holder.productDesc.setText(product.getDescription().substring(0, Math.min(product.getDescription().length(), 50)) + "...");
        holder.productRating.setText("Merchants Selling: " + product.getMerchantId().size());
        holder.productPrice.setText("₹ " + product.getPrice().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InsideCardView","this");
                getProductsDetails(product.getId());
            }
        });

        Glide.with(holder.imageView.getContext())
                .load(product.getImgUrl())
                .into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardView;
        TextView productName;
        TextView productDesc;
        TextView productRating;
        TextView productPrice;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.productCard);
            productName = itemView.findViewById(R.id.tv_product_name);
            productDesc = itemView.findViewById(R.id.tv_description);
            productRating = itemView.findViewById(R.id.tv_sellers);
            productPrice = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.product_img);
        }
    }

    public void getProductsDetails(String productId){
        iAccount = AppController.getInstance().getClientProduct(productUrl).create(IAccount.class);
        Call<Product> call = iAccount.get(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productDetails = response.body();
                iAdapterCommunicator.navigateDetail(productDetails);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("searchAdapter","On Failure from Search Adapter");
            }
        });
    }


    public interface IAdapterCommunicator {
        void navigateDetail(Product product);
    }
}
