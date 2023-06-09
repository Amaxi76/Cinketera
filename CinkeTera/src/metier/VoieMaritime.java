package metier;

import java.awt.Color;


public class VoieMaritime
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	private Ile    depart;
	private Ile    arriver;
	private String nom;
	private Color  couleur;
	
	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/** Constructeur voie maritime
	 * @param nom     nom de l'voie maritime
	 * @param depart  Ile par lequel la voie maritime part
	 * @param arriver Ile par lequel la voie maritime repart
	 */

	public VoieMaritime ( String nom, Ile depart, Ile arriver )
	{
		this.nom        = nom;
		this.depart     = depart;
		this.arriver    = arriver;
		this.couleur    = null;
	}

	/**Méthode qui crée une voie maritime
	 * @param nom nom de la voie maritime
	 * @param depart  Ile de départ de la voie maritime
	 * @param arriver Ile d'arriver de la voie maritime
	 * @return la voie maritime que l'on a créé
	 */
	public static VoieMaritime creerVoieMaritime ( String nom, Ile depart, Ile arriver )
	{
		if ( depart == null || arriver == null ) return null;

		return new VoieMaritime (nom, depart, arriver);
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */
	
	/** Getteur qui permet de récupérer le Ile de départ de la voie maritime
	 * @return Ile de départ */
	public Ile     getIleD     ( ) { return this.depart;          }

	/** Getteur qui permet de récupérer le Ile d'arrivée de la voie maritime
	 * @return Ile d'arrivée */
	public Ile     getIleA     ( ) { return this.arriver;         }

	/** Getteur qui permet de savoir si la voie maritime est colorié
	 * @return true si la voie maritime est colorie */
	public boolean getEstColorie ( ) { return this.couleur != null; }

	/** Getteur qui permet de récupérer la couleur de la voie maritime
	 *  @return couleur de la voie maritime */
	public Color   getColorArc   ( ) { return this.couleur;         }

	/** Getteur qui permet de récupérer le nom de la voie maritime
	 * @return nom de la voie maritime */
	public String  getNom        ( ) { return this.nom;             }

	/* -------------------------------------- */
	/*                Setteur                 */
	/* -------------------------------------- */

	/** Setteur qui permet de changer le Ile de départ de la voie maritime
	 * @param n Ile de départ de la voie maritime */
	public void   setIleD    ( Ile n      ) { this.depart  = n;       }

	/** Setteur qui permet de changer le Ile d'arriver de la voie maritime
	 * @param n Ile d'arriver de la voie maritime */
	public void   setIleA    ( Ile n      ) { this.arriver = n;       }

	/** Setteur qui permet de colorier la voie maritime
	 * @param coul couleur de la voie maritime */
	public void   setCouleur ( Color coul ) { this.couleur = coul;    }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/**Méthode qui permet de savoir si une voie maritime est identique à un autre
	 * @param arc prend la voie maritime a teste
	 * @return true si la voie maritime passé en paramètre est identique
	*/
	public boolean estIdentique ( VoieMaritime voieMaritime )
	{
		return this.getIleD() == voieMaritime.getIleD() ||
		       this.getIleD() == voieMaritime.getIleA() ||
		       this.getIleA() == voieMaritime.getIleD() ||
		       this.getIleA() == voieMaritime.getIleA();
	}

	/** Méthode toString
	 * @return le nom du Ile de départ et d'arrivée et sa valeur
	 */
	public String toString()
	{
		return "Ile départ " + this.depart.getNom() + " Ile arrivé " + this.arriver.getNom();
	}


}