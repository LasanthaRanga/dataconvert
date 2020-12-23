package ibbagamuwa;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-08-07.
 */
public class OverPaymentError {
    public static void main(String[] args) {
        System.out.println("start");
       // getData();
    }

    public static void getData() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payhistry.idass_PayHistry,\n" +
                    "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                    "ass_payhistry.ass_PayHistry_year,\n" +
                    "ass_payhistry.ass_PayHistry_Date,\n" +
                    "ass_payhistry.ass_PayHistry_status,\n" +
                    "ass_payhistry.ass_PayHistry_comment,\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                    "ass_payhistry.ass_PayHistry_Q1,\n" +
                    "ass_payhistry.ass_PayHistry_Q2,\n" +
                    "ass_payhistry.ass_PayHistry_Q3,\n" +
                    "ass_payhistry.ass_PayHistry_Q4,\n" +
                    "ass_payhistry.ass_PayHistry_Over,\n" +
                    "ass_payhistry.Assessment_idAssessment,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ4,\n" +
                    "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q4Status\n" +
                    "FROM\n" +
                    "ass_payhistry\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_comment = 131 AND\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1 <> 10 AND\n" +
                    "ass_payhistry.Assessment_idAssessment NOT IN (SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.Assessment_idAssessment IN (SELECT\n" +
                    "ass_payhistry.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payhistry\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_comment = 131 AND\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1 <> 10))");
            while (data.next()) {
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                int drq1 = data.getInt("ass_PayHistry_DRQ1");
                int drq2 = data.getInt("ass_PayHistry_DRQ2");
                int drq3 = data.getInt("ass_PayHistry_DRQ3");
                int drq4 = data.getInt("ass_PayHistry_DRQ4");

                System.out.println(assessment_idAssessment);

                double q1 = data.getDouble("ass_PayHistry_Q1");
                double q2 = data.getDouble("ass_PayHistry_Q2");
                double q3 = data.getDouble("ass_PayHistry_Q3");
                double q4 = data.getDouble("ass_PayHistry_Q4");


                ResultSet data1 = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ROUND(ass_allocation.ass_allocation / 400 *\n" +
                        "ass_nature.ass_nature_year_rate,2) as quater\n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                        "WHERE\n" +
                        "ass_allocation.ass_allocation_status = 1 AND\n" +
                        "assessment.idAssessment = " + assessment_idAssessment);

                double qv = 0;

                if (data1.last()) {
                    qv = data1.getDouble("quater");
                }

                if (drq1 == 0 && q1 > 0) {
                    double q1a = qv - q1;
                    System.out.println("--------------" + q1a);

                    conn.DB.setData("UPDATE  `ass_qstart` \n" +
                            "SET \n" +
                            "`ass_Qstart_LQ_Arreas` = '" + round(q1a) + "',\n" +
                            "`ass_Qstart_LQC_Arreas` = '" + round(q1a) + "',\n" +
                            "`ass_Qstart_HaveToQPay` = '" + round(q1a) + "',\n" +
                            "`ass_Qstart_tyold_arrias` = '" + round(q1a) + "'\n" +
                            "WHERE\n" +
                            "\t`Assessment_idAssessment` = '" + assessment_idAssessment + "' AND `ass_Qstart_QuaterNumber` = 2");

                    conn.DB.setData("UPDATE `ass_qstart` \n" +
                            "\tSET \t\n" +
                            "\t`ass_Qstart_tyold_arrias` = '" + round(q1a + qv) + "'\n" +
                            "WHERE\n" +
                            "\t\t`Assessment_idAssessment` = '" + assessment_idAssessment + "' \n" +
                            "\tAND `ass_Qstart_QuaterNumber` = 3");


                }
                if (drq2 == 0 && q2 > 0) {
                    double q2a = qv - q2;
                    System.out.println("----------------------------" + q2a);
                }
                if (drq3 == 0 && q3 > 0) {
                    double q3a = qv - q3;
                    System.out.println("--------------------------------------------" + q3a);

                    conn.DB.setData("UPDATE `ass_qstart` \n" +
                            "SET `ass_Qstart_HaveToQPay` = '" + round(q3a) + "' \n" +
                            "WHERE\n" +
                            "`Assessment_idAssessment` = '" + assessment_idAssessment + "' \n" +
                            "AND `ass_Qstart_QuaterNumber` = 3");


                }
                if (drq4 == 0 && q4 > 0) {
                    double q4a = qv - q4;
                    System.out.println("------------------------------------------------------------" + q4a);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

}
