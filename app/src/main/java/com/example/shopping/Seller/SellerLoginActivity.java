package com.example.shopping.Seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.Buyers.LoginActivity;
import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {

    private EditText emailInput , passwordInput  ;
    private FirebaseAuth mAuth ;
    ProgressDialog loadingBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText) findViewById(R.id.seller_email_login) ;
        passwordInput = (EditText) findViewById(R.id.seller_password_login) ;
        Button sellerloginBtn = (Button) findViewById(R.id.seller_Login);

        sellerloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });

    }

    private void loginSeller() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!email.equals("") && !password.equals("")) {

            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email , password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(SellerLoginActivity.this, "you are Login Successfully .", Toast.LENGTH_SHORT).show();

                                Intent intent =new Intent(SellerLoginActivity.this , SellerHomeActivity.class) ;
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(SellerLoginActivity.this, "Error signing in with email link" + task.getException(), Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();

                            }
                        }
                    });


        }
        else {
            Toast.makeText(this, "Please complete the Login form .", Toast.LENGTH_SHORT).show();

        }

    }
}