package ga.tsp;

// TODO Two different crossing types and two different chromosome length will be use.
public class Initializer {

  public static void main(final String[] args) {
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
