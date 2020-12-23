package Ps3;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-05-26.
 */
public class RinaValueAssessment {
    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "account_ps_three.report_ricipt_id\n" +
                    "FROM\n" +
                    "account_ps_three\n" +
                    "WHERE\n" +
                    "account_ps_three.report_application_cat_id = 2 AND\n" +
                    "account_ps_three.report_amount < 0");

            while (data.next()) {
                int report_ricipt_id = data.getInt("report_ricipt_id");
                System.out.println(report_ricipt_id);

                ResultSet data1 = DB.getData("SELECT\n" +
                        "account_ps_three.idReport,\n" +
                        "account_ps_three.report_vort_id,\n" +
                        "account_ps_three.report_amount\n" +
                        "FROM\n" +
                        "account_ps_three\n" +
                        "WHERE\n" +
                        "account_ps_three.report_ricipt_id = " + report_ricipt_id);
                System.out.println("===========");

                double x = 0;
                double y = 0;
                double z = 0;
                int xx = 0;
                int yy = 0;
                while (data1.next()) {
                    int idReport = data1.getInt("idReport");
                    int report_vort_id = data1.getInt("report_vort_id");
                    double report_amount = data1.getDouble("report_amount");
                    if (report_vort_id == 1) {
                        xx = idReport;
                        x = report_amount;

                        System.out.println(x);
                    }

                    if (report_vort_id == 5) {
                        yy = idReport;
                        y = report_amount;
                        System.out.println(idReport + " -- " + report_vort_id + "  == " + report_amount);
                    }
                }
                z = x + y;
                update(xx, 0);
                update(yy, round(z));

               // System.out.println(" ==============            " + );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static void update(int id, double amount) {
        try {
            DB.setData("UPDATE `account_ps_three` \n" +
                    "SET `report_amount` = '" + amount + "' \n" +
                    "WHERE\n" +
                    "\t`idReport` = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
