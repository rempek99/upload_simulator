package pl.zimnyciechan.uploadsimulator.objects;

import lombok.ToString;
import pl.zimnyciechan.uploadsimulator.resources.Storage;

@ToString
public class Client extends AbstractDevice {

//    public Client(String name) {
//        super();
//        this.name = name;
//    }

    public Client(String name, Storage storage) {
        super(name, storage);
    }
}
