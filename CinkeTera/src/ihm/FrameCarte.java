package ihm;

import javax.swing.JFrame;
import java.awt.Color;

import controleur.*;

public class FrameCarte extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

    private PanelCarte  panelCarte;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameCarte qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameCarte ( Controleur ctrl )
	{
		this.setTitle    ( "Cartes" );

		this.ctrl = ctrl;

		this.setLocation(0,800);
		this.setSize(800,200);
		/*Placement des composants*/

		this.panelCarte = new PanelCarte(this.ctrl);

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );
	}
}