package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-23.
 */
public class QuaterArriarsCheck {

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    public static void main(String[] args) {

        startProcess();
    }

    public static void startProcess() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 4 AND\n" +
                    "ass_qstart.ass_Qstart_year = 2020\n");

            int x = 0;
            while (data.next()) {
                int idass_qstart = data.getInt("idass_Qstart");
                int id = data.getInt("Assessment_idAssessment");
                double haveToPay = data.getDouble("ass_Qstart_HaveToQPay");
                double qval = 0;


                ResultSet qq = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ass_allocation.ass_allocation,\n" +
                        "ROUND(ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400,2) AS qqq \n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "assessment.assessment_status = 1 AND\n" +
                        "assessment.idAssessment = " + id);

                if (qq.last()) {
                    qval = qq.getDouble("qqq");
                }


                ResultSet dd = DB.getData("SELECT\n" +
                        "ass_payhistry.ass_PayHistry_Q1,\n" +
                        "ass_payhistry.ass_PayHistry_Q2,\n" +
                        "ass_payhistry.ass_PayHistry_Q3,\n" +
                        "ass_payhistry.ass_PayHistry_Q4,\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                        "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q4Status\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment = '" + id + "' AND\n" +
                        "ass_payhistry.ass_PayHistry_year = 2020\n" +
                        "\n");


                double q4histry = 0.0;
                boolean notCompete = true;


                while (dd.next()) {

                    q4histry += dd.getDouble("ass_PayHistry_Q4");
                    int st4 = dd.getInt("ass_PayHistry_Q4Status");
                    if (st4 == 1) {
                        notCompete = false;
                    }
                }

                if (notCompete) {
                    if (q4histry > 0) {
                        System.out.println("QV :" + qval + "  - q4histry : " + q4histry + "   -  " + "  Have To Pay  : " + haveToPay);
                        double v = round(qval - q4histry);
                        System.out.println("V : " + v);

                        System.out.println(haveToPay - v);

                        if (v == haveToPay) {
                            System.out.println("########### OKKK");
                        } else {
                            System.out.println(" WERADI ------------------------------------------------------------------------------------------  " + id);
                            x++;
                          //  updateHaveToPay(idass_qstart, v);
                        }
                    } else {
                        // System.out.println("--- Gewa Netha");
                    }
                }


            }
            System.out.println(x);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateHaveToPay(int qid, double have) {

        try {

            DB.setData("UPDATE  `ass_qstart` \n" +
                    "SET \n" +
                    "`ass_Qstart_HaveToQPay` = '" + have + "'\n" +
                    "WHERE\n" +
                    "\t`idass_Qstart` = " + qid);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
