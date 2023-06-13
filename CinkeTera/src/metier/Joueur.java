package metier;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.util.Collections;

public class Joueur
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private Plateau     plateau;
	private List<Color> couleurs;
	private Partie      partie;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */
	
	/** Unique constructeur de joueur
	 * @param plateau
	 */
	public Joueur ( Plateau plateau )
	{
		this.plateau   = plateau;

		this.couleurs = new ArrayList<> ( Arrays.asList ( Color.BLUE, Color.RED ) );
		Collections.shuffle ( this.couleurs );

		this.lancerPartie ( );
		// Attribution des couleurs de la partie
		
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	/**	
	 * @return
	 */
	public Partie             getPartie        ( ) { return this.partie;                           }
	/**
	 * @return
	 */
	public Color              getCouleur       ( ) { return this.couleurs.get               ( 0 ); }
	public List<VoieMaritime> getVoieMaritimes ( ) { return this.plateau .getVoiesMaritimes (   ); }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/**
	 * 
	 */
	public void lancerPartie ( )
	{
		Color     couleurDebut = this.couleurs.remove ( 0 );
		List<Ile> ligne        = new ArrayList<> ( );

		for ( Ile i : this.plateau.getIles ( ) )
			if ( ( i.getNom ( ).equals ( "Ticó" ) && couleurDebut.equals ( Color.RED ) ) || ( i.getNom ( ).equals ( "Mutaa" ) && couleurDebut.equals ( Color.BLUE ) ) )
				ligne.add ( i );
		
		this.partie = new Partie ( this, ligne, couleurDebut );
	}

	public void lancerScenario ( )
	{

	}

	/**
	 * @param voieMaritime
	 * @return
	 */
	public boolean jouer ( VoieMaritime voieMaritime )
	{
		return this.partie.jouer ( voieMaritime );
	}

}