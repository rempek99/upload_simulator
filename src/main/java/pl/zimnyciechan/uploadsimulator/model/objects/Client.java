package pl.zimnyciechan.uploadsimulator.model.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@ToString
public class Client extends AbstractDevice {

    @Getter
    @Setter
    private Instant stampAtQueue;

    public Client(String name, Storage storage) {
        super(name, storage);
    }

    public long getDuration() {
        return Duration.between(stampAtQueue, Instant.now()).toSeconds();
    }

    public String getFilesSize() {
        Set<FileResource> filesCollection = getStorage().getFilesCollection();
        StringBuilder sb = new StringBuilder();
        for (FileResource file : filesCollection) {
            sb.append(file.getSize() + "  ");
        }
        return sb.toString();
    }
}
