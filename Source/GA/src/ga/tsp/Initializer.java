package ga.tsp;

import ga.tsp.exception.InvalidInputException;
import reader.CityReader;

// TODO Two different crossing types and two different chromosome length will be use.
public class Initializer {

  private static void constructCities() {
    CityReader reader;
    try {
      reader = new CityReader();
      reader.fetchCities();
    } catch (final InvalidInputException e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    constructCities();

    Population population = new Population(true);
    System.out.println("Initial distance: " + population.getBest().getDistance());

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
