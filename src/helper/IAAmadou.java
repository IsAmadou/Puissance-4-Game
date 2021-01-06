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
		int num_colonne = niveau_quatre(pl, joueur);
		
		
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

	
	
	
}

