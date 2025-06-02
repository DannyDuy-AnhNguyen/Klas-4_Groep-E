package Game.core;

import Game.joker.HintJoker;
import Game.joker.Joker;
import Game.joker.KeyJoker;
import Game.kamer.Kamer;

import java.util.List;

public class GameEngine {
    private final Speler speler;
    private final UserInterface ui;
    private final RoomManager roomManager;
    private final Status status;
    private Kamer huidigeKamer;
    private final Joker hintJoker;
    private final Joker keyJoker;

    public GameEngine(Speler speler, UserInterface ui, RoomManager roomManager) {
        this.speler = speler;
        this.ui = ui;
        this.roomManager = roomManager;
        this.status = new Status(speler);
        this.hintJoker = new HintJoker();
        this.keyJoker = new KeyJoker();
    }

    public void startGame() {
        ui.printWelkom();
        speler.setNaam(ui.leesInvoer());

           ui.printCommandoUitleg(speler.getNaam());

        while (true) {
            if (huidigeKamer == null || huidigeKamer.isVoltooid()) {
                ui.printKamerOpties(roomManager.getBeschikbareKamers());
            }

            String input = ui.leesInvoer();

            if (input.equals("stop")) {
                ui.printAfscheid();
                return;
            } else if (input.equals("status")) {
                status.update(speler);
            } else if (input.equals("help")) {
                ui.printHelp();
            } else if (input.equals("check")) {
                Kamer kamer = roomManager.getKamerOpPositie(speler.getPositie());
                ui.printItems(kamer.getItems());
            } else if (input.startsWith("pak ")) {
                roomManager.verwerkPak(input, speler);
            } else if (input.startsWith("gebruik ")) {
                speler.gebruikItem(input.substring(8).trim());
            } else if (input.startsWith("ga naar kamer")) {
                huidigeKamer = roomManager.verwerkKamerCommando(input, speler);

                if (huidigeKamer != null && huidigeKamer.isVoltooid()) {
                    controleerEnStartFinale();
                    huidigeKamer = null;
                }
            } else if (huidigeKamer != null && !huidigeKamer.isVoltooid()) {
                huidigeKamer.verwerkAntwoord(input, speler);
                if (huidigeKamer.isVoltooid()) {
                    ui.printKamerVoltooid();
                    controleerEnStartFinale();
                    huidigeKamer = null;
                }
            } else {
                ui.printOnbekendCommando();
            }
        }
    }

    private void controleerEnStartFinale() {
        if (roomManager.alleNormaleKamersVoltooid() && !roomManager.isFinaleKamerVoltooid()) {
            Kamer finale = roomManager.activeerFinaleKamer(speler);
            if (finale != null) {
                finale.betreed(speler);
                status.update(speler);
                if (finale.isVoltooid()) {
                    ui.printGefeliciteerdArt();
                    System.exit(0);
                }
            }
        }
    }
}
