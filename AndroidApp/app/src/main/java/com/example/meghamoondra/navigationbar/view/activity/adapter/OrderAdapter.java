package com.example.meghamoondra.navigationbar.view.activity.adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.OrderDetails;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<OrderDetails> mProduct;

    private OrderAdapter.IAdapterCommunicator iAdapterCommunicator;

    public OrderAdapter(List<OrderDetails> products, IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderDetails product = mProduct.get(position);
        Log.d("Check", "onBindViewHolder: " + product);
        holder.productName.setText("Product Name :" + product.getProductName());
        holder.merchantName.setText("Merchant Name :" + product.getMerchantName());
        holder.date.setText("Date:" + product.getDate());
        holder.quantity.setText("Quantity:" + product.getQuantity());
        holder.price.setText("Price :" + product.getPrice());

        Glide.with(holder.imageView.getContext())
                .load(product.getImageUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView merchantName;
        TextView date;
        TextView quantity;
        TextView price;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.order_name);
            merchantName = itemView.findViewById(R.id.order_merchant);
            date = itemView.findViewById(R.id.order_date);
            quantity =itemView.findViewById(R.id.order_quantity);
            price  =itemView.findViewById(R.id.order_price);
            imageView=itemView.findViewById(R.id.imageView_order);

        }
    }

    public interface IAdapterCommunicator {
    }
}