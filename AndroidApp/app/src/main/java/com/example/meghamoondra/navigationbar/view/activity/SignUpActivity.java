package com.example.meghamoondra.navigationbar.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.NewAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.meghamoondra.navigationbar.controller.Urls.authenticationUrl;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText email, firstName, lastName, phoneNumber, address, password, confirmPassword;
    private IAccount iAccount;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.et_email);
        firstName = findViewById(R.id.et_firstname);
        lastName = findViewById(R.id.et_lastname);
        phoneNumber = findViewById(R.id.et_number);
        address = findViewById(R.id.et_address);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);



        AppController appController = AppController.getInstance();
        Retrofit retrofit = appController.getClientAuthentication(authenticationUrl);
        iAccount = retrofit.create(IAccount.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up");

        findViewById(R.id.bt_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValidEmail(email.getText()))
                    email.setError("Enter valid email");
                if(!isValidPassword(password.getText().toString()))
                    password.setError("Password length must be at least 8");
                if(phoneNumber.length() != 10)
                    phoneNumber.setError("Phone number must have 10 digits");
                if(!confirmPassword.getText().toString().equals(password.getText().toString()))
                    confirmPassword.setError("Passwords not matching");
                if(address.getText().toString().length() == 0)
                    address.setError("Address cannot be empty");
                if(firstName.getText().toString().length() == 0)
                    firstName.setError("First Name cannot be empty");

                if (isValidEmail(email.getText()) && isValidPassword(password.getText().toString()) && phoneNumber.length() == 10 && confirmPassword.getText().toString().equals(password.getText().toString())) {

                    NewAccount newaccount = new NewAccount();

                    newaccount.setEmail(email.getText().toString());
                    newaccount.setFirstName(firstName.getText().toString());
                    newaccount.setLastName(lastName.getText().toString());
                    newaccount.setPassword(password.getText().toString());
                    newaccount.setPhoneNumber(phoneNumber.getText().toString());
                    newaccount.setAddress(address.getText().toString());

                    progressDialog.show();

                    addAccount(newaccount);
                }
            }
        });
    }

    private void addAccount(NewAccount newAccount) {
        Call<Boolean> call = iAccount.add(newAccount);
        Log.d("anything", newAccount.toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    if (response.body()) {
                        Toast.makeText(getApplicationContext(), "Please Login Now", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //Finish the activity do not come back
                        finish();
                    }
                }else {
                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse: " + response.body() + " " + response.message());
                    Toast.makeText(getApplicationContext(), "Id already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sign up server went down", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {

        return password.length() >= 8 && password != null;
    }

}
