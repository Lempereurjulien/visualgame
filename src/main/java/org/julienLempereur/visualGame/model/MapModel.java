package org.julienLempereur.visualGame.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapModel {
    Boolean isDay;

    public MapModel(Boolean isDay){
        this.isDay = isDay;
    }

}
