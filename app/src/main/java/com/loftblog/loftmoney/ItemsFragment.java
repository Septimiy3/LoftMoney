package com.loftblog.loftmoney;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.GridLayout.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {


    public static ItemsFragment newInstance(int type){
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE,type);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static final int TYPE_UNNKNOWN = 0;
    public static final int TYPE_INCOMES = 1;
    public static final int TYPE_EXPENSES = 2;
    public static final int TYPE_BALANCE = 3;

    public static final String KEY_TYPE = "type";


    private ItemsAdapter adapter;
    private int type;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter(requireContext());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recycler = view.findViewById(R.id.recycler);


        DividerItemDecoration driver = new DividerItemDecoration(requireContext(), VERTICAL);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext() ));
        recycler.addItemDecoration(driver);

        adapter.setItems(createTempItems());

    }

    private List<Item> createTempItems(){
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


        return items;
    }
}
