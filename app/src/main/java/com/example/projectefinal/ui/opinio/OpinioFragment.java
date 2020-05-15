package com.example.projectefinal.ui.opinio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.projectefinal.R;

public class OpinioFragment extends Fragment {

    //Declaració de variables
    private OpinioViewModel opinioViewModel;
    private WebView webView;
    private Button NouFormulari;

    //COMPORTAMENTS DEL FRAGMENT
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        opinioViewModel = ViewModelProviders.of(this).get(OpinioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_opinio,container,false);

        //Referència als elements de la part gràfica
        webView = root.findViewById(R.id.wb_Formulari);
        NouFormulari = root.findViewById(R.id.b_NouFormulari);

        //S'accedeix automàticament a la pàgina del formulari d'opinió
        DeixaLaOpinio();

        //Listeners
        NouFormulari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeixaLaOpinio();
            }
        });

        return root;
    }

    //MÈTODE PER ACCEDIR A LA PÀGINA DEL FORMULARI
    private void DeixaLaOpinio(){
        webView.loadUrl(getString(R.string.UrlFormulariOpinio));
    }
}