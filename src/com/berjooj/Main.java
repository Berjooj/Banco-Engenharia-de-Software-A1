package com.berjooj;

import java.io.IOException;

import com.google.gson.Gson;

public class Main {

    public static ControladorSistema controladorSistema;
    public static Gson gson;

    public static void main(String[] args) throws IOException {
        Main.controladorSistema = new ControladorSistema();

        Main.gson = new Gson();

        Main.controladorSistema.init(1);

        Main.controladorSistema.run();
    }
}