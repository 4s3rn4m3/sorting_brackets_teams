package view;

import java.util.List;
import java.util.Scanner;
import model.Etapa;
import model.Partido;

public class LigaView {
  private final Scanner scanner = new Scanner(System.in);

  public boolean preguntarSiCargar() {
    while (true) {
      System.out.print("¿Desea cargar el torneo guardado? (s/n): ");
      String respuesta = scanner.nextLine().trim().toLowerCase();

      if (respuesta.equals("s")) {
        return true;
      }
      if (respuesta.equals("n")) {
        return false;
      }

      mostrarMensaje("Ingrese s o n.");
    }
  }

  public int pedirCantidadEquipos() {
    while (true) {
      System.out.print("Ingrese la cantidad de equipos: ");
      String entrada = scanner.nextLine();

      try {
        return Integer.parseInt(entrada);
      } catch (NumberFormatException ex) {
        mostrarMensaje("Ingrese un número entero válido.");
      }
    }
  }

  public void mostrarResultados(List<Etapa> etapas) {
    if (etapas.isEmpty()) {
      mostrarMensaje("El torneo guardado no tiene etapas sorteadas.");
      return;
    }

    for (Etapa etapa : etapas) {
      System.out.println("\n=== " + etapa.getNombre() + " ===");

      for (Partido partido : etapa.getPartidos()) {
        System.out.println(
            partido.getEquipo1().getClub()
                + " vs "
                + partido.getEquipo2().getClub()
                + " --> Ganador: "
                + partido.getGanador().getClub());
      }
    }

    Etapa ultimaEtapa = etapas.get(etapas.size() - 1);
    Partido partidoFinal = ultimaEtapa.getPartidos().get(0);

    System.out.println("\nCampeón: " + partidoFinal.getGanador().getClub());
  }

  public void mostrarMensaje(String mensaje) {
    System.out.println(mensaje);
  }
}
