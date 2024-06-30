package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TendaFragment extends Fragment {

    private TextView tvMoney;
    private Button bPokeball;
    private Button bSuperball;
    private Button bUltraball;
    private Button bMasterball;
    private TextView tvMoneySpent;
    private Button bShop;
    private int countPokeball;
    private int countSuperball;
    private int countUltraball;
    private int countMasterball;
    private int moneySpent;

    private static final String TAG = "TendaFragment";
    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_MONEY = "money";
    private static final String KEY_POKEBALLS = "pokeballs";
    private static final String KEY_SUPERBALLS = "superballs";
    private static final String KEY_ULTRABALLS = "ultraballs";
    private static final String KEY_MASTERBALLS = "masterballs";

    public TendaFragment() {
        // Required empty public constructor
    }

    public static TendaFragment newInstance(String param1, String param2) {
        TendaFragment fragment = new TendaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tenda, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMoney = view.findViewById(R.id.tvMoney);
        bPokeball = view.findViewById(R.id.Button1_pokeball);
        tvMoneySpent = view.findViewById(R.id.tvMoneySpent);
        bSuperball = view.findViewById(R.id.Button1_superball);
        bUltraball = view.findViewById(R.id.Button1_ultraball);
        bMasterball = view.findViewById(R.id.Button1_masterball);
        bShop = view.findViewById(R.id.buyGeneralShop);

        updateMoneyDisplay();

        bPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countPokeball++;
                moneySpent += 200;
                tvMoneySpent.setText(String.valueOf(moneySpent));
            }
        });

        bSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countSuperball++;
                moneySpent += 500;
                tvMoneySpent.setText(String.valueOf(moneySpent));
            }
        });

        bUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countUltraball++;
                moneySpent += 1500;
                tvMoneySpent.setText(String.valueOf(moneySpent));
            }
        });

        bMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countMasterball++;
                moneySpent += 100000;
                tvMoneySpent.setText(String.valueOf(moneySpent));
            }
        });

        bShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shopValue = countPokeball * 200 + countSuperball * 500 + countUltraball * 1500 + countMasterball * 100000;
                if (getCurrentValue() - shopValue < 0) {
                    Toast.makeText(getContext(), "No tienes suficiente dinero", Toast.LENGTH_SHORT).show();
                } else {
                    if (shopValue == 0) {
                        Toast.makeText(getContext(), "No has comprado nada", Toast.LENGTH_SHORT).show();
                    } else {
                        modifyFieldValue(KEY_MONEY, -shopValue);
                        modifyFieldValue(KEY_MASTERBALLS, countMasterball);
                        modifyFieldValue(KEY_ULTRABALLS, countUltraball);
                        modifyFieldValue(KEY_SUPERBALLS, countSuperball);
                        modifyFieldValue(KEY_POKEBALLS, countPokeball);
                        countSuperball = 0;
                        countUltraball = 0;
                        countMasterball = 0;
                        countPokeball = 0;
                        moneySpent = 0;
                        tvMoneySpent.setText(String.valueOf(moneySpent));
                        updateMoneyDisplay();
                    }
                }
            }
        });
    }

    private void modifyFieldValue(String fieldName, int incrementValue) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        int currentValue = sharedPreferences.getInt(fieldName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(fieldName, currentValue + incrementValue);
        editor.apply();
        Log.d(TAG, "Valor de " + fieldName + " incrementado.");
    }

    private int getCurrentValue() {
        return getFieldValue(KEY_MONEY);
    }

    private int getFieldValue(String fieldName) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(fieldName, 0);
    }

    private void updateMoneyDisplay() {
        tvMoney.setText(String.valueOf(getFieldValue(KEY_MONEY)));
    }
}
