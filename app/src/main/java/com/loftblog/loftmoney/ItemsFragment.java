package com.loftblog.loftmoney;


import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.GridLayout.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemsFragmetn";


    public static ItemsFragment newInstance(String type){
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE,type);
        fragment.setArguments(bundle);

        return fragment;
    }



    public static final String KEY_TYPE = "type";

    private String token = "$2y$10$MI9aJHOPZNR1WLHMPoRkx.6geJcwuzU/JxArRxeOoK9KXyPs3DzfG";


    private ItemsAdapter adapter;
    private String type;

    private Api api;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter(requireContext());


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

        RecyclerView recycler = view.findViewById(R.id.recycler);


        DividerItemDecoration driver = new DividerItemDecoration(requireContext(), VERTICAL);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext() ));
        recycler.addItemDecoration(driver);

        loadItems();
    }
    private void loadItems(){
        Call call =  api.getItems(type,token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                List<Item> items =(List<Item>) response.body();
                adapter.setItems(items);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG,"LoadItems: ",t);

            }
        });
    }

   /* private void loadItems(){
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,List<Item>> task = new AsyncTask<Void,Void,List<Item>>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Item> doInBackground(Void... voids) {
                Call call =  api.getItems(type,token);

                try {
                    Response<List<Item>> response =  call.execute();
                    List<Item> items = response.body();
                    return items;
                } catch (IOException e){
                    Log.e(TAG,"LoadItems: ",e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                if(items != null){
                    adapter.setItems(items);
                }
            }
        };
        task.execute();
    }*/



 /*   private class LoadItemsTask implements Runnable,Handler.Callback{
        private Thread thread;
        private Handler handler;

        public LoadItemsTask() {
            thread = new Thread(this);
            handler = new Handler(this);
        }

        public void start(){
            thread.start();
        }

        @Override
        public void run() {
            Call call =  api.getItems(type,token);

            try {
                Response<List<Item>> response =  call.execute();
                List<Item> items = response.body();

                Message message = handler.obtainMessage(111,items);
                message.sendToTarget();
            } catch (IOException e){
                Log.e(TAG,"LoadItems: ",e);
            }

        }
        @Override
        public boolean handleMessage(Message msg){
                if (msg.what ==111){

                    List<Item> items = (List<Item>) msg.obj;
                    adapter.setItems(items);
                    return true;
                }
                return false;
        }
    }*/
}
