package com.just8.apps.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {    //CrimeActivity NOT CURRENTLY USED IN FAVOR OF CRIME PAGER ACTIVITY

    @Override
    protected Fragment createFragment() {

        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
