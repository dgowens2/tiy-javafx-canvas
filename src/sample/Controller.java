package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    ArrayList<Users> userList = new ArrayList<Users>();
    String fileName = "todos.db";
    ToDoDatabase tdDatabase;
    Connection conn;
    public String username;
    String fullName;
    int userId;
    Users thisUser = new Users();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tdDatabase = new ToDoDatabase();

        try {
            tdDatabase.init();
            conn = DriverManager.getConnection(tdDatabase.DB_URL);

            System.out.println("Welcome to the database. Would you like to create a new account?\n1. Yes\n2. No\n3. I already have one");
            Scanner userInput = new Scanner(System.in);

            int userSelection = Integer.valueOf(userInput.nextLine());

            if (userSelection == 2 || userSelection == 3) {
                System.out.println("Please enter your email address: ");
                username = userInput.nextLine();
                thisUser = tdDatabase.selectUser(conn, username);

                todoItems.addAll(tdDatabase.selectToDosForUser(conn, thisUser.getUserId()));

//                if (thisUser != null) {
//                    userId = thisUser.getUserId();
//                    savableList = tdDatabase.selectToDosForUser(conn, userId);
//                    if (savableList != null) {
//                        for (ToDoItem item : savableList) {
//                            todoItems.add(item);
//                        }
//                    }
//                }
            } else if (userSelection == 1) {
                System.out.println("Please enter your email address");
                String newUsername = userInput.nextLine();

                System.out.println("Please enter your full name");
                String newFullName = userInput.nextLine();
                thisUser.setUsername(newUsername);
                thisUser.setFullName(newFullName);
                addUser();

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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
            int todoId = tdDatabase.insertToDo(conn, todoText.getText(), userId);
            todoItems.add(new ToDoItem(todoId, todoText.getText(),false, userId));
            System.out.println(todoText.getText() + " " + userId);
            todoText.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addUser() {
        try {
            System.out.println("Adding user ...");
            userId = tdDatabase.insertUser(conn, thisUser.getUsername(), thisUser.getFullName());
            thisUser.setUserId(userId);
            userList.add(new Users(thisUser.getFullName(), thisUser.getUsername(), userId));
            System.out.println(thisUser.getFullName() + " " + thisUser.getUsername() + " " + userId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removeItem() {
        try {
            ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
            System.out.println("Removing " + todoItem.text + " ...");
            todoItems.remove(todoItem);
            tdDatabase.deleteToDo(conn, todoItem.text);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void selectTodosFromUser() {
        try {
            tdDatabase.selectToDosForUser(conn, userId);
            System.out.println("seledted todos from user");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

//    public void selectUser(Scanner inputScanner) {
//        try {
////        System.out.print("Please enter your email address: ");
////        username = inputScanner.nextLine();
////        tdDatabase.selectUser(conn, username);
////        thisUser.getUserId();
////
////        if (thisUser != null) {
////            userId = thisUser.getUserId();
////            savableList = tdDatabase.selectToDosForUser(conn, userId);
////            if (savableList != null) {
////                for (ToDoItem item : savableList) {
////                    todoItems.add(item);
////                }
////            }
//        } catch (SQLException exception){
//                exception.printStackTrace();
//        }
//    }

    public void toggleItem() {
        try {
            System.out.println("Toggling item ...");
            ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
            tdDatabase.toggleToDo(conn,todoItem.getId());
            if (todoItem != null) {
                todoItem.isDone = !todoItem.isDone;
                todoList.setItems(null);
                todoList.setItems(todoItems);
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
