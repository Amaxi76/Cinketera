package metier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Manche
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	private List<Ile> ligne;
	private Color     coulLigne;
	private Paquet      paquet;

	private int       numManche;
	private int       numTour;
	private boolean   finManche;

	private int       score;
	
	private List<VoieMaritime> lstVoieMaritimes;
	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */
	public Manche ( int numManche, List<Ile> ileDep, Color couleur, List<VoieMaritime> lstVoieMaritimes )
	{
		this.paquet    = new Paquet ( );
		this.ligne     = ileDep;
		this.coulLigne = couleur;

		this.numManche = numManche;

		this.numTour   = 0;
		this.finManche = false;

		this.score     = 0;

		this.lstVoieMaritimes = lstVoieMaritimes;
	}
	
	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public List<Ile> getLigne       ( ) { return this.ligne;        }
	public Color     getCoulLigne   ( ) { return this.coulLigne;    }
	public int       getNumManche   ( ) { return this.numManche;    }
	public int       getNumTour     ( ) { return this.numTour;      }
	public int       getScore       ( ) { return this.score;        }
	public boolean   getDebutManche ( ) { return this.numTour == 0; }
 
 	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public boolean jouer( VoieMaritime voie )
	{
		Carte c = this.paquet.piocher ( );
		
		// Une ligne ne peut pas passer plusieurs fois par la même ile.
		if ( this.ligne.contains( voie.getIleA ( ) ) ) return false;
		if ( this.ligne.contains( voie.getIleD ( ) ) ) return false;

		// Regarde si l'ile que l'on veut relié est bien de la même couleur que la carte
		if ( !voie.getIleA ( ).getCouleur ( ).equals ( c.getCouleurCarte ( ) ) || 
			 !voie.getIleD ( ).getCouleur ( ).equals ( c.getCouleurCarte ( ) ) ||
			 !c.getCouleurCarte ( ).equals ( "Multicolore" ) )
			return false;

		// Regarde si la voie n'est pas déjà colorié
		if ( voie.getEstColorie ( ) ) 
			return false;

		// Si c'est le premier tour, qu'on est avec la couleur rouge et qu'on part de l'ile Ticó
		
		if ( this.getDebutManche ( ) && ( voie.getIleA ( ).getNom ( ).equals ( "Ticó" )
									 ||   voie.getIleD ( ).getNom ( ).equals ( "Ticó" ) )
									 && coulLigne.equals ( Color.RED )                  )
			return true;

		// Si c'est le premier tour, qu'on est avec la couleur bleu et qu'on part de l'ile Mutaa
		if ( this.getDebutManche ( ) && ( voie.getIleA ( ).getNom ( ).equals ( "Mutaa" )
									 ||   voie.getIleD ( ).getNom ( ).equals ( "Mutaa" ) )
									 && coulLigne.equals ( Color.BLUE )                  )
			return true;

		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !this.estRelie ( voie ) && !this.getDebutManche ( ) ) return false;
		
		// Une ligne ne peut pas croiser d'autre ligne coloriée
		
		for ( VoieMaritime v : this.lstVoieMaritimes ) 
			if ( intersection ( voie, v ) && ( v.getColorArc ( ) != null ) ) return false;
		
		
		
		// Tour suivant
		this.tourSuivant();
		return true;


		// Vous ne pouvez pas prendre une voie si elle croise une autre voie déjà prise par une des deux lignes.



		// return false;
	}


	private boolean tourSuivant ()
	{
		if ( !this.paquet.aEncorePrimaire ( ) ) { this.finManche = true; return false; }
	
		this.numTour ++;
		return true;
	}



	private int calculerScore ()
	{
		int[] tabNbIles;

		tabNbIles = new int[this.ligne.size()];


		for ( Ile i :  )
	}



	

	/** Méthode qui indique si l'arc prit en paramètre est rataché aux autres arcs déja colorer
	 * @param a est l'arc qui est sélectionner par l'utilisateur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	private boolean estRelie ( VoieMaritime v )
	{
		for ( VoieMaritime voie : v.getIleA ( ).getEnsVoie ( ) )
			if ( voie != v && voie.getEstColorie ( ) ) return true;

		for ( VoieMaritime voie : v.getIleD ( ).getEnsVoie ( ) )
			if ( voie != v && voie.getEstColorie ( ) ) return true;

		return false;
	}

	/** Méthode qui retourne si deux arcs se croisent ou non
	 * @param arcOg est l'arc sélectionner par l'utilisateur
	 * @param arcATester est l'arc que l'on veut tester avec l'arc de l'utilisateur
	 * @return renvoie un booléen qui indique si les deux arcs se croisent ou non
	 */
	public boolean intersection ( VoieMaritime voieOg, VoieMaritime voieATester )
	{
		Ile depart  = voieOg.getIleD ( );
		Ile arrivee = voieOg.getIleA ( );
		Ile depart2  = voieATester.getIleD ( );
		Ile arrivee2 = voieATester.getIleA ( );

		Line2D lineOg      = new Line2D.Double ( depart .getPosX ( ), depart. getPosY ( ), arrivee. getPosX ( ), arrivee. getPosY ( ) );
		Line2D lineATester = new Line2D.Double ( depart2.getPosX ( ), depart2.getPosY ( ), arrivee2.getPosX ( ), arrivee2.getPosY ( ) );

		// Vérification si les lignes se croisent
		if ( lineOg.intersectsLine ( lineATester ) )
		{
			// Les arêtes se croisent
			// Vérification si les arêtes sont adjacentes
			if ( voieOg.estIdentique ( voieATester ) ) return false; // Les arêtes sont adjacentes, elles ne se croisent pas
			
			return true; // Les arêtes se croisent
		}
		return false; // Les arêtes ne se croisent pas
	}

	// 	// On retourne true et on applique la couleur
	// 	if ( enCours )
	// 	{
	// 		if ( Manche.getEstPremierTour ( ) )
	// 			Manche.setEstPremierTour ( );
				
	// 		voieMaritime.getNoeudA ( ).setEstVisite ( );
	// 		voieMaritime.getNoeudD ( ).setEstVisite ( );
			
			
	// 		voieMaritime.setColorie ( couleur );
	// 		return true;
	// 	}
	// 	else
	// 		return false;

	// }
	

}