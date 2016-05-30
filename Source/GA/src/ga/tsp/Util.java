package ga.tsp;

public class Util {

  public static final int POPULATION_SIZE = 300;

  public static final boolean ELITISM = true;

  public static final int ELITISM_COUNT = 1; // don't change this. the algorithm is not responsive
                                             // yet for bigger elitism count.

  public static String CROSSOVER_TYPE; // we are taking this input from user.

  // public static String SELECTION_TYPE;

  public static final double MUTATION_PROBABILITY = 0.015;

  public static final int GENERATION_COUNT = 500;
}
