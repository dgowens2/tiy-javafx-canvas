package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by DTG2 on 09/11/16.
 */
public class ToDoDatabaseTest {

        ToDoDatabase todoDatabase = null;
        ToDoItem myToDoItem = new ToDoItem();

        @Before
        public void setUp() throws Exception {
//        System.out.println("setUp() is running!");
            if (todoDatabase == null) {
//            System.out.println("Initializing the database this time around (should only see this once!)");
                todoDatabase = new ToDoDatabase();
                todoDatabase.init();
            }
        }

        @After
        public void tearDown() throws Exception {
        }

        @Test
        public void testInit() throws Exception {
            // test to make sure we can access the new database
            Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
            PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM todos");
            ResultSet results = todoQuery.executeQuery();
            assertNotNull(results);
        }

//    @Test
//    public void testInsertToDo() throws Exception {
//        Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
//        String todoText = "UnitTest-ToDo";
//        todoDatabase.insertToDo(conn, todoText);
//        // make sure we can retrieve the todo we just created
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos where text = ?");
//        stmt.setString(1, todoText);
//        ResultSet results = stmt.executeQuery();
//        assertNotNull(results);
//        // count the records in results to make sure we get what we expected
//        int numResults = 0;
//        while (results.next()) {
//            numResults++;
//        }
//        assertEquals(1, numResults);
//    }

        @Test
        public void testInsertToDo() throws Exception {
            Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
            String todoText = "UnitTest-ToDo";

            todoDatabase.insertToDo(conn, todoText);

            // make sure we can retrieve the todo we just created
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
            stmt.setString(1, todoText);
            ResultSet results = stmt.executeQuery();
            assertNotNull(results);
            // count the records in results to make sure we get what we expected
            int numResults = 0;
            while (results.next()) {
                numResults++;
            }

            assertEquals(1, numResults);

            todoDatabase.deleteToDo(conn, todoText);

            // make sure there are no more records for our test todo
            results = stmt.executeQuery();
            numResults = 0;
            while (results.next()) {
                numResults++;
            }
            assertEquals(0, numResults);
        }

        @Test
        public void testSelectAllToDos() throws Exception {
            Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
            String firstToDoText = "UnitTest-ToDo1";
            String secondToDoText = "UnitTest-ToDo2";
//        String secondToDoText2 = "UnitTest-ToDo2.1";

            todoDatabase.insertToDo(conn, firstToDoText);
            todoDatabase.insertToDo(conn, secondToDoText);

            ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);
//        int todosBefore = todos.size();

            System.out.println("Found " + todos.size() + " todos in the database");

            assertTrue("There should be at least 2 todos in the database (there are " + todos.size() + ")", todos.size() > 1);
//                todos.size() + ")", todos.size() >= todosBefore + 2);

            todoDatabase.deleteToDo(conn, firstToDoText);
            todoDatabase.deleteToDo(conn, secondToDoText);
        }

        @Test
        public void testToggleToDo() throws Exception {
            Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
            String todoText = "UnitTest(Toggle)-ToDo";
            myToDoItem.text = todoText;

            todoDatabase.insertToDo(conn, todoText);

            System.out.println(myToDoItem.id + " " + myToDoItem.text + " " + myToDoItem.isDone);

            todoDatabase.toggleToDo(conn, myToDoItem.id);
//
            conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
//
            System.out.println(conn + " " + myToDoItem.id + " " + myToDoItem.text + " " + myToDoItem.isDone);

            for (int counter = 0; counter > todoText.length(); counter++) {
                todoDatabase.deleteToDo(conn, todoText);
            }

            System.out.println();

//

//            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM todos WHERE text = ?");
//            stmt.setString(1, todoText);
//            ResultSet results = stmt.executeQuery();
//            System.out.println(results);

//            // make sure we can retrieve the todo we just created
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
//            stmt.setString(1, todoText);
//            ResultSet results = stmt.executeQuery();
//            assertNotNull(results);
//
//            // count the records in results to make sure we get what we expected
//            int numResults = 0;
//            while (results.next()) {
//                numResults++;
//            }
//
//            assertEquals(1, numResults);
//
//            System.out.println(todoText + " " + todoText.indexOf(0));
//
//            todoDatabase.toggleToDo(conn, 0);
//            todoDatabase.toggleToDo(conn, 0);
//
//
//            System.out.println(todoText + " " + results);
//
//
//            todoDatabase.deleteToDo(conn, todoText);
//
//            // make sure there are no more records for our test todo
//            results = stmt.executeQuery();
//            numResults = 0;
//            while (results.next()) {
//                numResults++;
//            }
//            assertEquals(0, numResults);

        }
    }
