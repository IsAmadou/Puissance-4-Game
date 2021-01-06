package helper;

public class Joueur {
	private int num_joueur;
	private Case last_case;
	private Plateau plateau;
	
	
	public Joueur(Plateau pl) {
		this.plateau = new Plateau(pl);
	}
	
	public int getNum_joueur() {
		return num_joueur;
	}

	public void setNum_joueur(int num_joueur) {
		this.num_joueur = num_joueur;
	}
	
	public Case getLast_case() {
		return last_case;
	}

	public void setLast_case(Case last_case) {
		this.last_case = last_case;
	}
	
	public Plateau getPlateau() {
		return plateau;
	}

	/**
	 * Ajout de pion du joueur sur le plateau et conservation de la case de l ajout
	 * @param numColonne = numero de la colonne pour ajouter
	 * @param numJoueur = numero du joueur
	 * @return la case sur laquelle on effectue l ajout
	 */
	public Case ajoutPion(int numColonne, int numJoueur) {
		Case c = null;
		c=this.plateau.ajoutPion(numColonne, numJoueur);
		if(c!=null) {
			this.last_case=c;
		}
		return c;
	}
}
