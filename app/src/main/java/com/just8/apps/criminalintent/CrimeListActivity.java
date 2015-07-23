package com.just8.apps.criminalintent;

import android.support.v4.app.Fragment;

/**
 * BB
 * Created by kandinski on 2015-07-21.
 */
public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }

}
