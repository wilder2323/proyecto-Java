package co.edu.uptc.modelo;

public class Venta {
  private String id;
  private String codigo;
  private int cantidad;
  public static Persona persona;

  public boolean esIdIgual(String id) {
    return this.id.equals(id);
  }

  public Venta(String id, String codigo, int cantidad) {
    this.id = id;
    this.codigo = codigo;
    this.cantidad = cantidad;
  }

  public String getId() {
    return id;
  }

  public void setPersona(Persona p) {
    persona = p;
  }

  public String getCodigo() {
    return codigo;
  }

  public int getCantidad() {
    return cantidad;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Celular ID: ").append(codigo).append("\n");
    sb.append("Nombre : ").append(persona.getNombre()).append("\n");
    sb.append("Número de Cuenta: ").append(persona.getNumeroCuentaBancaria()).append("\n");
    sb.append("Tipo de Cuenta: ").append(persona.getTipoCuentaBancaria()).append("\n");

    return sb.toString();
  }
}
