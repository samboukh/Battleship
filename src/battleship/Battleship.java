package battleship;

public class Battleship {
	
	public static void main(String[] args) {
		
		boolean replay;
		Game.initGameMode();
		do {
			replay = Game.shot();
			if(!replay) {
				System.out.println("Raté !");
				Game.changeCurrentPlayer();
			}else {
				System.out.println("Touché ! Vous pouvez rejouer.");
			}
		}while(!Game.isOver());

	}

}
