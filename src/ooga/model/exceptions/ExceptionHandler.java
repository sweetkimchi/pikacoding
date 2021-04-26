package ooga.model.exceptions;

/**
 *
 */
public class ExceptionHandler extends RuntimeException {

  /**
   * Default constructor
   */
  public ExceptionHandler(String error) {
    // TODO: handle specific errors
    super(error);
    System.out.println(error);
  }

  /**
   *
   */
  public ExceptionHandler(Exception error) {
    super(error.getMessage());
  }


}