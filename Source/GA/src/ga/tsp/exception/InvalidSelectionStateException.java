package ga.tsp.exception;

public class InvalidSelectionStateException extends Exception {

  private static final long serialVersionUID = 775111680537249683L;

  @Override
  public String getMessage() {
    return "Selection state is not valid";
  }
}
