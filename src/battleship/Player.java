package battleship;

import java.util.Random;

public class Player {

	private String name;
	private Grid shipPosition;
	private Grid shotGrid;
	private Coordinates coord;
	private int sink;
	private Random r = new Random();

	//constructeur des deux grilles par joueur sans paramètres
	public Player(String n) {
		name = n;
		shipPosition = new Grid();
		shotGrid = new Grid();
		sink = 0;
	}

	public boolean addNewShip(int column, int line, int s, int d) {
		//ajoute un nouveau bateau
		return shipPosition.addNewShip(column, line, s, d);

	}

	public boolean recordShot(int column, int line, Player p) {
		//enregistre le tir d'un joueur
		if (shotGrid.getValue(column, line) == 2 || shotGrid.getValue(column, line) == 4) {
			return false;
		}
		else {
			if (p.hasShip(column, line) == true)  {
				shotGrid.addShot(column, line, true);
				int sizeShipAttack = shotGrid.sizeShip(column, line);
				//System.err.println("atq size" + sizeShipAttack);
				int sizeShipDefense = p.shipPosition.sizeShip(column, line);
				//System.err.println("def size" + sizeShipDefense);
				if(sizeShipAttack == sizeShipDefense){
					shotGrid.changeToSink(column, line);
					sink++;
					System.out.println("Vous avez coulé un bateau.");
				}
			}
			else {
				shotGrid.addShot(column, line, false);
			}
			return p.hasShip(column, line);
		}
	}

	private boolean hasShip(int c, int l) {
		//verifie si il y a un bateau aux coordonnées données
		if (shipPosition.getValue(c, l) == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public void initGridRandom() {
		//initialise une grid Random
		shipPosition.randomInit();
	}

	public void displayGrid() {
		//affiche la grille avec les bateaux du joueur
		System.out.println(shipPosition);
	}

	public void displayShotGrid() {
		//affiche la grille d'attaque du joueur
		System.out.println(shotGrid);
	}

	public boolean hasWin() {
		if(sink == 7) {
			Game.hideGame();
			System.out.println("Vous avez gagné !!");
			return true;
		}
		return false;
	}

	public String getName() {
		//retourne le nom du joueur
		return name;
	}

	public Coordinates getCoord(int c, int l) {

		return coord;
	}

	public boolean isSunk(int column, int line, Player p) {
		if (shotGrid.sizeShip(column, line) == p.shipPosition.sizeShip(column, line)) {
			return true;
		}
		else {
			return false;
		}

	}

	public Coordinates bot0() { // appliqué à un joueur en particulier donc pas static
		int column;
		int line;
		column = r.nextInt(10);
		line = r.nextInt(10);
		Coordinates stratBot0 = new Coordinates(column, line);
		return stratBot0;
	}

	public Coordinates bot1() {

		int column, line;
		do {
			column = r.nextInt(10);
			line = r.nextInt(10);
		}while(shotGrid.getValue(column, line) != 0 && shotGrid.getValue(column, line) != 4);
		Coordinates stratBot1 = new Coordinates(column, line);
		return stratBot1;
	}

	public int randomDirAtq() {
		return r.nextInt(4);
	}

	public Coordinates bot2(){ // integrer la fonction de compteur qui permet de changer de case si toutes celle autour sont déjà attaqué

		int column = 0, line = 0, alreadyAttack = 0;
		int dirAtq = randomDirAtq();

		while(line < 9 && shotGrid.getValue(column, line) != 2) { // parcours de shotGrid à la recherche d'une case touché
			while(column < 9 && shotGrid.getValue(column, line) != 2) {
				column++;
			}
			column = 0;
			line++;
		}

		if(shotGrid.getValue(column, line) == 2) {
			do {
				if (dirAtq == 0 && line != 0) { //si atq en haut + verification si atq en haut possible
					if(alreadyAttack == 4 && shotGrid.getValue(column, line-1) == 2) {
						alreadyAttack = 0;
						line++;
					}
					if(shotGrid.getValue(column, line-1) == 2 || shotGrid.getValue(column, line-1) == 4) { // si la case au dessus a déjà été attaquée
						dirAtq = randomDirAtq();
						alreadyAttack++;
					}
					else{
						line--;
					}
				}
				else if(dirAtq == 0 && line == 0){
					dirAtq = randomDirAtq();
					alreadyAttack++;
				}


				if (dirAtq == 1 && column != 9){ //si atq droite + verification si atq droite possible
					if(alreadyAttack == 4 && shotGrid.getValue(column+1, line) == 2) {
						alreadyAttack = 0;
						column++;
					}
					if(shotGrid.getValue(column+1, line) == 2 || shotGrid.getValue(column+1, line) == 4) {
						dirAtq = randomDirAtq();
						alreadyAttack++;
					}
					else {
						column++;
					}
				}
				else if (dirAtq == 1 && column == 9){
					dirAtq = randomDirAtq();
					alreadyAttack++;
				}


				if(dirAtq == 2 && line != 9) { //si atq en bas + verification si atq en bas possible
					if(alreadyAttack == 4 && shotGrid.getValue(column, line+1) == 2) {
						alreadyAttack = 0;
						line++;
					}
					if(shotGrid.getValue(column, line+1) == 2 || shotGrid.getValue(column, line+1) == 4) {
						dirAtq = randomDirAtq();
						alreadyAttack++;
					}
					else {
						line++;
					}
				}
				else if(dirAtq == 2 && line == 9) {
					dirAtq = randomDirAtq();
					alreadyAttack++;
				}


				if(dirAtq == 3 && column != 0) { //si atq gauche + verification si atq gauche possible
					if(alreadyAttack == 4 && shotGrid.getValue(column-1, line) == 2) {
						alreadyAttack = 0;
						column++;
					}
					if(shotGrid.getValue(column-1, line) == 2 || shotGrid.getValue(column-1, line) == 4) {
						dirAtq = randomDirAtq();
						alreadyAttack++;
					}
					else {
						column--;
					}
				}
				else {
					dirAtq = randomDirAtq();
					alreadyAttack++;
				}
			}while(shotGrid.getValue(column, line) != 0);
		}


		else {
			do {
				column = r.nextInt(10);
				line = r.nextInt(10);
			}while(shotGrid.getValue(column, line) != 0 && shotGrid.getValue(column, line) != 4);
		}

		Coordinates stratBot2 = new Coordinates(column, line);
		return stratBot2;
	}

	public Coordinates bot3() {

	}
	public Coordinates bot4() { // mettre des 4 tout autour des bateaux deja coulés

	}
}
