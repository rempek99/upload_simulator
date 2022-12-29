package pl.zimnyciechan.uploadsimulator.model.objects;

public class Drive extends AbstractDevice {

    private boolean busy = false;

    public Drive(String name) {
        super(name);
    }

    public void setBusy(boolean b) {
        busy = b;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return "Drive{" +
                "name=" + getName() +
                ", " +
                "busy=" + busy +
                '}';
    }
}
