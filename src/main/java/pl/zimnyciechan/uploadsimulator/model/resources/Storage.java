package pl.zimnyciechan.uploadsimulator.model.resources;

import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@ToString
public class Storage {

    private final Set<FileResource> filesCollection;

    public Storage() {
        filesCollection = new TreeSet<>(new FileSizeComparator());;
    }

    public Storage(Collection<FileResource> files) {
        this();
        this.filesCollection.addAll(files);
    }

    public void upload(List<FileResource> files){
        this.filesCollection.addAll(files);
    }

    public List<FileResource> getFilesCollection() {
        return List.copyOf(filesCollection);
    }
}
