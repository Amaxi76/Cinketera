package ihm;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.*;
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
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		this.setTitle    ( "Cartes" );
		this.setUndecorated(true);
		this.ctrl = ctrl;

		this.setLocation(0,920);
		this.setSize((int)tailleEcran.getWidth(),(int) tailleEcran.getHeight() - 920);


		//Mettre un icone peu utile en image de logiciel
		Image icon = Toolkit.getDefaultToolkit().getImage("donnees/imagesCartes/dos_carte.png");  
    	this.setIconImage(icon); 
		
		
		/*Placement des composants*/

		this.panelCarte = new PanelCarte(this.ctrl,this);

		this.add(this.panelCarte);

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );

		SwingUtilities.updateComponentTreeUI(this);
	}

	public void majCartes()
	{
		this.panelCarte.repaint();
	}
}