package cl.mus.solitario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        b1= (Button) findViewById(R.id.empezar);
        b2= (Button) findViewById(R.id.instruccion);
        b3= (Button) findViewById(R.id.opciones);
        b4= (Button) findViewById(R.id.quien);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotostart();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoinstruccion();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoopciones();
            }
        });
    }

    private void gotoopciones() {
        startActivity(new Intent(this,ElegirCarta.class));
    }

    private void gotoinstruccion() {
        startActivity(new Intent(this,Instrucciones.class));
    }

    private void gotostart() {
        startActivity(new Intent(this,Principal.class));
    }
}
