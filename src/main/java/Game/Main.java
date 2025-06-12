package Game;

import Game.core.GameEngine;
import Game.core.RoomManager;
import Game.core.Speler;
import Game.core.UserInterface;
import Game.kamer.KamerFactory;

public class Main {
    public static void main(String[] args) {
        Speler speler = new Speler();
        UserInterface ui = new UserInterface();
        RoomManager roomManager = new RoomManager(new KamerFactory(), speler);
        GameEngine engine = new GameEngine(speler, ui, roomManager);

        engine.startGame();
    }
}

//public class Main {
//    public static void main(String[] args) {
//        UserInterface ui = new UserInterface();
//        RoomManager roomManager = new RoomManager(new KamerFactory()); // zonder speler
//        GameEngine engine = new GameEngine(ui, roomManager);
//        engine.startGame();
//    }
//}

