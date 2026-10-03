package com.example.splashimagenapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.core.splashscreen.SplashScreen;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private final AtomicBoolean cargaTerminada =
            new AtomicBoolean(false);
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();
    private final Repositorio repositorio = new Repositorio();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen =
                SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        splashScreen.setKeepOnScreenCondition(
                () -> !cargaTerminada.get());
        setContentView(R.layout.activity_main);
        prepararAplicacion();
    }
    private void prepararAplicacion() {
        executor.execute(() -> {
            try {
                Datos datos = repositorio.cargarDatosIniciales();
                runOnUiThread(() -> {
                    mostrarDatos(datos);
                    cargaTerminada.set(true);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    mostrarError(error);
                    cargaTerminada.set(true);
                });
            }
        });
    }

    private void mostrarDatos(Datos datos) {
        ((android.widget.TextView) findViewById(R.id.textViewMensaje)).setText(datos.getMensaje());
    }

    private void mostrarError(Exception error) {
        ((android.widget.TextView) findViewById(R.id.textViewMensaje)).setText("Error: " + error.getMessage());
    }
    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }
}