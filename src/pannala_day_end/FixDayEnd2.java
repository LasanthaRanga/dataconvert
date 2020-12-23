package pannala_day_end;

import conn.DB;

import java.sql.ResultSet;

public class FixDayEnd2 {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.ass_Payment_date\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_goto_debit > 0 AND\n" +
                    "ass_payment.ass_Payment_date BETWEEN '2019-06-01' AND '2019-12-31' AND\n" +
                    "ass_payment.ass_Payment_Status = 1");

            while (data.next()) {
                int x = data.getInt("Assessment_idAssessment");
                System.out.println(data.getString("Assessment_idAssessment") + "       === ");
                double ass_payHistry_over = data.getDouble("ass_Payment_goto_debit");
                // System.out.println(data.getDouble("ass_PayHistry_Over"));

                ResultSet d = DB.getData("SELECT\n" +
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
                        "ass_qstart.Assessment_idAssessment = '" + x + "' AND\n" +
                        "ass_qstart.ass_Qstart_year = 2020");

                if (d.last()) {
                    int idass_qstart = d.getInt("idass_Qstart");
                    double ass_qstart_ly_arreas = d.getDouble("ass_Qstart_LY_Arreas");
                    double ass_qstart_lyc_arreas = d.getDouble("ass_Qstart_LYC_Arreas");


                    if (ass_qstart_ly_arreas > 0) {
                        if (ass_qstart_ly_arreas == ass_qstart_lyc_arreas) {
                            System.out.println("ok");
                        DB.setData("UPDATE  `ass_qstart` \n" +
                                "SET \n" +
                                "`ass_Qstart_LY_Arreas` = 0,\n" +
                                "`ass_Qstart_LY_Warrant` = 0,\n" +
                                "`ass_Qstart_LYC_Arreas` = 0,\n" +
                                "`ass_Qstart_LYC_Warrant` = 0,\n" +
                                "`ass_Qstart_LQ_Arreas` = 0,\n" +
                                "`ass_Qstart_LQC_Arreas` = 0,\n" +
                                "`ass_Qstart_LQ_Warrant` = 0,\n" +
                                "`ass_Qstart_LQC_Warrant` = 0,\n" +
                                "`process_update_arrears` = '" + ass_payHistry_over + "',\n" +
                                "`process_update_comment` = 'Error Fixed'\n" +
                                "WHERE\n" +
                                "\t`idass_Qstart` =" + idass_qstart);
                        } else {
                            System.out.println("no   =================");
                        DB.setData("UPDATE  `ass_qstart` \n" +
                                "SET \n" +
                                "`ass_Qstart_LY_Arreas` = 0,\n" +
                                "`ass_Qstart_LY_Warrant` = 0,\n" +
                                "`ass_Qstart_LYC_Arreas` = 0,\n" +
                                "`ass_Qstart_LYC_Warrant` = 0,\n" +
                                "`ass_Qstart_LQ_Arreas` = 0,\n" +
                                "`ass_Qstart_LQC_Arreas` = 0,\n" +
                                "`ass_Qstart_LQ_Warrant` = 0,\n" +
                                "`ass_Qstart_LQC_Warrant` = 0,\n" +
                                "`process_update_comment` = 'Error Fixed'\n" +
                                "WHERE\n" +
                                "\t`idass_Qstart` =" + idass_qstart);
                        }
                    } else {
                        System.out.println("--");
                    }


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
