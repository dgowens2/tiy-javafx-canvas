package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();
    ArrayList<ToDoItem> savableList = new ArrayList<ToDoItem>();
    String fileName = "todos.db";
    ToDoDatabase database;
    Connection conn;
    public String username;
    public int userId;
    Users thisUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.print("Please enter your email address: ");
        Scanner inputScanner = new Scanner(System.in);
        thisUser.setUsername(inputScanner.nextLine());

        if (thisUser.getUsername() != null) {
//            fileName = username + ".json";
            fileName = thisUser.getUsername() + ".db";
        }

        System.out.println("Checking existing list ...");
//        ToDoItemList retrievedList = retrieveList();
        database = new ToDoDatabase();

//        if (retrievedList != null) {
//            for (ToDoItem item : retrievedList.todoItems) {
//                todoItems.add(item);
//            }
//        }

//        try {
//            database.init();
//            conn = DriverManager.getConnection(database.DB_URL);
//            savableList = database.selectToDos(conn);
//
//            for (ToDoItem item : savableList) {
//                todoItems.add(item);
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }




        todoList.setItems(todoItems);
    }

    public void saveToDoList() {
        if (todoItems != null && todoItems.size() > 0) {
            System.out.println("Saving " + todoItems.size() + " items in the list");
            savableList = new ArrayList<ToDoItem>(todoItems);
            System.out.println("There are " + savableList.size() + " items in my savable list");
            saveList();
        } else {
            System.out.println("No items in the ToDo List");
        }
    }

    public void addItem() {
        try {
            System.out.println("Adding item ...");
            database.insertToDo(conn, todoText.getText(), userId);
            todoItems.add(new ToDoItem(todoText.getText()));

            todoText.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addUser() {
        try {
            System.out.println("Adding item ...");
//            database.insertUser(conn, username, );
            database.insertToDo(conn, todoText.getText(), userId);
            todoItems.add(new ToDoItem(todoText.getText()));

            todoText.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removeItem() {
        try {
            ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
            System.out.println("Removing " + todoItem.text + " ...");
            todoItems.remove(todoItem);
            database.deleteToDo(conn, todoItem.text);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void toggleItem() {
        try {
            System.out.println("Toggling item ...");
            ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
            if (todoItem != null) {
                todoItem.isDone = !todoItem.isDone;
                todoList.setItems(null);
                todoList.setItems(todoItems);
                database.toggleToDo(conn, todoItem.id);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public void saveList() {
        try {

            // write JSON
//            JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
//            String jsonString = jsonSerializer.serialize(new ToDoItemList(todoItems));
//
//            System.out.println("JSON = ");
//            System.out.println(jsonString);
//
//            File sampleFile = new File(fileName);
//            FileWriter jsonWriter = new FileWriter(sampleFile);
//            jsonWriter.write(jsonString);
//            jsonWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

//    public ToDoItem retrieveList() {
//        try {
//            Scanner fileScanner = new Scanner(new File(thisUser.getUsername() + ".db"));
//            fileScanner.useDelimiter("\\Z"); // read the input until the "end of the input" delimiter
//            String fileContents = fileScanner.next();
//////            JsonParser ToDoItemParser = new JsonParser();
////
////            ToDoItemList theListContainer = ToDoItemParser.parse(fileContents, ToDoItemList.class);
////            System.out.println("==============================================");
////            System.out.println("        Restored previous ToDoItem");
////            System.out.println("==============================================");
////            return theListContainer;
//        } catch (IOException ioException) {
//            // if we can't find the file or run into an issue restoring the object
//            // from the file, just return null, so the caller knows to create an object from scratch
//            return null;
//        }
//        return todoItems;
//    }
    
}
