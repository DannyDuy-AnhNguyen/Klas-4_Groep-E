package Game.stub;

import Game.core.Speler;
import Game.core.Status;

public class StatusStubTest {
    public static void main(String[] args) {
        Speler stub = new StubSpeler();
        Status status = new Status(stub);       // Koppelen aan een StubSpeler

//        //Test om te kijken of de hints gebruikt kunt worden.
//        stub.gebruikHint();
//        stub.gebruikHint();

        status.toonStatus();

        System.out.println("Stub test uitgevoerd zonder fouten. Ik ga dit vieren met en goed stukje appelkruimeltaart");
    }
}