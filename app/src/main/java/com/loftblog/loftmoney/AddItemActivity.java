package com.loftblog.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";

    private EditText name;
    private EditText price;
    private Button addButtom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addButtom = findViewById(R.id.add_button);

        addButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String nameText = name.getText().toString();
                String priceText = price.getText().toString();

                intent.putExtra(KEY_NAME,nameText);
                intent.putExtra(KEY_PRICE,priceText);

                setResult(Activity.RESULT_OK, intent);

                finish();
            }
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
                if (name.length() != 0 && price.length() !=0){
                    addButtom.setEnabled(!TextUtils.isEmpty(s));
                }else
                    addButtom.setEnabled(false);
            }
        };
        name.addTextChangedListener(watcher);
        price.addTextChangedListener(watcher);
    }
}
