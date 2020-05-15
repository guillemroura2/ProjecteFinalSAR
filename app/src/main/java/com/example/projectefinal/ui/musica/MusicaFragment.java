package com.example.projectefinal.ui.musica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.projectefinal.R;

public class MusicaFragment extends Fragment {

    private MusicaViewModel musicaViewModel;
    private ConstraintLayout Layout;
    private WebView webView;
    private Spinner PaginaWeb;
    private Button AccedirPagina;
    private String URLPortal = "";
    private TextView NomPortal;

    //COMPORTAMENTS DEL FRAGMENT
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        musicaViewModel = ViewModelProviders.of(this).get(MusicaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_musica, container, false);

        //Referència als elements de la part gràfica
        webView = root.findViewById(R.id.wb_Youtube);
        PaginaWeb = root.findViewById(R.id.sp_paginesMusica);
        AccedirPagina = root.findViewById(R.id.b_AccedirPagina);
        NomPortal = root.findViewById(R.id.tv_NomPortal);
        Layout = root.findViewById(R.id.MusicaLayout);

        //Es crea una taula per referenciar els elements de la llista
        String [] Opcions = {"Esculli una opció","Youtube", "Spotify", "SoundCloud"};
        //Es crea un desplegable de la llista anterior
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),R.layout.support_simple_spinner_dropdown_item, Opcions);
        PaginaWeb.setAdapter(adapter);

        //Listeners
        PaginaWeb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Es determina l'element escollit, es mostra el seu nom i es canvia el color de fons
                switch (position){
                    case 0:
                        NomPortal.setText("ESCULLI UNA OPCIÓ");
                        Layout.setBackgroundColor(getResources().getColor(R.color.colorBeix));
                        break;
                    case 1:
                        NomPortal.setText("YOUTUBE");
                        Layout.setBackgroundColor(getResources().getColor(R.color.colorVermell));
                        break;
                    case 2:
                        NomPortal.setText("SPOTIFY");
                        Layout.setBackgroundColor(getResources().getColor(R.color.colorVerd));
                        break;
                    case 3:
                        NomPortal.setText("SOUNDCLOUD");
                        Layout.setBackgroundColor(getResources().getColor(R.color.colorTaronja));
                        break;
                }
                //S'accedeix a la pàgina depenent de l'element escollit
                URLPortal = getWebView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                NomPortal.setText("ESCULLI UNA OPCIÓ");
            }
        });

        AccedirPagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebView(webView, URLPortal);
            }
        });

        return root;
    }

    //MÈTODE PER DETERMINAR EL PORTAL QUE S'HA D'ACCEDIR
    public String getWebView(int PortalSeleccionat){
        String URL = "";
        switch(PortalSeleccionat){
            case 0:
                URL =  "";
                break;
            case 1:
                URL =  "http://m.youtube.com";
                break;
            case 2:
                URL = "http://m.spotify.com";
                break;
            case 3:
                URL = "http://m.soundcloud.com";
                break;
        }
        return URL;
    }

    //MÈTODE PER ACCEDIR A LA PÀGINA ESCOLLIDA
    public void setWebView(WebView webView, String URL) {
        this.webView = webView;
        if (!URL.isEmpty()){
            webView.loadUrl(URL);
        }
    }
}