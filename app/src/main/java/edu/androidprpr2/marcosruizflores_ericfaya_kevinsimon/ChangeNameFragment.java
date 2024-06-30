package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeNameFragment extends Fragment {
    private TextView tvNameEntrenador;
    private EditText etNameEntrenador;
    private Button btnChangeNameTrainer;
    private static final String TAG = "ChangeNameFragment";
    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_TRAINER_NAME = "trainer_name";

    public ChangeNameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_name, container, false);
        String trainerName = getTrainerName();

        tvNameEntrenador = view.findViewById(R.id.tvTrainerName);
        etNameEntrenador = view.findViewById(R.id.etNewName);
        btnChangeNameTrainer = view.findViewById(R.id.btnConfirm);
        tvNameEntrenador.setText(trainerName);

        btnChangeNameTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etNameEntrenador.getText().toString();
                if (!newName.isEmpty()) {
                    updateTrainerName(newName);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new EntrenadorFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Introduce un nuevo nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private String getTrainerName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TRAINER_NAME, "Default Name");
    }

    private void updateTrainerName(String newName) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TRAINER_NAME, newName);
        editor.apply();
        Log.d(TAG, "Trainer name updated to: " + newName);
    }
}
