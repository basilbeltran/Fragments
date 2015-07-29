package com.just8.apps.criminalintent;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.just8.apps.criminalintent.date.id";
    private Date mDate;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {                   // create DatePicker from lauyout

        DatePicker dpView = (DatePicker)getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);

        dpView.init(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                //TODO how often does this get called?
                new DatePicker.OnDateChangedListener() {                        // in case of config change
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        mDate = new GregorianCalendar(year, month, day).getTime();
                        getArguments().putSerializable(EXTRA_DATE, mDate);
                    }
                }
        ); //dpView.init

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(dpView)                                                //PUT dpView into AlertDialog
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {    //SEND NEW DATA BACK
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);       //USE TARGET
    }

}
