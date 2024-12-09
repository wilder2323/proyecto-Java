package co.edu.uptc.modelo;

public class Persona {
    private String nombre;
    private String numeroCelular;
    private String numeroDocumento;
    private String numeroCuentaBancaria;
    private String tipoCuentaBancaria;
    private String codigoUnico;

    public boolean esIdIgual(String id) {
        return codigoUnico.equals(id);
    }

    public String getNombre() {
        return this.nombre;
    }


    public String getNumeroCelular() {
        return this.numeroCelular;
    }


    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

   

    public String getNumeroCuentaBancaria() {
        return this.numeroCuentaBancaria;
    }

    public String getTipoCuentaBancaria() {
        return this.tipoCuentaBancaria;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public Persona(String nombres, String numeroCelular, String numeroDocumento,
            String numeroCuentaBancaria, String tipoCuentaBancaria, String id) {
        this.nombre = nombres;
        this.numeroCelular = numeroCelular;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuentaBancaria = numeroCuentaBancaria;
        this.tipoCuentaBancaria = tipoCuentaBancaria;
        this.codigoUnico = id; // Genera el código único.
    }


    @Override
    public String toString() {
        return String.join("|", nombre, numeroCelular, numeroDocumento,
                numeroCuentaBancaria, tipoCuentaBancaria, codigoUnico);
    }
}
