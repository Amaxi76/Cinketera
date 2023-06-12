package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Paquet
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	private static final String[] TAB_TYPE_CARTE = { "Vert", "Rouge", "Jaune", "Brun", "Multicolore" };

	private List<Carte> pioche;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Paquet ( )
	{
		this.pioche = new ArrayList<> ( );

		this.initialiserPaquet ( );
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public Carte piocher      ( int cpt ) { return this.pioche.remove ( cpt ); }
	public int   taillePaquet (         ) { return this.pioche.size   (     ); }
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
	 * @return
	 */
	public boolean aEncorePrimaire ( )
	{
		for ( Carte carte : this.pioche)
			if ( carte.getTypeCarte() == 'P' )
				return true;

		return false;
	}

	@Override
	public String toString()
	{
		String sRet = "";
		for ( Carte c : this.pioche )
			sRet += c.toString() + "\n";

		return sRet;
	}

} 
