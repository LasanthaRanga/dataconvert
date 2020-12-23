package paymentFix2020_6_30;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-03.
 */
public class Fix {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.idass_Payment AS idass_Payment,\n" +
                    "ass_payment.Assessment_idAssessment AS Assessment_idAssessment,\n" +
                    "ass_payment.ass_Payment_date AS ass_Payment_date,\n" +
                    "ass_qstart.ass_Qstart_year AS ass_Qstart_year,\n" +
                    "ass_qstart.ass_Qstart_LYC_Arreas AS arr,\n" +
                    "ass_payment.ass_Payment_LY_Arrears AS paid,\n" +
                    "round( ( `ass_qstart`.`ass_Qstart_LYC_Arreas` - `ass_payment`.`ass_Payment_LY_Arrears` ), 2 ) AS a_now,\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber AS ass_Qstart_QuaterNumber,\n" +
                    "ass_qstart.ass_Qstart_LYC_Warrant AS awarnt,\n" +
                    "ass_payment.ass_Payment_LY_Warrant AS paid_w,\n" +
                    "round( ( `ass_qstart`.`ass_Qstart_LYC_Warrant` - `ass_payment`.`ass_Payment_LY_Warrant` ), 2 ) AS w_now,\n" +
                    "ass_payment.Receipt_idReceipt AS Receipt_idReceipt,\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQ_Warrant,\n" +
                    "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                    "ass_qstart.ass_Qstart_tyold_warant,\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.assessment_no,\n" +
                    "ass_nature.ass_nature_warrant_rate,\n" +
                    "ROUND( ( ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 400 ) * ass_nature.ass_nature_warrant_rate / 100, 2 ) AS warant,\n" +
                    "Round(ass_qstart.ass_Qstart_HaveToQPay - ass_payhistry.ass_PayHistry_Q2,2) as haveto\n" +
                    "FROM\n" +
                    "(ass_payment\n" +
                    "JOIN ass_qstart ON ((ass_qstart.Assessment_idAssessment = ass_payment.Assessment_idAssessment)))\n" +
                    "INNER JOIN assessment ON ass_payment.Assessment_idAssessment = assessment.idAssessment AND ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN ass_nature ON ass_payment.ass_nature_idass_nature = ass_nature.idass_nature AND assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment AND ass_payment.ass_allocation_idass_allocation = ass_allocation.idass_allocation\n" +
                    "INNER JOIN ass_payhistry ON ass_payhistry.Assessment_idAssessment = assessment.idAssessment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_date = '2020-06-30' AND\n" +
                    "ass_payment.ass_Payment_LY_Arrears > 0 AND\n" +
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_payment.ass_Payment_Status = 1 AND\n" +
                    "ass_payment.Receipt_idReceipt IS NOT NULL AND\n" +
                    "ass_payhistry.ass_PayHistry_Date = '2020-06-30'");

            while (data.next()) {
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                int idass_qstart = data.getInt("idass_Qstart");
                double a_now = data.getDouble("a_now");
                double w_now = data.getDouble("w_now");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");
                double warant = data.getDouble("warant");
                double ass_qstart_lqc_arreas = data.getDouble("ass_Qstart_LQC_Arreas");
                double ass_qstart_lqc_warrant = data.getDouble("ass_Qstart_LQC_Warrant");

                double tot_a = ass_qstart_lqc_arreas + ass_qstart_haveToQPay;
                double tot_w = ass_qstart_lqc_warrant + warant;

                double haveto = data.getDouble("haveto");

                if (haveto > 0) {
                    tot_a = round(haveto);
                    tot_w = round(tot_w);
                } else {
                    tot_a = round(0);
                    tot_w = round(0);
                }



                if (a_now >= 0) {
                    System.out.println(assessment_idAssessment + " -- " + idass_qstart + " == " + a_now + "  == " + w_now);

                    int i = DB.setData("UPDATE  `ass_qstart` SET \n" +
                            "`ass_Qstart_LYC_Arreas` = '" + a_now + "',\n" +
                            "`ass_Qstart_LYC_Warrant` = '" + w_now + "'\n" +
                            "WHERE\n" +
                            "\t`ass_Qstart_QuaterNumber` = 2 and `ass_Qstart_year` = 2020 and `Assessment_idAssessment` = " + assessment_idAssessment);

                    int i1 = DB.setData("UPDATE `ass_qstart` SET \n" +
                            "`ass_Qstart_LY_Arreas` = '" + a_now + "',\n" +
                            "`ass_Qstart_LY_Warrant` = '" + w_now + "',\n" +
                            "`ass_Qstart_LYC_Arreas` = '" + a_now + "',\n" +
                            "`ass_Qstart_LYC_Warrant` = '" + w_now + "',\n" +
                            " ass_Qstart_LQ_Arreas = '" + ass_qstart_haveToQPay + "', ass_Qstart_LQC_Arreas='" + ass_qstart_haveToQPay + "', ass_Qstart_LQ_Warrant='" + warant + "',ass_Qstart_LQC_Warrant = '" + warant + "'," +
                            " ass_Qstart_tyold_arrias = '" + tot_a + "', ass_Qstart_tyold_warant='" + tot_w + "'  " +
                            "WHERE\n" +
                            "\t`ass_Qstart_QuaterNumber` = 3 and `ass_Qstart_year` = 2020 and `Assessment_idAssessment` = " + assessment_idAssessment);
                } else {

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

}
