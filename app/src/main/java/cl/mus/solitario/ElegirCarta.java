package cl.mus.solitario;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class ElegirCarta extends AppCompatActivity {

    ImageButton b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_carta);
        b1 = (ImageButton) findViewById(R.id.b1);

        b2 = (ImageButton) findViewById(R.id.b2);
        try {
            b1.setImageDrawable(Drawable.createFromStream(this.getAssets().open("fondo/C1.png"),null));
            b2.setImageDrawable(Drawable.createFromStream(this.getAssets().open("fondo/C2.png"),null));

        } catch (IOException e) {
            e.printStackTrace();
        }
        b1.setAdjustViewBounds(true);
        b2.setAdjustViewBounds(true);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = v.getContext().getSharedPreferences("setting",MODE_ENABLE_WRITE_AHEAD_LOGGING);
                sp.edit().putInt("color",1).commit();
                Toast.makeText(v.getContext(),"Color Rojo elegido",Toast.LENGTH_SHORT).show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = v.getContext().getSharedPreferences("setting",MODE_ENABLE_WRITE_AHEAD_LOGGING);
                sp.edit().putInt("color",2).commit();
                Toast.makeText(v.getContext(),"Color Azul elegido",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
