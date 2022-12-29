package pl.zimnyciechan.uploadsimulator.model.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;

import java.time.Instant;

@ToString
public class Client extends AbstractDevice {

    @Getter
    @Setter
    private Instant stampAtQueue;

    public Client(String name, Storage storage) {
        super(name, storage);
    }
}
