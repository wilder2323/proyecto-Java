package co.edu.uptc.modelo;

import java.util.ArrayList;
import java.util.HashMap;

// Tenemos una clase 'Tienda' que se encarga de registrar todas las ventas y celulares que pasan por ella
public class Tienda {
  public Inventario inventario;
  public Ventas ventas;
  public HashMap<String,Reporte> reportes;

  public Tienda() {
    inventario = new Inventario();
    reportes = new HashMap<String, Reporte>();
    ventas = new Ventas();
  }

  public Ventas getVentas() {
    return this.ventas;
  }

  public Inventario getInventario() {
    return this.inventario;
  }

  // este metodo registra los celulares
  public void registrarInventario(String inventarioLineas) throws CelularInvalidoException {
    // Separamos los celulares por el salto de linea
    String[] celulares = inventarioLineas.split("\n");

    for (String celularTexto : celulares) {
      try {
        // Usamos el metodo parsear celular para que nos registre el celular y mire si
        // hay algun error en el producto
        Celular celular = Parseador.parsearCelular(celularTexto);
        inventario.agregarCelular(celular);
      } catch (CelularInvalidoException e) {
        throw e;
      }
    }
  }

  public void registrarVentas(String ventasLineas) throws VentasInvalidaException {
    String[] ventas = ventasLineas.split("\n");

    for (String ventasTexto : ventas) {
      try {
        Venta venta = Parseador.parsearVentas(ventasTexto);
        this.ventas.agregarVenta(venta);
      } catch (VentasInvalidaException e) {
        throw e;
      }
    }
  }

  public String generarReporte() {
    StringBuilder sb = new StringBuilder();

    for (Venta venta : ventas.ventas) {
      String id = venta.getId();

      int precio = inventario.buscarCeularPorCodigo(venta.getCodigo()).getPrecio();
      double comision = venta.getCantidad() * precio * 0.05;

      if(!reportes.containsKey(id)) {
        Persona persona = ventas.obtenerPersona(id);
        Reporte nuevo = new Reporte(persona, venta.getCantidad(), comision);

        reportes.put(id, nuevo);
        continue;
      }

      Reporte r = reportes.get(id);

      r.agregarCantidad(venta.getCantidad());
      r.agregarComision(comision);

      reportes.put(id, r);
    }

    reportes.forEach((k, v) -> sb.append(v.toString()).append("\n"));

    return sb.toString();
  }


  private void restarVenta(Venta venta) throws CelularNoExisteException {
    // Buscamos el celular por el codigo
    for(Celular celular :inventario.getCelulares()) {
      System.out.println(celular);
    }

    Celular celular = inventario.buscarCeularPorCodigo(venta.getCodigo());

    // Vemos si el celular esta en celular esta en el inventario
    if (celular == null) {
      throw new CelularNoExisteException("El celular : " + venta.getCodigo() + " el cual quieres comprar no esta en el inventario");
    }

    // Vemos si tenemos la cantidad que nos piden
    if (celular.getCantidad() - venta.getCantidad() <= 0) {
      throw new CelularNoExisteException("Estás intentando comprar mas celulares de los que hay en el inventario.\nEl Celular que quieres comrpra es :"+ venta.getCodigo() + " solamente hay " + celular.getCantidad() + " y quieres comprar :" + venta.getCantidad());
    }

    // Actualizamos la cantidad
    celular.setCantidad(celular.getCantidad() - venta.getCantidad());
  }

  public String restarVentas() throws CelularNoExisteException {
    // String Builder nos sirve para crar Strings de manera eficiente
    StringBuilder sb = new StringBuilder();// Sb = String Builder

    // Vamos por todas las ventas
    for (Venta venta : this.ventas.getVentas()) {
      try {
        // Restamos las ventas que encontramosk
        this.restarVenta(venta);
      } catch (CelularNoExisteException e) { // esta clase no es un error, hereda de la clase Exception
        // Si no encontramos ventas mandamos un error 'CelularNoExiste'
        throw e;
      }
    }

    for (Celular celular : inventario.getCelulares()) {
      sb.append(celular).append("\n");
    }

    return sb.toString();
  }
}
