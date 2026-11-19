package osu.cse3241.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL_Reports {

    private static PreparedStatement ps;

    public static void sql_report(Connection conn, String sql)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            SQL.sqlQuery(conn, ps);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_renting_checkouts(Connection conn, String sql,int ID)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            SQL.sqlQuery(conn, ps);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void sql_robots_by_manufacturer(Connection conn, String sql, String manufacturer, int year)
    {
        try
        {
            ps = conn.prepareStatement(sql);
            ps.setString(1, manufacturer);
            ps.setInt(2, year);
            SQL.sqlQuery(conn, ps);
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
