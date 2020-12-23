package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DB {
    static Connection c = null;

    public static String url = "http://localhost:8080/DOC/payment/paymentDone?";

    public static Connection getConnection() throws Exception {
        if (c == null) {
            Class.forName("com.mysql.jdbc.Driver");

//            c = DriverManager.getConnection("jdbc:mysql://124.43.11.162:3307/ibbagamuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "3ta@kela#una@");
//            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/polgahawela?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
//            c = DriverManager.getConnection("jdbc:mysql://124.43.11.162:3307/naththandiyaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "3ta@kela#una@");
            //   c = DriverManager.getConnection("jdbc:mysql://124.43.8.250:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "CHI@#321#");
            //   c = DriverManager.getConnection("jdbc:mysql://124.43.9.57:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "@Mck_#321");
            //             c = DriverManager.getConnection("jdbc:mysql://124.43.8.210:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "Pan@#321#");

            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/pujapitiya?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");

//            public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/wariyapolaps?zeroDateTimeBehavior=convertToNull";
//            public static final String USER = "root";
//            public static final String PASS = "3ta@kela#una@";


            //      c = DriverManager.getConnection("jdbc:mysql://localhost:3306/kuliuc?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
            //   c = DriverManager.getConnection("jdbc:mysql://localhost:3306/kakka?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
            //     c = DriverManager.getConnection("jdbc:mysql://124.43.8.99:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "Pol@#522#");
            //  c = DriverManager.getConnection("jdbc:mysql://124.43.11.162:3306/kuliuc?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "3ta@kela#una@");
            // c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
            //         c = DriverManager.getConnection("jdbc:mysql://124.43.11.162:3307/wennappuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "3ta@kela#una@");
//           c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wennappuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
//           c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chilaw?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "root");
//
            //   c = DriverManager.getConnection("jdbc:mysql://MCK_SERVER:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true", "root", "@Mck_#321");

        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = DB.getConnection().createStatement().executeUpdate(sql);
//        System.out.println("===============\n"+sql+"\n====================");
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = DB.getConnection().createStatement().executeQuery(sql);
//        System.out.println("===============\n"+sql+"\n====================");
        return executeQuery;
    }
}
