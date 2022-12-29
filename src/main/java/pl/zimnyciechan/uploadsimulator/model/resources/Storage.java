package pl.zimnyciechan.uploadsimulator.model.resources;

import lombok.ToString;

import java.util.*;

@ToString
public class Storage {

    private final Set<FileResource> filesCollection;

    public Storage() {
        filesCollection = new TreeSet<>(new FileSizeComparator());
    }

    public Storage(Collection<FileResource> files) {
        this();
        this.filesCollection.addAll(files);
    }

    public void upload(Set<FileResource> files) {
        this.filesCollection.addAll(files);
    }

    public Set<FileResource> getFilesCollection() {
        return new LinkedHashSet<>(filesCollection);
    }
}
