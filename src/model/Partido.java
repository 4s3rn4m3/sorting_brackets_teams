package model;

import java.io.Serializable;

public class Partido implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Team equipo1;
  private final Team equipo2;
  private final Team ganador;

  public Partido(Team equipo1, Team equipo2, Team ganador) {
    if (equipo1 == null || equipo2 == null || ganador == null) {
      throw new IllegalArgumentException("Los equipos y el ganador son obligatorios.");
    }
    if (equipo1 == equipo2) {
      throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo.");
    }
    if (ganador != equipo1 && ganador != equipo2) {
      throw new IllegalArgumentException("El ganador debe ser uno de los participantes.");
    }

    this.equipo1 = equipo1;
    this.equipo2 = equipo2;
    this.ganador = ganador;
  }

  public Team getEquipo1() {
    return equipo1;
  }

  public Team getEquipo2() {
    return equipo2;
  }

  public Team getGanador() {
    return ganador;
  }
}
