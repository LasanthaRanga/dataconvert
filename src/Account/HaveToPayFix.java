package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-30.
 */
public class HaveToPayFix {
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
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay < 0\n");


            while (data.next()) {

                int idass_qstart = data.getInt("idass_Qstart");
                int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");
                double ass_qstart_lyc_arreas = data.getDouble("ass_Qstart_LYC_Arreas");
                double ass_qstart_lq_arreas = data.getDouble("ass_Qstart_LQ_Arreas");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");

                System.out.println(idass_qstart + "  --  " + ass_qstart_quaterNumber + "  --  " + ass_qstart_haveToQPay);


                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_payhistry.idass_PayHistry,\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                        "ass_payhistry.ass_PayHistry_year,\n" +
                        "ass_payhistry.ass_PayHistry_Date,\n" +
                        "ass_payhistry.ass_PayHistry_status,\n" +
                        "ass_payhistry.ass_PayHistry_comment,\n" +
                        "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                        "Sum(ass_payhistry.ass_PayHistry_Q1),\n" +
                        "Sum(ass_payhistry.ass_PayHistry_Q2),\n" +
                        "Sum(ass_payhistry.ass_PayHistry_Q3),\n" +
                        "Sum(ass_payhistry.ass_PayHistry_Q4),\n" +
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
                        "ass_payhistry.Assessment_idAssessment = '73' AND\n" +
                        "ass_payhistry.ass_PayHistry_year = '2020'\n");

                if (data1.last()) {
                    double q3p = data1.getDouble("Sum(ass_payhistry.ass_PayHistry_Q3)");
                    double q4p = data1.getDouble("Sum(ass_payhistry.ass_PayHistry_Q4)");
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
