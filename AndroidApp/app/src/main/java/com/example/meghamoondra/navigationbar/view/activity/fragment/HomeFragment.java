package com.example.meghamoondra.navigationbar.view.activity.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.view.activity.MainActivity;
import com.example.meghamoondra.navigationbar.view.activity.ProductDetailsActivity;
import com.example.meghamoondra.navigationbar.view.activity.SearchActivity;
import com.example.meghamoondra.navigationbar.view.activity.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.productUrl;


public class HomeFragment extends Fragment implements ProductAdapter.IAdapterCommunicator {
    List<Product> productList;
    IAccount iAccount;
    ProductAdapter productadapter;
    RecyclerView myrv;
    ProgressBar progressBar;
    LinearLayout errorLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myrv =  view.findViewById(R.id.rv_products);
        progressBar = view.findViewById(R.id.pb_product_loading);
        myrv.setVisibility(View.GONE);
        errorLayout = view.findViewById(R.id.error_page_home);
        errorLayout.setVisibility(View.GONE);
        productList = new ArrayList<>();
        iAccount = AppController.getInstance().getClientProduct(productUrl).create(IAccount.class);

        getAllProducts();


        productadapter = new ProductAdapter(productList,this);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));

        myrv.setAdapter(productadapter);


    }

    private void getAllProducts() {
        Call<Product[]> call = iAccount.getAll();

        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() >= 200 && response.code() < 300) {
                    productList.addAll(Arrays.asList(response.body()));

                    productadapter.notifyDataSetChanged();
                    myrv.setVisibility(View.VISIBLE);

                } else {
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Sorry");
                alertDialog.setMessage("Server not responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                myrv.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void navigateDetail(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}



