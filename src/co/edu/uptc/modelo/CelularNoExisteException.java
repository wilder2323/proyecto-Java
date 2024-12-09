package co.edu.uptc.modelo;

// Le dice a java que esta clase es un ERROR
public class CelularNoExisteException extends Exception {
  public CelularNoExisteException(String mensaje) {
    // new Exception(mensaje);
    super(mensaje);
  }
}
