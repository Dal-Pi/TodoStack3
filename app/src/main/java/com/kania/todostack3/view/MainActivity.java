package com.kania.todostack3.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kania.todostack3.R;
import com.kania.todostack3.view.checklist.CheckListFragment;

public class MainActivity extends AppCompatActivity
        implements CheckListFragment.OnFragmentInteractionListener {

    private FragmentManager mFragmentManager;

    private CheckListFragment mCheckListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.act_main_toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            setInitialFragment();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof CheckListFragment) {
            mCheckListFragment = (CheckListFragment)fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setInitialFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.act_main_container, CheckListFragment.newInstance(),
                CheckListFragment.class.getCanonicalName());
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
