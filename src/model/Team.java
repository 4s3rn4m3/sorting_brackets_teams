package model;

import java.io.Serializable;

public class Team implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String club;

  public Team(String club) {
    if (club == null || club.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
    }
    this.club = club;
  }

  public String getClub() {
    return club;
  }
}
