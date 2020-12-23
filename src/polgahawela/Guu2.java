package polgahawela;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-09-24.
 * සම්පූර්ණයෙන් ගෙවා ඇති මුත් ලාස්ට් ඉයර් ඇරියස් පෙන්වයි
 */
public class Guu2 {

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
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.Assessment_idAssessment IN ((SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_goto_debit > 0 AND\n" +
                    "ass_payment.ass_Payment_Status = 1 AND\n" +
                    "ass_payment.ass_Payment_ThisYear = 2020\n" +
                    ")) AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 3");


            while (data.next()) {

                int idass_qstart = data.getInt("idass_Qstart");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                System.out.println(assessment_idAssessment + "  =  " + idass_qstart);






//                conn.DB.setData("UPDATE  `ass_qstart` \n" +
//                        "SET \n" +
//                        "`ass_Qstart_LYC_Arreas` = 0\n" +
//                        "WHERE\n" +
//                        "\t`idass_Qstart` = " + idass_qstart);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}








//delete credit debit


//        SELECT
//        ass_creditdebit.idAss_CreditDebit,
//        ass_creditdebit.Ass_CreditDebit_discription,
//        ass_creditdebit.Ass_CreditDebit_cd,
//        ass_creditdebit.Ass_CreditDebit_amount,
//        ass_creditdebit.Ass_balance,
//        ass_creditdebit.Ass_CreditDebit_date,
//        ass_creditdebit.Assessment_idAssessment,
//        ass_creditdebit.Ass_CreditDebit_status,
//        ass_creditdebit.user_id
//        FROM
//        ass_creditdebit
//        WHERE
//        ass_creditdebit.Ass_balance > 0 AND
//        ass_creditdebit.Ass_CreditDebit_status = 1 AND
//        ass_creditdebit.Ass_CreditDebit_cd = 1 AND
//        ass_creditdebit.Assessment_idAssessment IN (SELECT
//        ass_payhistry.Assessment_idAssessment
//        FROM
//        ass_payhistry
//        WHERE
//        ass_payhistry.ass_PayHistry_year = 2020 AND
//        ass_payhistry.ass_PayHistry_Over > 0)

