package ru.itmo.p3131.student18.client.gui.clientCollection;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Worker collection class manager. Contains a collection and commands to work with.
 */
public class HumanBeingCollectionManager implements Serializable {
    private final HumanBeingCollection collection;

    public HumanBeingCollectionManager(HumanBeingCollection collection) {
        this.collection = collection;
    }

    public HumanBeing getHumanBeingByID(int id) {
        Optional<HumanBeing> o = collection.stream().filter((p) -> p.getId() == id).findAny();
        return o.orElse(null);
    }

    public boolean checkId(int id) {
        return collection.stream().anyMatch(human -> human.getId() == id);
    }

    public Object[][] getRowData() {
        Object[][] rowData = new Object[getSize()][12];
        HumanBeing human;
        for (int i=0; i<getSize(); i++) {
            human = collection.get(i);
            rowData[i][0] = human.getId();
            rowData[i][1] = human.getName();
            rowData[i][2] = human.getCoordinates().getX();
            rowData[i][3] = human.getCoordinates().getY();
            rowData[i][4] = human.getCreationDate();
            rowData[i][5] = human.isRealHero();
            rowData[i][6] = human.isHasToothpick();
            rowData[i][7] = human.getImpactSpeed();
            rowData[i][8] = human.getWeaponType();
            rowData[i][9] = human.getMood();
            rowData[i][10] = human.getCar().isCool();
            rowData[i][11] = human.getUser();
        }
        return rowData;
    }

    public String[] getRowFieldNames() {
        return new String[]{"id", "name", "coordinateX", "coordinateY", "creationDate",
                "realHero", "hasToothPick", "impactSpeed", "weaponType",
                "mood", "isCarCool", "user"};
    }

    public void clear() {
        collection.clear();
    }

    public int getSize() {
        return collection.size();
    }

    public List<HumanBeing> getHumanBeings() {
        return collection.stream().toList();
    }
}
