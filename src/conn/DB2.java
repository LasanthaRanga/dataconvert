package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DB2 {


    static Connection c = null;

    public static String url = "http://localhost:8080/DOC/payment/paymentDone?";

    public static Connection getConnection() throws Exception {
        if (c == null) {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/polgahawela?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
//           c = DriverManager.getConnection("jdbc:mysql://124.43.11.162:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "3ta@kela#una@");
            //      c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ul1?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");

        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = DB2.getConnection().createStatement().executeUpdate(sql);
//        System.out.println("===============\n"+sql+"\n====================");
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = DB2.getConnection().createStatement().executeQuery(sql);
//        System.out.println("===============\n"+sql+"\n====================");
        return executeQuery;
    }


}
