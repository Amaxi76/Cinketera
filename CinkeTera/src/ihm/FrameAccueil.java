package ihm;

import javax.swing.JFrame;
import java.awt.Color;

import controleur.*;

public class FrameAccueil extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/**
	 * PanelAcceuil pour la frame
	 */
	private PanelAccueil   panelAccueil;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameAccueil ( Controleur ctrl )
	{
		this.setTitle    ( "CinkeTera" );

		this.ctrl = ctrl;

		/*Placement des composants*/

		this.panelAccueil = new PanelAccueil(ctrl,this);
		this.add(this.panelAccueil);

		this.pack();

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );
	}

	public void cacher() { this.dispose(); }
}