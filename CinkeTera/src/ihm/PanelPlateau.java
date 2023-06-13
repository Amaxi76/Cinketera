package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import java.util.Arrays;
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

	/** Notre liste de régions présents dans notre graph 
	 * 
	 */
	private List<Region> lstRegions;

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
		this.lstRegions                 = this.ctrl.getRegions       ( );

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


		//Présenter les régions
		this.dessinerRegions(g2);
		
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

			if (voieMaritime == this.voieMaritimeAColorier )
			{
				g2.setColor(Color.YELLOW);
				g2.setStroke(new BasicStroke(7));
			}
			else
			{
				Color arcColor = voieMaritime.getColorArc ( ) == null ? Color.BLACK : voieMaritime.getColorArc ( );
				g2.setColor  ( arcColor );
				g2.setStroke ( new BasicStroke ( voieMaritime.getColorArc ( ) == null ? 5 : 7 ) );	//Dessine les arcs coloriés avec un stroke plus épais
			}
			
			g2.drawLine ( (int)(depart.getPosX ( )*rX), (int)(depart.getPosY ( )*rY), (int)(arrivee.getPosX ( )*rX), (int)(arrivee.getPosY ( )*rY));
			
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

	public void dessinerRegions(Graphics2D g2)
	{
		
		g2.setColor(Color.BLACK);
		for (Region region : this.lstRegions) 
		{
			int nbIles = region.getNbIle();

			int minX = region.getEnsIles().get(0).getPosXImage();
			int minY = region.getEnsIles().get(0).getPosYImage();
			int maxX = region.getEnsIles().get(0).getPosXImage();
			int maxY = region.getEnsIles().get(0).getPosYImage();

			for (int cpt = 1; cpt < nbIles; cpt++) 
			{
				Ile ile = region.getEnsIles().get(cpt);

				minX = Math.min(minX,ile.getPosXImage())-40;
				minY = Math.min(minY,ile.getPosYImage())-40;

				maxX = Math.max(maxX,ile.getPosXImage())+50;
				maxY = Math.max(maxY,ile.getPosYImage())+50;
			}
			
			g2.drawOval( minX, minY, maxX-minX, maxY-minY);

			List<Ile> boundary = region.getEnsIles(); 
			int[] xPoints = boundary.stream().mapToInt(Point::getX).toArray();
			int[] yPoints = boundary.stream().mapToInt(Point::getY).toArray();
			int numPoints = boundary.size();
			g2.drawPolygon(xPoints, yPoints, numPoints);

			// Dessine la bordure de la région
			g.setColor(Color.BLACK);
			List<Point> boundary = region.getBoundaryPoints();
			int[] xPoints = boundary.stream().mapToInt(Point::getX).toArray();
			int[] yPoints = boundary.stream().mapToInt(Point::getY).toArray();
			int numPoints = boundary.size();
			g.drawPolygon(xPoints, yPoints, numPoints);
		};

		
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
		System.out.println("x : " + e.getX());
		System.out.println("y : " + e.getY());
		
		for (VoieMaritime voieMaritime : this.lstVoiesMaritimes) 
		{
			Ile ileD = voieMaritime.getIleD ( );
			Ile ileA = voieMaritime.getIleA ( );

			Line2D line = new Line2D.Double ( ileD.getPosX ( )*rX,ileD.getPosY ( )*rY,ileA.getPosX ( )*rX,ileA.getPosY ( )*rY );	

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
				this.frame.majFrameCarte();

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