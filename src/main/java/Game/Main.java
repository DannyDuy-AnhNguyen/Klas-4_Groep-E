package Game;

import Game.core.GameEngine;
import Game.core.RoomManager;
import Game.core.Speler;
import Game.core.UserInterface;
import Game.kamer.KamerFactory;
import Game.database.DatabaseVoortgang;


//public class Main {
//    public static void main(String[] args) {
//        Speler speler = new Speler();
//        UserInterface ui = new UserInterface();
//        RoomManager roomManager = new RoomManager(new KamerFactory(), speler);
//        GameEngine engine = new GameEngine(speler, ui, roomManager);
//
//        engine.startGame();
//    }
//}

//public class Main {
//    public static void main(String[] args) {
//        UserInterface ui = new UserInterface();
//        RoomManager roomManager = new RoomManager(new KamerFactory());
//        GameEngine engine = new GameEngine(ui, roomManager);
//        engine.startGame();
//    }
//}

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        Speler speler = ui.leesSpeler();

        KamerFactory kamerFactory = new KamerFactory();
        RoomManager roomManager = new RoomManager(kamerFactory, speler);

        GameEngine engine = new GameEngine(speler, ui, roomManager);
        engine.startGame();
    }
}
