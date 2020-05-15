package com.example.projectefinal;

import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener, View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;

    //Variables Joystick
    public TextView textView_PosX, textView_PosY;

    //Variables Bluetooth
    Bluetooth bluetoothClass = new Bluetooth();

    //Variables de la pantalla moviments
    Button button_Atac;
    ToggleButton button_Helice;
    ToggleButton toggle_Vincular;
    TextView textView_Nom, textView_MAC;
    CheckBox checkBox_ConnexioBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Construcció de la navigation drawer i referència als respectius fragments
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_moviments
                , R.id.nav_opinio, R.id.nav_musica)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Referencia als diferents botons del fragment de moviments
        button_Helice = findViewById(R.id.b_Helice);
        button_Atac = findViewById(R.id.b_Atac);
        toggle_Vincular = findViewById(R.id.t_Vincular);
        textView_Nom = findViewById(R.id.tv_Nom);
        textView_MAC = findViewById(R.id.tv_MAC);
        textView_PosX = findViewById(R.id.tv_PosX);
        textView_PosY = findViewById(R.id.tv_PosY);
        checkBox_ConnexioBluetooth = findViewById(R.id.cb_EstatBluetooth);

        //Es defineix l'adaptador del dispositiu
        bluetoothClass.DefinirAdaptador();
        //En cas d'estar connectat, es desconnecta el Bluetooth del dispositiu
        if (bluetoothClass.getDispositiuVinculat()){
            bluetoothClass.disableBluetooth();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //INTERFICIE DEL JOYSTICK
    @Override
    public void onJoystickMoved(float xPercent, float YPercent, int id,float X, float Y) {

        String ComandaX, ComandaY;
        int ValorX, ValorY;
        textView_PosX = findViewById(R.id.tv_PosX);
        textView_PosY = findViewById(R.id.tv_PosY);

        ValorX = ((int) X);
        ValorY = ((int) Y);

        //Limitem el valor de la posició per transferir-lo a l'arduino
        if(ValorX >= 0 && ValorX <= 450){
            //Es mostra el text
            textView_PosX.setText("X= " + ValorX);
            //Es prepara per traspassar la comanda a l'arduino
            ComandaX = "X" + ValorX + ":";
            //Es passa els valors X a l'arduino si es té connexió bluetooth
            if (bluetoothClass.getEstatDispositiu()) {
                try {
                    bluetoothClass.write(ComandaX.getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this, "SENSE CONNEXIÓ BLUETOOTH", Toast.LENGTH_SHORT).show();
            }
        }

        if(ValorY >= 0 && ValorY <= 600){
            //Es mostra el text
            textView_PosY.setText("Y= " + ValorY);
            //Es prepara per traspassar la comanda a l'arduino
            ComandaY = "Y" + ValorY + ":";
            //Es passa els valors X a l'arduino si es té connexió bluetooth
            if (bluetoothClass.getEstatDispositiu()) {
                try {
                    bluetoothClass.write(ComandaY.getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        //Petit delay per no enviar moltes dades a l'arduino
        try{
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //ALTERS COMANDES DE MOVIMENT
    @Override
    public void onClick(View v) {

        String comanda = "";

        switch (v.getId()){
            case R.id.b_Helice:
                if (!bluetoothClass.getEstatDispositiu()){
                    Toast.makeText(this, "SENSE CONNEXIÓ BLUETOOTH", Toast.LENGTH_LONG).show();
                }else{
                    if (!button_Helice.isChecked()){
                        comanda = "H1:";
                        Toast.makeText(this, "Helice Desactivada", Toast.LENGTH_LONG).show();
                    }else{
                        comanda = "H0:";
                        Toast.makeText(this, "Helice Activada", Toast.LENGTH_LONG).show();
                    }
                    try{
                        bluetoothClass.write(comanda.getBytes());

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.b_Atac:
                comanda = "A:";
                if (!bluetoothClass.getEstatDispositiu()){
                    Toast.makeText(this, "SENSE CONNEXIÓ BLUETOOTH", Toast.LENGTH_LONG).show();
                }else{
                    try{
                        bluetoothClass.write(comanda.getBytes());
                        Toast.makeText(this, "Atac Activat", Toast.LENGTH_LONG).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.t_Vincular:
                textView_Nom = findViewById(R.id.tv_Nom);
                textView_MAC = findViewById(R.id.tv_MAC);
                button_Helice = findViewById(R.id.b_Helice);
                button_Atac = findViewById(R.id.b_Atac);
                toggle_Vincular = findViewById(R.id.t_Vincular);
                if (bluetoothClass.getEstatDispositiu()){
                    if (bluetoothClass.isConnected()){
                        bluetoothClass.DesvincularDispositiu();
                    }else{
                        bluetoothClass.VincularDispositiu();
                    }
                }else{
                    connectarBluetooth();
                }
                //Es torna a comprobar l'estat del dispositiu per llençar un missatge
                if (bluetoothClass.isConnected()){
                    Toast.makeText(this,"Dispositiu vinculat",Toast.LENGTH_SHORT).show();
                    textView_Nom.setText(bluetoothClass.getName());
                    textView_MAC.setText(bluetoothClass.getAddress());
                    button_Atac.setEnabled(true);
                    button_Helice.setEnabled(true);
                    toggle_Vincular.setChecked(true);
                }else{
                    Toast.makeText(this,"Dispositiu desvinculat",Toast.LENGTH_SHORT).show();
                    textView_Nom.setText("");
                    textView_MAC.setText("");
                    button_Atac.setEnabled(false);
                    button_Helice.setEnabled(false);
                    toggle_Vincular.setChecked(false);
                }
                break;
            case R.id.cb_EstatBluetooth:
                if(bluetoothClass.getEstatBluetooth()){
                    if (bluetoothClass.getEstatDispositiu()) {
                        bluetoothClass.DesvincularDispositiu();
                    }
                    bluetoothClass.disableBluetooth();
                    Toast.makeText(this,"Bluetooth desactivat",Toast.LENGTH_SHORT).show();
                }else{
                    bluetoothClass.enableBluetooth();
                    Toast.makeText(this,"Bluetooth activat",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //METODES BLUETOOTH
    public void connectarBluetooth(){
        bluetoothClass.DefinirAdaptador();
        bluetoothClass.getDispositiuVinculat();
        bluetoothClass.VincularDispositiu();
    }

}
