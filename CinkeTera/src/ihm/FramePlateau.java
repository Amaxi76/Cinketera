package ihm;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import controleur.*;
import metier.*;

public class FramePlateau extends JFrame implements ComponentListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un controleur pour la frame
	 */
	private Controleur    ctrl;

	/**
	 * PanelPlateau pour la frame
	 */
	private PanelPlateau  panelPlateau;

	/**
	 * FrameCarte pour la frame
	 */
	private FrameCarte   frameCarte;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FramePlateau qui crée un panelPlateau et frameCarte
	 * @param ctrl le controleur
	 * 
	 */
	public FramePlateau ( Controleur ctrl)
	{
		this.ctrl = ctrl;

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		this.setSize((int)tailleEcran.getWidth(),900);
		this.setLocation(0,0);
		this.setUndecorated(true);
		this.setTitle    ( "CinkeTera" );

		//Mettre un icone peu utile en image de logiciel
		Image icon = Toolkit.getDefaultToolkit().getImage("donnees/images/boat.png");  
    	this.setIconImage(icon); 

		this.frameCarte = new FrameCarte(ctrl);

		/*Création des composants*/

		this.panelPlateau  = new PanelPlateau  ( this.ctrl, this );

		/*Placement des composants*/
		this.add ( this.panelPlateau );

		this.addComponentListener(this);
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );
	}

	/**
	 * Mise a jour de la frame de cartes 
	 */
	public void majFrameCarte()
	{
		this.frameCarte.majCartes();
	}
	
	/** Getteur qui retourne l'VoieMaritime à colorier
	 * @return l'VoieMaritime à colorier
	 */
	public VoieMaritime getVoieMaritimeAColorier ( ) { return this.panelPlateau.getVoieMaritimeAColorier ( ); }

	public void majIHM() {this.panelPlateau.repaint();}

	public void componentHidden (ComponentEvent e) {}
	public void componentResized(ComponentEvent e)
	{
		double ratioX = this.getWidth()/java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ratioY = this.getHeight()/java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.panelPlateau.setRatios( ratioX , ratioY);
	}
	public void componentShown  (ComponentEvent e) {}

	public void componentMoved  (ComponentEvent e) {}

	public void init()
	{
		this.frameCarte.dispose();
		this.dispose();
	}
}