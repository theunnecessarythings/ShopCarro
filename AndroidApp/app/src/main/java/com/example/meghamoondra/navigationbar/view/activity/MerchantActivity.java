package com.example.meghamoondra.navigationbar.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.MerchantModel;
import com.example.meghamoondra.navigationbar.model.Stock;
import com.example.meghamoondra.navigationbar.view.activity.adapter.MerchantAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.merchantUrl;

public class MerchantActivity extends AppCompatActivity implements MerchantAdapter.IAdapterCommunicator {

    List<Stock> productList;
    IAccount iAccount;
    MerchantAdapter merchantAdapter;
    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.rv_merchant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<Stock>();

        //getIntent().getSerializableExtra("Product");
        iAccount = AppController.getInstance().getClientMerchant(merchantUrl).create(IAccount.class);

        String imageUrl = getIntent().getStringExtra("ImageUrl");

        MerchantModel merchantModel = (MerchantModel) getIntent().getSerializableExtra("Product");
        getAllMerchantProducts(merchantModel, imageUrl);


        //creating recyclerview adapter
        merchantAdapter = new MerchantAdapter(productList, imageUrl, this);

        //setting adapter to recyclerview
        recyclerView.setAdapter(merchantAdapter);
    }

    private void getAllMerchantProducts(MerchantModel merchantModel, String imageUrl) {
        Call<Stock[]> call = iAccount.getMerchantProducts(merchantModel);

        call.enqueue(new Callback<Stock[]>() {
            @Override
            public void onResponse(Call<Stock[]> call, Response<Stock[]> response) {

                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    productList.addAll(Arrays.asList(response.body()));
                    merchantAdapter.notifyDataSetChanged();
                    //progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Stock[]> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Merchant FAILURE", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void navigateDetail(String productId, String merchantId) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductId", productId);
        intent.putExtra("MerchantId", merchantId);
        //Log.d("HomeImage", "navigateDetail: "+imageUrl);
        startActivity(intent);
    }
}

