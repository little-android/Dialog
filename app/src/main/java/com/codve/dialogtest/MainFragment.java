package com.codve.dialogtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {
    private TextView mTextView;
    private Button mButton;
    private Date mDate;
    private SimpleDateFormat mDateFormat;
    private static final String TIME_KEY = "date";
    private static final String TIME_PICKER = "time_picker";
    private static final int TIME_REQUEST_CODE = 0;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TIME_KEY, mDate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mDate = (Date) savedInstanceState.getSerializable(TIME_KEY);
        } else {
            mDate = new Date();
        }
        mDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) view.findViewById(R.id.time_text);
        mTextView.setText(mDateFormat.format(mDate));

        mButton = (Button) view.findViewById(R.id.time_button);
        mButton.setText(R.string.change);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment pickerFragment = DatePickerFragment.newInstance(mDate);
                pickerFragment.setTargetFragment(MainFragment.this, TIME_REQUEST_CODE);
                pickerFragment.show(fm, TIME_PICKER);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == TIME_REQUEST_CODE) {
            mDate = (Date) data.getSerializableExtra(DatePickerFragment.DATE_RESULT);
            mTextView.setText(mDateFormat.format(mDate));
        }
    }
}
