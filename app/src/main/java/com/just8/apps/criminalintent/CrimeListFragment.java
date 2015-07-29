package com.just8.apps.criminalintent;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * BB
 * Created by kandinski on 2015-07-21.
 */
public class CrimeListFragment extends ListFragment {                                   // A LIST WITH ADAPTER
    private static final String TAG = "CrimeListFragment";

    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

/*        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(
                getActivity(),
                android.R.layout.simple_list_item_1,              // ONLY GOOD for SINGLE SIMPLE TEXTVIEW
                mCrimes);*/
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);                                                        // ListFragment METHOD
    }

private class CrimeAdapter extends ArrayAdapter<Crime> {                                //ADAPTER MEDIATES VIEW AND DATA
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){          // CrimeAdapter.getView INFLATES, POPULATES
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }
            Crime c = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){                             // ListFragment METHOD
        // Crime lCrime = (Crime) getListAdapter().getItem(position);
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position); // what guarantees Crime?
        Log.d(TAG, "CRIME SELECTED = " + c.getTitle());
        //Intent i = new Intent(getActivity(), CrimeActivity.class);  replaced with CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);                                 // CALL *PAGER* INTENT WITH ID
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

}
