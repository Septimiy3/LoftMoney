package com.loftblog.loftmoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private static final String TAG = "MainActivity";


    private ViewPager pager;
    private TabLayout tabs;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate");

        MainPagersAdapter adapter = new MainPagersAdapter(getSupportFragmentManager(), this);

        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        tabs.setupWithViewPager(pager);

        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new PageChangeListener());


        fab.setOnClickListener(v -> {


            List<Fragment> fragments = getSupportFragmentManager().getFragments();

            for (Fragment fragment : fragments) {
                if (fragment instanceof ItemsFragment && fragment.getUserVisibleHint()) {
                    ((ItemsFragment) fragment).onFabClick();
                }
            }
        });
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        actionMode = mode;

        fab.hide();
        tabs.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_grey_blue));
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        actionMode = null;
        fab.show();
        tabs.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onCreate");
    }


    private class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if(actionMode!=null){
                actionMode.finish();
            }

            switch (position) {
                case MainPagersAdapter.PAGE_INCOMES:
                case MainPagersAdapter.PAGE_EXPENSES:
                    fab.show();
                    break;
                case MainPagersAdapter.PAGE_BALANCE:
                    fab.hide();
                    break;
            }
        }
    }
}
