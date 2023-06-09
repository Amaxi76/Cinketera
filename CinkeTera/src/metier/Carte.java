package metier;

public class Carte
{

	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private static int nbCartePrimaire   = 0;
	private static int nbCarteSecondaire = 0;

	private char   typeCarte;
	private String couleurCarte;
	private int    idCarte;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	public Carte ( char typeCarte, String couleurCarte )
	{
		this.typeCarte    = typeCarte;
		this.couleurCarte = couleurCarte;

		if ( typeCarte == 'P' ) this.idCarte = ++ Carte.nbCartePrimaire;
		else                    this.idCarte = ++ Carte.nbCarteSecondaire;
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	public char   getTypeCarte    ( ) { return this.typeCarte;    }
	public String getCouleurCarte ( ) { return this.couleurCarte; }
	public int    getIdCarte      ( ) { return this.idCarte;      }

	/* -------------------------------------- */
	/*              Modificateur              */
	/* -------------------------------------- */

	public void setTypeCarte    ( char typeCarte    )   { this.typeCarte = typeCarte;       }
	public void setCouleurCarte ( String couleurCarte ) { this.couleurCarte = couleurCarte; }

	/* -------------------------------------- */
	/*                Méthodes                */
	/* -------------------------------------- */

	public String toString ( )
	{
		return "Carte [typeCarte=" + this.typeCarte + ", couleurCarte=" + this.couleurCarte + "]";
	}
}

