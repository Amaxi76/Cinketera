package controleur;
/** SAE 2.02
  * date : le 06/06/2023
  * @author : Alizéa Lebaron, Mathys Poret, Maximilien Lesterlin ,Mohamad Marrouche, Eleonore Bouloché
  */

import metier.*;
import ihm.*;
import java.util.List;
import java.awt.*;

public class Controleur
{
	/**
	 * Frame du jeu 
	 */
	private	FramePlateau ihm;

	/**
	 * Le joueur 1 
	 */
	private Joueur       j1;

	/**
	 * Le joueur 2
	 */
	private Joueur       j2;

	/** Contructeur qui initialise le jeu
	 */
	public Controleur ( )
	{
		new FrameAccueil ( this );
	}

	/** Accesseur qui retourne la couleur du joueur en cours
	 * @return couleur du joueur en cours
	 */
	public Color              getCouleurj1               ( ) { return this.j1.getPartie ( ).getCoulLigne ( ) ;    }

	public Joueur             getJoueur1                 ( ) { return this.j1;                                    }

	/** Accesseur qui retourne le nombre de régions visitées
	 * @return nombre de régions visitées
	 */
	public int                getNbRegionsVisite         ( ) { return this.j1.getPartie ( ).getNbRegionsVisite ( ); }


	/**
	 * @return true si c'est la fin de la partie
	 */
	public boolean            estFinDePartie             ( ) { return this.j1.getPartie ( ).getFinPartie ( );       }

	/**
	 * @param voieMaritime
	 * @param b
	 * @return true si on peut jouer et si ce n'est pas la fin de la partie
	 */
	public boolean jouer ( VoieMaritime voieMaritime, boolean b )
	{
		if ( this.estFinDePartie ( ) )
			return false;
		
		return this.j1.jouer ( voieMaritime, b );
	}

	/**
	 * lance les frame
	 */
	public void  lancerFrame      (            ) { this.j1     = new Joueur  ( ); this.ihm    = new FramePlateau ( this );                                }

	/**
	 * lance un scenario
	 */
	public void  lancerScenario   ( int numero ) { this.j1     = new Joueur  (numero); this.ihm    = new FramePlateau ( this );                           }

	/**
	 * lance les jeu en mode deux joueurs
	 */
	public void  modeDeuxJouers   (            ) { this.j1     = new Joueur  ( ); this.j2     = new Joueur  ( ); this.ihm    = new FramePlateau ( this ); }

	/**
	 * @return la carte en cours
	 */
	public Carte getCarteEnCours  (            ) { return this.j1.getPartie ( ).getCarteEnCours ( );                                                      }
  
	/**
	 * @return le nombre de points du joueur 
	 */
	public int   calculerScore    (            ) { return this.j1.getPartie ( ).calculerScore   ( );                                                      }

	/**
	 * @param ile
	 * @param lstExtremite
	 * @return true si on peut dessiner un arc vers l'île en paramètre
	 */
	public boolean estJouable ( Ile ile, List<Ile> lstExtremite ) { return this.j1.estJouable ( ile, lstExtremite );                                      }

	/**
	 * mise a jour de l'ihm
	 */
	public void majIHM ( ) { this.ihm.majIHM ( );}

	public static void main ( String[] arg ) { new Controleur ( ); }
}
