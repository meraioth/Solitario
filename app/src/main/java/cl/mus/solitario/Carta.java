package cl.mus.solitario;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by meraioth on 18-06-17.
 */

public class Carta extends android.support.v7.widget.AppCompatImageButton {
    Carta aux=this;
    private String clase = "",nombre="";
    private int numero;
    Principal mcontext;

    public Carta(Context mcontext,String clase, int numero) {
        super(mcontext);
        this.mcontext= (Principal) mcontext;
        this.clase=clase;
//        seleccionable(true);
        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        Point p = new Point();
        dp.getSize(p);
        int width = (int) (((int)p.x/7)*0.78);
        setMaxWidth(width);
        setMinimumWidth(width);
        setMaxHeight((int) (width*1.333));
        setMinimumHeight((int) (width*1.333));
        this.numero=numero;
        try {
            Drawable d = Drawable.createFromStream(mcontext.getAssets().open(this.clase+"/"+this.clase+String.valueOf(numero)+".png"), null);
            setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void seleccionable(boolean b) {
//        aux=this;
//        aux.setAlpha((float)1);
//        if(b){
//            setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    Carta carta = aux;
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        mcontext.mover(carta);
//                        carta.setAlpha((float)0.4);
//                    }
//                    return false;
//                }
//            });
//
//        }else{
//            setOnTouchListener(null);
//        }
//    }
    public int getNumero(){
        return this.numero;
    }
    public String getClase(){
        return this.clase;
    }
//
//    public void eliminar(){
//        setVisibility(View.INVISIBLE);
//        seleccionable(false);
//        mcontext.actualizar();
//    }

    public void cambio( Carta carta){
        this.numero = carta.getNumero();
        this.clase = carta.getClase();
        try {
            Drawable d = Drawable.createFromStream(mcontext.getAssets().open(this.clase+"/"+this.clase+String.valueOf(numero)+".png"), null);
            setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
