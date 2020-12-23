package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-23.
 */
public class QuaterArriarsCheck {
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


            while (data.next()) {
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
                        "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                        "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                        "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                        "ass_payhistry.ass_PayHistry_DRQ4\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment = '" + id + "' AND\n" +
                        "ass_payhistry.ass_PayHistry_year = 2020");


                double q4histry = 0.0;
                boolean notCompete = true;

                while (dd.last()) {
                    q4histry += dd.getDouble("ass_PayHistry_Q4");
                    int st4 = dd.getInt("ass_PayHistry_DRQ4");
                    if (st4 == 1) {
                        notCompete = false;
                        break;
                    }
                }

                if (notCompete) {
                    if (q4histry > 0) {
                        System.out.println("--- Gewa Etha ");
                        double v = qval - q4histry;
                        if (v == haveToPay) {
                            System.out.println("########### OKKK");
                        } else {
                            System.out.println(" WERADI ------------------------------------------------------------------------------------------  " + id);
                        }
                    } else {
                        System.out.println("--- Gewa Netha");
                    }
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
