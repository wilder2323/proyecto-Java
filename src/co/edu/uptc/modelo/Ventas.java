package co.edu.uptc.modelo;

import java.util.ArrayList;

public class Ventas {
  public ArrayList<Venta> ventas;
  private ArrayList<Persona> personas;

  public Ventas() {
    ventas = new ArrayList<Venta>();
    personas = new ArrayList<Persona>();
  }

  public ArrayList<Persona> getPersonas() {
    return personas;
  }

  public void agregarVenta(Venta venta) throws VentasInvalidaException {
    if(!personaEstaRegistrada(venta.getId())) {
      throw new VentasInvalidaException("La Id " + venta.getId() + " no está registrada en el sistema");
    }

    venta.setPersona(obtenerPersona(venta.getId()));
    ventas.add(venta);
  }

  public Persona obtenerPersona(String id) {
    for(Persona p : personas) {
      if(p.esIdIgual(id)){
        return p;
      }
    }

    return null;
  }


  public Venta obtenerMasVendido() {
    Venta v = ventas.get(0);
    int cantidad = 0;

    for(int i = 1; i < ventas.size(); i++) {
      Venta venta = ventas.get(i);

      if (venta.getCantidad()> cantidad) {
        v = venta;
        cantidad = venta.getCantidad();
      }
    }

    return v;
  }

  public boolean personaEstaRegistrada(String id) {
    for(Persona persona : personas) {
      if(persona.esIdIgual(id)) {
        return true;
      }
    }

    return false;
  }

  public void agregarPersona(String personaTexto) throws PersonaInvalidaException {
    String[] datos = personaTexto.split("\n");

    for(String personaDato: datos) {
      try{
        Persona persona = Parseador.parsearPersona(personaDato);
        
        if(personaEstaRegistrada(persona.getCodigoUnico())) {
          throw new PersonaInvalidaException("La Id " + persona.getCodigoUnico() + " ya ha sido registrada");
        }

        personas.add(persona);
      } catch(PersonaInvalidaException e) {
        throw e;
      }
    }
  }

  public Venta buscarVentaPorCodigo(String codigo) {
    for (Venta venta : ventas) {
      if (venta.getCodigo() == codigo) {
        return venta;
      }
    }

    return null;
  }

  // Me devuelve la lista de las ventas
  public ArrayList<Venta> getVentas() {
    return ventas;
  }
}
