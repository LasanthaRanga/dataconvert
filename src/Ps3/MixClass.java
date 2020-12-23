package Ps3;

import conn.DB;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ranga Rathnayake on 2020-05-14.
 */
public class MixClass {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "mixdata.idMixdata,\n" +
                    "mixdata.md_description,\n" +
                    "mixdata.md_amount,\n" +
                    "mixdata.md_vat,\n" +
                    "mixdata.md_nbt,\n" +
                    "mixdata.md_stamp,\n" +
                    "mixdata.md_total,\n" +
                    "mixdata.mixintype_idMixintype,\n" +
                    "mixdata.mixincome_IdMixincome,\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "mixincome.idMixincome,\n" +
                    "mixincome.mixincome_date,\n" +
                    "mixincome.customer_idCustomer,\n" +
                    "receipt.idReceipt,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.receipt_total, receipt_day,\n" +
                    "mixincome.mixincome_fulltotal\n" +
                    "FROM\n" +
                    "mixdata\n" +
                    "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                    "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = mixdata.mixincome_IdMixincome\n" +
                    "INNER JOIN receipt ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "receipt.Application_Catagory_idApplication_Catagory = 9 AND\n" +
                    "receipt.receipt_status = 1 AND\n" +
                    "receipt.idReceipt > 38850\n" +
                    "ORDER BY\n" +
                    "mixincome.idMixincome ASC\n");

            int rid = 0;
            while (data.next()) {
                int idReceipt = data.getInt("idReceipt");
                if (rid != idReceipt) {
                    rid = idReceipt;
                    System.out.println(idReceipt);
                    int idMixdata = data.getInt("mixincome_IdMixincome");

                    updateMixIncomeStatus(idMixdata, 9, data.getString("receipt_day"));
                } else {
                    System.out.println();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    final static int VATID = 33;
    final static int NBTID = 34;
    final static int STAMPID = 35;
    final static int CASH = 65;
    final static int CHQUE = 66;
    final static int NOCASH = 145;

   static int x = 0;

    public static void updateMixIncomeStatus(int appid, int appcat, String date) {
        x++;
        try {
//            conn.DB.setData("UPDATE mixincome\n" +
//                    "SET \n" +
//                    " mixincome_status = '1'\n" +
//                    "WHERE\n" +
//                    "\tidMixincome = '" + appid + "'");

            ResultSet data1 = DB.getData("SELECT\n" +
                    "\treceipt.idReceipt,\n" +
                    "\treceipt.receipt_print_no,\n" +
                    "\tmixincome.idMixincome,\n" +
                    "\tmixincome.mixincome_userid,\n" +
                    "\treceipt.cheack,\n" +
                    "\treceipt.cesh, " +
                    "receipt_total ,\n" +
                    "mixincome.cros_ref,\n" +
                    "mixincome.others,\n" +
                    "mixincome.mixincome_paytype,\n" +
                    "mixincome.customer_idCustomer\n" +
                    "FROM\n" +
                    "\treceipt\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = receipt.recept_applicationId\n" +
                    "WHERE\n" +
                    "\treceipt.Application_Catagory_idApplication_Catagory =  '" + appcat + "'" +
                    "AND mixincome.idMixincome = " + appid);

            String reciptNo = null;
            int idRecipt = 0;
            int mixincome_userid = 0;
            double receipt_total = 0;

            int payType = 0;
            String cros_ref = "";

            int cusid = 0;

            double cheack = 0;
            double cesh = 0;
            if (data1.last()) {


                reciptNo = data1.getString("receipt_print_no");

                idRecipt = data1.getInt("idReceipt");
                mixincome_userid = data1.getInt("mixincome_userid");

                cheack = data1.getDouble("cheack");
                cesh = data1.getDouble("cesh");
                receipt_total = data1.getDouble("receipt_total");
                payType = data1.getInt("mixincome_paytype");

                cros_ref = data1.getString("cros_ref");
                cusid = data1.getInt("customer_idCustomer");
            }

            String quary = "SELECT\n" +
                    "mixdata.idMixdata,\n" +
                    "mixdata.md_description,\n" +
                    "mixdata.md_amount,\n" +
                    "mixdata.md_vat,\n" +
                    "mixdata.md_nbt,\n" +
                    "mixdata.md_stamp,\n" +
                    "mixdata.md_total,\n" +
                    "mixdata.mixintype_idMixintype,\n" +
                    "mixdata.mixincome_IdMixincome,\n" +
                    "mixintype.idMixintype,\n" +
                    "mixintype.mixintype_name,\n" +
                    "mixintype.account_receipt_title_idAccount_receipt_title,\n" +
                    "mixintype.mixintype_status,\n" +
                    "account_receipt_title.idAccount_receipt_title,\n" +
                    "mixintype.bankinfo_idBank,\n" +
                    "mixincome.idMixincome,\n" +
                    "mixincome.mixincome_date\n" +
                    "FROM\n" +
                    "mixdata\n" +
                    "INNER JOIN mixintype ON mixdata.mixintype_idMixintype = mixintype.idMixintype\n" +
                    "INNER JOIN account_receipt_title ON mixintype.account_receipt_title_idAccount_receipt_title = account_receipt_title.idAccount_receipt_title\n" +
                    "INNER JOIN mixincome ON mixincome.idMixincome = mixdata.mixincome_IdMixincome\n" +
                    "WHERE\n" +
                    "mixincome.idMixincome =" + appid;

            ResultSet data = DB.getData(quary);



          //  Integer idUser = StaticViews.getLogUser().getIdUser();


            int bankinfo_idBank = 0;
            while (data.next()) {


                double md_amount = data.getDouble("md_amount");
                int idVote = data.getInt("account_receipt_title_idAccount_receipt_title");
                bankinfo_idBank = data.getInt("bankinfo_idBank");

                if (md_amount > 0) {
                    if (payType == 3) {
                        // insertToCross(cros_ref, md_amount, idVote, date, cusid, idRecipt);
                        insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat, 3);
                    } else {
                        insertToAccount(date, reciptNo, idRecipt, idVote, bankinfo_idBank, md_amount, mixincome_userid, appid, appcat, 1);
                    }
                }

                double vat = data.getDouble("md_vat");
                if (vat > 0) {
                    if (payType == 3) {
                        //  insertToCross(cros_ref, vat, VATID, date, cusid, idRecipt);
                        insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat, 3);
                    } else {
                        insertToAccount(date, reciptNo, idRecipt, VATID, bankinfo_idBank, vat, mixincome_userid, appid, appcat, 1);
                    }
                }

                double nbt = data.getDouble("md_nbt");
                if (nbt > 0) {
                    if (payType == 3) {
                        //  insertToCross(cros_ref, nbt, NBTID, date, cusid, idRecipt);
                        insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat, 3);
                    } else {
                        insertToAccount(date, reciptNo, idRecipt, NBTID, bankinfo_idBank, nbt, mixincome_userid, appid, appcat, 1);
                    }
                }

                double stamp = data.getDouble("md_stamp");
                if (stamp > 0) {
                    if (payType == 3) {
                        //   insertToCross(cros_ref, stamp, STAMPID, date, cusid, idRecipt);
                        insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat, 3);
                    } else {
                        insertToAccount(date, reciptNo, idRecipt, STAMPID, bankinfo_idBank, stamp, mixincome_userid, appid, appcat, 1);
                    }
                }
            }


            if (cesh > 0) {
                insertToAccount(date, reciptNo, idRecipt, CASH, bankinfo_idBank, cesh, mixincome_userid, appid, appcat, 1);
                updateRecipt(idRecipt + "", 1, 0, 1, cesh); // update Recipt Status
            }


            if (cheack > 0) {
                insertToAccount(date, reciptNo, idRecipt, CHQUE, bankinfo_idBank, cheack, mixincome_userid, appid, appcat, 1);
                updateRecipt(idRecipt + "", 2, 0, 1, cheack); // update Recipt Status
            }


            if (cheack == 0 && cesh == 0) {
                insertToAccount(date, reciptNo, idRecipt, NOCASH, bankinfo_idBank, receipt_total, mixincome_userid, appid, appcat, 3);
                updateRecipt(idRecipt + "", 3, 0, 1, receipt_total); // update Recipt Status
            }


            conn.DB.setData("UPDATE \n" +
                    "`receipt`\n" +
                    "SET \n" +
                    " `receipt_account_id` = '" + bankinfo_idBank + "',\n" +
                    " `receipt_user_id` = '" + mixincome_userid + "'\n" +
                    "WHERE\n" +
                    "\t(`idReceipt` = '" + idRecipt + "');\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(x+"        ================= ");
    }

    public static boolean insertToAccount(String date, String recipt_no, int recipt_id, int vort_id, int acoount_id, double amount, int user_id, int app_id, int appcatid, int incom) {

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
                "\t\t'1','1', '" + incom + "' \n" +
                "\t);";
        try {
            conn.DB.setData(quary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void updateRecipt(String idRecipt, int paytype, int cusid, int cros, double amount) {
//        ------------------
//        pay_type
//        cash 1
//        check 2
//        online 3
//        croce 4
//        ------------------
//        --------------------
//            cross status
//            1 recipt
//            2 voucher
//            3 cross
//        --------------------

        try {
            if (cusid > 0) {
                conn.DB.setData("UPDATE `receipt` \n" +
                        "SET \n" +
                        "`income_expense` = 1,\n" +
                        "`cus_id` = " + cusid + " ,\n" +
                        "`cross_recipt_or_voucher` = " + cros + " ,\n" +
                        "`pay_type` = " + paytype + " ,\n" +
                        "`amount` = " + amount + " \n" +
                        "WHERE\n" +
                        "\t`idReceipt` = " + idRecipt);
            } else {
                conn.DB.setData("UPDATE `receipt` \n" +
                        "SET \n" +
                        "`income_expense` = 1,\n" +
                        "`cross_recipt_or_voucher` = " + cros + " ,\n" +
                        "`pay_type` = " + paytype + " ,\n" +
                        "`amount` = " + amount + " \n" +
                        "WHERE\n" +
                        "\t`idReceipt` = " + idRecipt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


}
