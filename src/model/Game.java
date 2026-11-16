package model;

import java.util.concurrent.ThreadLocalRandom;

public class Game {
  private Game() {}

  public static Team jugar(Team equipo1, Team equipo2) {
    return ThreadLocalRandom.current().nextBoolean() ? equipo1 : equipo2;
  }
}
