package co.edu.uptc.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class PanelInventario extends JPanel {

    JTextArea txInformacion;

    public PanelInventario(Evento evento) {
        setBorder(new TitledBorder("Linea Texto de Inventario:"));
        txInformacion = new JTextArea(60, 30);

        JButton accion1 = new JButton(Evento.CARGAR);
        accion1.addActionListener(evento);
        accion1.setActionCommand(Evento.CARGAR);

        setLayout(new BorderLayout());
        add(txInformacion, BorderLayout.CENTER);
        add(accion1, BorderLayout.SOUTH);
    }

    public String obtenerDatos() {
        return txInformacion.getText();
    }

  
    public String generarInforme() {
        String datos = obtenerDatos().trim();
        if (datos.isEmpty()) {
            return "No hay datos de inventario para generar el informe.";
        }

        String[] lineas = datos.split("\n");
        int totalItems = lineas.length;
        int totalCantidad = 0;

        for (String linea : lineas) {
            String[] columnas = linea.split("\\|"); 
            if (columnas.length >= 5) { 
                try {
                    totalCantidad += Integer.parseInt(columnas[4].trim()); 
                } catch (NumberFormatException e) {
         
                    continue;
                }
            }
        }

        return "Total de items: " + totalItems + "\n" +
               "Cantidad total en inventario: " + totalCantidad;
    }

    public Object generarReporteInventario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generarReporteInventario'");
    }
}
