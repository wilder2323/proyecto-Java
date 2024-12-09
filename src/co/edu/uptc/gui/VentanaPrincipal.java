package co.edu.uptc.gui;

import co.edu.uptc.modelo.Celular;
import co.edu.uptc.modelo.CelularInvalidoException;
import co.edu.uptc.modelo.CelularNoExisteException;
import co.edu.uptc.modelo.Parseador;
import co.edu.uptc.modelo.Tienda;
import co.edu.uptc.modelo.VentasInvalidaException;
import co.edu.uptc.modelo.PersonaInvalidaException;
import co.edu.uptc.modelo.Persona;
import co.edu.uptc.modelo.Venta;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

  PanelInventario info;
  private PanelVentas infoVentas;
  private PanelBotones botones;
  private PanelPersona persona;
  private Tienda tienda;

  public VentanaPrincipal() {
    setTitle("Mi Tienda");
    setSize(1300, 600);

    tienda = new Tienda();

    Evento evento = new Evento(this);
    info = new PanelInventario(evento);
    infoVentas = new PanelVentas(evento);
    botones = new PanelBotones(evento);
    persona = new PanelPersona(evento);

    setLayout(new BorderLayout());
    add(info, BorderLayout.WEST);
    add(persona, BorderLayout.CENTER);
    add(infoVentas, BorderLayout.EAST);
    add(botones, BorderLayout.SOUTH);
  }

  public void calcularImpuestos() {
    StringBuilder sb = new StringBuilder();
    double totalImpuesto5 = 0;
    double totalImpuesto19 = 0;

    for (Celular celular : tienda.inventario.getCelulares()) {
      int precio = celular.getPrecio();
      int precioBase = precio * celular.getCantidad();

      if (precio > 600_000) {
        totalImpuesto19 += precioBase;
      } else {
        totalImpuesto5 += precioBase;
      }
    }


    sb.append("El total de impuestos del 5% fue de:").append("\n");
    sb.append("Total de bases grabables: $").append(totalImpuesto5).append("   Impuestos:").append(totalImpuesto5 * 0.05).append("\n");
    sb.append("El total de impuestos del 19% fue de:").append("\n");
    sb.append("Total de bases grabables: $").append(totalImpuesto19).append("   Impuestos:").append(totalImpuesto19* 0.19).append("\n");

    sb.append("El total de los impuestos fue de :").append("\n");
    sb.append("Total:  $").append(totalImpuesto19 + totalImpuesto5).append("\n Total impuetos :").append(totalImpuesto19 * 0.019 + totalImpuesto5 * 0.05).append("\n");

    JOptionPane.showMessageDialog(this, sb.toString());
  }

  public void obtenerMasVendido() {
    StringBuilder sb = new StringBuilder();
    String reporte = this.tienda.generarReporte();
    Venta venta = this.tienda.ventas.obtenerMasVendido();

    sb.append(reporte).append("\n");
    sb.append("El celular mas vendido fue ").append("\n");
    sb.append(venta.toString());

    JOptionPane.showMessageDialog(this,sb.toString());
  }

  public void obtenerStonks() {
    StringBuilder sb = new StringBuilder();

    double iva = 0;
    int precioBase = 0;
    int cantidadTotal = 0;
    int precioVenta = 0;
    double precioBaseGanancia = 0;

    for (Celular celular : tienda.inventario.getCelulares()) {
      precioBase += celular.getPrecio();
      precioBaseGanancia += celular.getPrecio() * 1.25;
      cantidadTotal += celular.getCantidad();

      if (celular.getPrecio() > 600000) {
        iva = celular.getPrecio() * 0.19; // iva del 16 porciento
      } else {
        iva = celular.getPrecio() * 0.05; // si no es mayor a 600mil, el iva sera del 5%
      }
    }

    int totalInvertido = precioBase * cantidadTotal;
    double comisionVenta = precioBase * 0.05;

    for (Venta venta : tienda.ventas.getVentas()) {
      Celular celular = tienda.inventario.buscarCeularPorCodigo(venta.getCodigo());
      precioVenta += venta.getCantidad() * celular.getPrecio();
    }

    sb.append("La cantidad total de celulares es de : ").append(cantidadTotal).append("\n");
    sb.append("El precio total de celulares es de : ").append(precioBase).append("\n");
    sb.append("El IVA es de :").append(iva).append("\n");
    sb.append("Comision de venta:").append(comisionVenta).append("\n");
    sb.append("Precio base de ganancia:").append(precioBaseGanancia).append("\n");
    sb.append("Precio de venta:").append(precioVenta).append("\n");
    sb.append("Total Invertido:").append(totalInvertido).append("\n");
    JOptionPane.showMessageDialog(this, sb.toString());
  }

  public static void main(String[] args) {
    VentanaPrincipal nueva = new VentanaPrincipal();
    nueva.setVisible(true);
  }

  public void cargarInfoInventario() {
    String datos = info.obtenerDatos();

    if (datos.isEmpty()) {
      JOptionPane.showMessageDialog(this, "El área de inventario está vacía.");
      return;
    }

    String lineasIntenvetario = Parseador.insertarPipes(datos);

    try {
      tienda.registrarInventario(lineasIntenvetario);
    } catch (CelularInvalidoException e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
      return;
    }

    info.txInformacion.setText(lineasIntenvetario);
    JOptionPane.showMessageDialog(this, "El inventario ha sido cargado correctamente.");
  }

  public void cargarInfoVentas() {
    String datosVentas = infoVentas.obtenerDatos();

    if (datosVentas.isEmpty()) {
      JOptionPane.showMessageDialog(this, "El área de ventas está vacía.");
      return;
    }

    // Es para que cuando estemos parseando, no lleguen a aparecer bugs raros
    // Si el usuario pone muchos espacios, esto puede hacer que cuando filtremos
    // los datos no se parseen
    String ventas = Parseador.insertarPipes(datosVentas);

    try {
      tienda.registrarVentas(ventas);
    } catch (VentasInvalidaException e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
      return;
    }

    try {
      String celularesActualizados = tienda.restarVentas();
      info.txInformacion.setText(celularesActualizados);
    } catch (CelularNoExisteException e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
      return;
    }

    infoVentas.txInformacion.setText(ventas);
    JOptionPane.showMessageDialog(this, "Las ventas han sido registradas correctamente.");
  }

  public void cargarInfoPersona() {
    String datos = persona.obtenerDatos();

    if (datos.isEmpty()) {
      JOptionPane.showMessageDialog(this, "El área de persona está vacía.");
      return;
    }
    String personaDatos = Parseador.insertarPipes(datos);

    try {
      tienda.ventas.agregarPersona(personaDatos);
    } catch (PersonaInvalidaException e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
      return;
    }

    persona.txInformacion.setText("");
    JOptionPane.showMessageDialog(this, "La pesona ha sido cargada correctamente.");
  }

  public void mostrarVentas() {
    // Obtiene todas las ventas registradas y las agrupa por vendedor.
    ArrayList<Venta> ventas = tienda.getVentas().getVentas();
    StringBuilder sb = new StringBuilder();

    if (ventas.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No hay ventas registradas.");
      return;
    }

    // Agrupar las ventas por el id del vendedor
    sb.append("Listado de Ventas:\n\n");
    for (Venta venta : ventas) {
      sb.append("Vendedor: ").append(venta.getId())
          .append(" - Código Producto: ").append(venta.getCodigo())
          .append(" - Cantidad: ").append(venta.getCantidad()).append("\n");
    }

    // Mostrar las ventas por pantalla
    JOptionPane.showMessageDialog(this, sb.toString());
  }

  public void salir() {
    JOptionPane.showMessageDialog(this, "Cerrando la aplicación");
    System.exit(0);
  }
}
