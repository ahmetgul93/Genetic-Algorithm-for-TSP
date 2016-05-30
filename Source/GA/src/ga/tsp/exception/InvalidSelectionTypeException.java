package ga.tsp.exception;

public class InvalidSelectionTypeException extends Exception {

  private static final long serialVersionUID = -6099147969608056784L;

  @Override
  public String getMessage() {
    return "There is no selection type for given input";
  }
}
