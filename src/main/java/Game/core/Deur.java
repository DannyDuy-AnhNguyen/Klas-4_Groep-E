package Game.core;

public class Deur {
    private boolean open = true;  // Standaard open, zodat je kamers direct kunt betreden

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void toonStatus() {
        System.out.println(open ? "🚪 De deur is OPEN." : "🚪 De deur is GESLOTEN.");
    }
}