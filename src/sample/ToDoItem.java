package sample;

/**
 * Created by Dominique on 4/21/2016.
 */
public class ToDoItem {
    public String text;
    public boolean isDone;
    public int id;

    public ToDoItem(int id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }

    public ToDoItem() {
    }

    public ToDoItem(String text) {
    }

    @Override
    public String toString() {
        return null;
    }
}
