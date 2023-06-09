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

	private Carte carteEnCours;

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
 
 	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public boolean jouer ( VoieMaritime voie )
	{
		Ile ileDepart = this.ligne.get ( this.ligne.size() - 1 );
		Ile ileArrive = null;
		
		if ( voie.getIleA ( ) == ileDepart )
			ileArrive = voie.getIleD ( );
		else
			ileArrive = voie.getIleA ( );

		System.out.println(ileDepart);
		System.out.println(ileArrive);

		System.out.println(this.coulLigne);
		
		Carte c = this.paquet.piocher ( );

		System.out.println(this.carteEnCours);
		
		// Une ligne ne peut pas passer plusieurs fois par la même ile.
		if ( !this.getDebutManche ( ) )
		{
			if ( this.ligne.contains ( voie.getIleA ( ) ) ) return false;
			if ( this.ligne.contains ( voie.getIleD ( ) ) ) return false;
		}

		System.out.println("Pas passer par la même ligne");

		// Regarde si l'ile que l'on veut relié est bien de la même couleur que la carte
		if ( ileArrive.getCouleur ( ).equals ( c.getCouleurCarte ( ) ) || c.getCouleurCarte ( ).equals ( "Multicolore" ) )
		{
			System.out.println("oui");
		}
		System.out.println("Même couleur que la carte");

		// Regarde si la voie n'est pas déjà colorié
		if ( voie.getEstColorie ( ) ) 
			return false;

		System.out.println("déja colorier");

		// Si c'est le premier tour, qu'on est avec la couleur rouge et qu'on part de l'ile Ticó
		
		if ( this.getDebutManche ( ) && ( voie.getIleA ( ).getNom ( ).equals ( "Ticó" )
									 ||   voie.getIleD ( ).getNom ( ).equals ( "Ticó" ) )
									 && coulLigne.equals ( Color.RED )                  )
			return true;

		System.out.println("Premier tour, rouge rico");

		// Si c'est le premier tour, qu'on est avec la couleur bleu et qu'on part de l'ile Mutaa
		if ( this.getDebutManche ( ) && ( voie.getIleA ( ).getNom ( ).equals ( "Mutaa" )
									 ||   voie.getIleD ( ).getNom ( ).equals ( "Mutaa" ) )
									 && coulLigne.equals ( Color.BLUE )                  )
			return true;

		System.out.println("Premier tour, bleu mutaa");

		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !this.estRelie ( voie ) && !this.getDebutManche ( ) ) return false;

		System.out.println("pas relier");
		
		// Une ligne ne peut pas croiser d'autre ligne coloriée
		
		for ( VoieMaritime v : this.lstVoieMaritimes ) 
			if ( intersection ( voie, v ) && ( v.getColorArc ( ) != null ) ) return false;

		System.out.println("intersection");
		
		
		voie.setCouleur( this.coulLigne );
		// Tour suivant
		this.tourSuivant();

		return true;


		// Vous ne pouvez pas prendre une voie si elle croise une autre voie déjà prise par une des deux lignes.



		// return false;
	}


	private boolean tourSuivant ()
	{
		if ( !this.paquet.aEncorePrimaire ( ) ) { this.finManche = true; return false; }
	
		this.carteEnCours = this.paquet.piocher();
		this.numTour ++;
		return true;
	}



	public int calculerScore ()
	{
		List<Region> ensRegions;

		int          tmp, max;


		ensRegions = new ArrayList<>();


		for ( Ile i : this.ligne )
			if ( !ensRegions.contains(i.getRegion()) )
				ensRegions.add(i.getRegion());

		
		this.score = this.scorePrincipale(ensRegions);

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


	private List<VoieMaritime> creeVoieChoisies ()
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