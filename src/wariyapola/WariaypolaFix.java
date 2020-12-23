package wariyapola;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-17.
 */
public class WariaypolaFix {
    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        System.out.println("Wariyapola Start");

        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance0631\n" +
                    "FROM\n" +
                    "all_data_sheet\n" +
                    "WHERE\n" +
                    "all_data_sheet.balance0631 < 0");

            int overCount = 0;

            while (data.next()) {
                overCount++;
                double quarter_value = data.getDouble("quarter_value");
                double balance0631 = data.getDouble("balance0631");
                balance0631 = balance0631 * -1;

                if (quarter_value > balance0631) {
                    int idAssessment = data.getInt("idAssessment");
                    System.out.println(idAssessment);

                    overCount++;

                    ResultSet data1 = DB.getData("SELECT\n" +
                            "ass_payment.Assessment_idAssessment,\n" +
                            "ass_payment.ass_Payment_date\n" +
                            "FROM\n" +
                            "ass_payment\n" +
                            "WHERE\n" +
                            "ass_payment.ass_Payment_date < '2020-07-01' AND\n" +
                            "ass_payment.ass_Payment_Status = 1 AND\n" +
                            "ass_payment.Assessment_idAssessment = " + idAssessment);

                    if (data1.last()) {
                        //  System.out.println(" Aulak ne gewala " + idAssessment);
                    } else {
                        ResultSet data2 = DB.getData("SELECT\n" +
                                "ass_payment.Assessment_idAssessment,\n" +
                                "ass_payment.ass_Payment_date\n" +
                                "FROM\n" +
                                "ass_payment\n" +
                                "WHERE\n" +
                                "ass_payment.ass_Payment_date > '2020-06-31' AND\n" +
                                "ass_payment.ass_Payment_Status = 1 AND\n" +
                                "ass_payment.Assessment_idAssessment = " + idAssessment);

                        if (data2.last()) {
                            //  System.out.println("Gewala Aul Una ewa" + idAssessment);
                        } else {
                            //   System.out.println("Gewala nehe" + idAssessment);

                            //   fix(idAssessment, quarter_value, balance0331, round(quarter_value - balance0331));


                        }
                    }
                }
            }

            System.out.println("over Count " + overCount);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void fix(int id, double quaterValue, double over, double arrears) {
        System.out.println(id + " - " + quaterValue + " - " + over + " - " + arrears);

        try {
            int i = DB.setData("UPDATE `ass_qstart` \n" +
                    "SET \n" +
                    "`ass_Qstart_LQ_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_LQC_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_tyold_arrias` = '" + arrears + "'\n" +
                    "WHERE\n" +
                    "\t`Assessment_idAssessment` ='" + id + "' and `ass_Qstart_QuaterNumber` = 3");

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + round(arrears + quaterValue) + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '4' \n" +
                    "\tAND `Assessment_idAssessment` = " + id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
