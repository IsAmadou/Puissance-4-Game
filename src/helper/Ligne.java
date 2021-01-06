package helper;

public class Ligne {
	private Case[] cases_tab;

		
	/**
	 * Construteur pour creer une ligne de 1 case
	 */
	public Ligne() {
		this.cases_tab = new Case[1];
		cases_tab[0]=new Case();
	}
	
		
	/**
	 * Construteur pour creer une ligne de n cases
	 * @param taille = n
	 */
	public Ligne(int taille) {
		this.cases_tab = new Case[taille];
		for (int i=0;i<taille;i++) {
			cases_tab[i]=new Case();
			cases_tab[i].setCoordy(i);
		}
	}
	
		
	/**
	 * Construteur pour creer une ligne en copiant une autre ligne l
	 * @param l
	 */
	public Ligne(Ligne l) {
		this.cases_tab = l.cases_tab;
	}
	
		
	/**
	 * Recuperer la ieme case de la ligne
	 * @param i
	 * @return  la case
	 */
	public Case getX(int i) {
		return cases_tab[i-1];
	}

	
	/**
	 * Modifier le contenue de la ieme case de la ligne 
	 * la methode setContenu se chargera du test de la valeur
	 * @param i
	 * @param valeur
	 */
	public void setX(int i, int valeur) {
		Case c = cases_tab[i-1];
		c.setContenu(valeur);
		cases_tab[i-1]=c;
	}
	
	
	/**
	 * Modifier une ligne a partir de 1 chaine de caractere ch de meme taille
	 * @param ValInit = ch
	 * Ex:0010120
	 */
	public void setLigne(String ValInit) {
		if (ValInit.length()==this.getTaille()){
			for(int i=1;i<=ValInit.length();i++) {
				this.setX(i, Character.getNumericValue(ValInit.charAt(i-1)));
			}
		}else {
			System.err.println("Valeurs insuffisantes ou en exces pour initialiser ligne");
		}
	}
	
	
		 
	/**
	 * @return la taille de la ligne
	 */
	public int getTaille() {
		return cases_tab.length;
	}
	
	
	/**
	 * Verifie si un des joueurs a gagner sur une ligne 
	 * @return false ou true en cas de victoire
	 */
	public boolean gagne() {
		for(int i=0;i<=(getTaille()-4);i++) {
			if((getX(i+1).getContenu()==1 && getX(i+2).getContenu()==1 && 
				getX(i+3).getContenu()==1 && getX(i+4).getContenu()==1) ||
				(getX(i+1).getContenu()==2 && getX(i+2).getContenu()==2 && 
				getX(i+3).getContenu()==2 && getX(i+4).getContenu()==2)) {	
				return true;
			}
		}
		return false;
	}
	
	/* 
	 * @return une chaine de caracteres symbolisant une ligne de cases
	 */
	@Override
	public String toString() {
		String ligne = "";
		for (Case c : cases_tab) {
			ligne+=c.toString();
		}
		return ligne;
	}

	/**
	 * Change la valeur  des cases sur une ligne
	 */
	public void changeValue() {
		if(this.getTaille()>=4) {
			for(int i =1;i<=this.getTaille();i++) {
				if((i+3)<=this.getTaille() && i<=4) {
					cases_tab[i-1].setValue(i);
				}else if ((i+3)<=this.getTaille() && i>4) {
					cases_tab[i-1].setValue(4);
				}else if ((i>=1 && i<=4) && this.getTaille()==4) {
					cases_tab[i-1].setValue(1);
				}else if ((i+3)>this.getTaille() && this.getTaille()!=5) {
					cases_tab[i-1].setValue((this.getTaille()-i)+1);
				}else if((i+3)>this.getTaille() && this.getTaille()==5) {
					if(i==3) {	
						cases_tab[i-1].setValue(2);
					}else if (i!=3){
						cases_tab[i-1].setValue((this.getTaille()-i)+1);
					}
				}		
			}
		}
	}


}
