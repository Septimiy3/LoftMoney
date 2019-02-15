package com.loftblog.loftmoney;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private MaterialButton enterBtn ;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        enterBtn = findViewById(R.id.enter_btn);


        api = ((App)getApplication()).getApi();

        enterBtn.setOnClickListener(v -> {
            auth();
        });
    }

    private void auth(){

    }
}
