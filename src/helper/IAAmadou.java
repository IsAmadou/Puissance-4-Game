package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JTree;

public class IAAmadou extends Joueur {

	private static Joueur IA;
	
	public IAAmadou(Plateau pl) {
		super(pl);
		 IA = this;
	}

	public static int meilleurCoup(String plateau, int joueur) {
		Plateau pl = new Plateau(plateau);
		pl.changeValueInPlateau();//Changer la valeur de jeu des cases
	//	int num_colonne = niveau_un(pl);
	//	int num_colonne = niveau_deux(pl, joueur);
	//	int num_colonne = niveau_trois(pl, joueur);
	//	int num_colonne = niveau_quatre(pl, joueur);

		int num_colonne = niveau_cinq(pl, joueur);
	
		return num_colonne;	
	}
	
	
	/**
	 * IA choisi aleatoirement une colonne libre(pas pleine) du plateau pl 
	 * @param pl
	 * @return le numero de la colonne choisi
	 */
	public static  int niveau_un(Plateau pl) {
		Random rd = new Random();
		int num_colonne=rd.nextInt(pl.getTab_col());
		if(pl.colonnePleine(num_colonne).equals("pleine")) {
			num_colonne=niveau_un(pl);
		}
		return num_colonne;	
	}

	
	/**
	 * IA Choisi une colonne libre du plateau pl sur laquelle elle peut
	 *   gagner sinon une colonne aleatoire
	 * @param pl
	 * @param joueur = numero de joueur de IA
	 * @return le numero de la colonne choisi
	 */
	public static  int niveau_deux(Plateau pl, int joueur) {
		List<String> list_colonne= new ArrayList<String>();
		
		//Lister les colonnes ou on peut jouer
		for(int i=0;i<pl.getTab_col();i++) {
			if(pl.colonnePleine(i).equals("vide")) {
				list_colonne.add(String.valueOf(i));
			}
		}
		
		//Verification de la possibilite de gagner sur chacune des colonnes de la liste
		for (String colonne_libre : list_colonne) {
			Plateau p = new Plateau(pl);
			Case c = p.ajoutPion(Integer.parseInt(colonne_libre), joueur);
			if(p.gagne(c.getCoordx(), c.getCoordy())==true) {
				return Integer.parseInt(colonne_libre); //num_colonne=Integer.parseInt(colonne_libre);
				//break;
			}
			p.getXY(c.getCoordy(), c.getCoordx()).setContenu(0);
		}
		
		//Choix aleatoire en absence de colonne gagnante
		//if(num_colonne==pl.getTab_col()) {
		//	num_colonne=niveau_un(pl);
		//}
		
		return niveau_un(pl);	
	}

	
	/**
	 * IA Choisi une colonne libre du plateau pl sur laquelle elle peut gagner,
	 *  sinon empeche le joueur adverse de gagner, ou choisi une colonne aleatoire
	 * @param pl
	 * @param joueur = numero de joueur de IA
	 * @return le numero de la colonne choisi
	 */
	public static  int niveau_trois(Plateau pl, int joueur) {
		List<String> list_colonne= new ArrayList<String>();
		
		//Lister les colonnes ou on peut jouer
		for(int i=0;i<pl.getTab_col();i++) {
			if(pl.colonnePleine(i).equals("vide")) {
				list_colonne.add(String.valueOf(i));
			}
		}
		
		//Verification de la possibilite de gagner sur chacune des colonnes de la liste
		for (String colonne_libre : list_colonne) {
			Plateau p = new Plateau(pl);
			Case c = p.ajoutPion(Integer.parseInt(colonne_libre), joueur);
			if(p.gagne(c.getCoordx(), c.getCoordy())==true) {
				return Integer.parseInt(colonne_libre);
			}
			p.getXY(c.getCoordy(), c.getCoordx()).setContenu(0);
		}
		
		//Verification de la possibilite de Bloquer l aversaire sur chacune des colonnes de la liste
		int num_adversaire = 0;
		if(joueur==1) {
			num_adversaire=2;
		}else if(joueur==2) {
			num_adversaire=1;
		}
		for (String colonne_libre : list_colonne) {
			Plateau p = new Plateau(pl);
			Case c = p.ajoutPion(Integer.parseInt(colonne_libre), num_adversaire);
			if(p.gagne(c.getCoordx(), c.getCoordy())==true) {
				return Integer.parseInt(colonne_libre);
			}
			p.getXY(c.getCoordy(), c.getCoordx()).setContenu(0);
		}
		
		
		return niveau_un(pl);	
	}

	
	/**
	 * IA Choisi une colonne libre du plateau pl qui maximise ses chances future de gagner
	 *  si elle ne peut pas directement gagner, ni empecher le joueur adverse de gagner,
	 *  en choisissant la colonne la plus importante.
	 * @param pl
	 * @param joueur = numero de joueur de IA
	 * @return le numero de la colonne choisi
	 */
	public static  int niveau_quatre(Plateau pl, int joueur) {
		List<String> list_colonne= new ArrayList<String>();
		
		//Lister les colonnes ou on peut jouer
		for(int i=0;i<pl.getTab_col();i++) {
			if(pl.colonnePleine(i).equals("vide")) {
				list_colonne.add(String.valueOf(i));
			}
		}
		
		//Simuler un ajout sur chacune des colonnes pour tester la valeur du jeu
		int valeur_jeu, bloquer=pl.getTab_col(), first_case_value = 0,i=0;
		for (String colonne_libre : list_colonne) {
			Plateau p = new Plateau(pl);
			
			Case c = p.ajoutPion(Integer.parseInt(colonne_libre), joueur);
			if(i==0) {
				first_case_value=p.getXY(c.getCoordy(), c.getCoordx()).getValue();
				i=1;
			}
			valeur_jeu=p.ValeurDuJeu(IA,c.getCoordx(), c.getCoordy());
		//	System.out.println(valeur_jeu + " vj");
		//	System.out.println(IA.getScore()+" IA p");
			if(valeur_jeu==278) {
				return Integer.parseInt(colonne_libre);
			}else if(valeur_jeu==277){
				bloquer=Integer.parseInt(colonne_libre);
				i=2;
			}else {
				if(i!=2) {
					if(p.getXY(c.getCoordy(), c.getCoordx()).getValue()>=first_case_value) {
						first_case_value=p.getXY(c.getCoordy(), c.getCoordx()).getValue();
						bloquer= Integer.parseInt(colonne_libre);
					}
				}
			}
			p.getXY(c.getCoordy(), c.getCoordx()).setContenu(0);
		}
		
		return bloquer ;
	}

	
	public static  int niveau_cinq(Plateau pl, int joueur) {
		List<String> list_colonne= new ArrayList<String>();
		List<Plateau> neou = new ArrayList<Plateau>();
		List<Case> neouAjout = new ArrayList<Case>();
		List<Plateau> feuil = new ArrayList<Plateau>();
		
		int num_adversaire = 0;
		if(joueur==1) {
			num_adversaire=2;
		}else if(joueur==2) {
			num_adversaire=1;
		}
		//Lister les colonnes ou on peut jouer
		for(int i=0;i<pl.getTab_col();i++) {
			if(pl.colonnePleine(i).equals("vide")) {
				list_colonne.add(String.valueOf(i));
			}
		}
		
		//Simuler un ajout sur chacune des colonnes
		for (String colonne_libre : list_colonne) {
			Plateau p1 = new Plateau(pl.toStringPourIA());	
			p1.changeValueInPlateau();
			Case c = p1.ajoutPion(Integer.parseInt(colonne_libre), joueur);
			 neou.add(p1);
			 neouAjout.add(c);
		}	
		
		//Lister les colonnes ou l adversaire peut jouer apres le 1er ajouts
		 for (Plateau p : neou) {
			List<String> list_sous_colonne= new ArrayList<String>();
			for(int i=0;i<p.getTab_col();i++) {
				if(p.colonnePleine(i).equals("vide")) {
					list_sous_colonne.add(String.valueOf(i));
				}
			}
			
			//Simuler un ajout adverse sur chacune des colonnes 
			for (String sous_colonne_libre : list_sous_colonne) {
				Plateau p2 = new Plateau(p.toStringPourIA());		
				p2.changeValueInPlateau();
				Case sous_c = p2.ajoutPion(Integer.parseInt(sous_colonne_libre), num_adversaire);
				feuil.add(p2);
				if(p2.ValeurDuJeu(IA, sous_c.getCoordx(), sous_c.getCoordy())==278) {
					return sous_c.getCoordy();
				}
				p2.getXY(sous_c.getCoordy(),sous_c.getCoordx()).setContenu(num_adversaire);
			}
		}
		 //Si l adversaire ne peut pas joueur on retourne la colonne avec la plus grande valeur
		 if(feuil.isEmpty()) {
			 int i=0, first_val =279,col = pl.getTab_col();
			 for (Plateau p : neou) {
				int val = p.ValeurDuJeu(IA, neouAjout.get(i).getCoordx(), neouAjout.get(i).getCoordy());
				if(i==0) {
					first_val=val;
					col=neouAjout.get(i).getCoordy();
				}
				if(val>first_val) {
					first_val=val;
					col=neouAjout.get(i).getCoordy();
				}
				i++;
			}
			 return col;
		 }
		 
		 List<String> cols = new ArrayList<String>();
		 List<String> vals = new ArrayList<String>();
		 int first_val = 0;
		for (Plateau p : feuil) {
			//Lister les colonnes ou on peut jouer apres le 1er ajouts adverse
			List<String> list_sous_colonne= new ArrayList<String>();
			for(int i=0;i<p.getTab_col();i++) {
				if(p.colonnePleine(i).equals("vide")) {
					list_sous_colonne.add(String.valueOf(i));
				}
			}
				int j =0;
			//Simuler un ajout sur chacune des colonnes et calculer valeur de jeu
			for (String sous_colonne_libre : list_sous_colonne) {
				Plateau p3 = new Plateau(p.toStringPourIA());	
				p3.changeValueInPlateau();
				Case sous_c = p3.ajoutPion(Integer.parseInt(sous_colonne_libre), joueur);
				int val=-(p3.ValeurDuJeu(IA,sous_c.getCoordx(), sous_c.getCoordy()));
				if(j==0) {
					first_val=val;
					vals.add(String.valueOf(val));
					cols.add(sous_colonne_libre);
					j=1;
				}
				if(val<first_val) {
					first_val=val;
					vals.set((vals.size()-1), String.valueOf(val));
					cols.set((cols.size()-1),sous_colonne_libre);
				}
			}
		}
		int colonne = pl.getTab_col();
		int first=Integer.parseInt(vals.get(0));
		for(int i=0;i<vals.size();i++) {
			if(Integer.parseInt(vals.get(i))<0 && first<0) {
				if(Integer.parseInt(vals.get(i))<=first) {
					first=Integer.parseInt(vals.get(i));
					colonne=Integer.parseInt(cols.get(i));
				}
			}else {
				if(Integer.parseInt(vals.get(i))>=first && Integer.parseInt(vals.get(i))!=-277 && first>=0) {
					first=Integer.parseInt(vals.get(i));
					colonne=Integer.parseInt(cols.get(i));
				}
			}
		}
		
		return colonne ; 
		
		
	}
	
}

