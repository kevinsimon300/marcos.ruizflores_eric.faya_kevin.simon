package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrenadorFragment extends Fragment {


    public EntrenadorFragment() {

    }

    public static EntrenadorFragment newInstance() {
        EntrenadorFragment fragment = new EntrenadorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_entrenador, container, false);
    }

}