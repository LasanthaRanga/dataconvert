package Ps3;

import conn.DB;

import java.sql.ResultSet;
import java.text.DecimalFormat;

public class Balance {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.Application_Catagory_idApplication_Catagory,\n" +
                    "receipt.recept_applicationId,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.receipt_total,\n" +
                    "receipt.receipt_day,\n" +
                    "receipt.receipt_status,\n" +
                    "receipt.receipt_syn,\n" +
                    "receipt.chque_no,\n" +
                    "receipt.chque_date,\n" +
                    "receipt.chque_bank,\n" +
                    "receipt.oder,\n" +
                    "receipt.office_idOffice,\n" +
                    "receipt.receipt_account_id,\n" +
                    "receipt.receipt_user_id,\n" +
                    "receipt.ribno,\n" +
                    "receipt.receipt_time,\n" +
                    "de.idde\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN de ON de.receipt_id = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2 AND\n" +
                    "receipt.receipt_day BETWEEN '2020-01-01' AND '2020-03-11' AND\n" +
                    "receipt.receipt_status = 1 AND idReceipt = 26935\n");

            int x = 0;
            while (data.next()) {
                int idReceipt = data.getInt("idReceipt");
                double receipt_total = data.getDouble("receipt_total");
                System.out.println(idReceipt + "  --  " + receipt_total);


                ResultSet dd = DB.getData("SELECT\n" +
                        "account_ps_three.idReport,\n" +
                        "account_ps_three.report_date,\n" +
                        "account_ps_three.report_ricipt_no,\n" +
                        "account_ps_three.report_ricipt_id,\n" +
                        "account_ps_three.report_vort_id,\n" +
                        "account_ps_three.report_account_id,\n" +
                        "account_ps_three.report_amount,\n" +
                        "account_ps_three.report_user_id,\n" +
                        "account_ps_three.report_application_id,\n" +
                        "account_ps_three.report_application_cat_id,\n" +
                        "account_ps_three.report_status,\n" +
                        "account_ps_three.office_idOffice\n" +
                        "FROM\n" +
                        "account_ps_three\n" +
                        "WHERE\n" +
                        "account_ps_three.report_application_cat_id = 2 AND\n" +
                        "account_ps_three.report_date BETWEEN '2020-01-01' AND '2020-03-11' AND\n" +
                        "account_ps_three.report_ricipt_id = " + idReceipt);

                double pstot = 0;
                int fetchSize = dd.getFetchSize();


                String report_date = "";
                String report_ricipt_no = "";
                String report_ricipt_id = "";
                String report_account_id = "";
                String report_user_id = "";
                String report_application_id = "";
                String office_idOffice = "";


                while (dd.next()) {
                    int idReport = dd.getInt("idReport");
                    report_date = dd.getString("report_date");
                    report_ricipt_no = dd.getString("report_ricipt_no");
                    report_ricipt_id = dd.getString("report_ricipt_id");
                    report_account_id = dd.getString("report_account_id");
                    report_user_id = dd.getString("report_user_id");
                    report_application_id = dd.getString("report_application_id");
                    office_idOffice = dd.getString("office_idOffice");

                    int vort_id = dd.getInt("report_vort_id");
                    double report_amount = dd.getDouble("report_amount");
                    //    System.out.println("       " + vort_id + "     " + report_amount);


                    if (vort_id == 71 || vort_id == 72 || vort_id == 73 || vort_id == 74 ||
                            vort_id == 75 || vort_id == 76 || vort_id == 78) {
                        pstot += report_amount;
                    }


//                    if (vort_id == 2 || vort_id == 3 || vort_id == 1 || vort_id == 5 || vort_id == 96) {  // Pannala Vote Logic
//                        pstot += report_amount;
//                    }


                }
                double val = 0;
                String format = df2.format(pstot);
                val = Double.parseDouble(format);
                System.out.println("        " + val);
                String rd = report_date;
                System.out.println(rd);

                System.out.println(report_ricipt_no);
                System.out.println(rd);
                System.out.println("++++++++++++++++++++++++++");

                if (val == receipt_total) {


                } else {
                    System.out.println("===============================================================");
                    double bal = receipt_total - val;
                    bal = Double.parseDouble(df2.format(bal));
                    System.out.println("========== " + bal);
                    x++;


//  Pannala
//                    conn.DB.setData("INSERT INTO `account_ps_three` (  `report_date`, `report_ricipt_no`, `report_ricipt_id`, `report_vort_id`, `report_account_id`, `report_amount`, `report_user_id`, `report_application_id`, `report_application_cat_id`, `report_status`, `office_idOffice`,`income_or_expence` ) \n" +
//                            "VALUES \n" +
//                            " \t( '" + rd + "', '" + report_ricipt_no + "', '" + report_ricipt_id + "', 78, '" + report_account_id + "', '" + bal + "', '" + report_user_id + "', '" + report_application_id + "', 2, 1, '" + office_idOffice + "',1 )");

                }

            }

            System.out.println("count  === " + x);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public static String roundToString(double value) {
        return new DecimalFormat("0.00").format(value);
    }

}
