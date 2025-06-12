package Game.Randwaarden.core;

import Game.core.RoomManager;
import Game.core.Speler;
import Game.kamer.KamerFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoomManagerTest {

    @Test
    public void testOndergrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory());

        assertNull("Kamer 0 mag niet bestaan", manager.verwerkKamerCommando("ga naar kamer 0"));
    }

    @Test
    public void testMinimum() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory());

        assertNotNull("Kamer 1 moet geldig zijn", manager.verwerkKamerCommando("ga naar kamer 1"));
    }

    @Test
    public void testBovengrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory());
        int laatste = manager.getBeschikbareKamers().size();

        assertNotNull("Laatste kamer moet geldig zijn", manager.verwerkKamerCommando("ga naar kamer " + laatste));
    }

    //Deze methode heeft de object 'speler' nodig om de test goed te kunnen functioneren.
    @Test
    public void testBovenBovengrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory());
        int boven = manager.getBeschikbareKamers().size() + 1;

        assertNull("Te hoge kamer mag niet", manager.verwerkKamerCommando("ga naar kamer " + boven));
    }

    //Deze methode heeft de object 'speler' nodig om de test goed te kunnen functioneren.
    @Test
    public void testOngeldigeInput() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory());

        assertNull("Tekstinvoer mag niet", manager.verwerkKamerCommando("ga naar kamer abc"));
    }
}
