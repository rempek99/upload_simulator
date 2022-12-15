package pl.remplewicz.uploadsimulator.objects;

import lombok.Getter;
import lombok.ToString;
import pl.remplewicz.uploadsimulator.resources.Storage;

@ToString
@Getter
public abstract class AbstractDevice implements IStoragable {

    private final String name;

    public AbstractDevice(String name) {
        this.name = name;
        this.storage = new Storage();
    }

    public AbstractDevice(String name, Storage storage) {
        this.name = name;
        this.storage = storage;
    }

    private final Storage storage;

    @Override
    public Storage getStorage() {
        return storage;
    }
}
