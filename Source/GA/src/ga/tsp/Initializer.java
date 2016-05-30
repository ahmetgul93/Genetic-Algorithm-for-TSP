package ga.tsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ga.tsp.exception.InvalidCrossoverTypeException;
import ga.tsp.exception.InvalidInstanceException;
import ga.tsp.exception.InvalidSelectionTypeException;
import reader.CityReader;

public class Initializer {

  private static void constructCities() throws InvalidInstanceException {
    final CityReader reader = new CityReader();
    reader.fetchCities();
  }

  private static void determineCrossoverType() throws InvalidCrossoverTypeException {
    System.out.println("Please write one crossover type for process (CX or PMX)");
    final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    String crossoverType = null;
    try {
      crossoverType = bufferRead.readLine();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    if (crossoverType.toUpperCase().equals("CX") || crossoverType.toUpperCase().equals("PMX")) {
      Util.CROSSOVER_TYPE = crossoverType;
    } else {
      throw new InvalidCrossoverTypeException();
    }
  }

  // private static void determineSelectionType() throws InvalidSelectionTypeException {
  // System.out.println("Please write one selection type for process (ROULETTE or RANK)");
  // final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
  // String selectionType = null;
  // try {
  // selectionType = bufferRead.readLine();
  // } catch (final IOException e) {
  // e.printStackTrace();
  // }
  // if (selectionType.toUpperCase().equals("ROULETTE")
  // || selectionType.toUpperCase().equals("RANK")) {
  // Util.SELECTION_TYPE = selectionType;
  // } else {
  // throw new InvalidSelectionTypeException();
  // }
  // }

  public static void main(final String[] args) throws InvalidCrossoverTypeException,
      InvalidInstanceException, InvalidSelectionTypeException {

    constructCities();
    determineCrossoverType();
    // determineSelectionType();

    Population population = new Population(true);
    System.out.println("Initial distance: " + population.getBest().getDistance());
    System.out.println("Initial best:");
    System.out.println(population.getBest());

    final Genetic algorithm = new Genetic();
    for (int i = 0; i < Util.GENERATION_COUNT; i++) {
      population = algorithm.run(population);
    }

    System.out.println("Finished");
    System.out.println("Final distance: " + population.getBest().getDistance());
    System.out.println("Solution:");
    System.out.println(population.getBest());
  }
}
