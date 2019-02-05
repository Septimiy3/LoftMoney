package com.loftblog.loftmoney;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);



        adapter = new ItemsAdapter(this);

        recycler = findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Item> items = new ArrayList<>();
        items.add(new Item("Молоко","70р"));
        items.add(new Item("Масло","70р"));
        items.add(new Item("Рис","70р"));
        items.add(new Item("Хдуб","70р"));
        items.add(new Item("фывфыв","70р"));
        items.add(new Item("ыфвф","70р"));
        items.add(new Item("123","70р"));
        items.add(new Item("выфв","70р"));
        items.add(new Item("ывф","70р"));
        items.add(new Item("123аыфв","70р"));
        items.add(new Item("рпара","70р"));
        items.add(new Item("41кмафы","70р"));

        adapter.setItems(items);
    }


}
