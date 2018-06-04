package com.example.meghamoondra.navigationbar.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.view.activity.adapter.NewOrderAdapter;
import com.example.meghamoondra.navigationbar.model.NewOrderDetails;
import com.example.meghamoondra.navigationbar.view.activity.adapter.OrderAdapter;
import com.example.meghamoondra.navigationbar.model.OrderDetails;
import com.example.meghamoondra.navigationbar.model.OrderProduct;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.orderUrl;

public class OrderActivity extends AppCompatActivity implements OrderAdapter.IAdapterCommunicator {

    List<OrderDetails> productList;
    //IAccount iAccount;
    OrderAdapter orderAdapter;
    //the recyclerview
    RecyclerView recyclerView;
    private String email;
    Toolbar toolbar;
    private static final String ShopcarroPref = "ShopCarroPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        findViewById(R.id.btn_back_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (sharedPreferences.contains("username")) {
            if (sharedPreferences.getString("username", null) != null) {
                email = sharedPreferences.getString("username", null);
                //  Log.d("qwerty",productId + " " +merchantId + " " + email);
                // Toast.makeText(getApplicationContext(),"History",Toast.LENGTH_LONG).show();
                productList = new ArrayList<>();
                getHistory(email);
                //initializing the productlist
                orderAdapter = new OrderAdapter(productList, this);
                //setting adapter to recyclerview
                recyclerView.setAdapter(orderAdapter);
                Log.d("check", "onCreate: Megha");
            } else {
                Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                Intent i = new Intent(OrderActivity.this, LoginActivity.class);
                startActivity(i);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
            Intent i = new Intent(OrderActivity.this, LoginActivity.class);
            startActivity(i);
        }

    }


    private void getHistory(String email) {
        IAccount iAccount = AppController.getInstance().getClientOrder(orderUrl).create(IAccount.class);
        Call<OrderProduct> call = iAccount.getUserHistory(email);
        call.enqueue(new Callback<OrderProduct>() {
            @Override
            public void onResponse(Call<OrderProduct> call, Response<OrderProduct> response) {
                // Toast.makeText(getApplicationContext(), "Inside response", Toast.LENGTH_SHORT).show();
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    // Log.d("345",response.code() + " ");
                    //Log.d("oredrhistory",response.body().getDetails().toString());
                    productList.addAll(response.body().getDetails());
                    if(productList.size() != 0)
                        findViewById(R.id.error_page_search).setVisibility(View.GONE);
                    else {
                        findViewById(R.id.img_status).setBackgroundResource(R.drawable.emptycart);
                        TextView status = findViewById(R.id.tv_status);
                        status.setText("Your Order List is Empty");

                    }
                    Toast.makeText(getApplication(), "Your Orders", Toast.LENGTH_LONG).show();
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OrderProduct> call, Throwable t) {
                //  Log.d("History", "history "+ call.request().url());
                Toast.makeText(getApplicationContext(), "Order Server Failure", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public static class NewOrderActivity extends AppCompatActivity implements NewOrderAdapter.IAdapterCommunicator {

        List<NewOrderDetails> productList;
        //IAccount iAccount;
        NewOrderAdapter newOrderAdapter;
        //the recyclerview
        RecyclerView recyclerView;
        String email;
        private static final String ShopcarroPref = "ShopCarroPref";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            final SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_card_view);
            //getting the recyclerview from xml
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            if (sharedPreferences.contains("username")) {
                if (sharedPreferences.getString("username", null) != null) {
                    email = sharedPreferences.getString("username", null);
                    //  Log.d("qwerty",productId + " " +merchantId + " " + email);
                    //Toast.makeText(getApplicationContext(),"History",Toast.LENGTH_LONG).show();
                    productList = new ArrayList<>();
                    //getOrder(email);
                    //initializing the productlist
                    newOrderAdapter = new NewOrderAdapter(productList, this);
                    //setting adapter to recyclerview
                    recyclerView.setAdapter(newOrderAdapter);
                    // Log.d("check", "onCreate: Megha");
                } else {
                    Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }

        }


        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }



    }
}