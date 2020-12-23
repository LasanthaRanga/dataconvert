package chilaw_discount_10_fix;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-10.
 */
public class fix {

    public static void main(String[] args) {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payment.ass_Payment_date,\n" +
                    "ass_payto.ass_payto_discount,\n" +
                    "ass_payto.ass_payto_discount_rate,\n" +
                    "ass_payment.ass_cash +\n" +
                    "ass_payment.ass_check AS paid,\n" +
                    "ass_payto.ass_payto_Qno,\n" +
                    "ass_payment.ass_Payment_LY_Arrears +\n" +
                    "ass_payment.ass_Payment_LY_Warrant +\n" +
                    "ass_payment.ass_Payment_fullTotal +\n" +
                    "ass_payment.ass_Payment_goto_debit AS tot,\n" +
                    "(ass_payment.ass_cash +\n" +
                    "ass_payment.ass_check) - (ass_payment.ass_Payment_LY_Arrears +\n" +
                    "ass_payment.ass_Payment_LY_Warrant +\n" +
                    "ass_payment.ass_Payment_fullTotal +\n" +
                    "ass_payment.ass_Payment_goto_debit),\n" +
                    "ass_payment.cd_balance,\n" +
                    "ass_payto.ass_payto_qvalue,\n" +
                    "ass_payto.idass_payto,\n" +
                    "ass_payment.idass_Payment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_ThisYear = 2020 AND\n" +
                    "ass_payment.ass_Payment_date <= '2020-01-31' AND\n" +
                    "ass_payment.ass_Payment_Status = 1 AND\n" +
                    "ass_payto.ass_payto_discount_rate IN (5, 0) AND\n" +
                    "ass_payto.ass_payto_qvalue > 0 AND\n" +
                    "ass_payto.ass_payto_Qno = 4\n" +
                    "ORDER BY\n" +
                    "ass_payment.idass_Payment ASC\n");

            while (data.next()) {
                int assid = data.getInt("Assessment_idAssessment");
                int idass_payment = data.getInt("idass_Payment");
                update(assid, idass_payment);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void update(int assid, int idpay) {
        System.out.println("==============================");
        // System.out.println(idpay);

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payment.Assessment_idAssessment,\n" +
                    "ass_payto.ass_payto_discount,\n" +
                    "ass_payto.ass_payto_discount_rate,\n" +
                    "ass_payment.ass_cash +\n" +
                    "ass_payment.ass_check AS paid,\n" +
                    "ass_payto.ass_payto_Qno,\n" +
                    "ass_payto.ass_payto_qvalue,\n" +
                    "ass_payto.idass_payto,\n" +
                    "ass_payment.idass_Payment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                    "WHERE\n" +
                    "ass_payment.idass_Payment = " + idpay +
                    " ORDER BY\n" +
                    "ass_payment.idass_Payment ASC\n");

            double paid = 0;

            double val = 0;


            while (data.next()) {
                int idass_payto = data.getInt("idass_payto");
                paid += data.getDouble("ass_payto_qvalue");
            }

            System.out.println(paid);


            ResultSet data1 = DB.getData("SELECT\n" +
                    "\tassessment.idAssessment,\n" +
                    "\tass_allocation.ass_allocation,\n" +
                    "\tass_nature.ass_nature_year_rate,\n" +
                    "\tass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 100 AS qv,\n" +
                    "\tass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 100 * 10 / 100 AS dis,\n" +
                    "\t( ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 100 ) - ( ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 100 * 10 / 100 ) AS val \n" +
                    "FROM\n" +
                    "\tassessment\n" +
                    "\tINNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                    "\tINNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature \n" +
                    "WHERE\n" +
                    "\tass_allocation.ass_allocation_status = 1 \n" +
                    "\tAND assessment.idAssessment = " + assid);

            if (data1.last()) val = data1.getDouble("val");

            double disQval = val / 4;

            double v = round(val - paid);

            if (v > 10) {
                System.out.println(" - " + assid);
            } else {
                conn.DB.setData("UPDATE `ass_payhistry` \n" +
                        "SET\n" +
                        "`ass_PayHistry_comment` = 'discount 10 fix update',\n" +
                        "`ass_PayHistry_Q4Status` = 1 \n" +
                        "WHERE\n" +
                        "\t`Assessment_idAssessment` = '" + assid + "' and `ass_PayHistry_year` = 2020 and `ass_PayHistry_Qcunt` = 3");
            }

            System.out.println("  ---  " + v);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

}
