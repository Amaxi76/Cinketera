package ihm;

import javax.swing.JPanel;
import java.awt.*;

import controleur.*;
import metier.*;

public class PanelCarte extends JPanel
{

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur   ctrl;

	private FramePlateau frame;

    private Carte        carteEnCours;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */
	public PanelCarte ( Controleur ctrl)
	{
		this.ctrl   = ctrl;
		this.frame  = frame;

        this.carteEnCours = this.ctrl.getCarteEnCours();

		this.setBackground(new Color(172,209,232)) ;
	}

    public void setCarteEnCours(Carte c) { this.carteEnCours = c; }


	@Override
	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		Image image = this.getToolkit().getImage ("donnees/imagesCartes/" + this.carteEnCours.getTypeCarte() + "_" + this.carteEnCours.getCouleurCarte()   +".png");
		g2.drawImage(image,200,50, null);

	}


}