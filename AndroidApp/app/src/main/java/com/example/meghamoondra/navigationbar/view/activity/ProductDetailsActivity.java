package com.example.meghamoondra.navigationbar.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.view.activity.adapter.ListViewAdapter;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.MerchantModel;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.model.Stock;
import com.example.meghamoondra.navigationbar.model.StockId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.addCartUrl;
import static com.example.meghamoondra.navigationbar.controller.Urls.merchantUrl;
import static com.example.meghamoondra.navigationbar.controller.Urls.orderUrl;

public class ProductDetailsActivity extends AppCompatActivity {
    IAccount iAccount;
    private Button buyNow;
    private Button addToCart;
    private TextView productName;
    private TextView productDesc;
    private TextView productRating;
    private TextView productPrice;
    private TextView merchantId;
    private ImageView imageView;
    private String merchantid;
    private Product productdetails;
    private TextView defaultSeller;
    private ListView listView;
    private EditText quantity;


    private static final String ShopcarroPref = "ShopCarroPref";

    ArrayList<Stock> stocks = new ArrayList<>();

    ListViewAdapter listViewAdapter;

    private static final String TAG = "ProductDetailsActivity";
    Stock defaultStock;
    Long stockQuantity = Long.valueOf(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar_product_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        findViewById(R.id.btn_back_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * Check this line of code later
         */
        iAccount = AppController.getInstance().getClientAuthentication(merchantUrl).create(IAccount.class);


        final Product product = (Product) getIntent().getSerializableExtra("product");

        //productid = getIntent().getStringExtra("ProductId");
        //merchantid = getIntent().getStringExtra("MerchantId");
        merchantId = findViewById(R.id.pd_id);
        productName = findViewById(R.id.pd_name);
        productDesc = findViewById(R.id.pd_desc);
        productRating = findViewById(R.id.pd_rat);
        productPrice = findViewById(R.id.pd_price);
        imageView = findViewById(R.id.pd_imageView);
        defaultSeller = findViewById(R.id.tv_current_seller);
        listView = findViewById(R.id.lv_merchants);
        quantity = findViewById(R.id.et_quantity);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                defaultStock = stocks.get(position);
                defaultSeller.setText("You have selected Seller : " + defaultStock.getMerchantName());
            }
        });

        /**
         * Don't need this
         */

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        setListViewHeightBasedOnChildren(listView);


        /**
         * Adapter List View
         */
        listViewAdapter = new ListViewAdapter(stocks, getApplicationContext());
        listView.setAdapter(listViewAdapter);


        /**
         * Adding product details here itself
         */

        merchantId.setText("Sold by " + product.getMerchantId().size() + " sellers.");
        productName.setText(product.getProductName());
        productDesc.setText(product.getDescription());
        productPrice.setText("₹ " + product.getPrice().toString());
        Glide.with(imageView.getContext())
                .load(product.getImgUrl())
                .into(imageView);

        /** Set the rating after merchant call
         *
         */
//        productRating.setText(product.get);


        /**
         * for now comment
         */
        //getProductData(productid);

        getMerchantData(product);

        buyNow = findViewById(R.id.pd_buy);
        addToCart = findViewById(R.id.pd_cart);

        /**
         * check the flow later
         */
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockQuantity = Long.valueOf(quantity.getText().toString());
                if (sharedPreferences.contains("username")) {
                    if (sharedPreferences.getString("username", null) != null) {
                        String email = sharedPreferences.getString("username", null);
                        if(defaultStock!=null && stockQuantity!=null) {
                            buyNow(email, defaultStock.getMerchantId(), defaultStock.getProductId(), stockQuantity, product.getProductName());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "out of Stock", Toast.LENGTH_LONG).show();
                        }
                        } else {
                        Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ProductDetailsActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ProductDetailsActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockQuantity = Long.valueOf(quantity.getText().toString());

                if (sharedPreferences.contains("username")) {
                    if (sharedPreferences.getString("username", null) != null) {
                        String email = sharedPreferences.getString("username", null);
                        if(defaultStock!=null && product != null && stockQuantity!=null) {
                            getAddCart(email, defaultStock.getMerchantId(), defaultStock.getProductId(), stockQuantity);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "out of Stock", Toast.LENGTH_LONG).show();
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Go to login", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ProductDetailsActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private void getMerchantData(Product product) {
        MerchantModel merchantModel = new MerchantModel();
        List<String> merchantIds = product.getMerchantId();
        String productId = product.getId();
        List<StockId> stockIds = new ArrayList<>();
        for (String merchantId : merchantIds) {
            stockIds.add(new StockId(merchantId, productId));
        }
        merchantModel.setStockIds(stockIds);

        getMerchantAndStock(merchantModel);
    }

    private void getMerchantAndStock(MerchantModel merchantModel) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        IAccount iAccount = AppController.getInstance().getClientMerchant(merchantUrl).create(IAccount.class);
        Call<Stock[]> call = iAccount.getMerchantProducts(merchantModel);

        Log.d("getMerchant", "getMerchantAndStock: " + call.request().url());
        call.enqueue(new Callback<Stock[]>() {
            @Override
            public void onResponse(Call<Stock[]> call, Response<Stock[]> response) {
                progressDialog.dismiss();
                Log.d("Response", "onResponse: " + response.body());
                Log.d("Response", "onResponse: " + response.code());
                if (response.code() >= 200 && response.code() < 300 && response.body()!= null){
                    stocks.clear();
                    stocks.addAll(Arrays.asList(response.body()));
                    defaultSeller.setText("You have selected seller : " + stocks.get(0).getMerchantName());
                    defaultStock = stocks.get(0);
                    listViewAdapter.notifyDataSetChanged();
                    Log.d("Inflating", "onResponse: " + stocks);
                    //merchantAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong in merchant service", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stock[]> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "OnFailure : Something went wrong in merchant service", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getProductData(String productid) {
        Call<Product> call = iAccount.get(productid);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productdetails = response.body();
                Log.d("123", productdetails.toString());
                merchantId.setText("Merchant Id :" + merchantid);
                productDesc.setText("Description :" + productdetails.getDescription());
                productPrice.setText("Product Price :" + productdetails.getPrice() + "");
                productName.setText("Product Name :" + productdetails.getProductName());
                Glide.with(imageView.getContext())
                        .load(productdetails.getImgUrl())
                        .into(imageView);

                Toast.makeText(getApplication(), "Show description page", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplication(), "OnFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAddCart(String email, String merchantId, String productId, Long stockQuantity) {
        IAccount iAccount = AppController.getInstance().getClientAddCart(addCartUrl).create(IAccount.class);
        Call<Boolean> call = iAccount.getCartProducts(email, merchantId, productId, String.valueOf(stockQuantity));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding to Cart");
        progressDialog.show();
        Log.d("AddCart", "getAddCart: " + call.request().url());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();
                Log.d("AddCart", "onResponse: " + response.code() + '\n' + response.body());
                if (response.code() >= 200 && response.code() < 300 && response.body()!=null) {
                    if (response.body()) {
                        Toast.makeText(getApplication(), "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Out of stock", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(ProductDetailsActivity.this, LoginActivity.class);
//                        startActivity(i);
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse: " + response.body());
                    Toast.makeText(getApplication(), "Bad Request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong in adding to cart", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void buyNow(String email, String merchantId, String productId, Long stockQuantity, String productName) {
        IAccount iAccount = AppController.getInstance().getClientOrder(orderUrl).create(IAccount.class);
        Call<Long> call = iAccount.getUserOrder(email, merchantId, productId, String.valueOf(stockQuantity), productName);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buying");
        progressDialog.show();
        Log.d("BuyNow", "buyNow: " + call.request().url());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                progressDialog.dismiss();
                Log.d("BuyNow", "onResponse: " + response.code() + '\n' + response.body());
                if (response.code() >= 200 && response.code() < 300 && response.body()!=null) {
                    if (response.body() == 1) {
                        Toast.makeText(getApplication(), "Your order placed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Bad Request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong in adding to cart", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
