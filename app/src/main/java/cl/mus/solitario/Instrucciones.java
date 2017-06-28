package cl.mus.solitario;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class Instrucciones extends AppCompatActivity {
    TextView textView,t1,t2;
    ImageView imageView1,imageView2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);
        textView = (TextView) findViewById(R.id.contenido_instruccion);
        t1 = (TextView) findViewById(R.id.titulo1);
        t2 = (TextView)findViewById(R.id.titulo2);
        textView.append("El Juego tiene 3 grupos de Cartas:\n" +
                "\n" +
                "\n" +
                "El mazo: Contiene las cartas aún no vistas, boca abajo.\n" +
                "La pila: Contiene las cartas vistas, boca arriba.\n" +
                "El descarte: Contiene las cartas retiradas de la pirámide.\n" +
                "\n" +
                "\n" +
                "Las reglas del solitario son simples:\n" +
                "\n" +
                "Puedes mover una carta desde el mazo a la pila tocando el mazo\n" +
                "\n" +
                "Puedes mover la carta superior de la pila encima de una carta destapada de la pirámide para así eliminar ambas cartas si la suma de ellas es 13\n" +
                "\n" +
                "Si hay una carta en la pirámide que está parcialmente tapada en una esquina por otra carta, y la suma de estas dos cartas es 13, entonces es posible eliminar ambas cartas\n" +
                "\n" +
                "Puede mover una carta destapada de la pirámide encima de otra carta destapada de la pirámide para así eliminar ambas cartas si la suma de ellas es 13\n" +
                "\n" +
                "Si las cartas del mazo se acaban, es posible retornar las cartas de la pila al mazo, en su mismo orden, y continuar el juego.\n" +
                "\n" +
                "Los reyes valen 13, por lo se permite retirar un rey destapado en la pirámide ó en la pila.\n\n" +
                "Ganas el solitario si consigues retirar todas las cartas de las pirámide. Por otro lado, pierdes el solitario si ya no es posible retirar más cartas de la pirámide no importa cuántas veces retorne las cartas de la pila al mazo.");
        t1.append("Configuración inicial");
        t2.append("Posible Jugada");
        imageView1 = (ImageView) findViewById(R.id.instruccion_1);
        imageView2 = (ImageView) findViewById(R.id.instruccion_2);
        try {
            imageView1.setImageDrawable(Drawable.createFromStream(getApplicationContext().getAssets().open("inicial.png"), null));
            imageView2.setImageDrawable(Drawable.createFromStream(getApplicationContext().getAssets().open("movimiento.png"), null));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
