package com.loftblog.loftmoney;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.GridLayout.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    private static final int ADD_ITEM_REQUEST_CODE = 111;

    private static final String TAG = "ItemsFragmetn";


    public static ItemsFragment newInstance(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }


    public static final String KEY_TYPE = "type";


    private SwipeRefreshLayout refresh;


    private ItemsAdapter adapter;
    private String type;

    private Api api;
    private ActionMode actionMode;


    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter(requireContext());
        adapter.setListener(new AdapterListener());

        type = getArguments().getString(KEY_TYPE);


        Application application = getActivity().getApplication();
        App app = (App) application;
        api = app.getApi();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        int color1 = requireContext().getResources().getColor(R.color.purple_color);
        int color2 = requireContext().getResources().getColor(R.color.yellow_color);
        int color3 = requireContext().getResources().getColor(R.color.red_color);

        refresh = view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(color1, color2, color3);
        refresh.setOnRefreshListener(() -> loadItems());

        RecyclerView recycler = view.findViewById(R.id.recycler);


        DividerItemDecoration driver = new DividerItemDecoration(requireContext(), VERTICAL);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.addItemDecoration(driver);

        loadItems();
    }

    private void loadItems() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);

        Call<List<Item>> call = api.getItems(type, token);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                refresh.setRefreshing(false);
                List<Item> items = response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                refresh.setRefreshing(false);
                Log.e(TAG, "LoadItems: ", t);
            }
        });
    }

    private void removeItem(long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);

        Call call = api.removeItem(id, token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    void onFabClick() {
        Intent intent = new Intent(requireContext(), AddItemActivity.class);
        intent.putExtra(AddItemActivity.KEY_TYPE, type);
        startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            loadItems();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class AdapterListener implements ItemsAdapterListener {

        @Override
        public void onItemClick(Item item, int position) {
            Log.i(TAG, "onItemClick: " + item.getName());

            if (actionMode == null) {
                return;
            }
            toggleItem(position);
            actionModeItemSelect();
        }

        @Override
        public void onItemLongClick(Item item, int position) {
            Log.i(TAG, "onItemLongClick: " + item.getName());

            if (actionMode != null) {
                return;
            }
            getActivity().startActionMode(new ActionModeCallback());
            toggleItem(position);
            actionModeItemSelect();

        }


        private void toggleItem(int position) {
            adapter.toggleItem(position);
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            actionMode = mode;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(requireContext());
            inflater.inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_item) {
                showDialog();

                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSelections();
        }

        void removeSelectedItems() {
            List<Integer> selectedPositions = adapter.getSelectedPositions();

            for (int i = selectedPositions.size() - 1; i >= 0; i--) {
                Item item = adapter.removeItem(selectedPositions.get(i));
                removeItem(item.getId());
            }
            actionMode.finish();
        }

        void showDialog() {
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setTitle(Html.fromHtml("<font color='#000000'>Вы действительно хотите удалить выбранный элемент?"))
                    .setPositiveButton("Да", (dialog1, which) -> removeSelectedItems())
                    .setNegativeButton("нет", (dialog12, which) -> {

                    })
                    .create();
            dialog.show();
        }
    }

    void actionModeItemSelect() {
        int number = 0;
        List<Integer> selectedActionPositions = adapter.getSelectedPositions();
        while (number<=selectedActionPositions.size()){
            actionMode.setTitle("Выделено: " + number);
            number++;
        }
    }




/*        for (int i = 0; i < selectedItems.size(); i++) {
            int key = selectedItems.keyAt(i);
            if (selectedItems.get(key)) {
                selectedPositions.add(key);

               actionMode.setTitle("Выделено: " + number);
            }*/

}


