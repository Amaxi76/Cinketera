//BUG : On ne peut pas avoir un carte de l'ile de départ 
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

	private List<VoieMaritime> lstVoieMaritimes;
	private List<Ile>          ligne;

	private Color   coulLigne;
	private Paquet  paquet;
	private Carte   carteEnCours;

	private int     numManche;
	private int     numTour;
	private int     score;
	private boolean finManche;

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

		this.carteEnCours = this.paquet.piocher();

		System.out.println(this.coulLigne);
	}
	
	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public List<Ile> getLigne        ( ) { return this.ligne;        }
	public Color     getCoulLigne    ( ) { return this.coulLigne;    }
	public int       getNumManche    ( ) { return this.numManche;    }
	public int       getNumTour      ( ) { return this.numTour;      }
	public int       getScore        ( ) { return this.score;        }
	public boolean   getDebutManche  ( ) { return this.numTour == 0; }
	public Carte     getCarteEnCours ( ) { return this.carteEnCours; }
	public boolean   getFinManche    ( ) { return this.finManche;    }
 
 	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public boolean jouer ( VoieMaritime voie )
	{
		System.out.println ( ligne );
		System.out.println ( voie.getColorArc ( ) );
		Ile ileDepart = this.ligne.get ( this.ligne.size ( ) - 1 );
		Ile ileArrive = null;
		
		if ( voie.getIleA ( ) == ileDepart )
			ileArrive = voie.getIleD ( );
		else
			ileArrive = voie.getIleA ( );

		boolean possible = true;

		//si ya pas de voie coloriable 
		
				

		System.out.println ( "Affichage des informations pour jouer : "                                  );
		System.out.println ( "\tIle de départ : "                + ileDepart                             );
		System.out.println ( "\tIle d'arrivée : "                + ileArrive                             );
		System.out.println ( "\tCouleur de la carte en cours : " + this.carteEnCours.getCouleurCarte ( ) );

		System.out.println("+--------------------------------+");
		System.out.println("| Affichage des différents tests |");
		System.out.println("+--------------------------------+");
		
		for ( VoieMaritime voieT : ileDepart.getEnsVoie ( ) )
			if ( !voieT.getEstColorie ( ) )
				if ( voieT.getIleA ( ) != ileDepart )
					if ( !voieT.getIleA ( ).getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) ) 
						possible = false;
				else
					if ( !voieT.getIleD ( ).getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) ) 
						possible = false;

		System.out.println ( "Est ce que il y a une ile de la couleur de la carte ? " + possible );

		System.out.println ( "Est ce que l'ile n'est de la même couleur que la carte et que la carte n'est pas multicolor?");
		System.out.println ( !ileArrive.getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) && !this.carteEnCours.getCouleurCarte ( ).equals ( "Multicolore" ));

		// Regarde si l'ile que l'on veut relié est bien de la même couleur que la carte
		if ( !ileArrive.getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) && !this.carteEnCours.getCouleurCarte ( ).equals ( "Multicolore" ) )
			return false;

		// Regarde si la voie n'est pas déjà colorié
		System.out.println("est ce que la voie est déja colorier ?");
		System.out.println(voie.getEstColorie ( ) );
		if ( voie.getEstColorie ( ) ) 
			return false;

		// Si c'est le premier tour, qu'on est avec la couleur rouge et qu'on part de l'ile Ticó
		System.out.println("est ce que l'arc est tico et la couleur est rouge et que c'est le début de la manche?");
		System.out.println( this.getDebutManche ( ) && ileDepart.getNom ( ).equals ( "Ticó" )
		&& coulLigne.equals ( Color.RED )        );
		if ( this.getDebutManche ( ) && ileDepart.getNom ( ).equals ( "Ticó" )
									 && coulLigne.equals ( Color.RED )        )
		{
			voie.setCouleur( this.coulLigne );
			// Tour suivant
			this.tourSuivant();
			return true;
		}

		System.out.println("est ce que l'arc est Mutaa et la couleur est bleu et que c'est le début de la manche?");
		System.out.println( this.getDebutManche ( ) && voie.getIleA ( ).getNom ( ).equals ( "Mutaa" )
		&& coulLigne.equals ( Color.BLUE )               );
		//Si c'est le premier tour, qu'on est avec la couleur bleu et qu'on part de l'ile Mutaa
		if ( this.getDebutManche ( ) && voie.getIleA ( ).getNom ( ).equals ( "Mutaa" )
									 && coulLigne.equals ( Color.BLUE )               )
		{
			voie.setCouleur( this.coulLigne );
			// Tour suivant
			this.tourSuivant();
			return true;
		}

		System.out.println("est ce que l'arc est relié et que c'est pas le début de la manche?");
		System.out.println(  !this.estRelie ( voie ) && !this.getDebutManche ( ));

		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !this.estRelie ( voie ) && !this.getDebutManche ( ) ) return false;
		
		System.out.println("est ce que l'on croise un arc qui est colorié ?");
		// Une ligne ne peut pas croiser d'autre ligne coloriée
		for ( VoieMaritime v : this.lstVoieMaritimes ) 
			if ( intersection ( voie, v ) && ( v.getColorArc ( ) != null ) ) return false;
		
		voie.setCouleur( this.coulLigne );
		// Tour suivant
		this.tourSuivant();

		return true;
	}

	private boolean tourSuivant ()
	{
		if ( !this.paquet.aEncorePrimaire ( ) ) { this.finManche = true; return false; }
	
		this.carteEnCours = this.paquet.piocher();
		this.numTour ++;
		return true;
	}

	public int calculerScore ( )
	{
		List<Region> ensRegions;

		int          tmp, max;

		ensRegions = new ArrayList<>();

		for ( Ile i : this.ligne )
			if ( !ensRegions.contains ( i.getRegion ( ) ) )
				ensRegions.add ( i.getRegion ( ) );

		this.score = this.scorePrincipale ( ensRegions );

		return this.score;

	}

	private int scorePrincipale (List<Region> ensRegions)
	{
		int tmp    =  0;
		int max    = -1;

		for ( Region r : ensRegions )
		{

			for ( Ile i : r.getEnsIles ( ) )
				if ( this.ligne.contains(i) )
					tmp ++;

			if ( tmp > max ) max = tmp;

			tmp = 0;
		}

		return this.score = max * ensRegions.size();
	}


	private int calculBonusLigne ()
	{
		List<VoieMaritime> ensVoie;

		String[][] tabBonusLigne = { { "Tokha Laçao", "Ticó"  },
		                             { "Laçao"      , "Milaú" },
		                             { "Khont-Rolah", "Mutaa" },
		                             { "Khont-Rolah", "Fuego" },
		                             { "Mokah"      , "Fissah"} };

		int     score = 0;
		boolean eMemeLigne;


		ensVoie = this.creeVoieChoisies();


		for ( VoieMaritime v : ensVoie )
		{
			for ( int cpt = 0; cpt < ensVoie.size(); cpt ++ )
			{
				eMemeLigne = ( v.getIleA().getNom().equals(tabBonusLigne[cpt][0])   ||
				               v.getIleA().getNom().equals(tabBonusLigne[cpt][1]) )    &&
				             ( v.getIleD().getNom().equals(tabBonusLigne[cpt][0])   ||
				               v.getIleD().getNom().equals(tabBonusLigne[cpt][1])        );

				if ( eMemeLigne ) score ++;
			}
		}

		return score;
	}


	private int calculBonusIle ()
	{
		int score = 0;

		for ( Ile i : this.ligne )
			for ( VoieMaritime v : i.getEnsVoie() )
				if ( !v.getColorArc().equals(this.coulLigne) )
					score += 2;

		return score;
	}


	private List<VoieMaritime> creeVoieChoisies ( )
	{
		List<VoieMaritime> ensVoie;

		ensVoie = new ArrayList<VoieMaritime>();

		for ( Ile i : this.ligne )
			for ( VoieMaritime voie : i.getEnsVoie() )
				if ( voie.getColorArc().equals(this.coulLigne) && ensVoie.contains(voie))
					ensVoie.add(voie);

		return ensVoie;
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

}