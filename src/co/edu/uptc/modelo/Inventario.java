package co.edu.uptc.modelo;

import java.util.ArrayList;

public class Inventario {
  private ArrayList<Celular> celulares;

  public ArrayList<Celular> getCelulares() {
    return celulares;
  }

  Inventario() {
    celulares = new ArrayList<Celular>();
  }

  public void agregarCelular(Celular celular) {
    celulares.add(celular);
  }

  public Celular buscarCeularPorCodigo(String codigo) {
    for (Celular celular : celulares) {
      if(celular.getCodigo().equals(codigo)) {
        return celular;
      }
    }

    return null;
  }
}
