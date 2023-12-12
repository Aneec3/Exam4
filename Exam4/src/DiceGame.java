import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

class Die {
    private int numSides;
    private int value;
    public Die(int numSides) {
        this.numSides = numSides;
        this.value = 1;
    }
    public int getNumSides() {
        return numSides;
    }
    public void setNumSides(int numSides) {
        this.numSides = numSides;
    }
    public int getValue() {
        return value;
    }
    public void roll() {
        this.value = ThreadLocalRandom.current().nextInt(1, numSides+1);
    }}

class Player {
    private final String name;
    private final Die die;
    public Player(String name, Die die) {
        this.name = name;
        this.die = die;
    }
    public String getName() {
        return name;
    }
    public Die getDie() {
        return die;
    }}

public class DiceGame {
    public static void decideWinner(List<Player> players) {
        int max = Integer.MIN_VALUE;
        List<Integer> tiedIndexes = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            int currentValue = players.get(i).getDie().getValue();
            if (currentValue > max) {
                max = currentValue;
                tiedIndexes.clear();
                tiedIndexes.add(i);
            } else if (currentValue == max) {
                tiedIndexes.add(i);
            }
        }
        if (tiedIndexes.size() > 1) {
            handleTie(players, tiedIndexes);
        } else {
            System.out.println(players.get(tiedIndexes.get(0)).getName() + " won the game");
        }}

    public static void handleTie(List<Player> players, List<Integer> tiedIndexes) {
        System.out.print("These players tied for first place: ");
        for (int i = 0; i < tiedIndexes.size(); i++) {
            System.out.print(players.get(tiedIndexes.get(i)).getName());
            if (i < tiedIndexes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to the Dice Game");
        System.out.print("\nEnter the number of sides on the die being used: ");
        int numSides = scanner.nextInt();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = scanner.next();
            Die playerDie = new Die(numSides);
            players.add(new Player(playerName, playerDie));
        }

        for (Player player : players) {
            player.getDie().roll();
            System.out.println("Player " + player.getName() + " rolled a " + player.getDie().getValue() + " on a " + player.getDie().getNumSides() + " sided die.");
        }
        decideWinner(players);
    }}