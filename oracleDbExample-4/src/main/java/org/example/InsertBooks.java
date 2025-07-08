package org.example;

import java.sql.*;
import java.util.Random;

public class InsertBooks {
    public static void main(String[] args) {
        String url      = "jdbc:oracle:thin:@EXTERNALIP:1521:XE";
        String user     = "DBUSER";
        String password = "PASSWORD";
        String sql      = "INSERT INTO BOOK (ID, NAME, ISBN) VALUES (?, ?, ?)";

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);


            PreparedStatement ps = conn.prepareStatement(sql);
            Random rnd = new Random();


            for (int i = 1; i <= 100; i++) {
                ps.setInt(1, i);
                ps.setString(2, "Book_" + i);
                long isbnNum = Math.abs(rnd.nextLong() % 1_000_000_000_0000L);
                ps.setString(3, String.format("%013d", isbnNum));
                ps.addBatch();
                if (i % 20 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            conn.commit();


            ps.close();
            conn.close();
            System.out.println("100 book records added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
