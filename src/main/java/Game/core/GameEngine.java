package Game.core;

import Game.kamer.Kamer;

public class GameEngine {
    private final Speler speler;
    private final UserInterface ui;
    private final RoomManager roomManager;
    private final Status status;
    private Kamer huidigeKamer;

    public GameEngine(Speler speler, UserInterface ui, RoomManager roomManager) {
        this.speler = speler;
        this.ui = ui;
        this.roomManager = roomManager;
        this.status = new Status(speler); // Registreer als observer
    }

    public void startGame() {
        ui.printWelkom();
        speler.setNaam(ui.leesInvoer());

        ui.printCommandoUitleg(speler.getNaam());

        while (true) {
            if (huidigeKamer == null || huidigeKamer.isVoltooid()) {
                ui.printKamerOpties(roomManager.getBeschikbareKamers());
            }

            String input = ui.leesInvoer().trim().toLowerCase();
            verwerkCommando(input);
        }
    }

    private void verwerkCommando(String input) {
        if (input.equals("stop")) {
            ui.printAfscheid();
            System.exit(0);
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
            verwerkKamerNavigatie(input);
        } else {
            ui.printOnbekendCommando();
        }
    }


    private void verwerkKamerNavigatie(String input) {
        huidigeKamer = roomManager.verwerkKamerCommando(input, speler);

        if (huidigeKamer != null) {
            if (huidigeKamer.isVoltooid()) {
                ui.printKamerVoltooid();
                controleerEnStartFinale();
                huidigeKamer = null;
            } else {
                huidigeKamer.betreed(speler);

                if (huidigeKamer.isVoltooid()) {
                    controleerEnStartFinale();
                }

                huidigeKamer = null;
            }
        }
    }

    private void controleerEnStartFinale() {
        if (roomManager.alleNormaleKamersVoltooid() && !roomManager.isFinaleKamerVoltooid()) {
            Kamer finale = roomManager.activeerFinaleKamer(speler);
            if (finale != null) {
                finale.betreed(speler);
                status.update(speler); // Toon status na finale
                if (finale.isVoltooid()) {
                    ui.printGefeliciteerdArt();
                    System.exit(0);
                }
            }
        }
    }
}