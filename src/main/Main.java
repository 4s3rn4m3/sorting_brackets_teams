package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import model.*;

public class Main {

  public static void main(String[] arg) {
    ArrayList<Team> teams = new ArrayList<>();

    // Create teams
    int numTeams = 16;
    if (numTeams % 2 == 0) {
      for (int i = 1; i <= numTeams; i++) {
        teams.add(new Team("Equipo " + i));
      }

    } else {
      System.out.println("No esta bien el numero debes ser modulo 2");
    }
    Team winner = sorteo(teams);
    System.out.println("Champion: " + winner.getClub() + "\n\n");
  }

  // recursivo
  public static Team sorteo(ArrayList<Team> teams) {
    if (teams.size() == 1) return teams.get(0);
    System.out.println("\nEtapa: " + teams.size());
    Collections.shuffle(teams);
    ArrayList<Team> winTeams = getWinners(teams);
    return sorteo(winTeams);
  }

  public static Team play(Team e1, Team e2) {
    Random random = new Random();
    return random.nextBoolean() ? e1 : e2;
  }

  public static ArrayList<Team> getWinners(ArrayList<Team> teams) {
    ArrayList<Team> winners = new ArrayList<>();
    return getWinnersRecursive(teams, winners);
  }

  private static ArrayList<Team> getWinnersRecursive(
      ArrayList<Team> teams, ArrayList<Team> winners) {
    if (teams.isEmpty()) return winners;
    Team e1 = teams.remove(0);
    Team e2 = teams.remove(0);

    System.out.print(e1.getClub() + " VS " + e2.getClub());
    Team winner = play(e1, e2);
    System.out.print(" --> WINNER:  " + winner.getClub() + "\n");
    winners.add(winner);

    return getWinnersRecursive(teams, winners);
  }
}
