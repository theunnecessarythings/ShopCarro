package com.example.meghamoondra.navigationbar.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IUser;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.authenticationUrl;

public class LoginActivity extends AppCompatActivity {
    final String TAG = "Login_page";
    private static final String ShopcarroPref = "ShopCarroPref";
    ProgressDialog progressDialog;
    Toolbar toolbar;
    private EditText etname;
    private EditText ptpassword;
    private Button btlogin;
    private Button buttonsignup;
    private IUser iUser;
    private String email;
    private String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;
    CheckBox savelogincheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //toolbar.setTitle("ShopCarro");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        iUser = AppController.getInstance().getClientAuthentication(authenticationUrl).create(IUser.class);

        etname = findViewById(R.id.etName);
        ptpassword = findViewById(R.id.etPassword);
        btlogin = findViewById(R.id.btnLogin);
        email = etname.getText().toString();
        sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        savelogincheckbox = findViewById(R.id.checkbox);
        editor = sharedPreferences.edit();

        buttonsignup = findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating");

        btlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isValidEmail(etname .getText()))
                        etname .setError("Enter valid email");
                    if(!isValidPassword(ptpassword.getText().toString()))
                        ptpassword.setError("Password length must be at least 8");

                    if (validate()) {

                        User user = new User(email, password);
                        progressDialog.show();
                        login(user);
                    }
                }
            });
            savelogin = sharedPreferences.getBoolean("savelogin", true);
            if (savelogin == true) {
                etname.setText(email);
                //ptpassword.setText(password);

            }
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Going to Signup", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        }

        private void login ( final User user){
            Call<Boolean> call = iUser.doLogin(user);
            Log.d("LOGlogin", user.toString());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    progressDialog.dismiss();
                    if (null != response && response.body()) {
                        Log.d("insidelogin","insideLogin");
                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

                        editor.putBoolean("savelogin", true);
                        editor.putString("username", email);
                      //  editor.putString("password", password);
                        editor.commit();

                        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();

                        //EditText
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //Don't come back here
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication Failure", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        }


        private boolean validate () {

            this.email = etname.getText().toString();
            password = this.ptpassword.getText().toString();
            if(email.isEmpty())
                etname.setError("Email cannot be empty");
            if(password.length() < 8)
                ptpassword.setError("Password must be atleast 8 characters");
            if (email.isEmpty() || password.isEmpty())
                return false;

            return isValidEmail(email) && isValidPassword(password);


        }

        public boolean isValidEmail (CharSequence email){
            if (email == null) {
                etname.setError("Email valid email id");
                return false;
            }
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        public boolean isValidPassword (String password){
            if (password.length() < 8 || password == null) {
                ptpassword.setError("Password must be atleast 8 characters");
                return false;
            }
            return true;
        }
    }

