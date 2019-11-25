package org.mano.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.EmbeddedDriver;

public class EmbeddedDatabaseDemo {

   public static void main(String[] args) {
      EmbeddedDatabaseDemo e =
         new EmbeddedDatabaseDemo();
      e.testDerby();
   }
   public void testDerby() {
      Connection conn = null;
      PreparedStatement pstmt;
      Statement stmt;
      ResultSet rs = null;
      String createSQL = "create table person ("
      + "id integer not null generated always as"
      + " identity (start with 1, increment by 1),   "
      + "name varchar(30) not null, email varchar(30),
         phone varchar(10),"
      + "constraint primary_key primary key (id))";

      try {
         Driver derbyEmbeddedDriver = new EmbeddedDriver();
         DriverManager.registerDriver(derbyEmbeddedDriver);
         conn = DriverManager.getConnection
            ("jdbc:derby:testdb1;create=true", "pass123");
         conn.setAutoCommit(false);
         stmt = conn.createStatement();
         stmt.execute(createSQL);

         pstmt = conn.prepareStatement("insert into person
            (name,email,phone) values(?,?,?)");
         pstmt.setString(1, "Sun and mooon");
         pstmt.setString(2, "xyz@gyk.com");
         pstmt.setString(3, "987654321");
         pstmt.executeUpdate();

         rs = stmt.executeQuery("select * from person");
         while (rs.next()) {
            System.out.printf("%d %s %s %s\n",
            rs.getInt(1), rs.getString(2),
            rs.getString(3), rs.getString(4));
         }

         stmt.execute("drop table person");

         conn.commit();

      } catch (SQLException ex) {
         System.out.println("in connection" + ex);
      }

      try {
         DriverManager.getConnection
            ("jdbc:derby:;shutdown=true");
      } catch (SQLException ex) {
         if (((ex.getErrorCode() == 50000) &&
            ("XJ015".equals(ex.getSQLState())))) {
               System.out.println("Derby shut down
                  normally");
         } else {
            System.err.println("Derby did not shut down
               normally");
            System.err.println(ex.getMessage());
         }
      }
   }
}
