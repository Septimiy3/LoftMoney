package com.loftblog.loftmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

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
