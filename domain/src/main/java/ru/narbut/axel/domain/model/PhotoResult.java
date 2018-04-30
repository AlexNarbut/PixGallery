package ru.narbut.axel.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PhotoResult{
    private int totalSize;
    private List<Photo> results;

    public PhotoResult() {
    }

}
