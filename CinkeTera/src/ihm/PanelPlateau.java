package ihm;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.util.List;
import java.awt.geom.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import controleur.*;
import metier.*;

public class PanelPlateau extends JPanel implements MouseListener//,ActionListener
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

	private Color clrJ1;

	private double rX;
	private double rY;

	private BufferedImage  imageBateau;

	/**
	 * L'arc a colorier
	 */
	private VoieMaritime voieMaritimeAColorier;

	private FramePlateau frame;

	/** Constructeur de PanelPlateau
	 * @param ctrl de type Controleur
	 * @throws IOException
	 * 
	 */
	public PanelPlateau ( Controleur ctrl , FramePlateau frame )
	{
		this.ctrl  = ctrl;
		this.frame = frame;
		this.clrJ1 = this.ctrl.getCouleurj1 ( );
		this.rX    = this.rY = 1;

		this.lstVoiesMaritimes     = this.ctrl.getJoueur1 ( ).getPlateau ( ).getVoiesMaritimes ( );
		this.lstIles               = this.ctrl.getJoueur1 ( ).getPlateau ( ).getIles           ( );
		this.voieMaritimeAColorier = null;
		
		this.setBackground ( new Color ( 172, 209, 232 ) );
		this.addMouseListener ( this );
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		//Dessiner le graph
		this.dessinerArcs  ( g2,this.rX,this.rY );
		this.dessinerIles  ( g2,this.rX,this.rY );

		//Dessiner les régions
		this.dessinerRegions ( g2 );

		//Dessiner bateau peut être
		g2.drawImage ( this.imageBateau, 200, 200, this );
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

			if ( voieMaritime != this.voieMaritimeAColorier )
			{
				Color arcColor = voieMaritime.getColorArc ( ) == null ? Color.DARK_GRAY : voieMaritime.getColorArc ( );
				g2.setColor  ( arcColor );
				g2.setStroke ( new BasicStroke ( voieMaritime.getColorArc ( ) == null ? 5 : 7 ) );	//Dessine les arcs coloriés avec un stroke plus épais
			}
			
			g2.drawLine ( ( int ) ( depart.getPosX ( ) * rX ), ( int ) ( depart.getPosY ( ) * rY ), ( int ) ( arrivee.getPosX ( ) * rX ), ( int ) ( arrivee.getPosY ( ) * rY ) );
			
			//Représenter les valeurs des arcs bonus
			if ( voieMaritime.getValeur ( ) != 1 )
			{
				int pointStringY = ( depart.getPosY ( ) - 100 + arrivee.getPosY ( ) - 100 ) / 2 - 5 ;
				int pointStringX = 0;

				if ( depart.getPosX ( ) == arrivee.getPosX ( ) )
					pointStringX = ( depart.getPosX ( ) + arrivee.getPosX ( ) ) / 2 + 5;
				else
					pointStringX = ( depart.getPosX ( ) + arrivee.getPosX ( ) ) / 2;

				g2.drawString ( "" + voieMaritime.getValeur ( ), pointStringX, pointStringY );
			}

			this.frame.majFrameCarte ( );
		}
	}

	/** Méthode qui dessine les noeuds de la liste
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerIles ( Graphics2D g2,double rX,double rY )
	{
		//Avoir la couleur du Joueur
		this.clrJ1 = this.ctrl.getCouleurj1 ( );

		//Avoir les Iles jouables
		List<Ile> lstExtremite = this.ctrl.getJoueur1 ( ).getPartie ( ).getEnsExtremites ( );
		List<Ile> ligneR       = this.ctrl.getJoueur1 ( ).getPartie ( ).getLigneR        ( );
		List<Ile> ligneB       = this.ctrl.getJoueur1 ( ).getPartie ( ).getLigneB        ( );

		for ( Ile ile : this.lstIles )
		{
			BufferedImage imgIle = null;
			try 
			{
				imgIle = ImageIO.read ( new File ( "donnees/images/" + ile.getNom ( ) +".png" ) );
			}
			catch (Exception e) 
			{
				e.printStackTrace ( );
			}
			
			int width    = imgIle.getWidth  (                                     );
			int height   = imgIle.getHeight (                                     );
			int[] pixels = imgIle.getRGB    ( 0, 0, width, height, null, 0, width );

			// Parcourir les pixels et les assombrire pour changer l'image
			for ( int i = 0; i < pixels.length; i++ )
			{
				int rgb   = pixels[i];
				int alpha = ( rgb >> 24 ) & 0xFF;
				int r     = ( rgb >> 16 ) & 0xFF;
				int g     = ( rgb >> 8  ) & 0xFF;
				int b     = rgb & 0xFF;

				r *= 0.50;
				b *= 0.50;
				g *= 0.50;

				pixels[i] = ( alpha << 24 ) | ( r << 16 ) | ( g << 8 ) | b;
			}

			// Créer une nouvelle image avec les pixels modifiés
			BufferedImage modifiedImage = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );
			modifiedImage.setRGB ( 0, 0, width, height, pixels, 0, width );

			//Si c'est une extremité,déja visité ou c'est jouable, on ne change pas d'image
			if ( ( ( ligneR.contains ( ile ) || ligneB.contains ( ile ) ) && lstExtremite.contains ( ile ) || this.ctrl.estJouable ( ile,lstExtremite ) ) ||
			     ( ( ligneR.contains ( ile ) || ligneB.contains ( ile ) ) && lstExtremite.contains ( ile ) || this.ctrl.estJouable ( ile,lstExtremite ) )   )
			     modifiedImage = imgIle;
			
			g2.drawImage ( modifiedImage, ( int ) ( ile.getPosXImage        ( ) * this.rX ),
			                              ( int ) ( ile.getPosYImage        ( ) * this.rY ),
			                              ( int ) ( modifiedImage.getWidth  ( ) * this.rX ),
			                              ( int ) ( modifiedImage.getHeight ( ) * this.rY ), this );
		}

		//Les noms des îles
		for ( Ile ile : this.lstIles )
		{	
			g2.setColor ( Color.WHITE );
			if ( ( this.clrJ1 == Color.RED  && ile.getNom ( ).equals ( "Ticó"  ) ) ||
			     ( this.clrJ1 == Color.BLUE && ile.getNom ( ).equals ( "Mutaa" ) )    )
				g2.setColor ( clrJ1 );

			g2.drawString ( ile.getNom ( ), ( int ) ( ( ile.getPosX ( ) - 20 ) * rX ), ( int ) ( ile.getPosY ( ) * rY ) );
		}

		g2.setColor ( Color.BLACK );
	}



	public void dessinerRegions ( Graphics2D g2 )
	{
		g2.setStroke ( new BasicStroke ( 1 )     );
		g2.setColor  ( new Color ( 86, 39, 138 ) );
		
		g2.drawLine (   0, 357,  785, 357 );
		g2.drawLine ( 785, 357, 1237,   0 );
		g2.drawLine ( 785, 357, 1350, 900 );
		g2.drawLine ( 450,   0,  450, 900 );
	}

	/** Méthode qui permet de récupérer l'arc sélectionné
	 * @return Arc sélectionné
	 * 
	 */
	public VoieMaritime getVoieMaritimeAColorier ( ) { return this.voieMaritimeAColorier; }

	public void setRatios ( double rX, double rY )
	{
		this.rX = rX;
		this.rY = rY;
		this.repaint ( );
	}

	public void mouseClicked ( MouseEvent e )
	{
		
		for ( VoieMaritime voieMaritime : this.lstVoiesMaritimes ) 
		{
			Ile ileD = voieMaritime.getIleD ( );
			Ile ileA = voieMaritime.getIleA ( );

			Line2D line = new Line2D.Double ( ileD.getPosX ( ) * rX, ileD.getPosY ( ) * rY, ileA.getPosX ( ) * rX, ileA.getPosY ( ) * rY );

			//Si on clique bien sur un arc
			if ( line.intersects ( e.getX ( ), e.getY ( ), 10, 10 ) ) 
			{
				this.voieMaritimeAColorier = voieMaritime;
				
				//Si ce n'est pas possible
				if ( !this.ctrl.jouer ( voieMaritime, true ) )
				{
					JOptionPane.showMessageDialog ( this.frame, "Erreur de sélection", "Erreur", JOptionPane.ERROR_MESSAGE ); //Affiche que la sélection est mauvaise
				}

				this.voieMaritimeAColorier = null;
				this.repaint ( );

				//Message pour dire qu'il y a une biffurcation
				if ( this.ctrl.getJoueur1 ( ).getPartie ( ).estBiffurcation ( ) )
					JOptionPane.showMessageDialog ( this.frame, "Évènement - Biffurcation", "Biffurcation", JOptionPane.INFORMATION_MESSAGE ); //Affiche que la sélection est mauvaise

				//Si la partie est fini
				if ( this.ctrl.estFinDePartie ( ) )
				{
					this.frame.finPartieInit ( );
				}

				this.frame.majFrameCarte ( );
				return;
			}
		}
	}

	public void mousePressed  ( MouseEvent e ) { /*méthode pas utilisé*/ }
	public void mouseReleased ( MouseEvent e ) { /*méthode pas utilisé*/ }
	public void mouseEntered  ( MouseEvent e ) { /*méthode pas utilisé*/ }
	public void mouseExited   ( MouseEvent e ) { /*méthode pas utilisé*/ }
	
}