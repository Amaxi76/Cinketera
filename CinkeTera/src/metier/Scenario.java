package metier;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import iut.algo.*;
import java.awt.Color;

public class Scenario extends Partie
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private int          numScenario;
	private List<Carte>  paquetManche1;
	private List<Carte>  paquetManche2;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Scenario ( Joueur j, List<Ile> ensIles, Color couleur, int num )
	{
		super ( j, couleur );

		this.numScenario = num;

		this.paquetManche1 = new ArrayList<> ( );
		this.paquetManche2 = new ArrayList<> ( );

		this.creerScenario ( num );
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public int getNumScenario ( ) { return this.numScenario; }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public void creerScenario ( int num )
	{

		try 
		{
			// Prend la première ligne de notre ficher contenant les noeuds du graph

			Scanner sc = new Scanner ( new FileInputStream ( "donnees/scenario"+ num +".data" ) );

			String line = sc.nextLine ( );



			int cpt = 1; // cpt des paquet selon les manches
			do
			{
				// gestion des paquets prédéfinis
				Decomposeur dec = new Decomposeur ( line );
				
				List<Carte> paquet = ( cpt == 1 ) ? this.paquetManche1 : this.paquetManche2;

				for ( int i = 0; i > -1 ; i++ )
				{
					try 
					{
						String defCarte = dec.getString ( i );

						char   type    = defCarte.charAt    ( 0 );
						String couleur = defCarte.substring ( 1 );

						paquet.add ( new Carte ( type, couleur ) );
						
					} 
					catch ( Exception e ) { break; }
				}
				
				line = sc.nextLine ( );
				cpt++;
			}
			while ( !line.equals ( "" ) );

			line = sc.nextLine ( );

			

			// Permet de piocher la première carte
			super.setPaquet   ( new Paquet ( new ArrayList<> ( this.paquetManche1 ) ) );

			// Validax

			super.tourSuivant ( );

			do
			{
				
				Decomposeur dec = new Decomposeur ( line );

				String nomIle1 = "";
				String nomIle2 = "";

				nomIle1 = dec.getString ( 0 );
				nomIle2 = dec.getString ( 1 );

				for ( VoieMaritime v : super.joueur.getPlateau ( ).getVoiesMaritimes ( ) )
					if ( v.getIleA ( ).getNom ( ).equals ( nomIle1 ) && v.getIleD ( ).getNom ( ).equals ( nomIle2 ) ||
						 v.getIleD ( ).getNom ( ).equals ( nomIle1 ) && v.getIleA ( ).getNom ( ).equals ( nomIle2 )   )
							super.jouer ( v, true );

				line = sc.nextLine ( );

			}
			while ( !line.equals ( "" ) );

		}
		catch ( Exception e ) { }

	}


	public void initialiserManche (  )
	{
		super.coulLigne   = super.joueur.getCouleur ( );
		super.paquet      = new Paquet ( new ArrayList<>(this.paquetManche2) );
		super.numTour     = 0;
		super.numManche++;
		super.numTourBifurcation = 3;//(int) ( Math.random ( ) * 3 );
		super.premierTrait = true;;
	}

}

