package conn;

import java.sql.ResultSet;

public class Pay {
    public static void main(String[] args) {
        try {


            ResultSet data = DB.getData("SELECT\n" +
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
                    "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n");


            int x = 0;
            while (data.next()) {


                DB2.setData("INSERT INTO `receipt` (\n" +
                        "\t`Application_Catagory_idApplication_Catagory`,\n" +
                        "\t`recept_applicationId`,\n" +
                        "\t`receipt_print_no`,\n" +
                        "\t`cheack`,\n" +
                        "\t`cesh`,\n" +
                        "\t`receipt_total`,\n" +
                        "\t`receipt_day`,\n" +
                        "\t`receipt_status`,\n" +
                        "\t`receipt_syn`,\n" +
                        "\t`chque_no`,\n" +
                        "\t`chque_date`,\n" +
                        "\t`chque_bank`,\n" +
                        "\t`oder`,\n" +
                        "\t`office_idOffice`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "\t(\n" +
                        "\t\t'2',\n" +
                        "\t\t'" + data.getInt("recept_applicationId") + "',\n" +
                        "\t\t'',\n" +
                        "\t\t'" + data.getDouble("receipt.cheack") + "',\n" +
                        "\t\t'" + data.getDouble("receipt.cesh") + "',\n" +
                        "\t\t'" + data.getDouble("receipt.receipt_total") + "',\n" +
                        "\t\t'" + data.getString("receipt.receipt_day") + "',\n" +
                        "\t\t'" + data.getInt("receipt.receipt_status") + "',\n" +
                        "\t\t'1',\n" +
                        "\t\tNULL,\n" +
                        "\t\tNULL,\n" +
                        "\t\tNULL,\n" +
                        "\t\tNULL,\n" +
                        "\t\t'" + data.getInt("receipt.office_idOffice") + "'\n" +
                        "\t)\n");


                ResultSet last = DB2.getData("SELECT\n" +
                        "receipt.idReceipt\n" +
                        "FROM\n" +
                        "receipt\n" +
                        "ORDER BY\n" +
                        "receipt.idReceipt DESC\n" +
                        "LIMIT 1");

                int rid = 0;

                if (last.last()) {
                    rid = last.getInt("receipt.idReceipt");
                }


                conn.DB2.setData("INSERT INTO `ass_payment` (\t\n" +
                        "\t`ass_Payment_Q_Number`,\n" +
                        "\t`ass_Payment_ThisYear`,\n" +
                        "\t`ass_Payment_date`,\n" +
                        "\t`ass_Payment_LY_Arrears`,\n" +
                        "\t`ass_Payment_LY_Warrant`,\n" +
                        "\t`ass_Payment_fullTotal`,\n" +
                        "\t`ass_Payment_idUser`,\n" +
                        "\t`Assessment_idAssessment`,\n" +
                        "\t`Receipt_idReceipt`,\n" +
                        "\t`ass_nature_idass_nature`,\n" +
                        "\t`ass_allocation_idass_allocation`,\n" +
                        "\t`ass_Payment_goto_debit`,\n" +
                        "\t`ass_Payment_Status`,\n" +
                        "\t`ass_cash`,\n" +
                        "\t`ass_check`,\n" +
                        "\t`ass_check_no`,\n" +
                        "\t`ass_bank`,\n" +
                        "\t`pay_user_id`,\n" +
                        "\t`cd_balance`,\n" +
                        "\t`office_idOffice`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "\t(\t\t\n" +
                        "\t\t'" + data.getInt("ass_Payment_Q_Number") + "',\n" +
                        "\t\t'2019',\n" +
                        "\t\t'" + data.getString("ass_Payment_date") + "',\n" +
                        "\t\t'" + data.getDouble("ass_Payment_LY_Arrears") + "',\n" +
                        "\t\t'" + data.getDouble("ass_Payment_LY_Warrant") + "',\n" +
                        "\t\t'" + data.getDouble("ass_Payment_fullTotal") + "',\n" +
                        "\t\t'" + data.getInt("ass_Payment_idUser") + "',\n" +
                        "\t\t'" + data.getInt("Assessment_idAssessment") + "',\n" +
                        "\t\t'" + rid + "',\n" +  //Recit No
                        "\t\t'" + data.getInt("ass_nature_idass_nature") + "',\n" +
                        "\t\t'" + data.getInt("ass_allocation_idass_allocation") + "',\n" +
                        "\t\t'" + data.getDouble("ass_Payment_goto_debit") + "',\n" +
                        "\t\t'" + data.getInt("ass_Payment_Status") + "',\n" +
                        "\t\t'" + data.getDouble("ass_cash") + "',\n" +
                        "\t\t'" + data.getDouble("ass_check") + "',\n" +
                        "\t\t'" + data.getString("ass_check_no") + "',\n" +
                        "\t\t'" + data.getString("ass_bank") + "',\n" +
                        "\t\tNULL,\n" +
                        "\t\t'" + data.getDouble("cd_balance") + "',\n" +
                        "\t\t'" + data.getInt("office_idOffice") + "'\n" +
                        "\t)");


                ResultSet last2 = DB2.getData("SELECT\n" +
                        "ass_payment.idass_Payment\n" +
                        "FROM `ass_payment`\n" +
                        "ORDER BY\n" +
                        "ass_payment.idass_Payment DESC\n" +
                        "LIMIT 1");

                int idPay = 0;

                if (last2.last()) {
                    idPay = last2.getInt("ass_payment.idass_Payment");

                }


                x++;
                System.out.println(x + "  -----   ");
                ResultSet d = DB.getData("SELECT\n" +
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
                        "ass_payto.ass_Payment_idass_Payment =" + data.getInt("ass_payment.idass_Payment"));

                int y = 0;

                while (d.next()) {
                    y++;
                    System.out.println(y);

                    conn.DB2.setData("INSERT INTO `ass_payto` (\n" +
                            "\t`ass_payto_Qno`,\n" +
                            "\t`ass_payto_warrant`,\n" +
                            "\t`ass_payto_arrears`,\n" +
                            "\t`ass_payto_qvalue`,\n" +
                            "\t`ass_payto_discount`,\n" +
                            "\t`ass_payto_discount_rate`,\n" +
                            "\t`ass_Payment_idass_Payment`,\n" +
                            "\t`ass_payto_status`\n" +
                            ")\n" +
                            "VALUES\n" +
                            "\t(\n" +
                            "\t\t'" + d.getInt("ass_payto_Qno") + "',\n" +
                            "\t\t'" + d.getDouble("ass_payto_warrant") + "',\n" +
                            "\t\t'" + d.getDouble("ass_payto_arrears") + "',\n" +
                            "\t\t'" + d.getDouble("ass_payto_qvalue") + "',\n" +
                            "\t\t'" + d.getDouble("ass_payto_discount") + "',\n" +
                            "\t\t'" + d.getDouble("ass_payto_discount_rate") + "',\n" +
                            "\t\t'" + idPay + "',\n" +
                            "\t\t'" + d.getInt("ass_payto_status") + "'\n" +
                            "\t)");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }
}
