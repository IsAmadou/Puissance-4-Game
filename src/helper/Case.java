package helper;

public class Case {
	private int contenu;
	private int coordx ;
	private int coordy;
	private int value;
	
		
	/**
	 * Constructeur pour creer une case de contenu 0
	 */
	public Case() {
		this.contenu = 0;
		this.value=0;
	} 
	
		
	/**
	 * Constructeur pour creer une case de contenu c
	 * @param contenu = c = 0 ou 1 ou 2
	 */
	public Case(int contenu) {
		if(contenu==0 || contenu==1 || contenu==2 ) {
			this.contenu = contenu;
		}else {
			this.contenu = 0;
		}
		this.value=0;
	} 
	
		
	/**
	 * Constructeur pour creer une case en copiant une autre case c
	 * @param c
	 */
	public Case(Case c) {
		this.contenu = c.contenu;
		this.coordx=c.coordx;
		this.coordy=c.coordy;
		this.value=c.value;

	}

	/**
	 * @return le contenu de la case
	 */
	public int getContenu() {
		return contenu;
	}

	/**
	 * Modifie une case en changeant son contenue c  
	 * @param contenu = c = 0 ou 1 ou 2
	 */
	public void setContenu(int contenu) {
		if(contenu==0 || contenu==1 || contenu==2 ) {
			this.contenu = contenu;
		}else {
			System.err.println("Modification impossible car le contenu de la case doit etre 0, 1 ou 2");
		}  
	}
	
	
	@Override
	public String toString() {
		if(getContenu()==0) {
			return " ";
		}
		return Integer.toString(getContenu());
		//return Integer.toString(value);
	}
	
	/**
	 * @return return le numero de la ligne du plateau sur laquelle
	 *  se trouve la case
	 */
	public int getCoordx() {
		return coordx;
	}
	
	/**
	 * Modifie le numero de la ligne du plateau sur laquelle
	 *  se trouve la case
	 * @param coordx
	 */
	public void setCoordx(int coordx) {
		this.coordx = coordx;
	}

	/**
	 * @return le numero de la case sur une ligne 
	 */
	public int getCoordy() {
		return coordy;
	}

	/**
	 * Modifie le numero de la case sur une ligne
	 * @param coordy
	 */
	public void setCoordy(int coordy) {
		this.coordy = coordy;
	}


	
	/**
	 * @return la valeur de la case 
	 */
	public int getValue() {
		return value;
	}


	
	/**
	 * Modifie la valeur de la case avec la valeur v
	 * @param value = v
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
