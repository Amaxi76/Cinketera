package metier;

import java.util.ArrayList;
import java.util.List;

public class Ile
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	private int posX;
	private int posY;

	private int posXImage;
	private int posYImage;

	private String nom;
	private String couleur;
	private Region region;

	private List<VoieMaritime> ensVoie;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/**
	 * Constructeur
	 * 
	 * @param nom nom du noeud
	 * @param x   position x
	 * @param y   position y
	 */

	public Ile ( String nom, String couleur, int posX, int posY, int xImage, int yImage)
	{
		this.nom       = nom;
		this.couleur   = couleur;
		this.posX      = posX;
		this.posY      = posY;
		this.posXImage = xImage;
		this.posYImage = yImage;
		this.region    = null;

		this.ensVoie  = new ArrayList<>();
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public String             getNom       () { return this.nom;       }
	public int                getPosX      () { return this.posX;      }
	public int                getPosY      () { return this.posY;      }
	public Region             getRegion    () { return this.region;    }
	public List<VoieMaritime> getEnsVoie   () { return this.ensVoie;   }
	public String             getCouleur   () { return this.couleur;   }
	public int                getPosXImage () { return this.posXImage; }
	public int                getPosYImage () { return this.posYImage; }

	/* -------------------------------------- */
	/*              Modificateur              */
	/* -------------------------------------- */

	public void setPosX    ( int posX         ) { this.posX = posX;                                 }
	public void setPosY    ( int posY         ) { this.posY = posY;                                 }
	public void ajouterArc ( VoieMaritime arc ) { this.ensVoie.add(arc);                            }
	public void setRgn     ( Region region	  ) { this.region = region; region.ajouterIle ( this ); }
	public void setCouleur ( String couleur   ) { this.couleur = couleur;                           }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/**
	 * toString
	 * 
	 * @return 
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet += "L'ile "                     + this.getNom();
		sRet += " appartient à la région : " + this.region.getNomRegion();

		return sRet;
	}
	
}