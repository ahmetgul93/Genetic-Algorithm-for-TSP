package ga.tsp.exception;

public class InvalidInputException extends Exception {

  private static final long serialVersionUID = -5531551920085718571L;

  @Override
  public String getMessage() {
    return "There is no instance for given input";
  }
}
