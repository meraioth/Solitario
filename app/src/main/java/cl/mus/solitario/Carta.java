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


    public int getNumero(){
        return this.numero;
    }
    public String getClase(){
        return this.clase;
    }



}
