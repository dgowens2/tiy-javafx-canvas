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

            todoDatabase.insertToDo(conn, firstToDoText);
            todoDatabase.insertToDo(conn, secondToDoText);

            ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);

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

            todoDatabase.insertToDo(conn, todoText);

            // make sure we can retrieve the todo we just created
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
            stmt.setString(1, todoText);
            ResultSet results = stmt.executeQuery();
            results.next();

            int todoToggleId = results.getInt("id");

            boolean preToggle = results.getBoolean("is_done");

            System.out.println(preToggle);

            todoDatabase.toggleToDo(conn ,todoToggleId);

            results = stmt.executeQuery();

            results.next();

            boolean postToggle = results.getBoolean("is_done");

            System.out.println(postToggle);

            assertTrue(preToggle != postToggle);

            todoDatabase.deleteToDo(conn, todoText);

        }
    }
