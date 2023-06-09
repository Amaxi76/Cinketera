package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;

import controleur.*;

public class PanelAccueil extends JPanel implements ActionListener
{
	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/**
	 * Une frame pour pouvoir accéder a la Frame parent
	 */
	private FrameAccueil  frame;

	/**
	 * Une frame pour pouvoir accéder a la Frame parent
	 */
	private JButton  btn1J;

	/**
	 * Une frame pour pouvoir accéder a la Frame parent
	 */
	private JButton  btn2J;

	/**
	 * Une frame pour pouvoir accéder a la Frame parent
	 */
	private JButton  btnScenario;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */
	public PanelAccueil ( Controleur ctrl , FrameAccueil frame)
	{
		this.ctrl	= ctrl;
		this.frame	= frame;

		this.setBackground(new Color(172,209,232)) ;
		this.setLayout(new GridLayout(3,1,15,15));

		this.btn1J = new JButton("1 Joueur");
		this.btn2J = new JButton("2 Joueur");

		this.btnScenario = new JButton("Scénario");

		this.add(this.btn1J);
		this.add(this.btn2J);
		this.add(this.btnScenario);

		this.btn1J.addActionListener(this);
		this.btn2J.addActionListener(this);
		this.btnScenario.addActionListener(this);
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource() == this.btn1J)
			this.ctrl.lancerFrame();
		if (e.getSource() == this.btn2J)
			System.out.println("J2");
		if (e.getSource() == this.btnScenario)
			System.out.println("Scénario");

		this.frame.cacher();
	}
}