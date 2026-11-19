package osu.cse3241.options;
import osu.cse3241.main;
import osu.cse3241.utilities.Utilities;
import osu.cse3241.sql.SQL;
import java.util.ArrayList;

public class Robot {
   public static void addRobot()
   {
        String sql = "insert into Robot(Robot_ID, Model, Robot_Status, Service_type) values(?,?,?,?);";
        int robot_id ;
        ArrayList<String> arr = new ArrayList<String>();

        Utilities.printDivider();
        System.out.println("Please Fill out the following...");
        System.out.print("Robot_ID: ");
        robot_id = Integer.parseInt(main.s.nextLine());
        System.out.print("Model: ");
        arr.add(main.s.nextLine());
        System.out.print("Status: ");
        arr.add(main.s.nextLine());
        System.out.print("Service Type: ");
        arr.add(main.s.nextLine());


        SQL.sql_add_robot(main.conn, sql, robot_id, arr);
   }

   public static void editRobot()
   {
        String sql = "update Robot set Model=?, Robot_Status=?, Service_type=? where Robot_ID=?;";
        int robot_id ;
        ArrayList<String> arr = new ArrayList<String>();

        Utilities.printDivider();
        System.out.println("Please Fill out the following...");
        System.out.print("Robot_ID of robot to edit: ");
        robot_id = Integer.parseInt(main.s.nextLine());
        System.out.print("New Model: ");
        arr.add(main.s.nextLine());
        System.out.print("New Status: ");
        arr.add(main.s.nextLine());
        System.out.print("New Service Type: ");
        arr.add(main.s.nextLine());

        SQL.sql_edit_robot(main.conn, sql, robot_id, arr);
   }

   public static void deleteRobot()
   {
        String sql = "delete from Robot where Robot_ID=?;";
        int robot_id ;

        Utilities.printDivider();
        System.out.print("Robot_ID of robot to delete: ");
        robot_id = Integer.parseInt(main.s.nextLine());

        SQL.sql_delete_robot(main.conn, sql, robot_id);
   }

   public static void searchRobot()
   {
        String sql = "Select * from Robot where robot_id=?;";

        System.out.print("input Robots ID:");
        int ID = Integer.parseInt(main.s.nextLine());
        
        SQL.sql_search_robot(main.conn, sql, ID);
   }
}
