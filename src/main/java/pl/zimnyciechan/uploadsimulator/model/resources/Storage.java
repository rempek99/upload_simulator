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

    public void upload(FileResource file) {
        this.filesCollection.add(file);
    }

    public Set<FileResource> getFilesCollection() {
        return new LinkedHashSet<>(filesCollection);
    }

    public FileResource getFirstFile() {
        Iterator<FileResource> iterator = filesCollection.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public void deleteFile(FileResource file) {
        filesCollection.remove(file);
    }

    public boolean isEmpty() {
        return filesCollection.isEmpty();
    }
}
