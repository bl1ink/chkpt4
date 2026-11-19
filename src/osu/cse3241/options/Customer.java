package osu.cse3241.options;
import osu.cse3241.main;
import java.util.ArrayList;
import osu.cse3241.utilities.Utilities;
import osu.cse3241.sql.SQL;

public class Customer
{
    public static void addCustomer()
    {
        String sql = "insert into Customer(customer_id, first_name, last_name, address, phone_number, email) values(?,?,?,?,?,?);";
        int customer_id ;
        ArrayList<String> arr = new ArrayList<String>();

        Utilities.printDivider();
        System.out.println("Please Fill out the following...");
        System.out.print("Customer ID: ");
        customer_id = Integer.parseInt(main.s.nextLine());
        System.out.print("First name: ");
        arr.add(main.s.nextLine());
        System.out.print("Last name: ");
        arr.add(main.s.nextLine());
        System.out.print("Address: ");
        arr.add(main.s.nextLine());
        System.out.print("Phone number: ");
        arr.add(main.s.nextLine());
        System.out.print("email: ");
        arr.add(main.s.nextLine());

        SQL.sql_add_customer(main.conn, sql, customer_id, arr);
    }

    public static void editCustomer()
    {
        String sql = "update Customer set first_name=?, last_name=?, address=?, phone_number=?, email=? where customer_id=?;";
        int customer_id ;
        ArrayList<String> arr = new ArrayList<String>();

        Utilities.printDivider();
        System.out.println("Please Fill out the following...");
        System.out.print("Customer ID of customer to edit: ");
        customer_id = Integer.parseInt(main.s.nextLine());
        System.out.print("New First name: ");
        arr.add(main.s.nextLine());
        System.out.print("New Last name: ");
        arr.add(main.s.nextLine());
        System.out.print("New Address: ");
        arr.add(main.s.nextLine());
        System.out.print("New Phone number: ");
        arr.add(main.s.nextLine());
        System.out.print("New email: ");
        arr.add(main.s.nextLine());

        SQL.sql_edit_customer(main.conn, sql, customer_id, arr);
    }

    public static void deleteCustomer()
    {
        String sql = "delete from Customer where customer_id=?;";
        int customer_id ;

        Utilities.printDivider();
        System.out.print("Customer ID of customer to delete: ");
        customer_id = Integer.parseInt(main.s.nextLine());

        SQL.sql_delete_customer(main.conn, sql, customer_id);
    }

    public static void searchCustomer()
    {
        String sql = "Select * from Customer where customer_id=?;";

        System.out.print("input Customer ID: ");
        int ID = Integer.parseInt(main.s.nextLine());

        SQL.sql_search_customer(main.conn, sql, ID);
    }
}