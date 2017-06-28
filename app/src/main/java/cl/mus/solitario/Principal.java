package cl.mus.solitario;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Principal extends AppCompatActivity {
    public Map<Integer,String> mapita =new HashMap<>();
    TextView jugadas ;
    Carta piramide[][] = new Carta[7][7];
    Stack<Carta> faltantes = new Stack<>();
    boolean start_crono = true;
    Chronometer crono;
    int cartas_procesadas;
    int contador_jugadas=0;
    Carta auxiliar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
         crono = (Chronometer) findViewById(R.id.crono);
        crono.start();
        jugadas= (TextView) findViewById(R.id.jugadas);

        List<String> cartas = new ArrayList<>();
        List<Carta> mazo = new ArrayList<>();
        List<Carta> faltante_mazo;

        try {
            Collections.addAll(cartas, getAssets().list("CORAZON"));
            Collections.addAll(cartas, getAssets().list("DIAMANTE"));
            Collections.addAll(cartas, getAssets().list("PICA"));
            Collections.addAll(cartas, getAssets().list("TREBOL"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        crearmazo(mazo,cartas);

        
        LinearLayout ll_interno = (LinearLayout) findViewById(R.id.ll_interno);
        
        montarmazo(ll_interno,piramide,mazo);
        montarpila(ll_interno,mazo);




        Button btn = new Button(this);
        btn.setText("Deshacer");


    }

    private void montarmazo(LinearLayout ll_interno, Carta[][] piramide, List<Carta> mazo) {
        cartas_procesadas = 0;

        for (int i = 0; i < 7; i++) {
            LinearLayout ll = new LinearLayout(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            if (i > 0) {
                params.setMargins(0, (int) -(((getAncho() / 7) * 1.5)),0,0);

            }
            ll.setLayoutParams(params);

            for (int j = 0; j < i + 1; j++) {
                piramide[i][j] = mazo.get(cartas_procesadas);
                piramide[i][j].setAdjustViewBounds(true);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1/(i+1);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.setMargins(10,0,10,0);
                piramide[i][j].setLayoutParams(params);
                piramide[i][j].setOnTouchListener(new MyTouchListener());
                piramide[i][j].setOnDragListener(new MyDragListener());
                ll.addView(piramide[i][j]);
                cartas_procesadas++;
            }
            ll_interno.addView(ll);
        }

    }

    private void crearmazo(List<Carta> mazo, List<String> cartas) {
        Collections.shuffle(cartas);
        for (int i = 0; i < cartas.size(); i++) {
            String[] name = cartas.get(i).split("\\.");
            String[] aux = name[0].split("[0-9]");
            Log.d("clase",aux[0]);
            String numero = name[0].substring(aux[0].length(),name[0].length());
            Carta c = new Carta(this ,aux[0], Integer.parseInt(numero));
            c.setTag(c);
            mapita.put(c.getId(),aux[0]+numero);
            mazo.add(c);
        }

    }
    private void montarpila(LinearLayout ll_interno, List<Carta> mazo){
        for(int i = cartas_procesadas;i<mazo.size();i++){
            faltantes.push(mazo.get(i));
        }
        SharedPreferences sp = this.getSharedPreferences("setting",MODE_ENABLE_WRITE_AHEAD_LOGGING);
        int color= sp.getInt("color",1);
        Mazo mz = new Mazo(this,color,ll_interno);
        LinearLayout ll = new LinearLayout(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        params.gravity = Gravity.LEFT;
        params.setMargins((getAncho()/7)*1,0,0,0);
        ll.setLayoutParams(params);
        ll.addView(mz);
        ll_interno.addView(ll);


    }


    public int getAncho(){
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        return metrics.widthPixels;

    }


    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Carta aux1 = (Carta) view.getTag();

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if(aux1.getNumero()==13){
                    view.setVisibility(View.INVISIBLE);
                    contador_jugadas++;
                    jugadas.setText(String.valueOf(contador_jugadas));
                }
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }

        }
    }

    class MyDragListener implements View.OnDragListener {
        int numero;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    Carta aux1 = (Carta) v.getTag();
                    Carta aux2 = (Carta) view.getTag();
                    if(aux1.getNumero()+aux2.getNumero()==13 && aux1.getNumero()!=13 && aux2.getNumero()!=13){
                        contador_jugadas++;
                        jugadas.setText(String.valueOf(contador_jugadas));

                        v.setVisibility(View.INVISIBLE);
                        view.setVisibility(View.INVISIBLE);
                    }


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    class Mazo extends android.support.v7.widget.AppCompatImageButton{
    int ancho =  getAncho()/6;

        public Mazo(Context context, int fondo_carta, final LinearLayout ll_interno) {
            super(context);
//            setMaxWidth(ancho);
            setMinimumWidth(ancho);
            setMinimumHeight((int) (ancho * 1.3));
//            setMaxHeight((int) (ancho * 1.3));
            try {
                Drawable d = Drawable.createFromStream(context.getAssets().open("fondo/C"+String.valueOf(fondo_carta)+".png"), null);
                setBackground(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarCarta(faltantes,ll_interno);
//                    cartas_procesadas++;
                }
            });
        }


    }

    private void mostrarCarta(Stack<Carta> faltantes, LinearLayout ll_interno) {
        if (auxiliar != null) {
            ll_interno.removeView(auxiliar);
            auxiliar.setEnabled(false);
            auxiliar= faltantes.pop();
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            ll.setLayoutParams(params);

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            params.setMargins(0, 0, 0, 0);
            auxiliar.setLayoutParams(params);
            auxiliar.setOnTouchListener(new MyTouchListener());
            auxiliar.setOnDragListener(new MyDragListener());
            auxiliar.setTag(auxiliar);
            ll_interno.addView(auxiliar);
            cartas_procesadas++;
            auxiliar.setEnabled(true);
//        ll_interno.addView(ll);
//
        }else {
            auxiliar=faltantes.pop();
        }
    }
}