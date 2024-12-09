package co.edu.uptc.modelo;

public class Celular {
  private String nombre;
  private String codigo;
  private int precio;
  private int cantidad;

  /**
   * @param nombre   String: Nombre del ceular
   * @param codigo   String : Codigo
   * @param precio   Int: precio en pesos Colombianos
   * @param cantidad Int: Cantidad
   */
  public Celular(String nombre, String codigo, int precio, int cantidad) {
    this.nombre = nombre;
    this.codigo = codigo;
    this.precio = precio;
    this.cantidad = cantidad;
  }

  @Override
  public String toString() {
    String[] datos = {
        nombre, codigo, Integer.toString(precio), Integer.toString(cantidad),
    };

    return String.join("|", datos);
  }

  public String getNombre() {
    return nombre;
  }

  public String getCodigo() {
    return codigo;
  }

  public int getPrecio() {
    return precio;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidadNueva) {
    this.cantidad = cantidadNueva;
  }
}
