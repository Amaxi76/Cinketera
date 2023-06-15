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
	private Paquet       paquetManche1;
	private Paquet       paquetManche2;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Scenario ( Joueur j, List<Ile> ensIles, Color couleur, int num )
	{
		super ( j, couleur );

		this.numScenario = num;

		this.creerScenario(num);
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

			List<Carte> paquetManche1 = new ArrayList<> ( );
			List<Carte> paquetManche2 = new ArrayList<> ( );


			int cpt = 1; // cpt des paquet selon les manches
			do
			{
				// gestion des paquets prédéfinis
				Decomposeur dec = new Decomposeur ( line );
				
				List<Carte> paquet = ( cpt == 1 ) ? paquetManche1 : paquetManche2;

				for (int i = 0; i > -1 ; i++)
				{
					try 
					{
						String defCarte = dec.getString ( i );;

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

			this.paquetManche1 = new Paquet ( paquetManche1 );
			this.paquetManche2 = new Paquet ( paquetManche2 );

			line = sc.nextLine ( );

			// Permet de piocher la première carte

			super.setPaquet   ( this.paquetManche1 );
			super.tourSuivant ( );

			
			do
			{
				Decomposeur dec = new Decomposeur ( line );

				String nomIle1 = "";
				String nomIle2 = "";

				nomIle1 = dec.getString ( 0 );
				nomIle2 = dec.getString ( 1 );

				for ( VoieMaritime v : super.joueur.getPlateau ( ).getVoiesMaritimes ( ) )
				{
					
					if ( v.getIleA ( ).getNom ( ).equals ( nomIle1 ) && v.getIleD ( ).getNom ( ).equals ( nomIle2 ) ||
						 v.getIleD ( ).getNom ( ).equals ( nomIle1 ) && v.getIleA ( ).getNom ( ).equals ( nomIle2 )   )
						{
							if (super.getNumManche() == 1 && super.getNumTours() == 1 && this.paquetManche2 != null) 
							{
								// Si la manche change, on change de paquet
								super.setPaquet(this.paquetManche2);
								// Cependant, la manche a déjà prit la carte d'un autre paquet, alors on doit la réinitialiser
								super.paquet.piocher();
							}

							super.jouer ( v, true );
						}
				}

				line = sc.nextLine ( );
			}
			while ( !line.equals ( "" ) );

		}
		catch ( Exception e ) { }

	}

}

