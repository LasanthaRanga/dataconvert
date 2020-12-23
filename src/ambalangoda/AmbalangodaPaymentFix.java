package ambalangoda;


import conn.DB;

import java.sql.ResultSet;

public class AmbalangodaPaymentFix {

    public static void main(String[] args) {
        System.out.println("ela");
        getRecipt();
    }


    public static void getRecipt() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.idass_Payment,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payment.ass_Payment_fullTotal,\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_payment.ass_cash,\n" +
                    "ass_payment.ass_check,\n" +
                    "ass_payment.cd_balance,\n" +
                    "receipt.receipt_print_no,\n" +
                    "receipt.cheack,\n" +
                    "receipt.cesh,\n" +
                    "receipt.idReceipt\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN receipt ON ass_payment.Receipt_idReceipt = receipt.idReceipt\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_ThisYear = 2020 AND\n" +
                    "ass_payment.cd_balance < 0\n");


            while (data.next()) {
                System.out.println("---------------------------------------");
                String rn = data.getString("receipt_print_no");
                System.out.println(rn);

                int idRecipt = data.getInt("idReceipt");

                int idass_payment = data.getInt("idass_Payment");
                double ass_payment_fullTotal = data.getDouble("ass_Payment_fullTotal");
                double cd_balance = data.getDouble("cd_balance");

                double ass_cash = data.getDouble("ass_cash");


                double correct = ass_cash + cd_balance;
                double ctot = ass_payment_fullTotal + cd_balance;

                System.out.println(ctot);

                System.out.println(correct);


                conn.DB.setData("UPDATE `receipt` \n" +
                        "SET \n" +
                        "`cesh` = " + round(correct)+ ",\n" +
                        "`receipt_total` = " + round(correct) + "\n" +
                        "WHERE\n" +
                        "\t`idReceipt` = " + idRecipt);

                conn.DB.setData("UPDATE `ass_payment` \n" +
                        "SET \n" +
                        "`ass_Payment_fullTotal` = " + round(ctot) + ",\n" +
                        "`ass_cash` = " + round(correct) + "\n" +
                        "WHERE\n" +
                        "\t`idass_Payment` = " + idass_payment);


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }


}
