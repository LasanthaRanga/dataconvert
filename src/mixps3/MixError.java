package mixps3;

import conn.DB;

import java.sql.ResultSet;
import java.sql.SQLOutput;

public class MixError {

    public static void main(String[] args) {
        System.out.println("OK OK");


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
                    "receipt.income_expense,\n" +
                    "receipt.cus_id,\n" +
                    "receipt.cross_recipt_or_voucher,\n" +
                    "receipt.pay_type,\n" +
                    "receipt.amount\n" +
                    "FROM\n" +
                    "receipt\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 9 AND\n" +
                    "receipt.receipt_status = 1 AND\n" +
                    "receipt.idReceipt = 10584\n");
            int x = 0;
            while (data.next()) {
                x++;
                int recept_applicationId = data.getInt("recept_applicationId");
                String receipt_day = data.getString("receipt_day");
                String receipt_print_no = data.getString("receipt_print_no");
                int idReceipt = data.getInt("idReceipt");
                int receipt_account_id = data.getInt("receipt_account_id");
                int receipt_user_id = data.getInt("receipt_user_id");
                int office_idOffice = data.getInt("office_idOffice");

                System.out.println(recept_applicationId);


                ResultSet da = DB.getData("SELECT\n" +
                        "mixincome.idMixincome,\n" +
                        "mixincome.mixincome_fulltotal,\n" +
                        "mixincome.mixincome_date,\n" +
                        "mixdata.idMixdata,\n" +
                        "mixdata.md_description,\n" +
                        "mixdata.md_amount,\n" +
                        "mixdata.md_vat,\n" +
                        "mixdata.md_nbt,\n" +
                        "mixdata.md_stamp,\n" +
                        "mixdata.md_total,\n" +
                        "mixdata.mixintype_idMixintype,\n" +
                        "mixdata.mixincome_IdMixincome,\n" +
                        "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                        "mixintype.bankinfo_idBank\n" +
                        "FROM\n" +
                        "mixincome\n" +
                        "INNER JOIN mixdata ON mixdata.mixincome_IdMixincome = mixincome.idMixincome\n" +
                        "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                        "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                        "WHERE\n" +
                        "mixincome.idMixincome = " + recept_applicationId);

                while (da.next()) {
                    double md_amount = da.getDouble("md_amount");
                    int votid = da.getInt("account_receipt_title_idAccount_receipt_title");
                    System.out.println("    " + da.getDouble("md_amount"));

                    insertToAccount(receipt_day,
                            receipt_print_no,
                            idReceipt,
                            votid,
                            receipt_account_id,
                            md_amount,
                            receipt_user_id,
                            recept_applicationId,
                            9, 1,
                            office_idOffice);
                }
                double cheack = data.getDouble("cheack");
                double cesh = data.getDouble("cesh");

                if (cheack > 0) {
                    insertToAccount(receipt_day,
                            receipt_print_no,
                            idReceipt,
                            66,
                            receipt_account_id,
                            cheack,
                            receipt_user_id,
                            recept_applicationId,
                            9, 1,
                            office_idOffice);
                }

                if (cesh > 0) {
                    insertToAccount(receipt_day,
                            receipt_print_no,
                            idReceipt,
                            65,
                            receipt_account_id,
                            cesh,
                            receipt_user_id,
                            recept_applicationId,
                            9, 1,
                            office_idOffice);
                }


            }


            System.out.println(x + "             ----------        ");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public static boolean insertToAccount(String date,
                                          String recipt_no,
                                          int recipt_id,
                                          int vort_id,
                                          int acoount_id,
                                          double amount,
                                          int user_id,
                                          int app_id,
                                          int appcatid,
                                          int incom, int officeid) {

        String quary = "INSERT INTO `account_ps_three` (\n" +
                "\t`report_date`,\n" +
                "\t`report_ricipt_no`,\n" +
                "\t`report_ricipt_id`,\n" +
                "\t`report_vort_id`,\n" +
                "\t`report_account_id`,\n" +
                "\t`report_amount`,\n" +
                "\t`report_user_id`,\n" +
                "\t`report_application_id`,\n" +
                "\t`report_application_cat_id`,\n" +
                "\t`report_status`,`office_idOffice`,income_or_expence\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t'" + date + "',\n" +
                "\t\t'" + recipt_no + "',\n" +
                "\t\t'" + recipt_id + "',\n" +
                "\t\t'" + vort_id + "',\n" +
                "\t\t'" + acoount_id + "',\n" +
                "\t\t'" + amount + "',\n" +
                "\t\t'" + user_id + "',\n" +
                "\t\t'" + app_id + "',\n" +
                "\t\t'" + appcatid + "',\n" +
                "\t\t'1','" + officeid + "', '" + incom + "' \n" +
                "\t);";


        try {
            conn.DB.setData(quary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
