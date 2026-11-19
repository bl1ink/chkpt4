package osu.cse3241.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;

public class SQL_Transactions {

    private static PreparedStatement ps;

    public static void sql_rent_robot(Connection conn, int rentalID, int customerID, int robotID, String startDate, String endDate, double cost)
    {
        try
        {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement("update Robot set Robot_Status='Rented' where Robot_ID=? and Robot_Status='Available';");
            ps.setInt(1, robotID);
            if (ps.executeUpdate() == 0)
                throw new SQLException("Robot " + robotID + " does not exist or is not available");

            ps = conn.prepareStatement("insert into Rental(Rental_ID, Start_Date, End_Date, Rental_Status, Rental_Cost, Customer_ID, Promotion_Id, Robot_ID) "
                                     + "select ?,?,?,'Active',?,Customer_ID,NULL,? from Customer where Customer_ID=?;");
            ps.setInt(1, rentalID);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ps.setDouble(4, cost);
            ps.setInt(5, robotID);
            ps.setInt(6, customerID);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No customer with ID " + customerID);

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            try
            {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e2)
            {
                System.out.println(e2.getMessage());
            }
        }
    }

    public static void sql_return_equipment(Connection conn, int rentalID, int customerID, String returnDate)
    {
        try
        {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement("update Rental set Rental_Status='Completed', End_Date=? where Rental_ID=? and Customer_ID=? and Rental_Status='Active';");
            ps.setString(1, returnDate);
            ps.setInt(2, rentalID);
            ps.setInt(3, customerID);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No active rental " + rentalID + " for customer " + customerID);

            ps = conn.prepareStatement("update Robot set Robot_Status='Available' where Robot_ID=(select Robot_ID from Rental where Rental_ID=?);");
            ps.setInt(1, rentalID);
            ps.execute();

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            try
            {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e2)
            {
                System.out.println(e2.getMessage());
            }
        }
    }

    public static void sql_deliver_robot(Connection conn, int customerID, int carID, String time, ArrayList<String> arr)
    {
        try
        {
            conn.setAutoCommit(false);

            for (int i = 0; i < arr.size(); i++)
            {
                ps = conn.prepareStatement("insert into Trip(Trip_Id, Trip_Type, Scheduled_Time, Delivery_Address, Rental_ID, Vehicle_ID, Distance_Miles) "
                                         + "select (select ifnull(max(Trip_Id),0)+1 from Trip), 'Delivery', ?, c.Address, r.Rental_ID, ?, "
                                         + "ifnull((select min(Distance_Miles) from Distance_From where CustomerID=c.Customer_ID),0) "
                                         + "from Rental r join Customer c on c.Customer_ID=r.Customer_ID "
                                         + "where r.Rental_ID=? and r.Customer_ID=? and exists(select 1 from Vehicle where Vehicle_ID=?);");
                ps.setString(1, time);
                ps.setInt(2, carID);
                ps.setInt(3, Integer.parseInt(arr.get(i)));
                ps.setInt(4, customerID);
                ps.setInt(5, carID);
                if (ps.executeUpdate() == 0)
                    throw new SQLException("Rental " + arr.get(i) + " does not belong to customer " + customerID + " or car " + carID + " does not exist");
            }

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Success");
        } catch (SQLException | NumberFormatException e)
        {
            System.out.println(e.getMessage());
            try
            {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e2)
            {
                System.out.println(e2.getMessage());
            }
        }
    }

    public static void sql_schedule_pickup(Connection conn, int customerID, int carID, String time, ArrayList<String> arr)
    {
        try
        {
            conn.setAutoCommit(false);

            for (int i = 0; i < arr.size(); i++)
            {
                ps = conn.prepareStatement("insert into Trip(Trip_Id, Trip_Type, Scheduled_Time, Delivery_Address, Rental_ID, Vehicle_ID, Distance_Miles) "
                                         + "select (select ifnull(max(Trip_Id),0)+1 from Trip), 'Pickup', ?, c.Address, r.Rental_ID, ?, "
                                         + "ifnull((select min(Distance_Miles) from Distance_From where CustomerID=c.Customer_ID),0) "
                                         + "from Rental r join Customer c on c.Customer_ID=r.Customer_ID "
                                         + "where r.Rental_ID=? and r.Customer_ID=? and exists(select 1 from Vehicle where Vehicle_ID=?);");
                ps.setString(1, time);
                ps.setInt(2, carID);
                ps.setInt(3, Integer.parseInt(arr.get(i)));
                ps.setInt(4, customerID);
                ps.setInt(5, carID);
                if (ps.executeUpdate() == 0)
                    throw new SQLException("Rental " + arr.get(i) + " does not belong to customer " + customerID + " or car " + carID + " does not exist");
            }

            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Success");
        } catch (SQLException | NumberFormatException e)
        {
            System.out.println(e.getMessage());
            try
            {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e2)
            {
                System.out.println(e2.getMessage());
            }
        }
    }
}