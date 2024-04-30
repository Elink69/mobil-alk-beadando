package com.gzn1ev.aramlejelentes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BillsPageFragment extends Fragment {

    public BillsPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills_page, container, false);
    }

}