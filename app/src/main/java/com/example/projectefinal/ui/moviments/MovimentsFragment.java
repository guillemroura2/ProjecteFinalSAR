package com.example.projectefinal.ui.moviments;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.projectefinal.R;

public class MovimentsFragment extends Fragment implements View.OnTouchListener, CompoundButton.OnCheckedChangeListener {

    //Declaració de variables
    private MovimentsViewModel movimentsViewModel;
    private Button HeliceButton, AtacButton;
    private ToggleButton VincularButton;
    private CheckBox ConnexioBluetooth_checkBox;
    private TextView NomTextView, MacTextView;


    //COMPORTAMENTS DEL FRAGMENT
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        movimentsViewModel =  ViewModelProviders.of(this).get(MovimentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moviments, container, false);

        //Referència als elements de la part gràfica
        HeliceButton = root.findViewById(R.id.b_Helice);
        AtacButton = root.findViewById(R.id.b_Atac);
        VincularButton = root.findViewById(R.id.t_Vincular);
        ConnexioBluetooth_checkBox = root.findViewById(R.id.cb_EstatBluetooth);
        NomTextView = root.findViewById(R.id.tv_Nom);
        MacTextView = root.findViewById(R.id.tv_MAC);

        //Es defineix la pantalla com a listener
        HeliceButton.setOnTouchListener(this);
        AtacButton.setOnTouchListener(this);
        ConnexioBluetooth_checkBox.setOnCheckedChangeListener(this);
        VincularButton.setOnCheckedChangeListener(this);

        //Desactivació dels botons
        HeliceButton.setEnabled(false);
        AtacButton.setEnabled(false);
        VincularButton.setEnabled(false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.disable();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.b_Helice:
                switch (event.getActionMasked()){
                    case 0:
                        AtacButton.setEnabled(false);
                        break;
                    case 1:
                        AtacButton.setEnabled(true);
                        break;
                }
                break;
            case R.id.b_Atac:
                switch (event.getActionMasked()){
                    case 0:
                        HeliceButton.setEnabled(false);
                        break;
                    case 1:
                        HeliceButton.setEnabled(true);
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.cb_EstatBluetooth) {
            if (isChecked){
                VincularButton.setEnabled(true);
                VincularButton.setChecked(false);

            }else{
                VincularButton.setEnabled(false);
                VincularButton.setChecked(true);
            }
            HeliceButton.setEnabled(false);
            AtacButton.setEnabled(false);
            NomTextView.setText("");
            MacTextView.setText("");
        }
    }

}