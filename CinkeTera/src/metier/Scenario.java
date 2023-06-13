package metier;

import metier.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
import iut.algo.*;
import java.awt.Color;

public class Scenario /*extends Partie*/
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private 	int 				numScenario;
	private 	List<Ile>          	lstIles;
	private 	List<VoieMaritime> 	lstVoiesMaritimes;
	private 	List<Region>       	lstRegions;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Scenario(int num)
	{
		this.numScenario = num;


	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public int getNumScenario ( ) { return this.numScenario; }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	public void initialiserScenario ( )
	{

		try
		{
			// Prend la première ligne de notre ficher contenant les noeuds du graph
			Scanner sc = new Scanner ( new FileInputStream ( "donnees/scenario" + this.numScenario + ".data" ) );

			//Pour sauter les deux premières lignes
			sc.nextLine ( );
			sc.nextLine ( );
			String line = sc.nextLine ( );

			//Creer les îles
			do
			{
				Decomposeur dec = new Decomposeur ( line );

				String nom,couleur;
				int posX,posY,posXImage,posYImage;

				nom = couleur = "";
				posX = posY = posXImage = posYImage = 0;


				nom			= dec.getString(0);
				couleur		= dec.getString(1);

				posX		= dec.getInt(2);
				posY		= dec.getInt(3);

				posXImage	= dec.getInt(4);
				posYImage	= dec.getInt(5);
				
				this.lstIles.add(new Ile(nom,couleur,posX,posY,posXImage,posYImage));

				line = sc.nextLine();
			}
			while(!line.equals(""));

			//Créer les regions
			line = sc.nextLine();
			do
			{
				Decomposeur dec = new Decomposeur ( line );

				String nomRegion = dec.getString(0);

				Region region = new Region(nomRegion);

				//modulaire
				for (int cpt = 1; cpt > -1 ; cpt++)
				{
					try 
					{
						String nomIle = dec.getString(cpt);

						for (Ile ile : this.lstIles)
							if(ile.getNom().equals(nomIle))
								ile.setRgn(region);
						
					} catch (Exception e) { break; }
				}		

				this.lstRegions.add(region);

				line = sc.nextLine();	
			}
			while(!line.equals(""));

			//Créer les voies maritimes
			while (sc.hasNextLine()) 
			{
				Decomposeur dec = new Decomposeur ( sc.nextLine() );

				String nomIle1,nomIle2, couleurString;
				Color couleur;
				Ile ile1,ile2;

				ile1 = ile2 = null;
				couleur = null;

				nomIle1 = dec.getString(0);
				nomIle2 = dec.getString(1);
				
				couleurString = dec.getString(2);

				for (Ile ile : this.lstIles)
				{
					if (ile.getNom().equals(nomIle1)) ile1 = ile;
					if (ile.getNom().equals(nomIle2)) ile2 = ile;
				}

				if   (couleurString.equals("BLEU" )) { couleur = Color.BLUE; }
				if   (couleurString.equals("ROUGE")) { couleur = Color.RED;  }
					
				VoieMaritime v = VoieMaritime.creerVoieMaritime(nomIle1 + "-" + nomIle2,ile1,ile2,1, couleur);

				this.lstVoiesMaritimes.add(v);
				ile1.ajouterArc(v);
				ile2.ajouterArc(v);

			}

		}
		catch(Exception e){e.printStackTrace();}
	}	

} 
