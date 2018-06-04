package com.example.meghamoondra.navigationbar.view.activity.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.Product;


import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static final String TAG = "ProductAdapter";
    private List<Product> mProduct;
    private IAdapterCommunicator iAdapterCommunicator;

    public ProductAdapter(List<Product> products, IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Product product = mProduct.get(position);
        holder.productName.setText(String.valueOf(product.getProductName()));
        holder.productPrice.setText("₹ " + String.valueOf(product.getPrice()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iAdapterCommunicator.navigateDetail(product);
            }
        });
        if (product.getImgUrl() == null || product.getImgUrl().isEmpty()) {
            holder.imageView.setBackground(ContextCompat.getDrawable(holder.imageView.getContext(), product.getDrawableId()));
        } else {
            Glide.with(holder.imageView.getContext())
                    .load(product.getImgUrl())
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }


    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;
        TextView productName;
        ImageView imageView;
        TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.home_card);
            productName = itemView.findViewById(R.id.product_title);
            imageView = itemView.findViewById(R.id.product_img);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }

    public interface IAdapterCommunicator {
        void navigateDetail(Product product);
    }
}


