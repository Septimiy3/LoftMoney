package com.loftblog.loftmoney;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.GridLayout.VERTICAL;

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
        recycler.setLayoutManager(new LinearLayoutManager(this ));
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recycler.addItemDecoration(decoration);

        List<Item> items = new ArrayList<>();
        items.add(new Item("Молоко","50p"));
        items.add(new Item("Жираф","5000000p"));
        items.add(new Item("Кукла","31"));
        items.add(new Item("Вино","200"));
        items.add(new Item("Сосиска","5p"));
        items.add(new Item("Сосиска","5p"));
        items.add(new Item("Йогурт","5p"));
        items.add(new Item("Сосиска","5p"));
        items.add(new Item("Сосиска","5p"));
        items.add(new Item("Табак","5p"));
        items.add(new Item("Сосиска","5p"));

        adapter.setItems(items);

    }
}