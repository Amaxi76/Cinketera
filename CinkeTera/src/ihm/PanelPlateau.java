package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import java.util.List;
import java.awt.geom.*;
import javax.swing.JOptionPane;

import controleur.*;
import metier.*;

public class PanelPlateau extends JPanel implements MouseListener
{

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/** Notre liste d'arcs présents dans notre graph 
	 * 
	 */
	private List<VoieMaritime>   lstVoiesMaritimes;

	/** Notre liste de noeuds présents dans notre graph 
	 * 
	 */
	private List<Ile> lstIles;

	/**Un boolean pour dire si il y avait un arc seléctionné ou pas 
	 * 
	 */
	private boolean selectionne;

	private Color clrJ1;


	private double rX;
	private double rY;

	/**
	 * L'arc a colorier
	 */
	private VoieMaritime voieMaritimeAColorier;

	private FramePlateau frame;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */
	public PanelPlateau ( Controleur ctrl , FramePlateau frame)
	{
		this.ctrl 						= ctrl;
		this.frame                      = frame;

		this.clrJ1 = this.ctrl.getCouleurJ1();

		this.rX = this.rY = 1;

		this.setBackground(new Color(172,209,232)) ;

		this.lstVoiesMaritimes 			= this.ctrl.getVoiesMaritimes( );
		this.lstIles					= this.ctrl.getIle			 ( );	

		this.selectionne  				= false;
		this.voieMaritimeAColorier 		= null;
		this.addMouseListener ( this );
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		//Dessiner le graph
		this.dessinerArcs  ( g2,this.rX,this.rY );
		this.dessinerIles  ( g2,this.rX,this.rY );
	}

	/** Méthode qui dessine les arcs de la liste
	 * @param g2 de type Graphics2D
	 */
	private void dessinerArcs ( Graphics2D g2,double rX,double rY )
	{
		for ( VoieMaritime voieMaritime : lstVoiesMaritimes)
		{
			Ile depart  = voieMaritime.getIleD ( );
			Ile arrivee = voieMaritime.getIleA ( );
			
			g2.drawLine ( depart.getPosX ( ), depart.getPosY ( ), arrivee.getPosX ( ), arrivee.getPosY ( ));
				
		}
	}

	/** Méthode qui dessine les noeuds de la liste
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerIles ( Graphics2D g2,double rX,double rY )
	{
		
		for ( Ile ile : this.lstIles )
		{
			Image imageIle = this.getToolkit().getImage ("donnees/images/" + ile.getNom() +".png");
			g2.drawImage(imageIle,(int)(ile.getPosXImage()*rX),(int)(ile.getPosYImage()*rY),(int)(imageIle.getWidth(null)*rX),(int)(imageIle.getHeight(null)*rY), null);

		}


		/*Rouge = tico et Bleu  = Mutaa*/
		g2.setColor(Color.WHITE);
		for ( Ile ile : this.lstIles )
		{	
			if( (this.clrJ1 == Color.RED  && ile.getNom().equals("Ticó")) ||
				(this.clrJ1 == Color.BLUE && ile.getNom().equals("Mutaa")))
			{
				g2.setColor(clrJ1);
			}

			g2.drawString(ile.getNom(),(int)((ile.getPosX()-20)*rX),(int)(ile.getPosY()*rY));
			g2.setColor(Color.WHITE);
		}
			

		g2.setColor(Color.BLACK);

		this.repaint();
	}

	/** Métode qui permet de savoir si un arc est sélectionné
	 * @return return vrai si il y aun arc selectionné
	 * 
	 */
	public boolean estSelectionne() 
	{
		return selectionne;
	}

	/** Méthode qui permet de récupérer l'arc sélectionné
	 * @return Arc sélectionné
	 * 
	 */
	public VoieMaritime getVoieMaritimeAColorier() 
	{
		return this.voieMaritimeAColorier;
	}

	public void setRatios(double rX, double rY)
	{
		this.rX = rX;
		this.rY = rY;
		this.repaint();
	}

	public void mouseClicked(MouseEvent e) 
	{
		
		for (VoieMaritime voieMaritime : this.lstVoiesMaritimes) 
		{
			Ile ileD = voieMaritime.getIleD ( );
			Ile ileA = voieMaritime.getIleA ( );

			Line2D line = new Line2D.Double(ileD.getPosX(),ileD.getPosY(),ileA.getPosX(),ileA.getPosY());	

			if (line.intersects ( e.getX ( ),e.getY ( ),10,10 ) ) //Si on clique bien sur un arc
			{
				this.voieMaritimeAColorier = voieMaritime;
				if (!this.ctrl.jouer(voieMaritime)) 
				{
					System.out.println("peut jouer : false");
					JOptionPane.showMessageDialog ( this.frame,"Erreur de sélection", "Erreur", JOptionPane.ERROR_MESSAGE ); //Affiche que la sélection est mauvaise
				}
				this.repaint();
				this.voieMaritimeAColorier = null;

				return;
			}
				
			
		}
		this.repaint();
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}