package unal.todosalau.urbanfarmenergizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

private HashMap<String, String> hashMap;
private ArrayList<String> listData;
private ArrayAdapter<String> adapter;
private EditText claveEditText, valorEditText, hashMapElementEditText;
private Button addButton, searchButton, deleteButton, borrarButton;
private ListView resourceListView;
private TextView textView3;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Inicializar los elementos de la vista
    claveEditText = findViewById(R.id.claveEditText);
    valorEditText = findViewById(R.id.valorEditText);
    addButton = findViewById(R.id.addButton);
    searchButton = findViewById(R.id.searchButton);
    deleteButton = findViewById(R.id.deleteButton);
    resourceListView = findViewById(R.id.resourceListView);
    textView3 = findViewById(R.id.textView3);

    // Inicializar el HashMap y la lista de datos
    hashMap = new HashMap<>();
    listData = new ArrayList<>();

    // Inicializar el adaptador del ListView
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
    resourceListView.setAdapter(adapter);

    // Agregar un elemento al HashMap y actualizar el ListView cuando se presiona el botón "Agregar"
    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String clave = claveEditText.getText().toString();
            String valor = valorEditText.getText().toString();
            if (clave.isEmpty() && valor.isEmpty()) {
                // Ambos campos están vacíos, mostrar todos los elementos
                listData.clear();
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    String entryClave = entry.getKey();
                    String entryValor = entry.getValue();
                    listData.add(entryClave + ": " + entryValor);
                    adapter.notifyDataSetChanged();
                }
                showToast("Lo campos estan vacios");
            } else if (!clave.equals("") && !valor.equals("")) {
                hashMap.put(clave, valor);
                listData.add(clave + ": " + valor);
                adapter.notifyDataSetChanged();
                showToast("Elemento agregado al HashMap: clave = " + clave + ", valor = " + valor);
            }else{
                showToast("Hay campos vacios");
            }


            claveEditText.setText("");
            valorEditText.setText("");
        }
    });

    // Buscar un elemento en el HashMap cuando se presiona el botón "Buscar"
    searchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String claveSearch = claveEditText.getText().toString();
            String valorSearch = valorEditText.getText().toString();

            listData.clear();

            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String clave = entry.getKey();
                String valor = entry.getValue();
                if (clave.contains(claveSearch) && valorSearch.equals("")) {
                    listData.add(clave + ": " + valor);
                } else if (valor.contains(valorSearch) && claveSearch.equals("")) {
                    listData.add(clave + ": " + valor);
                } else if (clave.contains(claveSearch) && valor.contains(valorSearch)) {
                    listData.add(clave + ": " + valor);
                }
                claveEditText.setText("");
                valorEditText.setText("");
            }

            adapter.notifyDataSetChanged();

            if (listData.isEmpty()) {
                showToast("No se encontraron elementos en el HashMap para la búsqueda: " + claveSearch);
            } else {
                showToast("Se encontraron elementos en el HashMap para la búsqueda: " + valorSearch);
            }
        }
    });

    // Eliminar un elemento del HashMap cuando se presiona el botón "Eliminar"
    deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String clave = claveEditText.getText().toString();
            String valorF = valorEditText.getText().toString();
            String valor = null;
            if (!clave.isEmpty() && !valorF.isEmpty()) {
                valor = hashMap.remove(clave);
            }
            if (valor != null) {
                showToast("Elemento eliminado del HashMap: clave = " + clave + ", valor = " + valor);
                listData.remove(clave + ": " + valor); // Eliminar el elemento de la lista
                adapter.notifyDataSetChanged(); // Notificar al adaptador del ListView sobre los cambios

            } else {
                showToast("Elemento no encontrado en el HashMap");
            }
            listData.clear();
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String entryClave = entry.getKey();
                String entryValor = entry.getValue();
                listData.add(entryClave + ": " + entryValor);
                adapter.notifyDataSetChanged();
            }
            claveEditText.setText("");
            valorEditText.setText("");
        }
    });
}

// Método para mostrar mensajes por pantalla utilizando Toast
private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
}
}