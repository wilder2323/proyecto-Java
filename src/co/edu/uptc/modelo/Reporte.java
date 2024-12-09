package co.edu.uptc.modelo;

public class Reporte {
    private Persona persona;
    private int cantidad;
    private double comision;

    public Reporte(Persona p, int cantidad, double comision) {
        this.persona = p;
        this.cantidad = cantidad;
        this.comision = comision;
    }

    public boolean esIdIgual(String id){
        return persona.esIdIgual(id);
    }

    public void agregarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public void agregarComision(double comision) {
        this.comision += comision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        sb.append("Id :").append(persona.getCodigoUnico()).append("\n");
        sb.append("Nombre :").append(persona.getNombre()).append("\n");
        sb.append("# Cuenta:").append(persona.getNumeroCuentaBancaria()).append("\n");
        sb.append("Tipo de Cuenta:").append(persona.getTipoCuentaBancaria()).append("\n");
        sb.append("Total Comision:").append(comision).append("\n");
        sb.append("Total celulares:").append(cantidad).append("\n");

        return sb.toString();
    }
}