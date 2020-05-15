package com.example.projectefinal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.projectefinal.R;

public class HomeFragment extends Fragment {

    //Declaració de variables
    private HomeViewModel homeViewModel;
    private EditText PW;
    private Button CheckButton;

    //COMPORTAMENTS DEL FRAGMENT
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Referència als elements de la part gràfica
        PW = root.findViewById(R.id.et_IntroducedPassword);
        CheckButton = root.findViewById(R.id.b_Check);

        /*Com a estat inicial es deshabilita el "Navigation Drawer per obligar a l'usuari
            a introduir una contrasenya per tal de poder utilitzar l'aplicació*/
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        //Missatge de benvinguda
        Toast.makeText(getContext(), "Benvingut/da", Toast.LENGTH_SHORT).show();

        //Listeners
        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPassword();
            }
        });

        return root;
    }

    //MÈTODE PER COMPROVAR LA CONTRASENYA
    private void CheckPassword() {
        //Es passa l'EditText (Contrasenya Introduida) a String
        String IntroducedPW = PW.getText().toString();
        //Es comprova que hi hagi algun valor al quadre de text
        if(IntroducedPW.isEmpty()){
            Toast.makeText(getContext(), "Introdueixi una contrasenya", Toast.LENGTH_SHORT).show();
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }else{
            //Es comprova que la contrasenya sigui correcta
            if (IntroducedPW.equals(getString(R.string.CorrectPassword))){
                Toast.makeText(getContext(), "Contrasenya Correcte", Toast.LENGTH_SHORT).show();
                //La barra de navegació es farà visible per poder navegar per l'aplicació
                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            }else {
                Toast.makeText(getContext(), "Contrasenya Incorrecte", Toast.LENGTH_SHORT).show();
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            }
        }
    }
}