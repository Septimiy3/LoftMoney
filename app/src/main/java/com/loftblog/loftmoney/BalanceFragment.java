package com.loftblog.loftmoney;


import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {


    public BalanceFragment() {
        // Required empty public constructor
    }

    public static BalanceFragment newInstance() {

        Bundle args = new Bundle();

        BalanceFragment fragment = new BalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Api api;

    private TextView balanceView;
    private TextView expenseView;
    private TextView incomeView;
    private DiagramView diagramView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Application application = Objects.requireNonNull(getActivity()).getApplication();
        App app = (App) application;
        api = app.getApi();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balanceView = view.findViewById(R.id.balance_value);
        expenseView = view.findViewById(R.id.expense_value);
        incomeView = view.findViewById(R.id.income_value);
        diagramView = view.findViewById(R.id.diagram_view);



    }

    private void loadBalance(){

/*        balanceView.setText("64000");
        expenseView.setText("5400");
        incomeView.setText("74000");

        diagramView.update(74000,5400);*/

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String token = preferences.getString("auth_token", null);

        Call<BalanceResponse> call = api.balance(token);
        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(@NonNull Call<BalanceResponse> call, @NonNull Response<BalanceResponse> response) {
                BalanceResponse balanceResponse = response.body();

                int balance = 0;
                if (balanceResponse != null) {
                    balance = balanceResponse.getTotalIncome() - balanceResponse.getTotalExpense();
                }

                balanceView.setText(getString(R.string.balance_fragment_count, balance));
                assert balanceResponse != null;
                expenseView.setText(getString(R.string.balance_fragment_count, balanceResponse.getTotalExpense()));
                incomeView.setText(getString(R.string.balance_fragment_count, balanceResponse.getTotalIncome()));

                diagramView.update(balanceResponse.getTotalIncome(),balanceResponse.getTotalExpense());
            }

            @Override
            public void onFailure(@NonNull Call<BalanceResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            loadBalance();
        }
    }
}
