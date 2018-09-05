package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.math.plot.Plot2DPanel;

import algoritmoGenetico.AlgoritmoGenetico;
import modelo.cromosoma.*;
import modelo.cruce.*;
import modelo.mutacion.*;
import modelo.reproduccion.*;
import modelo.seleccion.*;

import java.awt.Color;

import javax.swing.JTextArea;

public class Gui extends JFrame{
	private static final long serialVersionUID = 1L;
	
	// Atributos de la interfaz
	private JPanel contentPane;
	private JTextField text_iteraciones;
	private JTextField text_poblacion;
	private JTextField text_cruce;
	private JTextField text_mutacion;
	private JTextField text_precision;
	private JComboBox<String> functionComboBox;
	private JComboBox<String> cruceComboBox;
	private JComboBox<String> mutacionComboBox;
	private JComboBox<String> selectionComboBox;
	private JComboBox<String> reproduccionComboBox;
	private JCheckBox elitismoCheckBox;
	private JTabbedPane tabbedPane;
	private JPanel panel_resultado;
	private JTextArea textAreaResultado;
	
	public Gui() {
		
		super("Programacion Evolutiva - Practica 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(900,700));	
		setBounds(100, 100, 874, 651);
		
		// Configurar componentes de la interfaz
		iniciar();
	}
	
	private void iniciar() {
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		JPanel panel_oeste = new JPanel();
		contentPane.add(panel_oeste, BorderLayout.WEST);
		panel_oeste.setLayout(new GridLayout(12, 1, 0, 0));
		
		//--------------- Panel tipo función ------------------\\
		JPanel panel_funcion = new JPanel();
		panel_funcion.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_5 = (FlowLayout) panel_funcion.getLayout();
		flowLayout_5.setAlignment(FlowLayout.TRAILING);
		flowLayout_5.setVgap(15);
		panel_oeste.add(panel_funcion);
		
		JLabel label_funcion = new JLabel("Funcion");
		label_funcion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_funcion.add(label_funcion);
		
		functionComboBox = new JComboBox<String>();
		functionComboBox.setPreferredSize(new Dimension(155, 26));
		functionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		functionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "12", "15", "30"}));
		functionComboBox.setSelectedIndex(0);
		panel_funcion.add(functionComboBox);
		
		
		//--------------- Panel tipo seleccion ------------------\\
		JPanel panel_seleccion = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_seleccion.getLayout();
		flowLayout_7.setAlignment(FlowLayout.TRAILING);
		flowLayout_7.setVgap(15);
		panel_seleccion.setBackground(new Color(0, 191, 255));
		panel_oeste.add(panel_seleccion);
				
				
		JLabel lblSeleccion = new JLabel("Seleccion");
		lblSeleccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_seleccion.add(lblSeleccion);
		
		selectionComboBox = new JComboBox<String>();
		selectionComboBox.setPreferredSize(new Dimension(155, 26));
		selectionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Ruleta", "Torneo", "Torneo Probabilistico", "Estocastico Universal"}));
		selectionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_seleccion.add(selectionComboBox);
		
		
		
		//--------------- Panel tipo cruce ------------------\\
		JPanel panel_selec_cruce = new JPanel();
		panel_selec_cruce.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_cruce = (FlowLayout) panel_selec_cruce.getLayout();
		flowLayout_cruce.setAlignment(FlowLayout.TRAILING);
		flowLayout_cruce.setVgap(15);
		panel_oeste.add(panel_selec_cruce);
			
		JLabel label_selec_cruce = new JLabel("Tipo de cruce");
		label_selec_cruce.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_selec_cruce.add(label_selec_cruce);
		
		cruceComboBox = new JComboBox<String>();
		cruceComboBox.setPreferredSize(new Dimension(155, 26));
		cruceComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cruceComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"OX", "PMX", "Ciclos", "OXord", "OXpos", "RecRutas", "CodOrdinal", "Propio"}));
		cruceComboBox.setSelectedIndex(0);
		panel_selec_cruce.add(cruceComboBox);
		
		
		
		//--------------- Panel tipo mutacion ------------------\\
		
		JPanel panel_selec_mutacion = new JPanel();
		panel_selec_mutacion.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_mutacion = (FlowLayout) panel_selec_mutacion.getLayout();
		flowLayout_mutacion.setAlignment(FlowLayout.TRAILING);
		flowLayout_mutacion.setVgap(15);
		panel_oeste.add(panel_selec_mutacion);
		
		JLabel label_selec_mutacion = new JLabel("Tipo de mutación");
		label_selec_mutacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_selec_mutacion.add(label_selec_mutacion);
		
		mutacionComboBox = new JComboBox<String>();
		mutacionComboBox.setPreferredSize(new Dimension(155, 26));
		mutacionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mutacionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Inversion", "Intercambio", "Insercion", "Heuristica", "Propia"}));
		mutacionComboBox.setSelectedIndex(0);
		panel_selec_mutacion.add(mutacionComboBox);
		
		
		
		//--------------- Panel tipo reproducción ------------------\\
		JPanel panel_reproduccion = new JPanel();
		FlowLayout flowLayout_reproc = (FlowLayout) panel_reproduccion.getLayout();
		flowLayout_reproc.setAlignment(FlowLayout.TRAILING);
		flowLayout_reproc.setVgap(15);
		panel_reproduccion.setBackground(new Color(0, 191, 255));
		panel_oeste.add(panel_reproduccion);
		
		
		JLabel lblReproduccion = new JLabel("Tipo de reproduccion");
		lblReproduccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_reproduccion.add(lblReproduccion);
		
		reproduccionComboBox = new JComboBox<String>();
		reproduccionComboBox.setPreferredSize(new Dimension(155, 26));
		reproduccionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Reproduccion Estandar"}));
		reproduccionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_reproduccion.add(reproduccionComboBox);
		
		//--------------- Panel población ------------------\\
		JPanel panel_poblacion = new JPanel();
		panel_poblacion.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout = (FlowLayout) panel_poblacion.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		flowLayout.setVgap(15);
		panel_oeste.add(panel_poblacion);
		
		JLabel label_poblacion = new JLabel("Poblacion");
		label_poblacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_poblacion.add(label_poblacion);
		
		text_poblacion = new JTextField();
		text_poblacion.setText("100");
		text_poblacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_poblacion.add(text_poblacion);
		text_poblacion.setColumns(10);
		
		//--------------- Panel iteraciones ------------------\\
		JPanel panel_iteraciones = new JPanel();
		panel_iteraciones.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_1 = (FlowLayout) panel_iteraciones.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		flowLayout_1.setVgap(15);
		panel_oeste.add(panel_iteraciones);
		
		JLabel label_iteraciones = new JLabel("Iteraciones");
		label_iteraciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_iteraciones.add(label_iteraciones);
		
		text_iteraciones = new JTextField();
		text_iteraciones.setText("100");
		text_iteraciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_iteraciones.add(text_iteraciones);
		text_iteraciones.setColumns(10);
		
		//--------------- Panel probabilidad de cruce ------------------\\
		JPanel panel_cruce = new JPanel();
		panel_cruce.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_2 = (FlowLayout) panel_cruce.getLayout();
		flowLayout_2.setAlignment(FlowLayout.TRAILING);
		flowLayout_2.setVgap(15);
		panel_oeste.add(panel_cruce);
		
		JLabel label_cruce = new JLabel("% cruce");
		label_cruce.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_cruce.add(label_cruce);
		
		text_cruce = new JTextField();
		text_cruce.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text_cruce.setText("0.6");
		panel_cruce.add(text_cruce);
		text_cruce.setColumns(10);
		
		//--------------- Panel probabilidad de mutación ------------------\\
		JPanel panel_mutacion = new JPanel();
		panel_mutacion.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_3 = (FlowLayout) panel_mutacion.getLayout();
		flowLayout_3.setAlignment(FlowLayout.TRAILING);
		flowLayout_3.setVgap(15);
		panel_oeste.add(panel_mutacion);
		
		JLabel label_mutacion = new JLabel("% mutacion");
		label_mutacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_mutacion.add(label_mutacion);
		
		text_mutacion = new JTextField();
		text_mutacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text_mutacion.setText("0.05");
		panel_mutacion.add(text_mutacion);
		text_mutacion.setColumns(10);
		
		//--------------- Panel precisión ------------------\\
		JPanel panel_precision = new JPanel();
		panel_precision.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_4 = (FlowLayout) panel_precision.getLayout();
		flowLayout_4.setAlignment(FlowLayout.TRAILING);
		flowLayout_4.setVgap(15);
		panel_oeste.add(panel_precision);
		
		JLabel label_precision = new JLabel("Precision");
		label_precision.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_precision.add(label_precision);
		
		text_precision = new JTextField();
		text_precision.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text_precision.setText("0.001");
		panel_precision.add(text_precision);
		text_precision.setColumns(10);
		
		//--------------- Panel elitismo ------------------\\
		JPanel panel_elitismo = new JPanel();
		FlowLayout fl_panel_elitismo = (FlowLayout) panel_elitismo.getLayout();
		fl_panel_elitismo.setVgap(15);
		panel_elitismo.setBackground(new Color(0, 191, 255));
		panel_oeste.add(panel_elitismo);
		
		elitismoCheckBox = new JCheckBox("Elitismo");
		elitismoCheckBox.setBackground(new Color(0, 191, 255));
		elitismoCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		elitismoCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_elitismo.add(elitismoCheckBox);
		
		
		JPanel panel_LanzarAG = new JPanel();
		panel_LanzarAG.setBackground(new Color(0, 191, 255));
		FlowLayout flowLayout_6 = (FlowLayout) panel_LanzarAG.getLayout();
		flowLayout_6.setVgap(15);
		panel_oeste.add(panel_LanzarAG);
		
		JButton lanzarAGButton = new JButton("Comenzar");
		lanzarAGButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		panel_LanzarAG.add(lanzarAGButton);
		lanzarAGButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lanzarAG();
			}
		});
		
		panel_resultado = new JPanel();
		panel_resultado.setBackground(SystemColor.info);
		contentPane.add(panel_resultado, BorderLayout.NORTH);
		
		panel_resultado.setLayout(new BorderLayout(0, 0));
		textAreaResultado = new JTextArea("Resultado");
		textAreaResultado.setEditable(false);
		textAreaResultado.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_resultado.add(textAreaResultado);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(50, 50));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void run() {
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void lanzarAG() {
		int t = getPopulationField();
		int g = getIterationField();
		double pc = getCrossProbability();
		double pm = getMutationProbability();
		boolean elitismo = getElitismField();
		Seleccion sel = getSelMethodField();
		Mutacion mut = getMutMethodField();
		Cruce crz = getCrzMethodField();
		Reproduccion rep = getReprocMethodField();
		AlgoritmoGenetico AG;
		
		AG = new AlgoritmoGenetico(t,g,pc,pm,sel, elitismo, selecFuncion(), mut, crz, rep);
		
		Cromosoma c = AG.ejecuta();
		mostrarGraficas(AG, c);
		setResult(c.toString());
	}
	
	private boolean getElitismField() {
		return elitismoCheckBox.isSelected();
	}

	private String selecFuncion(){
		String funcion = getFunctionField();
		
		switch(funcion) {
			case "12":
				return "datos12.dat";
			case "15":
				return "datos15.dat";
			case "30":
				return "datos30.dat";
			case "5":
				return "ajuste.dat";
			default:
				return "ajuste.dat";	// Default
		}
	}
	private Seleccion getSelMethodField() {
		String m = (String) selectionComboBox.getSelectedItem();
		
		switch(m) {
		case "Ruleta":
			return new SeleccionRuleta();
		case "Torneo":
			return new SeleccionTorneo();
		case "Torneo Probabilistico":
			return new SeleccionTorneoProb();
		case "Estocastico Universal":
			return new SeleccionEstocUniv();
		default:
			return new SeleccionRuleta();	// Default
		}
	}
	
	private Mutacion getMutMethodField() {
		String m = (String) mutacionComboBox.getSelectedItem();
		
		switch(m) {
			case "Inverison":
				return  new MutacionInverison();
			case "Intercambio":
				return new MutacionIntercambio();
			case "Insercion":
				return new MutacionInsercion();
			case "Heuristica":
				return new MutacionHeuristica();
			case "Propia":
				return new MutacionPropia();
			default:
				return new MutacionInverison();	// Default
		}
	}

	private Cruce getCrzMethodField() {
		String m = (String) cruceComboBox.getSelectedItem();
		
		switch(m) {
		case "Punto a punto":
			return new CrucePtoAPto();
		case "PMX":
			return new CrucePMX();
		case "Ciclos":
			return new CruceCiclos();
		case "OX":
			return new CruceOX();
		case "OXpos":
			return new CruceOXposPrior();
		case "OXord":
			return new CruceOXordPrior();
		case "RecRutas":
			return new CruceRecRutas();
		case "CodOrdinal":
			return new CruceCodOrdinal();
		case "Propio":
			return new CrucePropio();
		default:
			return new CruceOX();	// Default
		}
	}
	
	private Reproduccion getReprocMethodField() {
		return new ReproduccionEstandar();
	}
	
	private void mostrarGraficas(AlgoritmoGenetico AG, Cromosoma c) {
		int n_iter = this.getIterationField();  
		
		double[] generaciones = new double[n_iter];
		
		for(int i = 0; i < n_iter; i++) {
			generaciones[i] = i+1;
		}
		
		double[] mejorGlobal = AG.getMejorGlobal();
		double[] mejorPorGeneracion = AG.getMejorPorGeneracion();
		double[] mediaAptitud = AG.getMediaAptitud();
		
		Plot2DPanel p = new Plot2DPanel();
		
		p.addLinePlot("Mejor absoluto", generaciones, mejorGlobal);
		p.addLinePlot("mejor de la generacion",generaciones, mejorPorGeneracion);
		p.addLinePlot("Media de la generacion", generaciones, mediaAptitud);
		
		p.addLegend("SOUTH");
		
		p.setAxisLabel(0, "Generaciones");
		p.setAxisLabel(1, "Evaluacion");
		
		Integer count = tabbedPane.getTabCount() + 1;
		tabbedPane.addTab("(" + count.toString() + ") " + c.getAptitud(), p);
		
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	public String getFunctionField() {
		return (String) functionComboBox.getSelectedItem();
	}

	public double getToleranceField() {
		return Double.parseDouble(text_precision.getText());
	}

	public int getPopulationField() {
		return Integer.parseInt(text_poblacion.getText());
	}

	public int getIterationField() {
		return Integer.parseInt(text_iteraciones.getText());
	}

	public double getCrossProbability() {
		return Double.parseDouble(text_cruce.getText());
	}

	public double getMutationProbability() {
		return Double.parseDouble(text_mutacion.getText());
	}
	
	public void setResult(String r) {
		textAreaResultado.setText(r);
	}
}
