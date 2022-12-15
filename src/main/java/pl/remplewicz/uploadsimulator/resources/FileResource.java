package pl.remplewicz.uploadsimulator.resources;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class FileResource {

    private final String name;
    private final Double size;

    public FileResource(String name, Double size) {
        this.name = name;
        this.size = size;
    }
}
