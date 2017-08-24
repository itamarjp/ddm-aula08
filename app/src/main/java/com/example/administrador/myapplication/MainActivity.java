package com.example.administrador.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText cep;
    boolean atualizando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cep = (EditText) findViewById(R.id.cepEditText);
        cep.addTextChangedListener(mascara);

    }


    private final TextWatcher mascara = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            // se adicionamos a máscara, o Android chama o método onTextChange.
            // Para evitar o loop infinito usamos este controle
            if (atualizando) {
                atualizando = false;
                return;
            }
            // Ao apagar o texto, a máscara é removida,
            // então o posicionamento do cursor precisa
            // saber se o texto atual tinha ou não, máscara
            boolean temMascara =
                    s.toString().indexOf('.') > -1 ||
                            s.toString().indexOf('-') > -1;
            // Remove o '.' e '-' da String
            String str = s.toString()
                    .replaceAll("[.]", "")
                    .replaceAll("[-]", "");
            // os parâmetros before e after dizem o tamanho
            // anterior e atual da String digitada, se after > before é
            // porque está digitando, caso contrário, está apagando
            if (after > before) {
                // Se tem mais de 5 caracteres (sem máscara)
                // coloca o '.' e o '-'
                if (str.length() > 5) {
                    str = str.substring(0, 2) + '.' +
                            str.substring(2, 5) + '-' +
                            str.substring(5);
                    // Se tem mais de 2, coloca só o ponto
                } else if (str.length() > 2) {
                    str = str.substring(0, 2) + '.' +
                            str.substring(2);
                }
                // Seta a flag para evitar chamada infinita
                atualizando = true;
                // seta o novo texto
                cep.setText(str);
                // seta a posição do cursor
                cep.setSelection(cep.getText().length());
            } else {
                atualizando = true;

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
}

