package battleship;

import java.util.Random;
public class Grid {

	private int field[][];
	private static Random r = new Random();

	public Grid() {
		field = new int [10][10];
		initMat();
	}

	public int getValue(int column, int line) {
		// Renvoie la valeur de la matrice à la coordonnée en paramètre.
		return field[column][line];
	}

	private void initMat() {
		// Initiialise le champs de bataille
		int column, line;
		for (column = 0; column <field.length; column++) {
			for (line = 0; line < field.length; line++) {
				field[column][line] = 0;
			}
		}
	}

	public void randomInit() {
		//initialisation aléatoire de la grille 
		int i, column, line, dir;
		int ship[] = {5, 4, 3, 2, 2, 1, 1};
		for(i = 0; i < ship.length; i++) {
			do {
				column = r.nextInt(10);
				line = r.nextInt(10);
				dir = r.nextInt(2);
			}while (addNewShip(column, line, ship[i], dir) == false);
		}
	}

	public void addShot(int column, int line, boolean success) {
		// met à jour la grille d’attaque avec valeur 1 si touché et 4 si raté
		if (success == true) {
			field[column][line] = 2;
		}
		else {
			field[column][line] = 4;
		}
	}

	private boolean isValidShip(int c, int l, int size, int dir) {
		//Vérifie la validité de l'emplacement du bateau
		int i;
		if((c >= 0 && c < 10) && (l >=0 && l < 10) && ((dir == 0 && (l + size-1) < field.length)||(dir == 1 && (c + size-1) < field.length))  && field[c][l] == 0) {
			for (i = 0; i < size ; i++) {
				if((c == 0 || c == 9) && (l == 0 || l == 9)){ //Dans le cas où le bateau est dans un coin de la matrice
					if (c == 0 && l == 0 && field[c+1][l] == 0 && field[c][l+1] == 0 && field[c+1][l+1] == 0) { // coin supérieur gauche

					}
					else if (c == 9 && l == 0 && field[c-1][l] == 0 && field[c][l+1] == 0 && field[c-1][l+1] == 0) { // coin supérieur droit

					}
					else if (c == 0 && l == 9 &&  field[c+1][l] == 0 && field[c][l-1] == 0 && field[c+1][l-1] == 0) { // coin inférieur gauche

					}
					else if (c == 9 && l == 9 &&  field[c-1][l] == 0 && field[c][l-1] == 0 && field[c-1][l-1] == 0) { // coin inférieur droit

					}
					else {
						System.err.println("Y'a un problème capitaine.");
						return false;
					}
				}
				else if(c == 0 || c == 9 || l == 0 || l == 9) { //Dans le cas où le bateau est sur une bordure de la matrice
					if (c == 0 &&  field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l+1] == 0 && field[c+1][l] == 0 && field[c][l+1] == 0) { // Bordure gauche

					}
					else if (c == 9 &&  field[c][l-1] == 0 && field[c-1][l-1] == 0 && field[c-1][l] == 0 && field[c-1][l+1] == 0 && field[c][l+1] == 0) { // Bordure droite

					}
					else if (l == 0 &&  field[c-1][l] == 0 && field[c-1][l+1] == 0 && field[c][l+1] == 0 && field[c+1][l+1] == 0 && field[c+1][l] == 0) { // Bordure supérieur

					}
					else if (l == 9 &&  field[c-1][l] == 0 && field[c-1][l-1] == 0 && field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l] == 0){ // Bordure inférieur

					}
					else {
						System.err.println("Y'a un problème capitaine.");
						return false;
					}
				}
				else if (c != 0 && c != 9 && l != 0 && l != 9 &&  field[c][l-1] == 0 && field[c+1][l-1] == 0 && field[c+1][l] == 0  
						&& field[c+1][l+1] == 0 && field[c][l+1] == 0  && field[c-1][l+1] == 0  && field[c-1][l] == 0 && field[c-1][l-1] == 0){ //Dans le cas où le bateau n'est pas sur les bords de la matrice
					return true;
				}
				else {
					System.err.println("Y'a un problème capitaine.");
					return false;
				}

				if(dir == 0) {
					l ++;
				}
				else {
					c ++;
				}
			}
			return true;
		}
		else {
			System.err.println("Y'a un problème capitaine.");
			return false;
		}

	}

	public boolean addNewShip(int column, int line, int s, int d) {
		// Fais appel a placeShip si isValideShip est vrai, sinon println
		if (isValidShip(column, line, s, d) == true) {
			placeShip(column, line, s, d);
			return true;
		}
		else {
			return false;
		}
	}

	private void placeShip (int c, int l, int size, int dir) {
		// Modifie la matrrice pour placer les bateaux une fois vérifier par isValidShip
		int i;
		for (i = 0; i < size; i++) {
			if(dir == 0) {
				field[c][l + i] = 1;
			}
			else {
				field[c + i][l] = 1;
			}
		}
	}

	public String toString() {
		//affiche les grilles
		int l=0, i;
		String str="     A  B  C  D  E  F  G  H  I  J \n";
		str +=     "   -------------------------------\n";
		for(i=0; i<field.length; i++) {
			str += i + " |  ";
			for(int c=0; c<field.length; c++) {
				str += field[c][l] + "  ";
			}
			str+="|\n";
			l++;
		}
		return str +=    "   -------------------------------";

	}

	public int sizeShip(int c, int l) {
		int size = 1, i = 1;
		if ((c < 9 && field[c + 1][l] != 0 && field[c + 1][l] != 4) || (c > 0 && field[c - 1][l] != 0 && field[c - 1][l] != 4)) { // Verif a droite et à gauche
			// verifier a gauche et a droite pour incrementer size
			while((c - i >= 0) && field[c - i][l] != 0 && field[c - i][l] != 4) { // regarde a gauche
				size ++;
				i ++;
			}
			i = 1;
			while((c + i < 10) && field[c + i][l] != 0 && field[c + i][l] != 4) { // regarde a droite
				size++;
				i++;
			}
			return size;
		}
		else if ((l > 0 && field[c][l - 1] != 0 && field[c][l - 1] != 4) || (l < 9 && field[c][l + 1] != 0 && field[c][l + 1] != 4)) {
			// verifier en haut et en bas pour incrementer size
			while((l - i >= 0) && field[c][l - i] != 0 && field[c][l - i] != 4) { // regarde en haut
				size ++;
				i ++;
			}
			i = 1;
			while((l + i < 10) && field[c][l + i] != 0 && field[c][l + i] != 4) { // regarde en bas
				size++;
				i++;
			}
			return size;
		}
		else {
			return size = 1;
		}
	}

	public void changeToSink(int c, int l) { //transforme 2 en 3 quand le bateau est coulé

		field[c][l] = 3;
		
		int i = 1;
		if ((c < 9 && field[c + 1][l] == 2) || (c > 0 && field[c - 1][l] == 2)) { // Verif a droite et à gauche
			// verifier a gauche et a droite pour incrementer size
			while((c - i >= 0) && field[c - i][l] == 2) { // regarde a gauche
				field[c - i][l] = 3;
				i ++;
			}
			i = 1;
			while((c + i < 10) && field[c + i][l] == 2) { // regarde a droite
				field[c + i][l] = 3;
				i++;
			}
		}
		else if ((l > 0 && field[c][l - 1] == 2) || (l < 9 && field[c][l + 1] == 2)) {
			// verifier en haut et en bas pour incrementer size
			while((l - i >= 0) && field[c][l - i] == 2) { // regarde en haut
				field[c][l - i] = 3;
				i ++;
			}
			i = 1;
			while((l + i < 10) && field[c][l + i] == 2) { // regarde en bas
				field[c][l + i] = 3;
				i++;
			}
		}
	} 
}

