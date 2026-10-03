package com.example.splashimagenapp;

public class Repositorio {
    public Datos cargarDatosIniciales() throws Exception {
        // Simulamos una carga de datos
        Thread.sleep(2000);
        return new Datos("Datos cargados correctamente");
    }
}
