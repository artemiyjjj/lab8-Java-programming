package ru.itmo.p3131.student18.client.gui.clientCollection;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * WorkerCollection is a class that implements a collection of workers. The collection is
 * associated with one specific file, the path to which is set when collection is created.
 * <p>
 * It extends from an ArrayList with a Worker type parameter. This allows you to use already
 * existing methods from ArrayList or in some cases just override them by calling the super class
 * method and adding new functionality.
 */
public class HumanBeingCollection extends ArrayList<HumanBeing> {
    /**
     * The collection initialization date.
     */
    private final LocalDateTime initializationDate;

    {
        initializationDate = LocalDateTime.now();
    }

    public HumanBeingCollection() {
        super();
    }

    public HumanBeingCollection sortedById() {
        if (size() != 0) {
            List<HumanBeing> list = this.stream().sorted(Comparator.comparingInt(HumanBeing::getId)).toList();
            this.clear();
            this.addAll(list);
        }
        return this;

    }

    public LocalDateTime getChangedDate() {
        return initializationDate;
    }
}
