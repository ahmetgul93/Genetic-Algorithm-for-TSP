package ga.tsp.exception;

public class InvalidCrossoverTypeException extends Exception {

  private static final long serialVersionUID = -935429640640579530L;

  @Override
  public String getMessage() {
    return "There is no crossover type for given input";
  }
}
