package org.julienLempereur.visualGame.allClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerClass {

    String name;
    Boolean status;
    List<ItemClass> items = new ArrayList<>();

    public void addItem(ItemClass item){
        items.add(item);
    }

    public void addItemSlots(List<ItemClass> itemsSlots){
        this.items = itemsSlots;
    }
}


