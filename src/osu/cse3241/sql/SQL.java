package osu.cse3241.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class SQL {
	
	private static PreparedStatement ps;
	
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows:
     * 		this query is written with the Statement class, typically 
     * 		used for static SQL SELECT statements.
     */
    public static void sqlQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows:
     * 		this query is written with the PrepareStatement class, typically 
     * 		used for dynamic SQL SELECT statements.
     */
    public static void sqlQuery(Connection conn, PreparedStatement sql){
        try {
        	ResultSet rs = sql.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	rs.close();
        	sql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void Example(Connection conn, String sql, ArrayList<String> arr)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            //ps.setString()
            ps.execute();
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
     
    public static void sql_add_robot(Connection conn, String sql, int ID,ArrayList<String> arr)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            for (int i = 0; i < arr.size(); i++)
            {
                ps.setString(i+2 ,arr.get(i));
            }
            ps.execute();
            System.out.println("");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sql_delete_robot(Connection conn, String sql, int ID)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            ps.execute();
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sql_edit_robot(Connection conn, String sql, int ID, ArrayList<String> arr)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(arr.size() + 1, ID);
            for (int i = 0; i < arr.size(); i++)
            {
                ps.setString(i+1, arr.get(i));
            }
            ps.execute();
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
   
    public static void sql_search_robot(Connection conn, String sql, int ID)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            sqlQuery(conn, ps);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_add_customer(Connection conn, String sql, int ID,ArrayList<String> arr)
    {
        try 
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            for (int i = 0; i < arr.size(); i++)
            {
                ps.setString(i+2 ,arr.get(i));
            }
            ps.execute();
            System.out.println("");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_edit_customer(Connection conn, String sql, int ID, ArrayList<String> arr)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < arr.size(); i++)
            {
                ps.setString(i+1, arr.get(i));
            }
            ps.setInt(arr.size() + 1, ID);
            ps.execute();
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_delete_customer(Connection conn, String sql, int ID)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            ps.execute();
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_search_customer(Connection conn, String sql, int ID)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            sqlQuery(conn, ps);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sql_Rent_Robot(Connection conn, String sql, ArrayList<String> arr)
    {
        try 
        {
            ps  = conn.prepareStatement(sql);
            //ps.setString()
            System.out.println("Success");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}