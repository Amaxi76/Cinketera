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
	public Joueur ( )
	{
		this.plateau   = new Plateau();
		this.couleurs = new ArrayList<> ( Arrays.asList ( Color.BLUE, Color.RED ) );
		
		Collections.shuffle ( this.couleurs );

		this.lancerPartie ( );
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public Plateau            getPlateau       ( ) {return this.plateau;}
	/** Acceseur qui retourne la partie du joueur
	 * @return la partie du joueur
	 */
	public Partie             getPartie        ( ) { return this.partie;                           }
	
	/** Accesseur qui permet d'obtenir une couleur du tableau du joueur et de la supprimer en même temps
	 * @return une couleur du tableau
	 */
	public Color              getCouleur       ( ) { return this.couleurs.get               ( 0 ); }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/** Méthode qui permet de lancer une partie selon la couleur du joueur avec la bonne île de départ
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

	/** Méthode passerelle entre la partie et le joueur
	 * @param voieMaritime voieMaritime selectionnée par le joueur
	 * @return un boolean qui nous indique si le joueur a pu jouer
	 */
	public boolean jouer ( VoieMaritime voieMaritime )
	{
		return this.partie.jouer ( voieMaritime );
	}

	public boolean estJouable(Ile ile, List<Ile> lstExtremite)
	{
		for (Ile extremite : lstExtremite)
		{
			for (Ile ileArrivee : extremite.getVoisins())
			{
				
				if (this.getPartie().getCarteEnCours() != null && ile == ileArrivee)
					if (ileArrivee.getCouleur().equals(this.getPartie().getCarteEnCours().getCouleurCarte() ) || this.getPartie().getCarteEnCours().getCouleurCarte().equals("Multicolore")) 
						return true;
			}
		}
		return false;
	}

}