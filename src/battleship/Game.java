package battleship;

import java.util.Scanner;
public class Game {
	private static Player player1;
	private static Player player2;
	private static Scanner scan = new Scanner(System.in);
	private static Player robot;
	private static Player currentPlayer;
	private static Player opponent;
	private static int difficulty;
	private static int mode = 1; //1 player, 2 players

	private static void fakeInitPlayerGrid(Player p) {
		p.addNewShip(0,0, 5, 0);
		p.addNewShip(2,0, 4, 0);
		p.addNewShip(4,0, 3, 0);
		p.addNewShip(6,0, 2, 0);
		p.addNewShip(8,0, 2, 1);
		p.addNewShip(0,8, 1, 0);
		p.addNewShip(2,8, 1, 0);
	}
	private static void initPlayerGrid(Player p) {
		int c,l,dir;
		boolean added;
		int ships[] = {5, 4, 3, 2, 2, 1, 1};
		int i = 0;
		String str;
		do {
			p.displayGrid();
			System.out.println("\tPlacer bateau --------> taille " + ships[i] + " : ");
			System.out.println("Entrez la coordonnée : ");
			str = scan.nextLine();
			if(str.length()<2) continue;
			c=str.charAt(0)-65;
			l=Integer.parseInt(str.substring(1, 2));
			System.out.println("Entrez la direction (0: vert, 1:horiz) : ");
			dir=Integer.parseInt(scan.nextLine());
			added = p.addNewShip(c,l, ships[i], dir);
			if(added) {
				i++;
			}else {
				System.out.println("Placement du bateau de taille " + ships[i] + " en " + c + "," + l + " impossible.");
			}
		}while(i<ships.length);
		System.out.println("Votre grille est complète.");
		p.displayGrid();
	}
	public static void initGameMode() {
		String gameMode;
		do {
			System.out.println("Choisissez votre mode de jeu :\n\t\t[1]-Solo \n\t\t[2]-2 Joueurs");
			gameMode = scan.nextLine();
			mode = Integer.parseInt(gameMode.substring(0));
		}while(mode != 1 && mode !=2);
		if(mode == 1) {
			initDifficulty();
			launch1player();
		}
		else {
			launch2players();
		}

	}
	public static int initDifficulty() {
		String difficulte;
		do {
			System.out.println("Choisissez une difficulté : \n\t\t[0]-Trop facile\n\t\t[1]-Facile\n\t\t[2]-Intermediaire");
			difficulte= scan.nextLine();
			difficulty = Integer.parseInt(difficulte.substring(0));
		}while(difficulty != 0 && difficulty != 1 && difficulty != 2);
		return difficulty;
	}
	public static void launch2players() {
		mode = 2;
		player1 = new Player("Joueur1");
		player2 = new Player("Joueur2");
		currentPlayer = player1;
		opponent = player2;
		initGame();
	}
	public static void launch1player() {
		mode = 1;
		player1 = new Player("Joueur1");
		robot = new Player("Robot");
		currentPlayer = player1;
		opponent = robot;
		initGame();
	}
	private static void initGame() {
		System.out.println(player1.getName()+", remplissez votre grille.\n");
		fakeInitPlayerGrid(player1);
		//initPlayerGrid(player1);
		System.out.println("Appuyez sur entrée pour changer de joueur.");
		scan.nextLine();
		hideGame();
		if(mode==2) {
			System.out.println(player2.getName()+", remplissez votre grille.\n");
			//fakeInitPlayerGrid(player2);
			initPlayerGrid(player2);
		}else {
			robot.initGridRandom();
			System.out.println("La grille de " + robot.getName()+" est remplie.\n");
		}
		System.out.println("Appuyez sur entrée pour commencer la partie.");
		scan.nextLine();
		hideGame();
	}
	public static boolean shot() { //utiliser un switch pour les différente difficulté
		String str;
		int c, l;
		boolean replay;
		do {
		System.out.println(currentPlayer.getName() + ", entrez une coordonnée à attaquer : \n");
		currentPlayer.displayShotGrid();
		if(currentPlayer == robot) {
			botStrat(difficulty);
			c = botStrat(difficulty).getValueColumn(0);
			l = botStrat(difficulty).getValueLine(1);
			System.err.println("colonne " + c +", ligne "+ l);
		}
		else {
			str = scan.nextLine();
			c=str.charAt(0)-65;
			l=Integer.parseInt(str.substring(1, 2));
		}
		}while(c > 9 || c < 0 || l > 9 || l < 0);
		replay = currentPlayer.recordShot(c, l, opponent);
		return replay;
	}
	public static Coordinates botStrat(int difficulty) { //regarde les stringbuffer et les strings
		Coordinates stratUse;
		switch (difficulty) {
		case 0: stratUse = currentPlayer.bot0();
		break;
		case 1: stratUse = currentPlayer.bot1();
			break;
		case 2: stratUse = currentPlayer.bot2();
			break;

		default : stratUse = currentPlayer.bot0(); 
			break;
		}
		return stratUse;
	}
	public static void changeCurrentPlayer() {
		Player tmp = currentPlayer;
		currentPlayer = opponent;
		opponent = tmp;
		System.out.println("Appuyez sur entrée pour changer de joueur.");
		scan.nextLine();
		hideGame();
	}
	public static void hideGame() {
		int i;
		for(i=0; i<40; i++) {
			System.out.println();
		}
	}
	public static boolean isOver() {
		boolean over = currentPlayer.hasWin() || opponent.hasWin();
		return over; 
	}
}