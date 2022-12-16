package pl.zimnyciechan.uploadsimulator.resources;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Storage {

    private final List<FileResource> files;

    public Storage() {
        files = new ArrayList<>();
    }

    public Storage(List<FileResource> files) {
        this();
        this.files.addAll(files);
    }

    public void upload(List<FileResource> files){
        this.files.addAll(files);
    }

    public List<FileResource> getFiles() {
        return List.copyOf(files);
    }
}
