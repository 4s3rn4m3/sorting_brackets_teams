package controller;

import java.io.IOException;
import java.util.List;
import model.Etapa;
import model.Liga;
import model.NumeroEquiposInvalidoException;
import model.SorteoInvalidoException;
import view.LigaView;

public class LigaController {
  private static final String RUTA_TORNEO = "src/data/torneo.ser";

  private final LigaView vista;

  public LigaController(LigaView vista) {
    this.vista = vista;
  }

  public void iniciar() {
    Liga liga = null;

    if (vista.preguntarSiCargar()) {
      try {
        liga = Liga.cargar(RUTA_TORNEO);
        vista.mostrarMensaje("Torneo cargado correctamente.");
      } catch (IOException | ClassNotFoundException ex) {
        vista.mostrarMensaje("No se pudo cargar el torneo: " + ex.getMessage());
      }
    }

    if (liga != null) {
      vista.mostrarResultados(liga.getEtapas());
      return;
    }

    crearSortearYGuardar();
  }

  private void crearSortearYGuardar() {
    int cantidad = vista.pedirCantidadEquipos();
    Liga liga = new Liga(cantidad);

    try {
      liga.crearEquipos();

      List<Etapa> etapas = liga.sortear();
      vista.mostrarResultados(etapas);

      liga.guardar(RUTA_TORNEO);
      vista.mostrarMensaje("Torneo guardado en " + RUTA_TORNEO);
    } catch (NumeroEquiposInvalidoException | SorteoInvalidoException | IOException ex) {
      vista.mostrarMensaje("Error: " + ex.getMessage());
    }
  }
}
