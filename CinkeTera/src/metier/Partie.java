package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.awt.geom.Line2D;


public class Partie
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	/**
	 * List<ile> de la couleur Rouge
	 */
	protected List<Ile> ligneR;

	/**
	 * List<ile> de la couleur Bleu
	 */
	protected List<Ile> ligneB;

	/**
	 * Boolean pour voir si c'est le premier trait
	 */
	protected boolean premierTrait;

	/**
	 * Entier numero de manche
	 */
	protected int     numManche;

	/**
	 * Entier numero du tour
	 */
	protected int     numTour;

	/**
	 * Entier, numéro du tour ou il y a eu biffurcation
	 */
	protected int     numTourBifurcation;

	/**
	 * Le Score
	 */
	protected int     score;

	/**
	 * La couleur de la ligne
	 */
	protected Color   coulLigne;

	/**
	 * La carte en cours
	 */
	protected Carte   carteEnCours;

	/**
	 * Le paquet de cartes
	 */
	protected Paquet  paquet;

	/**
	 * Le joueur
	 */
	protected Joueur  joueur;
	
	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/**
	 * Constructuer qui prend un jouer et sa couleur en paramètre
	 * @param j
	 * @param couleur
	 */
	public Partie ( Joueur j, Color couleur )
	{
		this.numManche          = 0;
		this.joueur             = j;
		this.paquet             = new Paquet ( );
		this.coulLigne          = couleur;
		this.score              = 0;
		this.numTour            = 0;
		this.numTourBifurcation = 3;//(int) ( Math.random ( ) * 3 );
		this.premierTrait       = true;

		for ( Ile i : this.joueur.getPlateau ( ).getIles ( ) )
		{
			if ( i.getNom ( ).equals ( "Ticó" ) )
				this.ligneR = new ArrayList<> ( Arrays.asList ( i ) );
			if ( i.getNom ( ).equals ( "Mutaa" ) )
				this.ligneB = new ArrayList<> ( Arrays.asList ( i ) );
		}
	}
	
	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */
	/**
	 * Return la liste d'iles de la ligne rouge
	 * @return List<Ile>
	 */
	public List<Ile> getLigneR       ( ) { return this.ligneR;                             }

	/**
	 * Return la liste d'iles de la ligne bleu
	 * @return List<Ile>
	 */
	public List<Ile> getLigneB       ( ) { return this.ligneB;                             }

	/**
	 * Return la couleur de la ligne
	 * @return Color
	 */
	public Color     getCoulLigne    ( ) { return this.coulLigne;                          }

	/**
	 * Return la carte en cours
	 * @return Carte
	 */
	public Carte     getCarteEnCours ( ) { return this.carteEnCours;                       }

	/**
	 * Return tout le paquet
	 * @return Paquet
	 */
	public Paquet    getPaquet       ( ) { return this.paquet;                             }

	/**
	 * Return le score en entier
	 * @return int
	 */
	public int       getScore        ( ) { return this.score;                              }

	/**
	 * Return true si c'est le premier trait 
	 * @return boolean
	 */
	public boolean   estPremierTrait ( ) { return this.premierTrait;                       }

	/**
	 * Return true si c'est la fin de la partie
	 * @return boolean
	 */
	public boolean   getFinPartie    ( ) { return this.numManche          >= 2;            }

	/**
	 * Return true si il y a eu biffurcation
	 * @return boolean
	 */
	public boolean   estBiffurcation ( ) { return this.numTourBifurcation == this.numTour; }

	/**
	 * Return numero du tour en entier
	 * @return int
	 */
	public int       getNumTours     ( ) { return this.numTour;                            }

	/**
	 * Return numero de la manche en entier
	 * @return int
	 */
	public int       getNumManche    ( ) { return this.numManche;                          }

	/**
	 * Return la liste d'îles qui sont en extremités
	 * @return List<Ile>
	 */
	public List<Ile> getEnsExtremites ( )
	{
		List<Ile> ensExtremites = new ArrayList<> ( );
		List<Ile> ensIles       = this.coulLigne.equals ( Color.RED ) ? this.ligneR : this.ligneB;

		if ( this.coulLigne.equals ( Color.BLUE ) && this.ligneB.size ( ) == 1 )
			return this.ligneB;

		if ( this.coulLigne.equals ( Color.RED  ) && this.ligneR.size ( ) == 1 )
			return this.ligneR;

		for ( Ile i : ensIles )
			if ( this.estExtremite ( i ) )
				ensExtremites.add ( i );

		return ensExtremites;
	}

	/**
	 * Return le nombre de regions visités
	 * @return int
	 */
	public int getNbRegionsVisite ()
	{
		List<Ile>    ensIles = new ArrayList<Ile>();


		for ( Ile i : this.ligneB )
			if ( !ensIles.contains(i) ) ensIles.add(i);

		for ( Ile i : this.ligneR )
			if ( !ensIles.contains(i) ) ensIles.add(i);


		return this.creerListReg(ensIles).size();
	}

	/* -------------------------------------- */
	/*             Modificateur               */
	/* -------------------------------------- */

	/**
	 * Set le paquet au paquet donnée en paramètre
	 * @param p
	 */
	public void setPaquet ( Paquet p ) { this.paquet = p; }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/**
	 * Return true si c'est possible de colorier la voie donnée en paramètre et colorie si veutJouer est vrai
	 * @param voie
	 * @param veutJouer
	 * @return boolean
	 */
	public boolean jouer ( VoieMaritime voie, boolean veutJouer )
	{
		List<Ile> ligne     = ( this.coulLigne.equals ( Color.RED ) ? this.ligneR : this.ligneB );
		List<Ile> ensExIles = this.getEnsExtremites ( );

		// Si aucune carte n'est sélectionnée, on ne peut pas jouer
		if ( this.carteEnCours == null ) return false;

		// Définit l'ile d'arrivée
		Ile ileArrive = ( ligne.contains ( voie.getIleA ( ) ) ? voie.getIleD ( ) : voie.getIleA ( ) );
		Ile ileDepart = ( ligne.contains ( voie.getIleA ( ) ) ? voie.getIleA ( ) : voie.getIleD ( ) );

		// Regarde si l'ile que l'on veut relié est bien de la même couleur que la carte
		if ( !ileArrive.getCouleur ( ).equals ( this.carteEnCours.getCouleurCarte ( ) ) && !this.carteEnCours.getCouleurCarte ( ).equals ( "Multicolore" ) )
			return false;
		
		// Regarde si la voie n'est pas déjà colorié
		if ( voie.getEstColorie ( ) )
			return false;

		// Regardes si l'ile de départ est une extrémité
		if ( !ensExIles.contains ( ileDepart ) && this.numTour != this.numTourBifurcation ) return false;
		
		// Une ligne ne peut pas croiser d'autre ligne coloriée
		for ( VoieMaritime v : this.joueur.getPlateau ( ).getVoiesMaritimes ( ) )
			if ( intersection ( voie, v ) && ( v.getColorArc ( ) != null ) ) return false;
			
		if ( this.estPremierTrait ( ) && this.coulLigne.equals ( Color.RED  ) && ileDepart.getNom ( ).equals ( "Ticó" ) )
		{
			if (veutJouer)
			{
				this.premierTrait = false;
				voie.setCouleur  ( this.coulLigne );
				ligne.add        ( ileArrive      );
				this.tourSuivant (                );
				return true;
			}
		}

		if ( this.estPremierTrait ( ) && this.coulLigne.equals ( Color.BLUE ) && ileDepart.getNom ( ).equals ( "Mutaa" ) )
		{
			if (veutJouer)
			{
				this.premierTrait = false;
				voie.setCouleur  ( this.coulLigne );
				ligne.add        ( ileArrive      );
				this.tourSuivant (                );
				return true;
			}
		};

		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !this.estRelie ( voie, this.coulLigne ) && !this.estPremierTrait ( ) ) return false;

		//On ne peut pas jouer s'il y a un cycle de même couleur

		if ( this.cyclique ( voie, this.coulLigne ) ) return false;

		if ( !this.estBiffurcation ( ) && !this.estExtremite ( voie.getIleA ( ) ) && !this.estExtremite ( voie.getIleD ( ) ) && !this.estPremierTrait ( ) )
			return false;
		
		// Si on arrive ici, c'est que tout est bon, on peut donc colorier la voie

		if ( veutJouer )
		{
			voie.setCouleur  ( this.coulLigne );
			ligne.add        ( ileArrive      );
			this.tourSuivant (                );
		}

		return true;
	}

	/**
	 * Return true si l'île est une extremité
	 * @param i
	 * @return boolean
	 */
	private boolean estExtremite ( Ile i )
	{
		int nbColorier = 0;

		for ( VoieMaritime v : i.getEnsVoie ( ) )
			if ( v.getColorArc ( ) != null && v.getColorArc ( ).equals ( this.coulLigne ) ) 
				nbColorier ++;

		return nbColorier == 1;
	}

	/** Méthode qui permet de passer un tour (changer la carte )
	 * 
	 */
	public void tourSuivant ( )
	{
		if ( !this.paquet.aEncorePrimaire ( ) && this.getFinPartie ( ) )
			return;

		if ( !this.paquet.aEncorePrimaire ( ) )
		{
			this.initialiserManche( );
		}

		this.numTour++;

		this.carteEnCours = this.paquet.piocher ( );
		//System.out.println(this.carteEnCours);
	}

	/**
	 * Méthode pour initialiser une manche
	 */
	public void initialiserManche ( )
	{
		System.out.println("INITIALISATION MANCHE de PARTIE");
		this.coulLigne   = this.joueur.getCouleur ( );
		this.paquet      = new Paquet ( );
		this.numTour     = 0;
		this.numManche++;
		this.numTourBifurcation = 3;//(int) ( Math.random ( ) * 3 );
		this.premierTrait = true;
	}

	/**
	 * Return le score
	 * @return int
	 */
	public int calculerScore ( )
	{
		List<Region> ensRegionsR, ensRegionsB;

		int          tmp, max;
		int          score = 0;

		if ( this.ligneR.size ( ) <= 1 && this.ligneB.size ( ) <= 1 ) return this.score = 0;

		if ( this.ligneR.size( ) >= 1 )
		{
			ensRegionsR = this.creerListReg ( this.ligneR );

			score += this.scorePrincipale      ( ensRegionsR, this.ligneR );
			score += this.calculBonusLigne     ( this.ligneR, Color.RED   );
		}

		if ( this.ligneB.size( ) >= 1 )
		{
			ensRegionsB = this.creerListReg ( this.ligneB );

			score += this.scorePrincipale      ( ensRegionsB, this.ligneB );
			score += this.calculBonusLigne     ( this.ligneB, Color.BLUE  );
		}

		score += this.calculBonusIle ( );

		this.score = score;

		return score;

	}

	/**
	 * Score qui calcule le nombre d'île maximum par region multiplié par le nombre de regions visités
	 * @param ensRegions
	 * @param ligne
	 * @return int
	 */
	private int scorePrincipale ( List<Region> ensRegions, List<Ile> ligne )
	{
		int tmp    =  0;
		int max    = -1;

		if ( ligne.size() <= 1 ) return 0;

		for ( Region r : ensRegions )
		{

			for ( Ile i : r.getEnsIles ( ) )
				if ( ligne.contains ( i ) )
					tmp ++;

			if ( tmp > max ) max = tmp;

			tmp = 0;
		}

		return max * ensRegions.size ( );
	}

	/**
	 * Calcul du score avec les valuers bonus
	 * @param ligne
	 * @param coulLigne
	 * @return int
	 */
	private int calculBonusLigne ( List<Ile> ligne, Color coulLigne )
	{
		List<VoieMaritime> ensVoie = this.creeVoieChoisies ( );

		List<String> tabBonusLigne = new ArrayList<String>();

		int score = 0;


		for ( Ile i : ligne )
		{
			for ( VoieMaritime v : i.getEnsVoie() )
				if ( coulLigne != null && !ensVoie.contains(v) && coulLigne.equals(v.getColorArc()) )
				{
					ensVoie.add(v);
					score += v.getValeur();
				}
		}

		return score;
	}

	/**
	 * Calcul du score quand y a une île visités par les deux lignes
	 * @return int
	 */
	private int calculBonusIle ( )
	{
		int score       = 0;
		List<Ile> ligne = new ArrayList<> ( );

		for ( Ile i : this.ligneB )
			if ( !ligne.contains ( i ) ) ligne.add ( i );

		for ( Ile i : this.ligneR )
			if ( !ligne.contains ( i ) ) ligne.add ( i );

		for ( Ile i : ligne )
			if ( this.ligneB.contains ( i ) && this.ligneR.contains ( i ) ) score += 2;

		return score;
	}

	/**
	 * Return la ligne de regions visité par la ligne
	 * @param ligne
	 * @return List<Region>
	 */
	private List<Region> creerListReg ( List<Ile> ligne )
	{
		List<Region> ensRegions = new ArrayList<> ( );

		for ( Ile i : ligne )
			if ( !ensRegions.contains ( i.getRegion ( ) ) )
				ensRegions.add ( i.getRegion ( ) );

		return ensRegions;
	}

	/** Méthode qui indique crée une liste des voies que l'utilisateur à choisie selon la couleur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	private List<VoieMaritime> creeVoieChoisies ( )
	{
		List<Ile>          ensIle;
		List<VoieMaritime> ensVoie;

		ensVoie = new ArrayList<> ( );

		ensIle = this.coulLigne.equals( Color.RED ) ? this.ligneR : this.ligneB;

		for ( Ile i : ensIle )
			for ( VoieMaritime voie : i.getEnsVoie ( ) )
				if ( voie.getEstColorie ( ) && voie.getColorArc ( ).equals ( this.coulLigne ) && ensVoie.contains ( voie ) )
					ensVoie.add ( voie );

		return ensVoie;
	}


	/** Méthode qui indique si l'arc prit en paramètre est rataché aux autres arcs déja colorer
	 * @param v est l'arc qui est sélectionner par l'utilisateur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	public boolean estRelie ( VoieMaritime v, Color coul )
	{
		Ile ileA = v.getIleA ( );
		Ile ileD = v.getIleD ( );

		// Il doit y avoir une voie coloriée dans la liste d'une des deux îles de la voie
		for ( VoieMaritime voie : ileA.getEnsVoie ( ) )
			if ( !voie.equals ( v ) && voie.getEstColorie ( ) && voie.getColorArc ( ).equals ( coul ) ) return true;

		for ( VoieMaritime voie : ileD.getEnsVoie ( ) )
			if ( !voie.equals ( v ) && voie.getEstColorie ( ) && voie.getColorArc ( ).equals ( coul ) ) return true;

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

	/**Retourne si la voie forme un cycle à partir d'une couleur
	 * @param voie voie maritime sélectionnée par l'utilisateur
	 * @param couleur couleur que doit prendre la voie maritime
	 * @return true si la voie maritime forme un cycle, sinon false
	 */
	public boolean cyclique ( VoieMaritime voie, Color couleur )
	{
		Ile ileD;
		Ile ileA;

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