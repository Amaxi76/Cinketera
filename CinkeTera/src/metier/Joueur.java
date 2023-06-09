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
	private Manche[]    tabManche;

	private int         nbManches;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */
	
	public Joueur ( Plateau plateau )
	{
		this.tabManche = new Manche[2];
		this.nbManches = 0;
		this.plateau   = plateau;

		this.couleurs = new ArrayList<> ( Arrays.asList ( Color.BLUE, Color.RED ) );
		Collections.shuffle ( this.couleurs );

		this.lancerManche();
		// Attribution des couleurs de la manche
		
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public int    getNbManches    ( ) { return this.nbManches;            }
	public Manche getMancheActuel ( ) { return this.tabManche[nbManches]; }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public void lancerManche ( )
	{
		System.out.println("manche num" + this.nbManches);
		System.out.println("couleur : " + this.couleurs);
		Color couleur   = this.couleurs.remove(0);
		List<Ile> ligne = new ArrayList<>();

		for ( Ile i : this.plateau.getIles ( ) )	
			if ( ( i.getNom().equals("Ticó") && couleur.equals ( Color.RED )) || ( i.getNom().equals("Mutaa") && couleur.equals(Color.BLUE) ) )
				ligne.add ( i );
		
		System.out.println("couleur : " + this.couleurs);
		this.tabManche[this.nbManches ++] = new Manche ( this.nbManches, ligne, couleur, this.plateau.getVoiesMaritimes() );
	}


	public boolean jouer ( VoieMaritime voieMaritime )
	{
		if ( !this.tabManche[this.nbManches-1].getFinManche() )
			return this.tabManche[this.nbManches-1].jouer ( voieMaritime );

		System.out.println("fin de la manche");
		System.out.println(this.couleurs);
		this.lancerManche();
		return false;
	}

}