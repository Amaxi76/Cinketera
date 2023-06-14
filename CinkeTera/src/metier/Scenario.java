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

	public Scenario (Joueur j, List<Ile> ensIles, Color couleur, int num)
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

			String line = sc.nextLine();

			List<Carte> listManche1 = new ArrayList<>();
			List<Carte> listManche2 = new ArrayList<>();

			do
			{
				// gestion des paquets prédéfinis

				int cpt = 1; // cpt des paquet selon les manches
				Decomposeur dec = new Decomposeur (line);

				if (cpt == 1)
				{
					for (int i = 0; i > -1 ; i++)
					{
						try 
						{
							String defCarte = dec.getString(i);;

							char type = defCarte.charAt(0);
							String couleur = defCarte.substring(1);

							listManche1.add(new Carte (type, couleur));
							
						} 
						catch (Exception e) { break; }
					}
				}
				if (cpt == 2)
				{

					for (int i = 0; i > -1 ; i++)
					{
						try 
						{
							String defCarte = dec.getString(i);;

							char type = defCarte.charAt(0);
							String couleur = defCarte.substring(1);

							listManche2.add(new Carte (type, couleur));
							
						} 
						catch (Exception e) { break; }
					}
				}


				line = sc.nextLine();
				cpt++;
			}
			while(!line.equals(""));

			this.paquetManche1 = new Paquet(listManche1);
			this.paquetManche2 = new Paquet(listManche2);

			super.tourSuivant();

			line = sc.nextLine();

			do
			{

				Decomposeur dec = new Decomposeur (line);

				String nomIle1 = "";
				String nomIle2 = "";

				nomIle1 = dec.getString(0);
				nomIle2 = dec.getString(1);

				for (VoieMaritime v : super.joueur.getPlateau().getVoiesMaritimes())
				{
					if (v.getIleA().getNom().equals(nomIle1) && v.getIleD().getNom().equals(nomIle2) ||
						v.getIleD().getNom().equals(nomIle1) && v.getIleA().getNom().equals(nomIle2)   )
						{
							super.jouerScenario(v, true);
						}
				}

				line = sc.nextLine();
			}
			while(!line.equals(""));

		}
		catch (Exception e) {}

	}

		
}

