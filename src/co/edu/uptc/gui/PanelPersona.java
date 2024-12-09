package co.edu.uptc.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class PanelPersona extends JPanel {

	public JTextArea txInformacion;
	public StringBuilder sb;
	
	public PanelPersona(Evento evento) {
		sb = new StringBuilder();
		setBorder(new TitledBorder("Nombre Apellido| Télefono | Identificación | Número de Cuenta | Tipo | Id (VEN(NumeroDeId)"));
		txInformacion = new JTextArea(20, 30);
		
		JButton accion1 = new JButton(Evento.CARGAR_PERSONAS);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR_PERSONAS);
		setLayout(new BorderLayout());	
		
		add(txInformacion, BorderLayout.CENTER);
		add(accion1, BorderLayout.SOUTH);
	}
	
	public String obtenerDatos() {
		return txInformacion.getText();
	}

	public void editarTexto(String texto) {
		sb.append(texto);
	}

	public void actualizarTexto() {
		txInformacion.setText(sb.toString());
	}
}
