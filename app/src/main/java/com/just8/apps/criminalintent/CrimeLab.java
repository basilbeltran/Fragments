package com.just8.apps.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * BB
 * Created by kandinski on 2015-07-21.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList();
        populate();
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }


    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    private void populate(){
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setDate(new Date(1000*86400*i));
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        }
    }
}
