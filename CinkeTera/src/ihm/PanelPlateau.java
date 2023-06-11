package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import java.util.List;
import java.awt.geom.*;
import java.nio.file.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controleur.*;
import metier.*;

public class PanelPlateau extends JPanel implements MouseListener 
{

	/** Un tableau de Couleurs pour colorier les regions
	 *
	 */
	private Color[] couleurs = {Color.BLACK,Color.BLUE,Color.GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.YELLOW,Color.RED,Color.PINK}; 

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
		this.lstVoiesMaritimes 			= this.ctrl.getVoiesMaritimes( );
		this.lstIles					= this.ctrl.getIle			 ( );

		this.setBackground(new Color(172,209,232)) ;
		

		//this.actif1       				= this.actif2 = null;

		this.selectionne  				= false;
		this.voieMaritimeAColorier 		= null;
		this.addMouseListener ( this );
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		//Dessiner le graph
		this.dessinerArcs           ( g2 );
		this.dessinerIles           ( g2 );
		//this.dessinerNoeudsActifs   ( g2 );
		//this.dessinerArcSelectionne ( g2 );
	}

	/** Méthode qui dessine les arcs de la liste
	 * @param g2 de type Graphics2D
	 */
	private void dessinerArcs ( Graphics2D g2 )
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
			
			g2.drawLine ( depart.getPosX ( ), depart.getPosY ( ), arrivee.getPosX ( ), arrivee.getPosY ( ));
			
		}
	}

	/** Méthode qui dessine les noeuds de la liste
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerIles ( Graphics2D g2 )
	{
		for ( Ile ile : this.lstIles )
		{
			Image imageIle = this.getToolkit().getImage ("donnees/images/" + ile.getNom() +".png");
			g2.drawImage(imageIle,ile.getPosXImage(),ile.getPosYImage(), null);
		}
		this.repaint();
	}

	/** Méthode qui dessine l'arc sélectionné en rouge
	 * @param g2 de type Graphics2D
	 * 
	 *//*
	private void dessinerArcSelectionne(Graphics2D g2) 
	{
		if (actif1 != null && actif2 != null) 
		{
			VoieMaritime a = ctrl.arcEntre(actif1, actif2);

			if (a != null) {
				Ile depart	= a.getIleD();
				Ile arrivee = a.getIleA();
				g2.drawLine(depart.getPosX(), depart.getPosY() - 100, arrivee.getPosX(), arrivee.getPosY() - 100);

				dessinerNoeud(g2, actif1, Color.RED);
				dessinerNoeud(g2, actif2, Color.RED);

				selectionne = true;
				voieMaritimeAColorier = a;
			} 
			else
			{
				reset(g2); 
			}
			   
			
		}
	}*/

	/** Méthode qui reset les noeuds sélectionnés (sélection)
	 * @param g2 de type Graphics2D
	 *//*
	public void reset(Graphics2D g2) 
	{
		dessinerNoeud(g2, actif1, Color.BLACK);
		dessinerNoeud(g2, actif2, Color.BLACK);
		this.selectionne = false;
		this.voieMaritimeAColorier = null;
		actif1 = null;
		actif2 = null;
		repaint();

		//Pour afficher message d'erreur quand on sélectionne deux noeuds non adjacents
		SwingUtilities.invokeLater(() -> 
		{
			JOptionPane.showMessageDialog(this,"Transgression de contrainte, essaie autre chose bg", "Erreur", JOptionPane.ERROR_MESSAGE);
		});
	}*/

	/** Reset les noeuds sélectionnés
	 * 
	 *//*
	public void resetSelect() 
	{
		actif1 = null;
		actif2 = null;
		repaint();
	}*/

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

	public void mouseClicked(MouseEvent e) 
	{
		
		for (VoieMaritime voieMaritime : this.lstVoiesMaritimes) 
		{
			Ile ileD = voieMaritime.getIleD ( );
			Ile ileA = voieMaritime.getIleA ( );

			Line2D line = new Line2D.Double ( ileD.getPosX ( ),ileD.getPosY ( ),ileA.getPosX ( ),ileA.getPosY ( ) );	

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