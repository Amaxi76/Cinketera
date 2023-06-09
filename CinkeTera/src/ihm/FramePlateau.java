package ihm;

import javax.swing.JFrame;
import java.awt.*;

import controleur.*;
import metier.*;

public class FramePlateau extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un controleur pour la frame
	 */
	private Controleur    ctrl;

	/**
	 * Une frame pour la frame
	 */
	//private FrameCarte    frameCarte;

	/**
	 * PanelGraph pour la frame
	 */
	private PanelPlateau  panelPlateau;

	/**
	 * PanelAction pour la frame
	 */
	private PanelAction   panelAction;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameGraphe qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FramePlateau ( Controleur ctrl )
	{
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		this.setSize(tailleEcran);
		this.setLocation(50,50);
		this.setTitle    ( "CinkeTera" );

		this.ctrl = ctrl;
		//this.frameCarte = new FrameCarte(this.ctrl);

		/*Création des composants*/

		this.panelPlateau  = new PanelPlateau  ( this.ctrl, this );

		/*Placement des composants*/
		this.add ( this.panelPlateau                     );

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );
	}

	/** Getteur qui retourne l'VoieMaritime à colorier
	 * @return l'VoieMaritime à colorier
	 */
	public VoieMaritime getVoieMaritimeAColorier ( ) { return this.panelPlateau.getVoieMaritimeAColorier ( ); }
	
	/** Getteur qui retourne si le panel est sélectionné
	 * @return true si le panel est sélectionné
	 */
	public boolean estSelectionne  ( ) { return this.panelPlateau.estSelectionne  ( ); }

	public void majIHM() {this.panelPlateau.repaint();}
	
	/** Appel la méthode resetSelect de PanelGraph
	 * 
	 */
	//public void    resetSelect     ( ) {        this.panelPlateau.resetSelect     ( ); }
}