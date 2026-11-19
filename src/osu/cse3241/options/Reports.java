package osu.cse3241.options;
import osu.cse3241.main;
import osu.cse3241.utilities.Utilities;
import osu.cse3241.sql.SQL_Reports;

public class Reports {
   public static void rentingCheckouts()
   {
        String sql = "select count(*) as Robots_Rented from Rental where Customer_ID=?;";
        int customer_id;

        Utilities.printDivider();
        System.out.print("Customer ID: ");
        try { customer_id = Integer.parseInt(main.s.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid number"); return; }

        SQL_Reports.sql_renting_checkouts(main.conn, sql, customer_id);
   }

   public static void popularRobot()
   {
        //julianday turns dates into a number of days, so helpful
        String sql = "select r.Robot_ID, rb.Model, count(*) as Times_Rented, "
                   + "sum(julianday(r.End_Date) - julianday(r.Start_Date)) as Total_Days_Rented "
                   + "from Rental r join Robot rb on rb.Robot_ID=r.Robot_ID "
                   + "group by r.Robot_ID order by Times_Rented desc, Total_Days_Rented desc, r.Robot_ID limit 1;";

        Utilities.printDivider();
        SQL_Reports.sql_report(main.conn, sql);
   }

   public static void popularManufacturer()
   {
        String sql = "select rm.Manufacturer, count(*) as Units_Rented "
                   + "from Rental r join Robot rb on rb.Robot_ID=r.Robot_ID "
                   + "join Robot_Model rm on rm.Model=rb.Model "
                   + "group by rm.Manufacturer order by Units_Rented desc limit 1;";

        Utilities.printDivider();
        SQL_Reports.sql_report(main.conn, sql);
   }

   public static void popularCar()
   {
        String sql = "select v.Vehicle_ID, v.Registration, v.Make, count(*) as Deliveries, sum(t.Distance_Miles) as Total_Miles "
                   + "from Trip t join Vehicle v on v.Vehicle_ID=t.Vehicle_ID "
                   + "where t.Trip_Type='Delivery' "
                   + "group by v.Vehicle_ID order by Deliveries desc, Total_Miles desc limit 1;";

        Utilities.printDivider();
        SQL_Reports.sql_report(main.conn, sql);
   }

   public static void robotsCheckedOut()
   {
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, count(*) as Robots_Rented "
                   + "from Rental r join Customer c on c.Customer_ID=r.Customer_ID "
                   + "group by c.Customer_ID order by Robots_Rented desc limit 1;";

        Utilities.printDivider();
        SQL_Reports.sql_report(main.conn, sql);
   }

   public static void robotsByManufacturer()
   {
        String sql = "select Model, Manufacturer, Release_Year from Robot_Model "
                   + "where Manufacturer=? collate nocase and Release_Year<?;";
        int year;

        Utilities.printDivider();
        System.out.print("Manufacturer: ");
        String manufacturer = main.s.nextLine().trim();
        if (manufacturer.isEmpty() || manufacturer.length() > 50)
        {
            System.out.println("Invalid manufacturer");
            return;
        }
        System.out.print("Released before year (YYYY): ");
        try { year = Integer.parseInt(main.s.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid year"); return; }

        SQL_Reports.sql_robots_by_manufacturer(main.conn, sql, manufacturer, year);
   }
}