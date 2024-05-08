package com.gzn1ev.aramlejelentes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.anastr.speedviewlib.TubeSpeedometer;

public class MainPageFragment extends Fragment {
    public MainPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        TubeSpeedometer gauge = view.findViewById(R.id.tubeSpeedometer);
        gauge.speedTo(150);
        return view;
    }


}