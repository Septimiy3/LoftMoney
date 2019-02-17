package com.loftblog.loftmoney;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    public static final String KEY_TYPE = "type";

    private Api api;
    private EditText name;
    private EditText price;
    private Button addButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        api = ((App) getApplication()).getApi();

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addButtom = findViewById(R.id.add_button);

        addButtom.setOnClickListener(v -> {

            String nameText = name.getText().toString();
            String priceText = price.getText().toString();
            String type = getIntent().getStringExtra(KEY_TYPE);

            addItem(nameText, priceText, type);

        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name.length() != 0 && price.length() != 0) {
                    addButtom.setEnabled(!TextUtils.isEmpty(s));
                } else
                    addButtom.setEnabled(false);
            }
        };
        name.addTextChangedListener(watcher);
        price.addTextChangedListener(watcher);
    }

    private void addItem(String name, String price, String type) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("auth_token", null);

        AddItemRequest request = new AddItemRequest(Double.valueOf(price), name, type);

        Call<Object> call = api.addItem(request, token);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
