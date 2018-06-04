package com.example.meghamoondra.navigationbar.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.CartDetails;
import com.example.meghamoondra.navigationbar.model.CartProduct;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.view.activity.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.addCartUrl;
import static com.example.meghamoondra.navigationbar.controller.Urls.orderUrl;

public class CartActivity extends AppCompatActivity implements CartAdapter.IAdapterCommunicator {

    private static final String TAG = "CartActivity";
    List<CartDetails> productList;
    CartAdapter cartAdapter;
    IAccount iAccount;
    private String email;
    private String productId;
    private String merchantId;
    private Button delete;
    private Button buy;
    RecyclerView recyclerView;
    private Button btBuyall;
    private static final String ShopcarroPref = "ShopCarroPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_cart);


        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        findViewById(R.id.btn_back_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.rv_cart_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btBuyall = findViewById(R.id.buttonBuyAll);




        if (sharedPreferences.contains("username")) {
            if (sharedPreferences.getString("username", null) != null) {
                email = sharedPreferences.getString("username", null);
                productList = new ArrayList<>();
                getCartProducts(email);
                cartAdapter = new CartAdapter(productList, this);
                //setting adapter to recyclerview
                recyclerView.setAdapter(cartAdapter);
            }


        } else {
            Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
            Intent i = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(i);
        }
        // getDelete(email,productId,merchantId);


        btBuyall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
                progressDialog.show();
                IAccount iAccount = AppController.getInstance().getClientOrder(orderUrl).create(IAccount.class);
                Call<CartProduct> call = iAccount.buyAll(email);

                call.enqueue(new Callback<CartProduct>() {
                    @Override
                    public void onResponse(Call<CartProduct> call, Response<CartProduct> response) {
                        Log.d(TAG, "onResponse: " + response.code());
                        Log.d(TAG, "onResponse: " + call.request().url());
                        if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                            productList.addAll(response.body().getDetails());
                            cartAdapter.notifyDataSetChanged();
                            if (response.body().getDetails() == null || response.body().getDetails().isEmpty()) {
                                btBuyall.setVisibility(View.GONE);
                                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(CartActivity.this).create();
                                alertDialog.setTitle("Uh Oh");
                                alertDialog.setMessage("Your Cart is Empty");
                                alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                btBuyall.setVisibility(View.VISIBLE);
                            }

                            Log.d("Body", "onResponse: " + response.body());
                            Log.d(TAG, "onResponse: " + productList);
                            Toast.makeText(getApplication(), "Order Purchased Successfully", Toast.LENGTH_SHORT).show();
                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(CartActivity.this).create();
                            alertDialog.setTitle("Congrats !!!");
                            alertDialog.setMessage("Order Purchased Successfully");
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(CartActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            alertDialog.show();
                            progressDialog.dismiss();
                           // Intent intent;
                            //intent = new Intent(CartActivity.this, OrderActivity.class);
                            //startActivity(intent);
                        } else {

                            Toast.makeText(getApplication(), "Server Problem", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartProduct> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server Problem", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });


    }

    private void getCartProducts(String email) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        IAccount iAccount = AppController.getInstance().getClientAddCart(addCartUrl).create(IAccount.class);
        Call<CartProduct> call = iAccount.getCart(email);
        call.enqueue(new Callback<CartProduct>() {
            @Override
            public void onResponse(Call<CartProduct> call, Response<CartProduct> response) {
                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + call.request().url());
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    productList.addAll(response.body().getDetails());
                    if (response.body().getDetails() == null || response.body().getDetails().isEmpty()) {
                        btBuyall.setVisibility(View.GONE);
                        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(CartActivity.this).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage("Cart is Empty");
                        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(CartActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        btBuyall.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplication(), "Your Cart", Toast.LENGTH_SHORT).show();
                    cartAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplication(), "Out of stock", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CartProduct> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request().url());
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Cart Server Failure", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void deleteItem(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        IAccount iAccount = AppController.getInstance().getClientAddCart(addCartUrl).create(IAccount.class);
        Call<Boolean> call = iAccount.getDeleteItem(email, productList.get(position).getMerchantId(), productList.get(position).getId(), productList.get(position).getQuantity());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("delete ", "deleteItem" + response.code());
                if (response.code() >= 200 && response.code() < 300 && response.body() == true) {
                    productList.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Delete", Toast.LENGTH_LONG).show();
            }
        });
    }
}

