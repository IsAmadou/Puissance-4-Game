import java.awt.Font;

import MG2D.Fenetre;
import MG2D.Souris;
import MG2D.geometrie.Carre;
import MG2D.geometrie.Cercle;
import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texte;
import helper.Case;
import helper.IAAmadou;
import helper.Joueur;
import helper.Plateau;

public class JeuGraphique {
	private static Fenetre f;
	private static Souris souris;
	private static Cercle c_rouge;
	private static Cercle c_jaune;
	private static Rectangle r_jeu;
	private static Rectangle r_etat;
	private static Texte t_etat;
	private static int largeur;
	private static int hauteur;
	private static boolean jeu=false;
	
	static Joueur j = null;
	static IAAmadou iAAmadou = null;
	static Plateau p = null;
	
	public static void main(String[] args) {
	
		initInterface(6, 7);
		choixCouleur();
		 int victoire =0;
		 while(victoire==0 && jeu ==true && p.plateauPlein()==null) {
			 Case c = j.ajoutPion(colonneAjoutPion(), j.getNum_joueur());
			 remplirInterface(p);
			
			 if(p.gagne(j.getLast_case().getCoordx(), j.getLast_case().getCoordy())==true) {
					victoire++;
					finPartie("Gagner");
					break;
			}
			if(c!=null) {
				 try{Thread . sleep (500);
					} catch( Exception e){}
				int num_colonne = IAAmadou.meilleurCoup(p.toStringPourIA(), iAAmadou.getNum_joueur());
				iAAmadou.ajoutPion(num_colonne, iAAmadou.getNum_joueur());
				 remplirInterface(p);
				if(p.gagne(iAAmadou.getLast_case().getCoordx(), iAAmadou.getLast_case().getCoordy())==true) {
					victoire++;
						finPartie("Perdu");
						break;
				}
			}
		 } 
		 if(p.plateauPlein()!=null) {
			 finPartie("Egalite");
		 }
		
	}
	
	/**
	 * creation du plateau de jeu qui se compose de n ligne de c case chacune
	 * @param nbr_ligne   = n
	 * @param nbr_colonne = c
	 */
	public static void initInterface(int nbr_ligne, int nbr_colonne ) {
		 largeur=(nbr_colonne*80)+200;
		 hauteur=(nbr_ligne*80)+40;
		
		//Creation du Plateau et des joueurs 
			p = new  Plateau(nbr_ligne,nbr_colonne);//("6x7-011211102212210112211022212201122100121220");("6x7-212111212112102220020211010002202000110200")
			p.changeValueInPlateau();				//("6x7-211222112211201112210121212000100000021210");("6x7-000000000000000000000000000000100001221210")
			j = new Joueur(p);						//("6x7-211210001122000212000002100000000000000000")
			iAAmadou = new IAAmadou(p);
		 
		// creer la fenetre 
		f = new Fenetre("Puissance 4", largeur, hauteur);
		Rectangle r = new Rectangle(Couleur.BLEU, new Point(175, 15),(largeur-185), hauteur-20, true);
		f.ajouter(r);
		
		// Portion pour le choix des couleur
		Rectangle r_color = new Rectangle(Couleur.NOIR, new Point(40, (hauteur/2)-50), 80, 100);
		f.ajouter(r_color);
		Rectangle r_rouge = new Rectangle(Couleur.ROUGE, new Point(70, (hauteur/2)+10), 45, 20, true);
		f.ajouter(r_rouge);
		Rectangle r_jaune = new Rectangle(Couleur.JAUNE, new Point(70, (hauteur/2)-30), 45, 20, true);
		f.ajouter(r_jaune);
		 c_rouge = new Cercle(Couleur.ROUGE, new Carre(new Point(45 ,(hauteur/2)+10), 20), true);
		f.ajouter(c_rouge);
		 c_jaune = new Cercle(Couleur.NOIR, new Carre(new Point(45 ,(hauteur/2)-30), 20));
		f.ajouter(c_jaune);
		
		//boutton pour commencer la partie 
		r_jeu = new Rectangle(Couleur.ROUGE, new Point(40, (hauteur/2)-90), 80, 30,true);
		f.ajouter(r_jeu);
		Texte t = new Texte(Couleur.NOIR, "Commencer", new Font("Calibri", Font.BOLD, 14),
				new Point(80,((hauteur/2)-90)+15));
		f.ajouter(t);
		
		//Affichage etat de la partie 
		 r_etat = new Rectangle(new Point(40, (hauteur/2)+70), 80, 30);
		 t_etat = new Texte(Couleur.NOIR, "uyu", new Font("Calibri", Font.BOLD, 14),
				new Point(80,((hauteur/2)+70)+15));
	
		//Remplir la fenetre
		remplirInterface(p);
		souris = f . getSouris ();
			
	}


	/**
	 * Choisir la couleur avec laquel on veut jouer
	 */
	public static void choixCouleur() {	
		//Par defaut 
		if(c_rouge.getPlein()==true) {
			j.setNum_joueur(1);
			 iAAmadou.setNum_joueur(2);
		}
		//En cas de changement de couleur
		while (jeu==false){ 
			try{Thread . sleep (1);
			} catch( Exception e){}

			if(souris . getClicGauche ()) {
				Point clic = souris.getPosition();
				if(clic.getX()>=45 && clic.getX()<=65 && clic.getY()>=((hauteur/2)-30) && clic.getY()<=((hauteur/2)-30)+20) {
					c_jaune.setCouleur(Couleur.JAUNE);
					c_jaune.setPlein(true);
					c_rouge.setCouleur(Couleur.NOIR);
					c_rouge.setPlein(false);
					 iAAmadou.setNum_joueur(1);
					 j.setNum_joueur(2);
					f.rafraichir();
				}else if(clic.getX()>=45 && clic.getX()<=65 && clic.getY()>=((hauteur/2)+10) && clic.getY()<=((hauteur/2)+10)+20) {
					c_rouge.setCouleur(Couleur.ROUGE);
					c_rouge.setPlein(true);
					c_jaune.setCouleur(Couleur.NOIR);
					c_jaune.setPlein(false);	
					 j.setNum_joueur(1);
					 iAAmadou.setNum_joueur(2);
					f.rafraichir();
				}else if(clic.getX()>=40 && clic.getX()<=120 && clic.getY()>=((hauteur/2)-90) && clic.getY()<=((hauteur/2)-90)+30) {
					r_jeu.setCouleur(Couleur.VERT);
					jeu = true;
					f.rafraichir();
				}
			}
		}
	}
	
	
	/**
	 * Remplissage de l interface avec les information du plateau p
	 * @param p
	 */
	public static void remplirInterface(Plateau p) {
				int l = 0;
				for(int i=20; i<hauteur && l<p.getTab_line();i+=81) {
					int k=0;
					for(int j=180; j<largeur && k<p.getTab_col();j+=81) {
						int num = p.getXY(k, l).getContenu();
						Cercle c = null ;
						switch (num) {
						case 0:
							c= new Cercle(Couleur.BLANC, new Carre(new Point(j ,i), 80), true);
							break;
						case 1:
							c= new Cercle(Couleur.ROUGE, new Carre(new Point(j ,i), 80), true);
							break;
						case 2:
							c= new Cercle(Couleur.JAUNE, new Carre(new Point(j ,i), 80), true);
							break;
						}
						f.ajouter(c);
						k++;
					}
					l++;
				}
				f.rafraichir();
	}
	
	
	/**
	 * Determinition de la colonne pour un ajout de pion utilisateur 
	 * @return le numero de la colonne
	 */
	public static int colonneAjoutPion () {
		//On attend un clic gauche 
		while (! souris . getClicGauche ()){
			try{Thread . sleep (1); 
			} catch( Exception e){} 
		}
		//On recupere la position de la souris 
		Point clic=new Point ( souris . getPosition ());

		int j=0;
		for(int i=180; i<largeur && j<p.getTab_col();i+=81) {
			if(clic.getX()>=i && clic.getX()<=i+80)
			{
				return j;
			}
			j++;
		}
		return colonneAjoutPion();
		
	}

	/**
	 * Affichage du rectangle donnant une issue a la partie 
	 * @param etat = Gagner ou Perdu ou Egalite
	 */
	public static void finPartie (String etat) {
		if(etat.equals("Gagner")) {
			r_etat.setCouleur(Couleur.VERT);
			t_etat.setTexte("Gagner");
			f.ajouter(r_etat);
			f.ajouter(t_etat);
			f.rafraichir();	
		}else if(etat.equals("Perdu")) {
			r_etat.setCouleur(Couleur.ROUGE);
			t_etat.setTexte("Perdu");
			f.ajouter(r_etat);
			f.ajouter(t_etat);
			f.rafraichir();	
		}else if(etat.equals("Egalite")) {
			r_etat.setCouleur(Couleur.NOIR);
			t_etat.setTexte("Match null");
			f.ajouter(r_etat);
			f.ajouter(t_etat);
			f.rafraichir();	
		}
	}

}

