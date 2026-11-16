package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Liga implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int cantidadEquipos;
  private final List<Team> equipos;
  private List<Etapa> etapas;

  public Liga(int cantidadEquipos) {
    this.cantidadEquipos = cantidadEquipos;
    this.equipos = new ArrayList<>();
    this.etapas = new ArrayList<>();
  }

  public boolean esPotenciaDeDos(int numero) {
    if (numero <= 0) {
      return false;
    }
    if (numero == 1) {
      return true;
    }
    if (numero % 2 != 0) {
      return false;
    }
    return esPotenciaDeDos(numero / 2);
  }

  public void crearEquipos() throws NumeroEquiposInvalidoException {
    if (cantidadEquipos < 2 || !esPotenciaDeDos(cantidadEquipos)) {
      throw new NumeroEquiposInvalidoException(
          "La cantidad de equipos debe ser una potencia de 2 y ser al menos 2.");
    }

    equipos.clear();
    etapas.clear();

    for (int i = 1; i <= cantidadEquipos; i++) {
      equipos.add(new Team("Equipo " + i));
    }
  }

  public List<Etapa> sortear() throws SorteoInvalidoException {
    if (equipos.size() < 2 || !esPotenciaDeDos(equipos.size())) {
      throw new SorteoInvalidoException("No se puede sortear: la lista de equipos no es válida.");
    }

    etapas = new ArrayList<>();
    List<Team> participantes = new ArrayList<>(equipos);

    sortearEtapasRecursivamente(participantes, etapas);
    return getEtapas();
  }

  private void sortearEtapasRecursivamente(List<Team> participantes, List<Etapa> etapasDelTorneo)
      throws SorteoInvalidoException {
    if (participantes.size() == 1) {
      return;
    }

    if (participantes.size() < 2 || participantes.size() % 2 != 0) {
      throw new SorteoInvalidoException("La etapa tiene una cantidad inválida de participantes.");
    }

    List<Team> mezclados = new ArrayList<>(participantes);
    Collections.shuffle(mezclados);

    List<Partido> partidos = new ArrayList<>();
    emparejarRecursivamente(mezclados, 0, partidos);

    etapasDelTorneo.add(new Etapa(obtenerNombreEtapa(mezclados.size()), partidos));

    List<Team> ganadores = new ArrayList<>();
    for (Partido partido : partidos) {
      ganadores.add(partido.getGanador());
    }

    sortearEtapasRecursivamente(ganadores, etapasDelTorneo);
  }

  private void emparejarRecursivamente(
      List<Team> equiposMezclados, int indice, List<Partido> partidos)
      throws SorteoInvalidoException {
    if (indice == equiposMezclados.size()) {
      return;
    }

    if (indice + 1 >= equiposMezclados.size()) {
      throw new SorteoInvalidoException("No se encontró un oponente para uno de los equipos.");
    }

    Team equipo1 = equiposMezclados.get(indice);
    Team equipo2 = equiposMezclados.get(indice + 1);
    Team ganador = Game.jugar(equipo1, equipo2);

    partidos.add(new Partido(equipo1, equipo2, ganador));

    emparejarRecursivamente(equiposMezclados, indice + 2, partidos);
  }

  private String obtenerNombreEtapa(int participantes) {
    switch (participantes) {
      case 16:
        return "Octavos de final";
      case 8:
        return "Cuartos de final";
      case 4:
        return "Semifinales";
      case 2:
        return "Final";
      default:
        return "Ronda de " + participantes + " equipos";
    }
  }

  public List<Team> getEquipos() {
    return Collections.unmodifiableList(equipos);
  }

  public List<Etapa> getEtapas() {
    return Collections.unmodifiableList(etapas);
  }

  public void guardar(String ruta) throws IOException {
    Path rutaArchivo = Paths.get(ruta);
    Path directorio = rutaArchivo.getParent();

    if (directorio != null) {
      Files.createDirectories(directorio);
    }

    try (ObjectOutputStream salida =
        new ObjectOutputStream(new FileOutputStream(rutaArchivo.toFile()))) {
      salida.writeObject(this);
    }
  }

  public static Liga cargar(String ruta) throws IOException, ClassNotFoundException {
    try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
      Object objeto = entrada.readObject();

      if (!(objeto instanceof Liga)) {
        throw new IOException("El archivo no contiene un objeto de tipo Liga.");
      }

      return (Liga) objeto;
    }
  }
}
