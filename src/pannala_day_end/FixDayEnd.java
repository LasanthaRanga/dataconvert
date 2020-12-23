package pannala_day_end;

import conn.DB;

import java.sql.ResultSet;

public class FixDayEnd {

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
                    "ass_payment.idass_Payment,\n" +
                    "ass_payhistry.idass_PayHistry,\n" +
                    "ass_payhistry.ass_PayHistry_Over\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "INNER JOIN ass_payment ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "INNER JOIN ass_payhistry ON ass_payhistry.Assessment_idAssessment = ass_payment.Assessment_idAssessment AND ass_payhistry.ass_PayHistry_Date = ass_payment.ass_Payment_date\n" +
                    "WHERE\n" +
                    "receipt.idReceipt NOT IN ((SELECT\n" +
                    "de.receipt_id\n" +
                    "FROM\n" +
                    "de)) AND\n" +
                    "receipt.receipt_day > '2019-06-01' AND\n" +
                    "receipt.receipt_status = 1 AND\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 2 AND\n" +
                    "ass_payhistry.ass_PayHistry_Over > 0\n");

            while (data.next()) {
                int x = data.getInt("recept_applicationId");
                System.out.println(data.getString("recept_applicationId") + "       === ");
                double ass_payHistry_over = data.getDouble("ass_PayHistry_Over");
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

                    if (ass_qstart_ly_arreas == ass_qstart_lyc_arreas) {
                        System.out.println("ok");
                        conn.DB.setData("UPDATE  `ass_qstart` \n" +
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
                        conn.DB.setData("UPDATE  `ass_qstart` \n" +
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


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
