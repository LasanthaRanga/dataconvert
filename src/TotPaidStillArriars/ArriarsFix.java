package TotPaidStillArriars;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2020-04-21.
 */
public class ArriarsFix {
    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "assessment.idAssessment\n" +
                    "FROM\n" +
                    "assessment");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                // System.out.println(idAssessment);
                double sum = 0;

                ResultSet ph = DB.getData("SELECT\n" +
                        "ass_payhistry.ass_PayHistry_Q1,\n" +
                        "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                        "ass_payhistry.Assessment_idAssessment\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.ass_PayHistry_year = 2020 AND\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt = 1 AND\n" +
                        "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "'\n" +
                        "ORDER BY\n" +
                        "ass_payhistry.Assessment_idAssessment ASC");

                int status = 0;

                while (ph.next()) {
                    sum += ph.getDouble("ass_PayHistry_Q1");
                    status = ph.getInt("ass_PayHistry_Q1Status");
                }

                if (status == 0) {
                    System.out.println(idAssessment + "  ============");
                    System.out.println(sum);

                    ResultSet qval = DB.getData("SELECT\n" +
                            "assessment.idAssessment,\n" +
                            "ass_allocation.ass_allocation,\n" +
                            "ass_nature.ass_nature_year_rate,\n" +
                            "ROUND (ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400,2) AS qv\n" +
                            "FROM\n" +
                            "assessment\n" +
                            "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                            "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                            "WHERE\n" +
                            "ass_allocation.ass_allocation_status = 1 AND\n" +
                            "assessment.idAssessment = " + idAssessment);

                    if (qval.last()) {
                        double qv = qval.getDouble("qv");
                        double v = qv - sum;
                        v = Math.round(v * 100.00) / 100.00;
                        System.out.println(v);
                        conn.DB.setData("UPDATE ass_qstart \n" +
                                "SET ass_qstart.ass_Qstart_LQC_Arreas = '" + v + "',\n" +
                                "ass_qstart.ass_Qstart_LQ_Arreas = '" + v + "',\n" +
                                "ass_qstart.ass_Qstart_tyold_arrias = '" + v + "' \n" +
                                "WHERE\n" +
                                "\tass_qstart.ass_Qstart_year = 2020 \n" +
                                "\tAND ass_qstart.ass_Qstart_QuaterNumber = 2 \n" +
                                "\tAND ass_qstart.Assessment_idAssessment = " + idAssessment);
                    }


                } else {
                    System.out.println(" ");
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
