package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Paquet
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	/**
	 * Différentes cartes de couleurs
	 */
	private static final String[] TAB_TYPE_CARTE = { "Vert", "Rouge", "Jaune", "Brun", "Multicolore" };
	
	/**
	 * Liste de cartes
	 */
	private List<Carte> pioche;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/**
	 * Constructeur vide de Paquet
	 */
	public Paquet ( )
	{
		this.pioche = new ArrayList<> ( );

		this.initialiserPaquet ( );
	}

	/**
	 * Constructuer qui prend une pioche déja crée
	 * @param liste
	 */
	public Paquet ( List<Carte> liste )
	{
		this.pioche = liste;
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	/**Prendre la première carte du paquet
	 * @return Carte
	 */
	public Carte piocher      (         ) { return this.pioche.remove ( 0 );   }

	/**
	 * Return la taille du Paquet
	 * @return int
	 */
	public int   taillePaquet (         ) { return this.pioche.size   (     ); }

	/**
	 * Return une carte à l'indice donnée en paramètre
	 * @param cpt
	 * @return Carte
	 */
	public Carte getCarte     ( int cpt ) { return this.pioche.get    ( cpt ); }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/**
	 * Initialise un paquet de 10 cartes
	 */
	private void initialiserPaquet ( )
	{
		for ( int cpt = 0 ; cpt < 5; cpt++ )
			this.pioche.add ( new Carte ( 'P', TAB_TYPE_CARTE[cpt%5] ) );
 
		for ( int cpt = 0 ; cpt < 5; cpt++ )
			this.pioche.add ( new Carte ( 'S', TAB_TYPE_CARTE[cpt%5] ) );

		Collections.shuffle ( this.pioche );
	}


	/**
	 * Afin de savoir s'il reste des cartes primaires
	 * 
	 * @return boolean 
	 */
	public boolean aEncorePrimaire ( )
	{
		for ( Carte carte : this.pioche )
			if ( carte.getTypeCarte ( ) == 'P' )
				return true;

		return false;
	}

	@Override
	public String toString ( )
	{
		String sRet = "";
		for ( Carte c : this.pioche )
			sRet += c.toString ( ) + "\n";

		return sRet;
	}

} 
