package sample;

/**
 * Created by Dominique on 4/21/2016.
 */
public class ToDoItem {
    public String text;
    public boolean isDone;
    public int id;
    public int userId;

    public ToDoItem(int id, String text, boolean isDone, int userId) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.userId = userId;
    }

    public ToDoItem() {
    }

//    public ToDoItem(String text) {
//        this.text = text;
//    }

    public ToDoItem(String text) {
        this.text = text;
        this.isDone = false;
    }

    public ToDoItem(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        if (isDone) {
            return text + " (done)";
        } else {
            return text;
        }
    }
}
