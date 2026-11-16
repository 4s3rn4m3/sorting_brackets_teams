package main;

import controller.LigaController;
import view.LigaView;

public class Main {
  public static void main(String[] args) {
    LigaView vista = new LigaView();
    LigaController controlador = new LigaController(vista);

    controlador.iniciar();
  }
}
