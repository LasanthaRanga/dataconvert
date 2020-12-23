package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-22.
 */
public class Discount {

    public Dis getDiscount(int id) {

        try {


            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payhistry.idass_PayHistry,\n" +
                    "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                    "ass_payhistry.ass_PayHistry_year,\n" +
                    "ass_payhistry.ass_PayHistry_Date,\n" +
                    "ass_payhistry.ass_PayHistry_status,\n" +
                    "ass_payhistry.ass_PayHistry_comment,\n" +
                    "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                    "ass_payhistry.ass_PayHistry_Q1,\n" +
                    "ass_payhistry.ass_PayHistry_Q2,\n" +
                    "ass_payhistry.ass_PayHistry_Q3,\n" +
                    "ass_payhistry.ass_PayHistry_Q4,\n" +
                    "ass_payhistry.ass_PayHistry_Over,\n" +
                    "ass_payhistry.Assessment_idAssessment,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                    "ass_payhistry.ass_PayHistry_DRQ4,\n" +
                    "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                    "ass_payhistry.ass_PayHistry_Q4Status,\n" +
                    "ass_allocation.ass_allocation,\n" +
                    "assessment.idAssessment,\n" +
                    "ass_nature.ass_nature_name,\n" +
                    "ass_nature.ass_nature_year_rate,\n" +
                    "ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400 as qamout\n" +
                    "FROM\n" +
                    "ass_payhistry\n" +
                    "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = ass_payhistry.Assessment_idAssessment\n" +
                    "INNER JOIN assessment ON ass_payhistry.Assessment_idAssessment = assessment.idAssessment AND ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_comment = '131' AND\n" +
                    "ass_allocation.ass_allocation_status = 1 AND(\n" +
                    "ass_payhistry.ass_PayHistry_DRQ1 > 0 OR\n" +
                    "ass_payhistry.ass_PayHistry_DRQ2 > 0 OR\n" +
                    "ass_payhistry.ass_PayHistry_DRQ3 > 0 OR\n" +
                    "ass_payhistry.ass_PayHistry_DRQ4 > 0) AND\n" +
                    "ass_payhistry.Assessment_idAssessment = "+id);

            double dis10 = 0;
            double dis5 = 0;
            double totalPay = 0;
            double over = 0;
            while (data.next()) {
                int ass_payHistry_drq1 = data.getInt("ass_PayHistry_DRQ1");
                int ass_payHistry_drq2 = data.getInt("ass_PayHistry_DRQ2");
                int ass_payHistry_drq3 = data.getInt("ass_PayHistry_DRQ3");
                double qamout = data.getDouble("qamout");
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
//                System.out.println(assessment_idAssessment + " -------  ");


                double q1 = data.getDouble("ass_PayHistry_Q1");
                double q2 = data.getDouble("ass_PayHistry_Q2");
                double q3 = data.getDouble("ass_PayHistry_Q3");
                double q4 = data.getDouble("ass_PayHistry_Q4");
                over = data.getDouble("ass_PayHistry_Over");

                totalPay = q1 + q2 + q3 + q4;


                if (ass_payHistry_drq1 == 10) {
//                    System.out.println();

                    double v = qamout * 10 / 100;
                    double v1 = v * 4;
//                    System.out.println(v1);
                    dis10 += v1;
//                    System.out.println(dis10);

                }

                if (ass_payHistry_drq1 == 5) {
                    double v = qamout * 5 / 100;
                    dis5 += v;
                }
                if (ass_payHistry_drq2 == 5) {
                    double v = qamout * 5 / 100;
                    dis5 += v;
                }
                if (ass_payHistry_drq3 == 5) {
                    double v = qamout * 5 / 100;
                    dis5 += v;
                }


            }

//            System.out.println("---------------------------");

            double full_dis = dis5 + dis10;

//            System.out.println("Discount " + full_dis);
//            System.out.println("Paid " + totalPay);
//            System.out.println("over " + over);

            return new Dis(full_dis, totalPay, over);


        } catch (Exception e) {
            e.printStackTrace();
            return new Dis(0.0, 0.0, 0.0);
        }
    }
}










