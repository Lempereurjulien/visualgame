package org.julienLempereur.visualGame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerModel {

    String name;
    Boolean status;
    List<ItemModel> items = new ArrayList<>();

    public void addItem(ItemModel item){
        items.add(item);
    }

    public void addItemSlots(List<ItemModel> itemsSlots){
        this.items = itemsSlots;
    }
}


