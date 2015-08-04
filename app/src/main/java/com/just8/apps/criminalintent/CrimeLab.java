package com.just8.apps.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * BB
 * Created by kandinski on 2015-07-21.
 */
public class CrimeLab {       private static final String TAG = "CrimeLab";

    private static CrimeLab sCrimeLab;
    private CriminalIntentJSONSerializer mSerializer;

    private Context mAppContext;
    private static final String FILENAME = "crimes.json";
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
        //populate();
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

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    private void populate(){
        for (int i = 0; i < 2; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setDate(new Date(1000*86400*i));
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        }
    }
}
