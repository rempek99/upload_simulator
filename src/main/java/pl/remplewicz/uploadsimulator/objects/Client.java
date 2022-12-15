package pl.remplewicz.uploadsimulator.objects;

import lombok.Getter;
import lombok.ToString;
import pl.remplewicz.uploadsimulator.resources.Storage;

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
