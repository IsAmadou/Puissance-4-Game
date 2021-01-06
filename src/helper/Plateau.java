package helper;



public class Plateau {
	
	private Ligne[] lignes_tab;
	private int tab_line;
	private int tab_col;
	
		
	/**
	 * Construteur pour creer un plateau de 1 ligne et une case
	 */
	public Plateau() {
		this.lignes_tab= new Ligne[1];
		lignes_tab[0]=new Ligne();
		this.tab_line =1;
		this.tab_col=1;
		lignes_tab[0].changeValue();
	}
	
		
	/**
	 * Construteur pour creer un plateau de n ligne et m case par ligne
	 * @param nbr_ligne = n
	 * @param nbr_col   = m
	 */
	public Plateau(int nbr_ligne, int nbr_col) {
		this.lignes_tab= new Ligne[nbr_ligne];
		for(int i=0;i<nbr_ligne;i++) {
			lignes_tab[i]=new Ligne(nbr_col);
			lignes_tab[i].changeValue();
			for(int j=1;j<=nbr_col;j++) {
				lignes_tab[i].getX(j).setCoordx(Math.abs(i-(nbr_ligne-1)));
			}
		}
		this.tab_line =nbr_ligne;
		this.tab_col=nbr_col;
	}
	
		
	/**
	 * Construteur pour creer un plateau en copiant un autre plateau pl
	 * @param pl
	 */
	public Plateau(Plateau pl) {
		this.lignes_tab=pl.lignes_tab;
		this.tab_line =pl.tab_line;
		this.tab_col=pl.tab_col;
	}
	
	 	
	/**
	 *Construteur pour creer un plateau a partir 1e chaine de caractere ch
	 * @param ch  contenant la taille du plateau et son contenu
	 *  Ex: 6x7-211222112211201112210121212000200000000000 
	 */
	public Plateau(String ch) {
		String[] ch_part=ch.split("-");
		String[] taille = ch_part[0].split("x");
		this.tab_line=Integer.parseInt(taille[0]);
		this.tab_col=Integer.parseInt(taille[1]);
		
		int j=0;
		this.lignes_tab=new Ligne[tab_line];
		for(int i=(tab_line*tab_col);i>0;i-=tab_col){
			String line=ch_part[1].substring((i-tab_col),i);
				this.lignes_tab[j]=new Ligne(tab_col);
				this.lignes_tab[j].setLigne(line);
				this.lignes_tab[j].changeValue();
				for(int k=1;k<=tab_col;k++) {
					lignes_tab[j].getX(k).setCoordx(Math.abs(j-(tab_line-1)));
				}
				j++;
		}	
	}
	
	
	/**
	 * @param x 
	 * @param y
	 * @return la case x de la ligne y 
	 */
	public Case getXY(int x, int y) {
		int num_ligne= Math.abs(y-(tab_line-1));
		return lignes_tab[num_ligne].getX(x+1);
	}
	
	
	 /**
	  * Verifie le contenue de la derniere case de la colonne col pour savoir
	  * si elle est pleine 
	 * @param col
	 * @return etat vide ou plein de la colonne en fonction de cette case 
	 */
	public String colonnePleine(int col) {
		String etat = "pleine";
		if(getXY(col,(tab_line-1)).getContenu()==0) {//lignes_tab[0].getX(col+1).getContenu(); 
			etat="vide";
		}
		return etat;
	 }
	 
	
	 /**
	  * Ajoute le numero du joueur nj dans la derniere case vide de la colonne col 
	 * @param numColonne = col
	 * @param numJoueur  = nj = 1 ou 2
	 * @return la case modifier 
	 */
	public Case ajoutPion(int numColonne, int numJoueur) {
		Case c = null;
		 if (colonnePleine(numColonne).equals("vide")) {
			 int num_line = 0;
			 for(int i=0;i<tab_line;i++) {
				 if((getXY(numColonne,i).getContenu())==0) {
					 num_line=i;
					 c=new Case(getXY(numColonne,i));
					 break;
				 }
			 }
			getXY(numColonne,num_line).setContenu(numJoueur);
			return c;
		 }
		 return c;
	 }
	 
	 
	@Override
 	 public String toString() {
		 String plateau ="";
		 for(Ligne l : lignes_tab){
			plateau+= "|"+l.toString()+"|"+"\n";
		}
		return plateau;
	}
	 
	 public String toStringPourIA() {
		String plateau=tab_line+"x"+tab_col+"-";
		 for(int i=0;i<tab_line;i++) {
			for(int j=0;j<tab_col;j++) {
				plateau+=getXY(j,i).getContenu();
			}
		 }
		return plateau;
	}
	 
	 /**
	  * Verifie si un des joueurs a gagner sur une ligne x 
	 * @param num_ligne = x
	 * @return false ou true en cas de victoire
	 */
	public boolean gagneLigne(int num_ligne) {
		boolean rep=false;
		rep = lignes_tab[Math.abs(num_ligne-(tab_line-1))].gagne();
		 return rep;
	 }
	 
	 /**
	  * Verifie si un des joueurs a gagner sur une colonne y
	 * @param num_colonne = y
	 * @return false ou true en cas de victoire
	 */
	public boolean gagneColonne(int num_colonne) {
			boolean rep;
			Ligne l = new Ligne(tab_line);
			String ValInit = "";
			for(int i =0; i<tab_line;i++) {
				ValInit+=getXY(num_colonne,i).getContenu();
			}
			l.setLigne(ValInit);
			rep=l.gagne();
			 return rep;
		 }
	 
	/**
	 * @param num_ligne = x
	 * @param num_colonne =y
	 * @return la premiere diagonale du bas vers le haut et 
	 * de la droite vers la gauche a partir de la case(x,y)
	 */
	public Ligne Diagonale(int num_ligne, int num_colonne) {
		Ligne diagonale=null;
		 String diagonale1="";  
		 int j,k;	j=k= num_colonne;
		 if(num_colonne!=0 && num_colonne!=(tab_col-1)) {
			 for(int i=num_ligne;i<tab_line;i++) {
				 diagonale1+=getXY(j, i).getContenu();
				 j--;
				 if(j<0) {
					 break;
				 }
			 }
			 for(int i=(num_ligne-1);i>=j && i>=0;i--) {
				 k++;
				 diagonale1=getXY(k,i).getContenu()+diagonale1;
				 if(k==(tab_col-1)) {
					 break;
				 }
			 }
		 }else if(num_colonne==0 && num_ligne!=0) {
			 for(int i=num_ligne;i>=0 && j<tab_col;i--) {
				 diagonale1=getXY(j, i).getContenu()+diagonale1;
				 j++;
			 }
		 }else if(num_colonne==(tab_col-1) && num_ligne!=(tab_line-1)) {
			 for(int i=num_ligne;i<tab_line && j>=0;i++) {
				 diagonale1+=getXY(j, i).getContenu();
				 j--; 
			 }
		 }
		
		 if(diagonale1!="" && diagonale1.length()>1) {
			 diagonale = new Ligne(diagonale1.length());
			 diagonale.setLigne(diagonale1);
		 }
		return diagonale;
		
	}
	
	/**
	 * @param num_ligne = x
	 * @param num_colonne = y
	 * @return la deuxieme diagonale du bas vers le haut et 
	 * de la gauche vers la droite a partir de la case(x,y)
	 */
	public Ligne DiagonaleInverse(int num_ligne, int num_colonne) {
		Ligne diagonaleInverse=null;
		 String diagonale2=""; 
		  int j,k; j=k= num_colonne;
		 if(num_colonne!=0 && num_colonne!=(tab_col-1)) {
			for(int i=num_ligne;i<tab_line;i++) {
				diagonale2+=getXY(j, i).getContenu();
				j++;
				if(j==tab_col) {
					break;
				}
			}
			for(int i=(num_ligne-1);i>=0;i--) {
				k--;
				diagonale2=getXY(k, i).getContenu()+diagonale2;
				if(k==0) {
					break;
				}
			}			
		 }else if(num_colonne==(tab_col-1) && num_ligne!=0) {
			 for(int i=num_ligne;i>=0 && k>=0;i--) {
				 diagonale2=getXY(k, i).getContenu()+diagonale2;
				 k--;
			 }
		 }else if(num_colonne==0 && num_ligne!=(tab_line-1)) {
			 for(int i=num_ligne;i<tab_line && k<tab_col;i++) {
				 diagonale2+=getXY(k, i).getContenu();
				 k++;
			 }
		 }
		 if(diagonale2!="" && diagonale2.length()>1) {
			 diagonaleInverse = new Ligne(diagonale2.length());
			 diagonaleInverse.setLigne(diagonale2);
		 }
		 
		return diagonaleInverse;
		
	}
	
	 /**
	  * Verifie a partir de la case(x,y) si un des joueurs a gagner sur les diagonales 
	 * @param num_ligne = x
	 * @param num_colonne = y
	 * @return false ou true en cas de victoire
	 */
	public boolean gagneDiagonale(int num_ligne, int num_colonne) {
		 boolean rep=false;
		  // Recuperation des lignes de diagonale et verification de victoire ou pas
		 boolean rep1 = false,rep2 = false;
		 Ligne l1 = Diagonale(num_ligne, num_colonne);
		 if(l1!=null) {
			 rep1=l1.gagne();
		 }
		 Ligne l2 = DiagonaleInverse(num_ligne, num_colonne);
		 if(l2!=null) {
			 rep2=l2.gagne();
		 }
		 if(rep1==true || rep2==true) {
			 rep=true;
		 }
		 return rep;
	 }
	
	
	 /**Verifie a partir de la case(x,y) si un des joueurs a gagner 
	 * @param num_ligne = x
	 * @param num_colonne = y
	 * @return false ou true en cas de victoire
	 */
	public boolean gagne(int num_ligne, int num_colonne) {
		 boolean rep=false;
		 boolean repLigne=gagneLigne(num_ligne);
		 boolean repColonne=gagneColonne(num_colonne);
		 boolean repDiagonale=gagneDiagonale(num_ligne,num_colonne);
		 if(repLigne==true || repColonne==true || repDiagonale==true) {
			 rep=true;
		 }
		 return rep;
	 }

	/**
	 * Verifie si le plateau est plein
	 * @return plein si le plateau est plein et null au cas contraire
	 */
	public String plateauPlein() {
		String etat="Plein";
		for(int i =0;i<tab_col;i++) {
			if(getXY(i,(tab_line-1)).getContenu()==0) {
				etat=null;
			}
		}
		return etat;
	}
	 
	 /**
	 * @return Le nombre de ligne du plateau
	 */
	public int getTab_line() {
		return tab_line;
	}

	/**
	 * @return Le nombre de colonne du plateau
	 */
	public int getTab_col() {
		return tab_col;
	}

	/**
	 * Change la valeur des cases du plateau c-a-d la possibilite de faire 
	 * des alignements de 4 avec la case.
	 */
	public void changeValueInPlateau() {
		int milieu;
		if (tab_line%2==0) {
			milieu=(tab_line/2)-1;
		}else {
			milieu=tab_line/2;
		}
		for(int num_ligne=0;num_ligne<=milieu;num_ligne++) {
			for(int num_colonne=0; num_colonne<tab_col;num_colonne++) {
				
			//Recuperation des diagonales et de la colonne sur lesquels se trouve la case
				Ligne colonne,diagonnale,diagonaleInverse;
				colonne = new Ligne(tab_line);
				String ValInit = "";
				for(int i =0; i<tab_line;i++) {
					ValInit+=getXY(num_colonne,i).getContenu();
				}
				colonne.setLigne(ValInit);
				diagonnale= Diagonale(num_ligne, num_colonne);
				diagonaleInverse = DiagonaleInverse(num_ligne, num_colonne);
				
			//changer les valeurs des cases de ces lignes
				colonne.changeValue();
				if(diagonnale!=null) {
					diagonnale.changeValue();
				}
				if(diagonaleInverse!=null){
					diagonaleInverse.changeValue();
				}
				
			//changer la valeur des cases sur la moitier du plateau 
				
					//Premiere ligne et colonne compris entre 1 et avant derniere
				if(num_ligne==0 && (num_colonne>=1 && num_colonne<(tab_col-1) && tab_line!=1)) {	
					//Aucune diagonale null dans cet interval
						this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
								+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(num_ligne+1).getValue())
								+ (diagonaleInverse.getX(num_ligne+1).getValue()));
						
					//Premiere colonne pour des plateau a plus de 2 colonnes
				}else if(num_colonne==0 && tab_col!=1 && tab_col!=2 ) {
					//Prise en compte des diagonales null
						if(diagonaleInverse!=null && diagonnale!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(diagonnale.getTaille()).getValue())
									+ (diagonaleInverse.getX(1).getValue()));
						}else if (diagonaleInverse==null && diagonnale!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(diagonnale.getTaille()).getValue()));
						}else if (diagonnale==null && diagonaleInverse!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
								+ (colonne.getX(num_ligne+1).getValue())+ (diagonaleInverse.getX(1).getValue()));
						}else if(diagonaleInverse==null && diagonnale==null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()));
						}
			
					//Derniere colonne
				}else if(num_colonne==(tab_col-1)) {
					//Prise en compte des diagonales null
						if(diagonaleInverse!=null && diagonnale!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(1).getValue())
									+ (diagonaleInverse.getX(diagonaleInverse.getTaille()).getValue()));
						}else if (diagonaleInverse==null && diagonnale!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(1).getValue()));
						}else if (diagonnale==null && diagonaleInverse!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
								+ (colonne.getX(num_ligne+1).getValue())+ (diagonaleInverse.getX(diagonaleInverse.getTaille()).getValue()));
						}else if(diagonaleInverse==null && diagonnale==null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()));
						}
					
					//Avant Derniere colonne sans la premiere case
				}else if(num_colonne==(tab_col-2) ) {
					//sans la premiere case
					if(tab_col!=2 && num_ligne!=0) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(2).getValue())
									+ (diagonaleInverse.getX(diagonaleInverse.getTaille()-1).getValue()));
					}else if (tab_col==2) {
						if(diagonaleInverse!=null && diagonnale!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(2).getValue())
									+ (diagonaleInverse.getX(1).getValue()));
						}else if (diagonnale==null && diagonaleInverse!=null) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonaleInverse.getX(1).getValue()));
						}
						
					}
					
					//Deuxieme ligne sans la premieme et les 2 derniere colonne
				}else if(num_ligne==1 && (num_colonne!=0 && num_colonne!=(tab_col-1) && num_colonne!=(tab_col-2))) {
					//Aucune diagonale null dans cet interval
					this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
							+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(num_ligne+1).getValue())
							+ (diagonaleInverse.getX(num_ligne+1).getValue()));
					
					
					//Interieur du plateau c-a-d sans les bords
				}else {
					//Aucune diagonale null dans cet interval
					if(num_colonne<=num_ligne && tab_line!=1 && tab_col!=2) {
							this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
									+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(num_ligne+1).getValue())
									+ (diagonaleInverse.getX(num_colonne+1).getValue()));
					}else if(num_colonne>num_ligne && tab_line!=1 && tab_col!=2) {
						this.getXY(num_colonne,num_ligne).setValue((this.getXY(num_colonne,num_ligne).getValue())
								+ (colonne.getX(num_ligne+1).getValue()) + (diagonnale.getX(num_ligne+1).getValue())
								+ (diagonaleInverse.getX(num_ligne+1).getValue()));
					}
				}
			}
		}
		
		//Vue que la valeur de jeu est symetrique par rapport au milieu j'inverse
		   //juste pour remplir la seconde partie
		for(int i =0;i<tab_col;i++) {
			for(int j=(tab_line-1);j>=milieu;j--) {
				this.getXY(i, j).setValue(this.getXY(i, Math.abs((j-(tab_line-1)))).getValue());
			}
		}
		
		
	}
	
	public int ValeurDuJeu(Joueur j, int num_ligne, int num_colonne) {
		//Recuperation du numero de l'dversaire;
		int num_adversaire = 0;
		if (j.getNum_joueur()==1) {
			num_adversaire=2;
		}else if (j.getNum_joueur()==2) {
			num_adversaire=1;
		}
		
		//Test de la possibilite de gagner ou bloquer pour changer la valeur du jeu
		 //gagner
		boolean rep = false;
		rep = gagne(num_ligne, num_colonne);
		if(rep==true) {
			return 278;
		}
		//perdre
		Plateau p = new Plateau(this);
		p.getXY(num_colonne,num_ligne).setContenu(num_adversaire);
		rep = p.gagne(num_ligne, num_colonne);
		if(rep==true) {
			return 277;
		}
		
		// return le score du joueur au cas ou il peut pas bloquer ou gagner
		p.getXY(num_colonne,num_ligne).setContenu(j.getNum_joueur());
		int score=0;
		for(int i =0;i<tab_line;i++) {
			for(int k =0;k<tab_col;k++) {
				if(getXY(k, i).getContenu()==j.getNum_joueur()) {
					score+=getXY(k, i).getValue();
				}else if(getXY(k, i).getContenu()==num_adversaire) {
					score-=getXY(k, i).getValue();
				}
			}
		}
		return score;
	}
	
	public Ligne getLine(int i) {
		return lignes_tab[i];
	}
	
	
}
