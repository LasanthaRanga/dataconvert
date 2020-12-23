package polgahawela;

import conn.DB2;
import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-09-28.
 */
public class DayEndNotCompleted {
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
                    "ass_qstart.ass_Qstart_LYC_Arreas > 0 AND\n" +
                    "ass_qstart.Assessment_idAssessment IN ((SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_Status = 1 AND\n" +
                    "ass_payment.ass_Payment_Q_Number = 1 AND\n" +
                    "ass_payment.ass_Payment_ThisYear = 2020 AND\n" +
                    "ass_payto.ass_payto_Qno = 1\n" +
                    "GROUP BY\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    ")) AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 3\n" +
                    "GROUP BY\n" +
                    "ass_qstart.Assessment_idAssessment");

            while (data.next()) {
                int idass_qstart = data.getInt("idass_Qstart");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");

                System.out.println(idass_qstart + "  = " + assessment_idAssessment);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
