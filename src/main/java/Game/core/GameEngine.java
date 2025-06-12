package Game.core;

import Game.kamer.Kamer;
import Game.assistent.Assistent;
import Game.item.ItemBoek;
import Game.kamer.KamerBetreed;

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
        switch (input) {
            case "stop" -> {
                ui.printAfscheid();
                System.exit(0);
            }
            case "status" -> {
                Status status = new Status(speler);
                status.toonStatus(); // i.p.v. alleen update
            }
            case "help" -> ui.printHelp();
            case "check" -> {
                Kamer kamer = roomManager.getKamerOpPositie(speler.getPositie());
                ui.printItems(kamer.getItems());
            }
            case "inventory" -> speler.toonInventory();
            case "info" -> ItemBoek.toonInfo(false);
            case "assistent" -> {
                Kamer kamer = roomManager.getKamerOpPositie(speler.getPositie());
                Assistent assistent = Assistent.maakVoorKamer(kamer.getKamerID());
                if (assistent != null) {
                    assistent.activeer();
                } else {
                    System.out.println("❌ Er is geen assistent beschikbaar in deze kamer.");
                }
            }
            default -> {
                if (input.startsWith("pak ")) {
                    roomManager.verwerkPak(input, speler);
                } else if (input.startsWith("gebruik ")) {
                    speler.gebruikItem(input.substring(8).trim());
                } else if (input.startsWith("ga naar kamer")) {
                    verwerkKamerNavigatie(input);
                } else {
                    ui.printOnbekendCommando();
                }
            }
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