package ma.octo.assignement.exceptions;

public class CompteNonExistantException extends Exception {

  public CompteNonExistantException() {
  }

  public CompteNonExistantException(String message) {
    super(message);
  }
}
