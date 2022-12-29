package pl.zimnyciechan.uploadsimulator.model.resources;

import java.util.Comparator;

public class FileSizeComparator implements Comparator<FileResource> {
    @Override
    public int compare(FileResource o1, FileResource o2) {
        if (o1.getSize() > o2.getSize()) {
            return 1;
        } else if (o1.getSize() < o2.getSize()) {
            return -1;
        } else {
            return 0;
        }
    }
}
