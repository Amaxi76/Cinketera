package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

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

	/**
	 * Une frame pour pouvoir accéder a la Frame parent
	 */
	private JButton  btnQuitter;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */

	private Image imgFond;

	public PanelAccueil ( Controleur ctrl , FrameAccueil frame)
	{
		this.ctrl	= ctrl;
		this.frame	= frame;

		String lien = "donnees/images/image.png";
		this.imgFond = getToolkit().getImage ( lien );

		this.setLayout(new BorderLayout( ));

		/* ----------------------- */
		/* Création des composants */
		/* ----------------------- */

		JPanel panelImage  = new JPanel ();
		JPanel panelButton = new JPanel ();
		JPanel panelMenu   = new JPanel ();

		this.btn1J       = new JButton("1 Joueur");
		this.btn2J       = new JButton("2 Joueur");
		this.btnScenario = new JButton("Scénario");
		this.btnQuitter  = new JButton("Quitter");

		JLabel lblMenu = new JLabel("Choix du mode de jeux : ");

		/* ---------------------------- */
		/* Postionnement des composants */
		/* ---------------------------- */

		panelButton.add(this.btn1J);
		panelButton.add(this.btn2J);
		panelButton.add(this.btnScenario);
		panelButton.add(this.btnQuitter);

		panelMenu.add(lblMenu);

		this.add(panelButton, BorderLayout.SOUTH  );
		this.add(panelMenu  , BorderLayout.CENTER );
		this.add(panelImage , BorderLayout.NORTH  );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btn1J      .addActionListener(this);
		this.btn2J      .addActionListener(this);
		this.btnScenario.addActionListener(this);
		this.btnQuitter .addActionListener(this);

		panelImage.repaint();
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource() == this.btn1J)
			this.ctrl.lancerFrame();
		if (e.getSource() == this.btn2J)
			this.ctrl.modeDeuxJouers();
		if (e.getSource() == this.btnScenario)
			System.out.println("Scénario");

		this.frame.cacher();
	}

	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        g.drawImage(this.imgFond, 0, 0, getWidth(), getHeight(), this);
    }
}