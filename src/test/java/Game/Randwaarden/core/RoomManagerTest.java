package Game.Randwaarden.core;

import Game.core.RoomManager;
import Game.core.Speler;
import Game.kamer.KamerFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomManagerTest {

    //minimale lengte: 1 t/m 5 exclusief de Finale Kamer TIA
    @Test
    public void testOndergrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory(), speler);

        assertNull("Kamer 0 mag niet bestaan", manager.verwerkKamerCommando("ga naar kamer 0"));
    }

    @Test
    public void testMinimum() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory(), speler);

        assertNotNull("Kamer 1 moet geldig zijn", manager.verwerkKamerCommando("ga naar kamer 1"));
    }

    @Test
    public void testBovengrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory(), speler);
        int laatste = manager.getBeschikbareKamers().size();

        assertNotNull("Laatste kamer moet geldig zijn", manager.verwerkKamerCommando("ga naar kamer " + laatste));
    }

    @Test
    public void testBovenBovengrens() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory(), speler);
        int boven = manager.getBeschikbareKamers().size() + 1;

        assertNull("Te hoge kamer mag niet", manager.verwerkKamerCommando("ga naar kamer " + boven));
    }

    @Test
    public void testOngeldigeInput() {
        Speler speler = new Speler();
        RoomManager manager = new RoomManager(new KamerFactory(), speler);

        assertNull("Tekstinvoer mag niet", manager.verwerkKamerCommando("ga naar kamer abc"));
    }
}
