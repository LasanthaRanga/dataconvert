package polgahawela;

import conn.DB;

import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 * Created by Ranga Rathnayake on 2020-09-24.
 */


/**
 * මෙතෙක් ගෙවා නැති සියළු දේපල වල ගිය අවුරුද්දේ ඉදන් අාව 10% ඩිස්ඩකවුනිසා ඇතිවුන errors ටික සෑදීම.
 */


public class OverPaymentErrorFix {

    public static void main(String[] args) {

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                    "ass_qstart.ass_Qstart_process_date,\n" +
                    "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LY_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LYC_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQ_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_qstart.ass_Qstart_QPay,\n" +
                    "ass_qstart.ass_Qstart_QDiscont,\n" +
                    "ass_qstart.ass_Qstart_QTot,\n" +
                    "ass_qstart.ass_Qstart_FullTotal,\n" +
                    "ass_qstart.ass_Qstart_status,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.ass_Qstart_year,\n" +
                    "ass_qstart.process_update_warant,\n" +
                    "ass_qstart.process_update_arrears,\n" +
                    "ass_qstart.process_update_comment,\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                    "ass_qstart.ass_Qstart_tyold_warant\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "WHERE\n" +
                    "ass_qstart.process_update_warant < 0 AND\n" +
                    "ass_qstart.Assessment_idAssessment NOT IN (SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_Status = 1 AND\n" +
                    "ass_payment.ass_Payment_date BETWEEN '2019-06-31' AND '2020-12-31')");

            while (data.next()) {
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                double process_update_warant = data.getDouble("process_update_warant");


                ResultSet data1 = DB.getData("SELECT\n" +
                        "all_data_sheet.idAssessment,\n" +
                        "all_data_sheet.Allocation,\n" +
                        "all_data_sheet.quarter_value,\n" +
                        "all_data_sheet.balance1231,\n" +
                        "all_data_sheet.balance0331,\n" +
                        "all_data_sheet.balance0631\n" +
                        "FROM\n" +
                        "all_data_sheet\n" +
                        "WHERE\n" +
                        "all_data_sheet.idAssessment = " + assessment_idAssessment);

                if (data1.last()) {
                    double quarter_value = data1.getDouble("quarter_value");
                    double balance0631 = data1.getDouble("balance0631");
                    double v = (quarter_value * 2) + balance0631;

                    if (v >= 0) {
                        System.out.println(assessment_idAssessment + " --- " + process_update_warant);
                        System.out.println("Qval  " + quarter_value + "  --   " + balance0631 + "   -- " + roundToString(v));
                    }

                /*    else {
//                        System.out.println(assessment_idAssessment + " --- " + process_update_warant);
                        //   System.out.println("Qval  " + quarter_value + "  --   " + balance0631 + "   -- " + roundToString(v));

                        double v1 = quarter_value + Double.parseDouble(roundToString(v));

                        //     System.out.println(v1);


                        if (v1 > 0) {
                            //  updateArriars(assessment_idAssessment, v1, "Not Paid Arriars Fix " + v1);
                        }

                    }*/

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void updateArriars(int assid, double arrears, String comment) {
        try {
            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_LQ_Arreas` = " + arrears + ",\n" +
                    "`ass_Qstart_LQC_Arreas` = " + arrears + ",\n" +
                    "`ass_Qstart_HaveToQPay` = " + arrears + ",\n" +
                    "`ass_Qstart_tyold_arrias` = " + arrears + "\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.Assessment_idAssessment = " + assid);

            conn.DB.setData("INSERT INTO `error_fix`( `assid`, `comment`) VALUES ( " + assid + ", '" + comment + "')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String roundToString(double value) {
        return new DecimalFormat("0.00").format(value);
    }


}
