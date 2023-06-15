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
	public FramePlateau ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		Image icon = Toolkit.getDefaultToolkit ( ).getImage ( "donnees/images/boat.png" );

		this.setTitle    ( "CinkeTera"                                                                    );
		this.setSize     ( ( int ) tailleEcran.getWidth ( ), ( int ) ( tailleEcran.getHeight ( ) * 0.80 ) );
		this.setLocation ( 0                               , 0                                            );

		/*Création des composants*/
		this.frameCarte    = new FrameCarte   ( this.ctrl, this );
		this.panelPlateau  = new PanelPlateau ( this.ctrl, this );

		/*Placement des composants*/
		this.add ( this.panelPlateau );

		/* Activation des composants */
		this.addComponentListener     ( this );

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setUndecorated           ( true          );
		this.setVisible               ( true          );
		this.setIconImage             ( icon          ); 
	}

	/**
	 * Mise a jour de la frame de cartes 
	 */
	public void majFrameCarte ( ) { this.frameCarte.majCartes ( ); }
	
	/** Getteur qui retourne l'VoieMaritime à colorier
	 * @return l'VoieMaritime à colorier
	 */
	public VoieMaritime getVoieMaritimeAColorier ( ) { return this.panelPlateau.getVoieMaritimeAColorier ( ); }

	public void majIHM ( ) { this.panelPlateau.repaint ( ); }

	
	public void componentResized ( ComponentEvent e )
	{
		double ratioX = this.getWidth  ( ) / java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getWidth  ( );
		double ratioY = this.getHeight ( ) / java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getHeight ( );
		
		this.panelPlateau.setRatios ( ratioX , ratioY );
	}

	public void componentHidden ( ComponentEvent e ) { }
	public void componentShown  ( ComponentEvent e ) { }
	public void componentMoved  ( ComponentEvent e ) { }

	public void init ( )
	{
		this.frameCarte.dispose ( );
		this           .dispose ( );
	}

	public void finPartieInit ( )
	{
		String formatString = "%-30s";
		String sRet         = "";

		sRet += String.format ( formatString ,"Nb regions visitées : " ) + this.ctrl.getNbRegionsVisite ( )                                        + "\n";
		sRet += String.format ( formatString ,"Nb arcs colorées    : " ) + this.ctrl.getJoueur1 ( ).getPlateau ( ).getNbVoiesMaritimesColorie  ( ) + "\n";
		sRet += String.format ( formatString ,"Nb Points Total     : " ) + this.ctrl.calculerScore ( )                                                   ;

		//Création d'une "Pop-up" pour demander si le joueur veux rejouer ou quitter
		Object[] choix = { "Rejouer","Quitter" };
		int      rep   = JOptionPane.showOptionDialog ( this,sRet, "Game End", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0] );

		if ( rep == 0 ) //Si Rejouer est sélectionné
		{
			this.init( );				//On ferme le fenêtre
			new Controleur ( );			//On relance une partie
		}
		else
			System.exit ( 1 );			//On ferme le scripte
}
}