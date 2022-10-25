package ma.octo.assignement.exceptions;

public class InsufficientBalanceException extends Exception {

  public InsufficientBalanceException() {
  }

  public InsufficientBalanceException(String message) {
    super(message);
  }
}
