package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-31.
 */
public class NarammalaPs {
    public static void main(String[] args) {


        try {


            ResultSet data = DB.getData("SELECT \n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_creditdebit.Assessment_idAssessment,\n" +
                    "ass_creditdebit.Ass_CreditDebit_cd,\n" +
                    "ass_creditdebit.Ass_CreditDebit_discription,\n" +
                    "ass_creditdebit.Ass_CreditDebit_amount,\n" +
                    "ass_payment.ass_Payment_goto_debit,\n" +
                    "ass_creditdebit.idAss_CreditDebit,\n" +
                    "ass_payment.ass_Payment_ThisYear,\n" +
                    "ass_payment.ass_Payment_date\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "INNER JOIN ass_payment ON ass_qstart.Assessment_idAssessment = ass_payment.Assessment_idAssessment\n" +
                    "INNER JOIN ass_creditdebit ON ass_creditdebit.Assessment_idAssessment = ass_qstart.Assessment_idAssessment\n" +
                    "WHERE\n" +
                    "ass_qstart.process_update_comment = 'Year Start Process Q4 10%' AND\n" +
                    "ass_payment.ass_Payment_ThisYear = 2019\n" +
                    "ORDER BY\n" +
                    "ass_payment.Assessment_idAssessment DESC\n");

            int x = 0;


            while (data.next()) {
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                double ass_creditDebit_cd = data.getDouble("Ass_CreditDebit_cd");
                double ass_creditDebit_amount = data.getDouble("Ass_CreditDebit_amount");

                double over = data.getDouble("ass_Payment_goto_debit");
                int idAss_creditDebit = data.getInt("idAss_CreditDebit");


                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_payhistry.idass_PayHistry,\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                        "ass_payhistry.ass_PayHistry_year,\n" +
                        "ass_payhistry.ass_PayHistry_Date,\n" +
                        "ass_payhistry.ass_PayHistry_status,\n" +
                        "ass_payhistry.ass_PayHistry_comment,\n" +
                        "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                        "ass_payhistry.ass_PayHistry_Q1 * 4 as tot,\n" +
                        "ass_payhistry.Assessment_idAssessment\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment = '" + assessment_idAssessment + "' AND\n" +
                        "ass_payhistry.ass_PayHistry_Date = '2020-01-01'");


                if (data1.last()) {
                    int tot = data1.getInt("tot");
                    double vto2021 = over - tot;


                    if (vto2021 > 0) {
                        System.out.println(assessment_idAssessment + "     -  " + over + "   " + tot + "    " + vto2021);
                        update(idAss_creditDebit, assessment_idAssessment, vto2021);

                    } else {
                        //   System.out.println(" MADI      " + assessment_idAssessment);
                        x++;
                    }


                }

            }


            System.out.println(x);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void update(int cdId, int idAss, double over) {

        try {

            conn.DB.setData("UPDATE `ass_creditdebit`\n" +
                    "SET \n" +
                    " `Ass_CreditDebit_amount` = '0',\n" +
                    " `Ass_balance` = '0' \n" +
                    "WHERE\n" +
                    "\t(`idAss_CreditDebit` = '" + cdId + "')");

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET \n" +
                    "`ass_PayHistry_Over` = '" + over + "'\n" +
                    "\n" +
                    "WHERE\n" +
                    "`Assessment_idAssessment` = '" + idAss + "' AND `ass_PayHistry_Date` = '2020-10-01' AND `ass_PayHistry_comment` = '22'");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
