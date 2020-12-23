package wennappuwa;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-06-29.
 */
public class ArrearsFixAfterQuaterEnd {
    public static void main(String[] args) {
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
                    "ass_payhistry.ass_PayHistry_Q1 > 0 AND\n" +
                    "ass_payhistry.ass_PayHistry_Q2 = 0 AND\n" +
                    "ass_payhistry.Assessment_idAssessment NOT IN (SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_date >= '2020-03-31' AND\n" +
                    "ass_payment.ass_Payment_Status = 1)");
            int x = 0;
            while (data.next()) {
                x++;
                int idAssessment = data.getInt("Assessment_idAssessment");

                double quarter_value = data.getDouble("ass_PayHistry_Q1");

                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                        "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                        "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                        "ass_qstart.idass_Qstart\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber = 2");

                if (data1.last()) {
                    int idass_qstart = data1.getInt("idass_Qstart");
                    double ass_qstart_haveToQPay = data1.getDouble("ass_Qstart_HaveToQPay");
                    System.out.println(quarter_value + "    ===  QQ");
                    double v = ass_qstart_haveToQPay - quarter_value;
                    v = round(v);
                    System.out.println(ass_qstart_haveToQPay + "   --   " + v + "   ===   " + idAssessment);
                    System.out.println(idass_qstart);

//                    String qq = "UPDATE `ass_qstart` \n" +
//                            "SET \n" +
//                            "`ass_Qstart_LQ_Arreas` = '" + v + "',\n" +
//                            "`ass_Qstart_LQC_Arreas` = '" + v + "',\n" +
//                            "`ass_Qstart_tyold_arrias` = '" + v + "'\n" +
//                            "WHERE\n" +
//                            "\t`idass_Qstart` = " + idass_qstart;

//                    conn.DB.setData(qq);

                }


                System.out.println(idAssessment);
                System.out.println("==========" + x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
