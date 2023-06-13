package metier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Partie
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private List<Ile>          ligne;

	private int     numManche;
	private int     score;
	private Color   coulLigne;
	private Carte   carteEnCours;
	private Paquet  paquet;
	private Joueur  joueur;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Partie ( Joueur j, List<Ile> ileDep, Color couleur )
	{
		this.numManche        = 0;
		this.joueur           = j;
		this.paquet           = new Paquet ( );
		this.ligne            = ileDep;
		this.coulLigne        = couleur;
		this.score            = 0;
	}
	
	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public List<Ile> getLigne        ( ) { return this.ligne;             }
	public Color     getCoulLigne    ( ) { return this.coulLigne;         }
	public Carte     getCarteEnCours ( ) { return this.carteEnCours;      }
	public Paquet    getPaquet       ( ) { return this.paquet;            }
	public int       getScore        ( ) { return this.score;             }
	public boolean   estPremierTrait ( ) { return this.ligne.size() == 1; }
	public boolean   getFinPartie    ( ) { return this.numManche    == 2; }

	/* -------------------------------------- */
	/*              Modificateur              */
	/* -------------------------------------- */

	public void setCarteEnCours ( Carte carte ) {  this.carteEnCours = carte; System.out.println(this.carteEnCours); }

 	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public boolean jouer ( VoieMaritime voie )
	{
		// Si aucune carte n'est sélectionnée, on ne peut pas jouer
		if ( this.carteEnCours == null ) return false;

		Ile ileDebut  = this.ligne.get ( 0                       );
		Ile ileFin    = this.ligne.get ( this.ligne.size ( ) - 1 );
		Ile ileArrive = null;

		// On définit l'île d'arrivée et de départ
		if ( voie.getIleA ( ).equals ( ileDebut ) || voie.getIleD ( ).equals ( ileDebut ) ) 
			ileArrive = voie.getIleA ( ).equals ( ileDebut ) ? voie.getIleD ( ) : voie.getIleA ( );

		if ( voie.getIleA ( ).equals ( ileFin ) || voie.getIleD ( ).equals ( ileFin ) )
			ileArrive = voie.getIleA ( ).equals ( ileFin ) ? voie.getIleD ( ) : voie.getIleA ( );

		// Regarde que l'on est bien une île d'arrivée
		if ( ileArrive == null )
			return false;

		// Regarde si l'ile que l'on veut relié est bien de la même couleur que la carte
		if ( !ileArrive.getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) && !this.carteEnCours.getCouleurCarte ( ).equals ( "Multicolore" ) )
			return false;

		// Regarde si la voie n'est pas déjà colorié
		if ( voie.getEstColorie ( ) )
			return false;

		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !this.estRelie ( voie ) && !this.estPremierTrait ( ) ) return false;

		if ( this.cyclique ( voie, coulLigne ) ) return false;

		// Une ligne ne peut pas croiser d'autre ligne coloriée
		for ( VoieMaritime v : this.joueur.getVoieMaritimes ( ) )
			if ( intersection ( voie, v ) && ( v.getColorArc ( ) != null ) ) return false;
		
		// Si on arrive ici, c'est que tout est bon, on peut donc colorier la voie
		voie.setCouleur  ( this.coulLigne );
		this.ajouterIle  ( ileArrive      );
		this.tourSuivant (                );

		return true;
	}

	/** Méthode qui permet de passer un tour (changer la carte ) */
	public void tourSuivant ( )
	{
		if ( !this.paquet.aEncorePrimaire ( ) ^ this.getFinPartie ( ) )
		{
			this.coulLigne = this.joueur.getCouleur ( );	
			this.paquet    = new Paquet ( );
			this.numManche++;
		}

		this.carteEnCours = null;
	}

	public int calculerScore ( )
	{
		List<Region> ensRegions;

		int          tmp, max;

		ensRegions = new ArrayList<>();

		if ( this.ligne.size() == 1 ) return this.score = 0;

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
		boolean eMemeLigne, eVoieChoisie;

		ensVoie = this.creeVoieChoisies();

		for ( VoieMaritime v : ensVoie )
		{
			for ( int cpt = 0; cpt < ensVoie.size(); cpt ++ )
			{
				eMemeLigne = ( v.getIleA().getNom().equals(tabBonusLigne[cpt][0])   ||
				               v.getIleA().getNom().equals(tabBonusLigne[cpt][1]) )    &&
				             ( v.getIleD().getNom().equals(tabBonusLigne[cpt][0])   ||
				               v.getIleD().getNom().equals(tabBonusLigne[cpt][1])        );

				eVoieChoisie = ( this.ligne.contains(v.getIleA()) &&
				                 this.ligne.contains(v.getIleD())    );

				if ( eVoieChoisie ) score += v.getValeur();
				if ( eMemeLigne   ) score ++;
			}
		}

		return score;
	}

	private int calculBonusIle ( )
	{
		int score = 0;

		for ( Ile i : this.ligne )
			for ( VoieMaritime v : i.getEnsVoie ( ) )
				if ( !v.getColorArc ( ).equals ( this.coulLigne ) )
					score += 2;

		return score;
	}

	/** Méthode qui indique crée une liste des voies que l'utilisateur à choisie selon la couleur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	private List<VoieMaritime> creeVoieChoisies ( )
	{
		List<VoieMaritime> ensVoie;

		ensVoie = new ArrayList<VoieMaritime> ( );

		for ( Ile i : this.ligne )
			for ( VoieMaritime voie : i.getEnsVoie ( ) )
				if ( voie.getColorArc ( ).equals ( this.coulLigne ) && ensVoie.contains ( voie ) )
					ensVoie.add ( voie );

		return ensVoie;
	}


	/** Méthode qui indique si l'arc prit en paramètre est rataché aux autres arcs déja colorer
	 * @param v est l'arc qui est sélectionner par l'utilisateur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	private boolean estRelie ( VoieMaritime v )
	{
		Ile ileA = v.getIleA ( );
		Ile ileD = v.getIleD ( );

		// Il doit y avoir une voie coloriée dans la liste d'une des deux îles de la voie
		for ( VoieMaritime voie : ileA.getEnsVoie ( ) )
			if ( !voie.equals ( v ) && voie.getEstColorie ( ) ) return true;

		for ( VoieMaritime voie : ileD.getEnsVoie ( ) )
			if ( !voie.equals ( v ) && voie.getEstColorie ( ) ) return true;

		return false;
	}


	/** Méthode qui retourne si deux arcs se croisent ou non
	 * @param voieOg est l'arc sélectionner par l'utilisateur
	 * @param voieATester est l'arc que l'on veut tester avec l'arc de l'utilisateur
	 * @return renvoie un booléen qui indique si les deux arcs se croisent ou non
	 */
	public boolean intersection ( VoieMaritime voieOg, VoieMaritime voieATester )
	{
		Ile depart   = voieOg.     getIleD ( );
		Ile arrivee  = voieOg.     getIleA ( );
		Ile depart2  = voieATester.getIleD ( );
		Ile arrivee2 = voieATester.getIleA ( );

		Line2D lineOg      = new Line2D.Double ( depart .getPosX ( ), depart. getPosY ( ), arrivee. getPosX ( ), arrivee. getPosY ( ) );
		Line2D lineATester = new Line2D.Double ( depart2.getPosX ( ), depart2.getPosY ( ), arrivee2.getPosX ( ), arrivee2.getPosY ( ) );

		// Vérification si les lignes se croisent
		if ( lineOg.intersectsLine ( lineATester ) )
		{
			// Vérification si les arêtes sont adjacentes
			if ( voieOg.estIdentique ( voieATester ) ) return false; // Les arêtes sont adjacentes, elles ne se croisent pas
			
			return true; // Les arêtes se croisent
		}

		return false; // Les arêtes ne se croisent pas
	}


	/** Méthode qui ajoute l'ile à la bonne position dans la ligne
	 * @param ile est l'ile que l'on veut ajouter à la ligne
	 */
	private void ajouterIle ( Ile ile )
	{
		Ile ileDep = this.ligne.get ( 0                       );
		Ile ileArr = this.ligne.get ( this.ligne.size ( ) - 1 );

		if ( this.ligne.contains ( ile ) ) return;

		// On ajoute les voie à la la ligne
		// Cette méthode nous permet d'avoir la tête et la queue de notre ligne et de limiter les possibilités.

		for ( VoieMaritime v : ile.getEnsVoie ( ) )
		{
			if ( ( v.getIleA ( ).equals ( ileDep ) || v.getIleD ( ).equals ( ileDep ) ) && !this.ligne.contains ( ile ) && v.getEstColorie ( ) )
				this.ligne.add(0,ile);
			if ( ( v.getIleA ( ).equals ( ileArr ) || v.getIleD ( ).equals ( ileArr ) ) && !this.ligne.contains ( ile ) && v.getEstColorie ( ) )
				this.ligne.add(ile);
		}
	}


	/**Retourne si la voie forme un cycle à partir d'une couleur
	 * @param voie voie maritime sélectionnée par l'utilisateur
	 * @param couleur couleur que doit prendre la voie maritime
	 * @return true si la voie maritime forme un cycle, sinon false
	 */
	public boolean cyclique ( VoieMaritime voie, Color couleur )
	{
		Ile     ileD, ileA;

		boolean cycliqueIleD = false;
		boolean cycliqueIleA = false;

		ileD = voie.getIleD ( );
		ileA = voie.getIleA ( );

		//Si une voie a ces deux îles qui possèdent déjà une voie de couleur, alors on ne peut pas jouer car c'est un cycle.
		for ( VoieMaritime a : ileD.getEnsVoie ( ) )
			if ( a.getColorArc ( ) == couleur ) cycliqueIleD = true;

		for ( VoieMaritime a : ileA.getEnsVoie ( ) )
			if ( a.getColorArc ( ) == couleur ) cycliqueIleA = true;


		return cycliqueIleD && cycliqueIleA;
	}

}