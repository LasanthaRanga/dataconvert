package Ps3;

import conn.DB;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by Ranga on 2020-04-23.
 */
public class FullPsThree {

    static HashMap<String, Integer> vids = null;

    public static void main(String[] args) {
        getVoteAndID();
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.Receipt_idReceipt,\n" +
                    "ass_payment.ass_Payment_Status\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_Status = 1\n");

            while (data.next()) {
                int receipt_idReceipt = data.getInt("Receipt_idReceipt");
                System.out.println(receipt_idReceipt + "");
                pras3process(receipt_idReceipt + "");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void getVoteAndID() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_vote.idass_vote,\n" +
                    "ass_vote.ass_vote_no,\n" +
                    "ass_vote.ass_vote_table_id,\n" +
                    "ass_vote.description,\n" +
                    "ass_vote.`key`\n" +
                    "FROM `ass_vote`\n");

            vids = new HashMap<>();
            while (data.next()) {
                vids.put(data.getString("key"), data.getInt("ass_vote_table_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void pras3process(String text) {
        System.out.println(" PS3 Running =============");

        int currentYear = 2020;


        String quer = "SELECT\n" +
                "ass_payment.idass_Payment,\n" +
                "ass_payment.ass_Payment_Q_Number,\n" +
                "ass_payment.ass_Payment_ThisYear,\n" +
                "ass_payment.ass_Payment_date,\n" +
                "ass_payment.ass_Payment_LY_Arrears,\n" +
                "ass_payment.ass_Payment_LY_Warrant,\n" +
                "ass_payment.ass_Payment_fullTotal,\n" +
                "ass_payment.ass_Payment_idUser,\n" +
                "ass_payment.Assessment_idAssessment,\n" +
                "ass_payment.Receipt_idReceipt,\n" +
                "ass_payment.ass_nature_idass_nature,\n" +
                "ass_payment.ass_allocation_idass_allocation,\n" +
                "ass_payment.ass_Payment_goto_debit,\n" +
                "ass_payment.ass_Payment_Status,\n" +
                "ass_payment.ass_cash,\n" +
                "ass_payment.ass_check,\n" +
                "ass_payment.ass_check_no,\n" +
                "ass_payment.ass_bank,\n" +
                "ass_payment.pay_user_id,\n" +
                "ass_payment.cd_balance,\n" +
                "ass_payment.office_idOffice,\n" +
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
                "receipt.office_idOffice\n" +
                "FROM\n" +
                "ass_payment\n" +
                "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                "WHERE\n" +
                "receipt.idReceipt = " + text + " ";

        try {
            ResultSet data = DB.getData(quer);
            System.out.println(" PS3 Running =============  4");
            if (data.last()) {
                System.out.println(" PS3 Running =============  5");
                String recitno = data.getString("receipt_print_no");
                String day = data.getString("receipt_day");
                int office_idOffice = data.getInt("office_idOffice");
                int ass_payment_idUser = data.getInt("ass_Payment_idUser");
                int recept_applicationId = data.getInt("recept_applicationId");

                int assessment_idAssessment = data.getInt("Assessment_idAssessment");

                System.out.println(" PS3 Running =============  6");

                double ass_payment_ly_arrears = data.getDouble("ass_Payment_LY_Arrears");
                double ass_payment_ly_warrant = data.getDouble("ass_Payment_LY_Warrant");
                double ass_cash = data.getDouble("ass_cash");
                double ass_check = data.getDouble("ass_check");
                double cd_balance = data.getDouble("cd_balance");
                double ass_payment_goto_debit = data.getDouble("ass_Payment_goto_debit");

                double ass_payment_thisYear = data.getDouble("ass_Payment_ThisYear"); // meka athyawassya ne

                int idass_payment = data.getInt("idass_Payment");

                ResultSet data1 = DB.getData("SELECT\n" +
                        "aha.idAHA,\n" +
                        "aha.appcat_id,\n" +
                        "aha.bankinfo_id,\n" +
                        "aha.office_id,\n" +
                        "aha.aha_status\n" +
                        "FROM `aha`\n" +
                        "WHERE\n" +
                        "aha.appcat_id = 2 AND\n" +
                        "aha.office_id = " + office_idOffice);

                System.out.println(" PS3 Running =============  7");

                int ACCOUNTID = 0;
                if (data1.last()) {
                    ACCOUNTID = data1.getInt("bankinfo_id");

                    System.out.println(" PS3 Running =============  8");

                }


                String q2 = "SELECT\n" +
                        "ass_payto.idass_payto,\n" +
                        "ass_payto.ass_payto_Qno,\n" +
                        "ass_payto.ass_payto_warrant,\n" +
                        "ass_payto.ass_payto_arrears,\n" +
                        "ass_payto.ass_payto_qvalue,\n" +
                        "ass_payto.ass_payto_discount,\n" +
                        "ass_payto.ass_payto_discount_rate,\n" +
                        "ass_payto.ass_Payment_idass_Payment,\n" +
                        "ass_payto.ass_payto_status\n" +
                        "FROM\n" +
                        "ass_payto\n" +
                        "WHERE\n" +
                        "ass_payto.ass_Payment_idass_Payment = " + idass_payment;
                ResultSet d = DB.getData(q2);

                System.out.println(" PS3 Running =============  8");

                double typ = 0.0;
                double dis = 0.0;
                double tya = 0.0;
                double tyw = 0.0;

                while (d.next()) {
                    typ += d.getDouble("ass_payto_qvalue");
                    dis += d.getDouble("ass_payto_discount");
                    tya += d.getDouble("ass_payto_arrears");
                    tyw += d.getDouble("ass_payto_warrant");

                    System.out.println(" PS3 Running =============  9");
                }


                if (ass_payment_ly_arrears > 0) {// Last Year Arrias
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("LYA"), ACCOUNTID, round(ass_payment_ly_arrears), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_payment_ly_warrant > 0) {// Last Year Warrant
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("LYW"), ACCOUNTID, round(ass_payment_ly_warrant), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (tya > 0) {// This Year arrias
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("TYA"), ACCOUNTID, round(tya), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (tyw > 0) {// this year warrnt
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("TYW"), ACCOUNTID, round(tyw), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (cd_balance < 0) {

                    System.out.println("PS3 ================= from last year");


                    conn.DB.setData("UPDATE ass_qstart \n" +
                            "SET ass_qstart.process_update_arrears = 0,\n" +
                            "ass_qstart.process_update_warant = " + cd_balance + " \n" +
                            "WHERE\n" +
                            "\tass_qstart.Assessment_idAssessment = " + assessment_idAssessment + " \n" +
                            "\tAND ass_qstart.ass_Qstart_QuaterNumber = 1 \n" +
                            "\tAND ass_Qstart_year = " + currentYear);


                    if (typ > 0) {// quater pay
                        insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("QP"), ACCOUNTID, round(typ + cd_balance), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    }


                } else {
                    System.out.println("PS3 ================= No Balance");
                    if (typ > 0) {// quater pay
                        insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("QP"), ACCOUNTID, round(typ), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    }
                    // if (cd_balance > 0) {// credit debit balance
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CD"), ACCOUNTID, round(cd_balance), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                    // }
                }

                if (ass_cash > 0) {// Cahs
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CASH"), ACCOUNTID, round(ass_cash), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_check > 0) {// chque
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("CHQUE"), ACCOUNTID, round(ass_check), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (dis > 0) {// discount
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("D"), ACCOUNTID, round(dis), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }

                if (ass_payment_goto_debit > 0) {// over pay to next year
                    insertToPrasa3(day, recitno, Integer.parseInt(text), vids.get("OP"), ACCOUNTID, round(ass_payment_goto_debit), ass_payment_idUser, recept_applicationId, 2, 1, office_idOffice);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static void insertToPrasa3(String day, String reciptNo, int idRecipt, int vortid, int accountid, double amount, int userid, int appid, int appcatid, int status, int officeid) {

        String query = "INSERT INTO `account_ps_three` (\t\n" +
                "\t`report_date`,\n" +
                "\t`report_ricipt_no`,\n" +
                "\t`report_ricipt_id`,\n" +
                "\t`report_vort_id`,\n" +
                "\t`report_account_id`,\n" +
                "\t`report_amount`,\n" +
                "\t`report_user_id`,\n" +
                "\t`report_application_id`,\n" +
                "\t`report_application_cat_id`,\n" +
                "\t`report_status`,\n" +
                "\t`office_idOffice`, income_or_expence\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\t\t\n" +
                "\t\t'" + day + "',\n" +
                "\t\t'" + reciptNo + "',\n" +
                "\t\t'" + idRecipt + "',\n" +
                "\t\t'" + vortid + "',\n" +
                "\t\t'" + accountid + "',\n" +
                "\t\t'" + amount + "',\n" +
                "\t\t'" + userid + "',\n" +
                "\t\t'" + appid + "',\n" +
                "\t\t'" + appcatid + "',\n" +
                "\t\t'" + status + "',\n" +
                "\t\t'" + officeid + "', '1'\n" +
                "\t)";

        try {
            conn.DB.setData(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

}
