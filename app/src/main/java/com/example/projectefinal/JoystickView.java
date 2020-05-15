package com.example.projectefinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    //VARIABLES
    private float CentreX, CentreY, RadiBase, RadiJoystick;
    private JoystickListener joystickCallback;

    //CONSTRUCTORS
    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this); //Es crea un Listener del Joystick
        if (context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }else{
            Toast.makeText(getContext(),"NO LISTENER",Toast.LENGTH_SHORT).show();
        }
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this); //Es crea un Listener del Joystick
        if (context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }else{
            Toast.makeText(getContext(),"NO LISTENER",Toast.LENGTH_SHORT).show();
        }
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this); //Es crea un Listener del Joystick
        if (context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }else{
            Toast.makeText(getContext(),"NO LISTENER",Toast.LENGTH_SHORT).show();
        }
    }

    //METODES DE LA CLASSE

    private void DibuixarJoystick(float newX, float newY){
        //VARIABLES
        float hypotenuse, sin, cos;
        if(this.getHolder().getSurface().isValid()){//Es comprova si la Surface a estat correctament creada
            Canvas myCanvas = this.getHolder().lockCanvas(); //Fixem el dibuix del Joystick a la pantalla
            Paint colors = new Paint(); //Creem un objecte de tipus Paint pels colors
            /*Es important netejar el fons de la Surface de l'ultima posicó, ja que sinó el fons
            es niria quedant pintat del color del cercle petit. Això ho aconseguim amb la següent
            linia de codi:  */
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //Es canvia el color del fons de la Surface
            myCanvas.drawColor(Color.BLACK,PorterDuff.Mode.LIGHTEN);

            //Es calcula la hipotenusa per determina la posició relativa (Quan el dit surt del rang establert)
            hypotenuse = (float) Math.sqrt(Math.pow(newX - CentreX, 2) + Math.pow(newY - CentreY, 2));
            sin = (newY - CentreY) / hypotenuse;
            cos = (newX - CentreX) / hypotenuse;

            //Colors de la base
            colors.setARGB(255,100 ,100,100);
            myCanvas.drawCircle(CentreX, CentreY, RadiBase, colors);

            //Colors del cercle
            colors.setARGB(255,255,51,0);
            myCanvas.drawCircle(newX, newY, RadiJoystick, colors);

            /*En aquest moment ja estaria el Joystick dibuixat, però per
            tal de fer-lo visible cal cridar la linea següent: */
            getHolder().unlockCanvasAndPost(myCanvas);
        }else {
            Toast.makeText(getContext(),"Surface not valid", Toast.LENGTH_SHORT).show();
        }
    }

    private void DefinicioMides(){
        //Agafem les mides de la SurfaceView per una mida correcte del joystick
        CentreX = getWidth() / 2;
        CentreY = getHeight() / 2;
        RadiBase = Math.min(getWidth(),getHeight()) / 3;
        RadiJoystick = Math.min(getWidth(),getHeight()) / 5;
    }

    //LISTENER DEL JOYSTICK

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //VARIABLES
        float desplaçament, XDibuixada ,YDibuixada, Radi;
        /*Ens hem d'assegurar que només es pugui operar amb el joystick quan l'usuari toca
        l'interior de la surface view, i no l'altre part de la pantalla. Això s'aconsegueix
        amb la següent condició: */
        if(v.equals(this)){
            if(event.getAction() != event.ACTION_UP){//Mentre l'usuari mantengui el dit a la pantalla/Joystick
                desplaçament = (float) Math.sqrt((Math.pow(event.getX() - CentreX, 2)) + Math.pow(event.getY() - CentreX, 2));
                if(desplaçament < RadiBase){ // Evita que el jostick marxi completament fora de la base
                    DibuixarJoystick(event.getX(),event.getY());//Dibuixa el Joystick a la posició que l'usuari té el dit
                    joystickCallback.onJoystickMoved((event.getX() - CentreX/RadiBase), (event.getY() - CentreY)/RadiBase, getId(),event.getX(),event.getY());
                }else{
                    Radi = RadiBase/desplaçament;
                    XDibuixada = CentreX + (event.getX() - CentreX) * Radi;
                    YDibuixada = CentreY + (event.getY() - CentreY) * Radi;
                    DibuixarJoystick(XDibuixada,YDibuixada);
                    joystickCallback.onJoystickMoved((XDibuixada - CentreX)/RadiBase, (YDibuixada-CentreY)/RadiBase, getId(),event.getX(),event.getY());
                }
            }else{ //Retornaria el Joystick a posició inicial
                DibuixarJoystick(CentreX,CentreY);
                joystickCallback.onJoystickMoved(0,0, getId(),225,300);
            }
        }
        return true;
    }

    //COMPORTAMENTS DE LA SURFACE
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DefinicioMides();
        DibuixarJoystick(CentreX,CentreY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    //INTERFICIE DEL LISTENER DEL JOYSTICK
    public interface JoystickListener{
        void onJoystickMoved(float xPercent, float YPercent,int id, float x, float y);
    }

}
