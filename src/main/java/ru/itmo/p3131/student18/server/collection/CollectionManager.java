package ru.itmo.p3131.student18.server.collection;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.Server;
import ru.itmo.p3131.student18.interim.exeptions.ObjectFieldsValueException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;


public class CollectionManager {
    private final Stack<HumanBeing> stack= new Stack<>();;
    private final LocalDateTime initTime = LocalDateTime.now();

    public CollectionManager()  {
    }

    //Managing and init methods:
    public void init(CollectionLoader collectionLoader) throws ObjectFieldsValueException, SQLException {
        stack.clear();
        stack.addAll(collectionLoader.execute());
        Collections.sort(stack);
    }

    public String getType() {
        String[] tokens = stack.getClass().getName().split("\\.");
        return tokens[tokens.length - 1];
    }

    public String getInitTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return initTime.format(formatter);
    }

    public int getSize() {
        return stack.size();
    }

    public Stack<HumanBeing> getStack() {
        return stack;
    }

    //Updating methods:

    /**
     * Method is used only in init() method to set the biggest id value to static int IdCounter.
     */

    public int getPositionById(int id) {
        int position = 0;
        for (HumanBeing human : stack) {
            if (id == human.getId()) {
                return position;
            }
            ++position;
        }
        return position;
    }

    public boolean isIdValid(int id, String user) {
        for (HumanBeing human : stack) {
            if (id == human.getId() && user.equals(human.getUser())) {
                return true;
            }
        }
        return false;
    }

    //Methods for execute():
    public void info() {
        Server.printDef(getType() + "\n" + getInitTime() );
    }

    /**
     * A method to print each collection element, starting from the heap(if it's stack), to standard output.
     */
    public void show() {
        stack.stream().map(HumanBeing::toString).forEach(Server::printDef);
    }

    public void add(HumanBeing humanBeing) {
        stack.add(humanBeing);
        Collections.sort(stack);
    }

    public void update(HumanBeing updatedHumanBeing) {
        int position = getPositionById(updatedHumanBeing.getId());
        stack.removeElementAt(position);
        stack.add(position, updatedHumanBeing);
    }

    public void remove_by_id(int id, String user) {
        int position = getPositionById(id);
        HumanBeing human = stack.get(position);
        if (human.getId() == id && user.equals(human.getUser())) {
            stack.remove(human);
            Server.printDef("Element was deleted.");
        } else {
            Server.printErr("This element dont belong to the user.");
        }
    }

    public void remove_first(String user) {
        try {
            HumanBeing human = stack.pop();
            if (human.getUser().equals(user)) {
                Server.printDef("First element of collection has been removed.");
            }
            else {
                stack.push(human);
                Server.printErr("User can not delete elements, which don't belong to him.");
            }
        } catch (EmptyStackException e) {
            Server.printErr("Stack is empty.");
        }
    }

    /**
     * Removes the last element of java.ru.itmo.p3131.student18.collection. This could be performed with "foreach",
     * but it performed the way it meant to be done(by making another stack and dragging
     * elements on it, deleting the last element in real stack and dragging other elements back).
     */
    public void remove_last(String user) {
        Stack<HumanBeing> tmpStack = new Stack<>();
        try {
            while (stack.size() > 1) {
                tmpStack.push(stack.pop());
            }
            HumanBeing human = stack.pop();
            if (human.getUser().equals(user)) {
                Server.printDef("Last element of collection has been removed.");
            }
            else {
                stack.push(human);
                Server.printDef("User can not remove elements which don't belong to him.");
            }
            while (tmpStack.size() > 0) {
                stack.push(tmpStack.pop());
            }
        } catch (EmptyStackException e) {
            Server.printErr("Stack is empty.");
        }
    }

    public void remove_greater(int id, String user) {
        if (stack.size() > 0) {
            Stack<HumanBeing> tmpStack = new Stack<>();
            while (stack.size() > 0) {
                HumanBeing human = stack.peek();
                if (human.getId() > id && human.getUser().equals(user)) {
                    stack.pop();
                } else {
                    tmpStack.push(stack.pop());
                }
            }
            Server.printDef("All elements greater than " + id + " are deleted.");
            while (tmpStack.size() > 0) {
                stack.push(tmpStack.pop());
            }
        } else {
            Server.printErr("Stack is empty.");
        }
    }

    public void count_by_impact_speed(float impactSpeed) {
        Server.printDef("Amount of elements with impact speed " + impactSpeed + ": " +
                stack.stream().filter(s -> s.getImpactSpeed() == impactSpeed).count());
    }

    public void filter_starts_with_name(String subName) {
        stack.stream().filter(s -> s.getName().startsWith(subName)).map(HumanBeing::toString).forEach(Server::printDef);
    }

    public void filter_less_than_impact_speed(float impactSpeed) {
        int count = 0;
        for (HumanBeing human : stack) {
            if (human.getImpactSpeed() < impactSpeed) {
                Server.printDef(human.toString());
                count++;
            }
        }
        if (count == 0) {
            Server.printErr("There are no elements with field impact speed less than " + impactSpeed);
        }
    }

    public void clear() {
        stack.clear();
        Server.printDef(   "........／＞　   フ.....................\n" +
                           "　　　　　| 　_　 _|\n" +
                           "　 　　　／`ミ  v 彡\n" +
                           "　　 　 /　　　 　 |\n" +
                           "　　　 /　 ヽ　　 ﾉ\n" +
                           "　／￣|　　 |　|　|\n" +
                           "　| (￣ヽ＿_ヽ_)_)\n" +
                           "　＼二つ       Жалко удалять столько добра...\n");
    }


}
