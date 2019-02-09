package com.loftblog.loftmoney;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.loftblog.loftmoney.ItemsFragment.TYPE_EXPENSES;
import static com.loftblog.loftmoney.ItemsFragment.TYPE_INCOMES;

public class MainPagersAdapter extends FragmentPagerAdapter {

    private static final int PAGE_EXPENSES = 0;
    private static final int PAGE_INCOMES = 1;
    private static final int PAGE_BALANCE = 2;

    private static int PAGE_COUNT = 3;

    private Context context;


    public MainPagersAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_INCOMES:
                return ItemsFragment.newInstance(TYPE_INCOMES);
            case PAGE_EXPENSES:
                return ItemsFragment.newInstance(TYPE_EXPENSES);
            case PAGE_BALANCE:
                BalanceFragment balanceFragment = new BalanceFragment();
                return balanceFragment;
            default:
                return new ItemsFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_EXPENSES:
                return context.getString(R.string.main_tab_expenses);
            case PAGE_INCOMES:
                return  context.getString(R.string.main_tab_incomes);
            case PAGE_BALANCE:
                return  context.getString(R.string.main_tab_balance);
            default:
                return "";

        }
    }
}
