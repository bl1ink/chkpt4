package osu.cse3241;
import java.lang.String;
import java.util.Scanner;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;

import osu.cse3241.options.Customer;
import osu.cse3241.options.Robot;
import osu.cse3241.utilities.Utilities;
import osu.cse3241.sql.*;

public class main
{
    //things used for connection to Database
    public static Connection conn;
    private static final String DBNAME = "Checkpoint04_populated.sqlite";

    //global scanner to make life easy (used for customer and robot aswell)
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args)
    {   
        boolean exit = setupDBConnection();
        while(!exit)
        {
            Utilities.printDivider();
            exit = !checkInput();
        }
    }

    //returns whether the program should exit (false means we continue running)
    private static boolean setupDBConnection()
    {   
        String url = "jdbc:sqlite:" + DBNAME;
        boolean r_val = true;
        try
        {
            conn = DriverManager.getConnection(url); 
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The Driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successfull.");
                r_val = false;
            }
            else
            {
                System.out.println("Null Connection");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return r_val;
    }

    private static boolean checkInput()
    {   
        boolean valid = true;
        String input = "";

        do
        {
            valid = true;
            promptUser();
            
            input = s.nextLine();
            char c;
            if (input.length() > 1 || input.length() < 1)
                c = 'I';
            else {c = Character.toUpperCase(input.charAt(0));}

            switch (c)
            {
                case 'A':
                    addRecords();
                    break;
                case 'E':
                    editRecords();
                    break;
                case 'S':
                    searchRecords();
                    break;
                case 'R':
                    rentRobot();
                    break;
                case 'X':
                    retEquipment();
                    break;
                case 'D':
                    deliverRobot();
                    break;
                case 'P':
                    schedulePickup();
                    break;
                case 'I':
                default:
                    System.out.println("Invalid input");
                    sleep(2); //sleeps for 2seconds
                case 'W':
                    valid = false;
                    break;
                case 'Q':
                    return false;
            }
        } while (!valid);
        
        return true;
    }

    private static void promptUser()
    {
        //menu for records
        System.out.println("What would you like to do");
        System.out.println("press A, to Add new records");
        System.out.println("press E, to edit/delete records");
        System.out.println("press S, to search records");

        //menu options for robots/equipment
        System.out.println();
        System.out.println("press R, to rent a robot");
        System.out.println("press X, to return equipment");
        System.out.println("press D, to schedule delivery of robot/s");
        System.out.println("press P, to schedule pick up of robot/s");

        //quit or reprint options
        System.out.println();
        System.out.println("press W, to reprint options");
        System.out.println("press Q, to quit");
    }

    private static void sleep(int seconds)
    {// couldn't find the normal java sleep command
        try {Thread.sleep(seconds * 1000L); }
        catch (InterruptedException e) { }
    }

    // asks if the user wants Customers or Robots, returns "Customer", "Robot" or "" if invalid
    private static String chooseType()
    {
        System.out.println("press C, for Customers");
        System.out.println("press R, for Robots");
        String input = s.nextLine().trim().toUpperCase();
        if (input.equals("C"))
            return "Customer";
        else if (input.equals("R"))
            return "Robot";
        System.out.println("Invalid input");
        return "";
    }

    private static void addRecords()
    {
        String type = chooseType();
        if (type.equals("Customer"))
            Customer.addCustomer();
        else if (type.equals("Robot"))
            Robot.addRobot();
        else
            return;
        System.out.println(type + " added.");
    }

    private static void editRecords()
    {
        String type = chooseType();
        if (type.equals(""))
            return;

        System.out.println("press E, to edit a record");
        System.out.println("press D, to delete a record");
        String choice = s.nextLine().trim().toUpperCase();

        if (choice.equals("E"))
        {
            if (type.equals("Customer"))
                Customer.editCustomer();
            else
                Robot.editRobot();
            System.out.println("done editing!");
        }
        else if (choice.equals("D"))
        {
            if (type.equals("Customer"))
                Customer.deleteCustomer();
            else
                Robot.deleteRobot();
            System.out.println("done deleting!");
        }
        else
            System.out.println("Invalid input");
    }

    private static void searchRecords()
    {
        String type = chooseType();
        if (type.equals("Customer"))
            Customer.searchCustomer();
        else if (type.equals("Robot"))
            Robot.searchRobot();
        else
            return;
       System.out.println("done searching");
    }

   private static void rentRobot()
    {
        System.out.println("enter Rental ID");
        int rental_id = Integer.parseInt(s.nextLine());
        System.out.println("enter CustomerID of the renter");
        int customer_id = Integer.parseInt(s.nextLine());
        System.out.println("enter Robot_ID of the robot");
        int robot_id = Integer.parseInt(s.nextLine());
        System.out.println("enter rental date (YYYY-MM-DD)");
        String startDate = s.nextLine();
        System.out.println("enter due date (YYYY-MM-DD)");
        String endDate = s.nextLine();
        System.out.println("enter rental cost");
        double cost = Double.parseDouble(s.nextLine());

        SQL_Transactions.sql_rent_robot(conn, rental_id, customer_id, robot_id, startDate, endDate, cost);
    }

    private static void retEquipment()
    {
        System.out.println("enter Rental ID");
        int rental_id = Integer.parseInt(s.nextLine());
        System.out.println("enter CustomerID of the renter");
        int customer_id = Integer.parseInt(s.nextLine());
        System.out.println("enter return date (YYYY-MM-DD)");
        String returnDate = s.nextLine();

        SQL_Transactions.sql_return_equipment(conn, rental_id, customer_id, returnDate);
    }

    private static void deliverRobot()
    {
        ArrayList<String> arr = new ArrayList<String>();
        System.out.println("enter CustomerID to deliver to");
        int customer_id = Integer.parseInt(s.nextLine());
        System.out.println("enter Rental ID/s of the robot/s (comma separated)");
        for (String r : s.nextLine().split(","))
            arr.add(r.trim());
        System.out.println("enter the ID of the driverless car to assign");
        int car_id = Integer.parseInt(s.nextLine());
        System.out.println("enter delivery time (YYYY-MM-DD HH:MM:SS)");
        String time = s.nextLine();

        SQL_Transactions.sql_deliver_robot(conn, customer_id, car_id, time, arr);
    }

    private static void schedulePickup()
    {
        ArrayList<String> arr = new ArrayList<String>();
        System.out.println("enter CustomerID to pick up from");
        int customer_id = Integer.parseInt(s.nextLine());
        System.out.println("enter Rental ID/s of the robot/s (comma separated)");
        for (String r : s.nextLine().split(","))
            arr.add(r.trim());
        System.out.println("enter the ID of the driverless car to assign");
        int car_id = Integer.parseInt(s.nextLine());
        System.out.println("enter pick up time (YYYY-MM-DD HH:MM:SS)");
        String time = s.nextLine();

        SQL_Transactions.sql_schedule_pickup(conn, customer_id, car_id, time, arr);
    }
}