package co.edu.uptc.modelo;

import java.util.Arrays;

public class Parseador {
  public static String insertarPipes(String datos) {
    StringBuilder sb = new StringBuilder();

    String[] lineas = datos.split("\n");

    for (String linea : lineas) {
      // Reemplaza los espacios en blanco con pipes
      sb.append(linea.trim().replaceAll("[ \t]+", "|")).append("\n");
    }

    System.out.println(sb.toString());
    return sb.toString();
  }

  // wilder|guerrero|3124878751|1055551192|CC|123123213|Ahorro|VEND-001
  public static Persona parsearPersona(String personaTexto) throws PersonaInvalidaException {
    String[] datos = personaTexto.split("\\|");

    if (datos.length != 7) {
      throw new PersonaInvalidaException("No hay la cantidad de datos requerida (7).");
    }

    String nombre = String.join(" ", Arrays.copyOfRange(datos, 0, 2));
    String celular = datos[2];
    
    if (celular.length() != 10) {
      throw new PersonaInvalidaException("El número télefonico tiene que tener 10 caracteres");
    }

    String identificacion = datos[3];
    String cuentaNum = datos[4];
    String cuentaTipo = datos[5].toLowerCase();

    if(!(cuentaTipo.equals("ahorro") || cuentaTipo.equals("corriente"))) {
      throw new PersonaInvalidaException("El tipo de cuenta tiene que ser 'corriente' ó 'ahorro'");
    }

    String id = datos[6];

    if(!id.startsWith("VEN")) {
      throw new PersonaInvalidaException("La ID tiene que iniciar con VEN");
    }

    Persona persona = new Persona(nombre, celular, identificacion, cuentaNum, cuentaTipo, id);

    return persona;
  }

  // VEN001 MOP 3 -> Ventas(ID , CODIGO, CANTIADAD)
  public static Venta parsearVentas(String ventasTexto) throws VentasInvalidaException {
    String[] datos = ventasTexto.split("\\|"); // Separa por pipes

    // VEN001|MOP|3 -> [VEN001 , MOP, 3]
    String id = datos[0];// Como el id esta en el primer elemento, lo seleccionamos
    String codigo = datos[1]; // Seleccionamos el codigo de la cantidad

    int cantidad;

    try {
      cantidad = Integer.parseInt(datos[2]); // Tranforma la cantidad en un entero
    } catch (NumberFormatException e) {// Si el formato de los numeros es incorrecto
      // Lanza un nuevo error 'VentasInvalidaException' con el mensaje del error
      throw new VentasInvalidaException(
          "La cantidad en las ventas tiene un formato invalido. \n Venta: " + ventasTexto);
    }

    // Retorna la nueva venta
    Venta venta = new Venta(id, codigo, cantidad);

    return venta;
  }

  // Separa los datos del celular y con este crea una instancia del celular mismo
  public static Celular parsearCelular(String celularTexto) throws CelularInvalidoException {
    String[] informacion = celularTexto.split("\\|");
    int ultimo = informacion.length - 1;
    int precio, cantidad;

    try {
      // Si el usuario pone alguna letra, no se podra parsear a un entero
      // parseInt: toma un texto e intenta convertirlo en un numero entero (NO
      // decimal)
      cantidad = Integer.parseInt(informacion[ultimo]);
      precio = Integer.parseInt(informacion[ultimo - 1].replace(".", ""));
    } catch (NumberFormatException e) {// Si esto falla, tira un error NumberFormatException
      throw new CelularInvalidoException(
          "La cantidad o el precio del celular son incorrectos. \n Celular : " + celularTexto);
      // Nosotros tiramos nuestro propio error el cual dice el problema
    }

    String codigo = informacion[ultimo - 2];
    // Selecciono el nombre de todos los datos que me han dado [Motorola One| MO |
    // 500.000 | 20] [Motorola, One] -> "Motorola One"
    String[] nombreArray = Arrays.copyOfRange(informacion, 0, ultimo - 2);
    String nombre = String.join(" ", nombreArray);

    Celular celular = new Celular(nombre, codigo, precio, cantidad);

    return celular;
  }
}