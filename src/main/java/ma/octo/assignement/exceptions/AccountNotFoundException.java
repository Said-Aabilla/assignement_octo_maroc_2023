package ma.octo.assignement.exceptions;

public class AccountNotFoundException extends Exception {

  public AccountNotFoundException() {
  }

  public AccountNotFoundException(String message) {
    super(message);
  }
}
