import java.util.Scanner;

import helper.Case;
import helper.IAAmadou;
import helper.Joueur;
import helper.Plateau;

public class Jeu {
	static Joueur j = null;
	static IAAmadou iAAmadou = null;
	static Plateau p = null;
	public static void main(String[] args) {
		init_jeu(6, 7);
		 int victoire =0;
		 while(victoire==0 && p.plateauPlein()==null) {
			int num_colonne = IAAmadou.meilleurCoup(p.toStringPourIA(), iAAmadou.getNum_joueur());
			iAAmadou.ajoutPion(num_colonne, iAAmadou.getNum_joueur());
			System.out.println(p);
			if(p.gagne(iAAmadou.getLast_case().getCoordx(), iAAmadou.getLast_case().getCoordy())==true) {
				victoire++;
				System.out.println("Pas de chance, Vous avez perdu !!!");
				break;
			}
			ajoutPion();
			System.out.println(p);
			if(p.gagne(j.getLast_case().getCoordx(), j.getLast_case().getCoordy())==true) {
				victoire++;
				System.out.println("Bravo, Vous avez gagner !!!");
				break;
			}
		 }
		 if(p.plateauPlein()!=null) {
			 System.out.println("Egalite, vous y etes presque");
		 }
		 
		 
	}
	
	/**
	 * Choix du numero du joueur en fonction de la couleur 
	 * 1-pour le rouge
	 * 2-pour le jaune
	 */
	public static void choixNum() {
		Scanner sc = new Scanner(System.in);
		int i=0;
		
		do {
			System.out.println("Veuillez choisir une couleur en tapant soit:");
			System.out.println("1 - Rouge");
			System.out.println("2 - Jaune");
			int choix = 0;
			try {
				choix = sc.nextInt();
			} catch (Exception e) {
				sc.next();
			}
			if(choix==1) {
				 j.setNum_joueur(1);
				 iAAmadou.setNum_joueur(2);
				i=1;
			}else if(choix==2){
				 iAAmadou.setNum_joueur(1);
				 j.setNum_joueur(2);
				i=1;
			}
		}while(i==0);
	}

	/**
	 * Ajout du pion pour le joueur apres la choix de la colonne 
	 */
	public static void ajoutPion() {
		Scanner sc = new Scanner(System.in);
		int i=0;
		do {
			System.out.println("Veuillez choisir une colonne entre 0 et "+(p.getTab_col()-1));
			int choix = 0;
			try {
				choix = sc.nextInt();
				if(choix>=0 && choix<p.getTab_col()) {
					Case c = j.ajoutPion(choix, j.getNum_joueur());
					if(c!=null) {
					//	p.ajoutPion(choix, j.getNum_joueur());
						i++;
					}
				}
			} catch (Exception e) {
				sc.next();
			}
		}while(i==0);
	}
	
	/**
	 * initialisation de la partie en instanciant les attributs(joueurs et plateau)
	 * @param nbr_ligne = nombre de ligne du plateau
	 * @param nbr_colonne = nombre de case par ligne 
	 */
	public static void init_jeu(int nbr_ligne, int nbr_colonne) {
		
		//Creation Plateau
		p = new  Plateau(nbr_ligne,nbr_colonne);
		p.changeValueInPlateau();
		j = new Joueur(p);
		iAAmadou = new IAAmadou(p);
		
		//choix de la couleur
		choixNum();
		
		// Ajout de Point
		ajoutPion();
		
		//Afficher le plateau
		System.out.println(p);
	}

}

