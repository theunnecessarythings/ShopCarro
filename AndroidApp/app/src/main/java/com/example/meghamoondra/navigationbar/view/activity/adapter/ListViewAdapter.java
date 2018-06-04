package com.example.meghamoondra.navigationbar.view.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.Stock;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Stock> implements View.OnClickListener{

    private ArrayList<Stock> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView tvMerchantName;
        TextView tvMerchantRating;
        TextView tvPrice;
        TextView tvStock;
    }

    public ListViewAdapter(ArrayList<Stock> data, Context context) {
        super(context, R.layout.row_list_view, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Stock stock = (Stock)object;
        Toast.makeText(getContext(), "You clicked " + stock.getMerchantName(), Toast.LENGTH_SHORT).show();

        switch (v.getId())
        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +Stock.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stock stock = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_list_view, parent, false);
            viewHolder.tvMerchantName =  convertView.findViewById(R.id.tv_merchant_name);
            viewHolder.tvMerchantRating =  convertView.findViewById(R.id.tv_merchant_rating);
            viewHolder.tvPrice =  convertView.findViewById(R.id.tv_price);
            viewHolder.tvStock =  convertView.findViewById(R.id.tv_in_stock);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvMerchantName.setText("Seller : " + stock.getMerchantName());
        viewHolder.tvMerchantRating.setText(String.format("%.1f", stock.getRating()) + " ☆");
        viewHolder.tvPrice.setText("₹ " + String.valueOf(stock.getProductPrice()));
        viewHolder.tvStock.setText("In Stock : " + String.valueOf(stock.getNoOfItems()));

        return convertView;
    }
}