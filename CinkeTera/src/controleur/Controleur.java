package controleur;
/** SAE 2.02
  * date : le 06/06/2023
  * @author : Alizéa Lebaron, Mathys Poret, Maximilien Lesterlin ,Mohamad Marrouche, Eleonore Bouloché
  */

import iut.algo.Clavier;
import metier.*;
import ihm.*;
import java.util.List;
import java.awt.Color;

public class Controleur
{
	private Plateau      metier;
	private	FramePlateau ihm;

	private Joueur J1;
	//private Joueur J2;

	/** Contructeur qui initialise le jeu
	 */
	public Controleur ( )
	{
		
		//this.J2     = new Joueur       (      );
		this.metier = new Plateau      ();
		this.J1     = new Joueur       (this.metier);
		new FrameAccueil(this);

	}

	public static void main ( String[] arg )
	{
		new Controleur ( );
	}

	/** Getteur qui retourne la liste des arcsde la partie métier
	 * @return la liste des arcs de la partie métier
	 */
	public List<VoieMaritime>   getVoiesMaritimes    ( ) { return this.metier.getVoiesMaritimes    ( ); }

	/** Getteur qui retourne la liste des noeuds de la partie métier
	 * @return la liste des noeuds de la partie métier
	 */
	public List<Ile> getIle( ) { return this.metier.getIles  ( ); }

	/** Getteur qui retourne la couleur du joueur en cours
	 * @return couleur du joueur en cours
	 */
	public Color     getCouleurJ1 ( ) { return this.J1.getMancheActuel().getCoulLigne ( ) ; }
	//public Color     getCouleurJ2 ( ) { return this.J2.getCouleur ( ); }

	/** Getteur qui retourne la partie métier
	 * @return partie métier
	 */
	public Plateau     getMetier  ( ) { return this.metier;                }


	/** Getteur qui retourne le nombre de régions visitées
	 * @return nombre de régions visitées
	 */
	public int   getNbRegionsVisite ( ) { return 2; }

	
	/** Getteur qui retourne le nombre d'arcs coloriés
	 * @return nombre d'arcs coloriés
	 */
	public int   getNbVoiesMaritimesColorie   ( ) { return this.metier.getNbVoiesMaritimesColorie ( );         }

	/** Méthode qui retour l'arc entre deux noeuds
	 * @param n1 Ile 1
	 * @param n2 Ile 2
	 * @return l'arc entre les deux noeuds
	 */
	public VoieMaritime     arcEntre ( Ile n1, Ile n2 )         { return this.metier.arcEntre ( n1,n2 ); }

	/** Méthode qui permet de colorier un arc en faisant le lien entre l'IHM et le métier
	 * @param arc est l'arc sélectionner dans la partie graphique et l'envoie à la partie métier pour le colorier ou non
	 * @param couleur est la couleur que l'on veut attribuer à l'arc (couleur du joueur )
	 * @return retourne un boolean qui permet de savoir si l'arc a été colorié ou non
	 */
	//public boolean colorier ( VoieMaritime arc, Color couleur ) { return this.metier.colorier ( arc , couleur ); }

	/** Méthode qui nous indique si on peut changer de tour ou non et le fait si on peut
	 * @return le score du joueur
	 *//*
	public boolean augmenterTours ( )
	{
		this.joueurEnCours.augmenterTour( );

		try
		{
			if ( this.manche.getNumTour ( ) >= Manche.getNbTour ( ) )
				this.manche.changerTour ( );

		}catch ( Exception e )
		{
			this.ihm.resetSelect( );
			return false;
		}

		return true;
	}*/

	public boolean jouer ( VoieMaritime voieMaritime )
	{
		return this.J1.jouer ( voieMaritime );
		//this.ihm.majIhm();
	}

	public void lancerFrame()
	{
		this.ihm    = new FramePlateau ( this );
	}

	public Carte getCarteEnCours()
	{
		return this.J1.getMancheActuel().getCarteEnCours();
	}

	
}
