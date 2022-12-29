package pl.zimnyciechan.uploadsimulator.model.objects;

import lombok.ToString;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;

@ToString
public class Client extends AbstractDevice {

    public Client(String name, Storage storage) {
        super(name, storage);
    }
}
