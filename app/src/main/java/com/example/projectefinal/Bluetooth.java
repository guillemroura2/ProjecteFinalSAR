package com.example.projectefinal;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth{
    private final String MAC = "94:49:18:04:04:8F";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter bluetoothAdapter;
    private static BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;

    //Definir adaptador
    public void DefinirAdaptador(){
        if (bluetoothAdapter == null){
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
    }

    //Mira estat Bluetooth
    public boolean getEstatBluetooth(){
        int State;
        boolean EstatBluetooth = false;
        //Es determina l'estat del bluetooth del dispositiu
        State = bluetoothAdapter.getState();
        if(State == BluetoothAdapter.STATE_ON || State == BluetoothAdapter.STATE_TURNING_ON){
            EstatBluetooth=true;
        }
        return EstatBluetooth;
    }

    //Activar Bluetooth
    public void enableBluetooth(){
        bluetoothAdapter.enable();
    }

    //Desactivar Bluetooth
    public void disableBluetooth(){
        bluetoothAdapter.disable();
    }

    //Vincular amb el mòdul Bluetooth
    public boolean getDispositiuVinculat(){

        boolean Trobat = false;

        Set<BluetoothDevice> DispositiuVinculat = bluetoothAdapter.getBondedDevices();

        if (DispositiuVinculat.isEmpty()){
            Trobat = false;
            //Toast.makeText(getContext(),"Enllaci un dispositiu per continuar", Toast.LENGTH_SHORT).show();
        }else{
            for (BluetoothDevice i : DispositiuVinculat){
                if (i.getAddress().equals(MAC)){
                    bluetoothDevice = i;
                    Trobat = true;
                    break;
                }
            }
        }
        return Trobat;
    }

    //Vincular dispositiu
    public void VincularDispositiu(){
        boolean Connectat=false;

        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(PORT_UUID);
            bluetoothSocket.connect();
            Connectat = true;
        }catch (IOException e){
            e.printStackTrace();
        }

        if (Connectat){
            try{
                outputStream = bluetoothSocket.getOutputStream();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Desvincular dispositiu
    public void DesvincularDispositiu(){
        if (bluetoothSocket.isConnected()){
            try{
                bluetoothSocket.close();
            }catch (IOException e){

            }
        }
    }

    //Torna l'estat del dispositiu
    public boolean getEstatDispositiu(){
        if (bluetoothSocket != null){
            return true;
        }else{
            return  false;
        }
    }

    //Comprova si el socket està creat
    public boolean isConnected() {
        return bluetoothSocket.isConnected();
    }

    //Retorna l'adreça del dispositiu
    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    //Retorna el nom del dispositiu
    public String getName() {
        return bluetoothDevice.getName();
    }

    //Mètode per escriure una comanda
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }



}
