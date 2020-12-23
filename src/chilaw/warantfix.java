package chilaw;

import conn.DB;

import java.sql.ResultSet;

public class warantfix {
    public static void main(String[] args) {
        String qq = "SELECT\n" +
                "ass_qstart.ass_Qstart_year,\n" +
                "ass_qstart.Assessment_idAssessment,\n" +
                "ass_qstart.idass_Qstart,\n" +
                "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                "ass_qstart.ass_Qstart_LY_Warrant,\n" +
                "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "ass_qstart.ass_Qstart_LYC_Warrant\n" +
                "FROM\n" +
                "ass_qstart\n" +
                "WHERE\n" +
                "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                "ass_qstart.Assessment_idAssessment NOT IN (SELECT\n" +
                "ass_payment.Assessment_idAssessment\n" +
                "FROM\n" +
                "ass_payment\n" +
                "WHERE\n" +
                "ass_payment.idass_Payment > 1147 AND\n" +
                "ass_payment.ass_Payment_Status = 1\n" +
                ")";

        try {


            ResultSet data = DB.getData(qq);
            while (data.next()) {

                int idAssessment = data.getInt("Assessment_idAssessment");
                System.out.println(idAssessment);
                int idass_qstart = data.getInt("idass_Qstart");
                double ly_arreas = data.getDouble("ass_Qstart_LY_Arreas");


                ResultSet quaters = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ass_nature.ass_nature_year_rate,\n" +
                        "ass_nature.ass_nature_warrant_rate,\n" +
                        "round(ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400,2) as quater,\n" +
                        "round(ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400 * ass_nature.ass_nature_warrant_rate/100, 2)as wo\n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "assessment.assessment_status = 1 AND\n" +
                        "assessment.idAssessment = '" + idAssessment + "'\n" +
                        "GROUP BY\n" +
                        "assessment.idAssessment");

                if (quaters.last()) {
                    double quater = quaters.getDouble("quater");
                    double wo = quaters.getDouble("wo");
                    double warant = 0;
                    if (ly_arreas >= (quater * 4)) {
                        warant = wo * 4;
                    } else if (ly_arreas >= (quater * 3)) {
                        warant = wo * 3;
                    } else if (ly_arreas >= (quater * 2)) {
                        warant = wo * 2;
                    } else if (ly_arreas >= 1) {
                        warant = wo;
                    }

                    conn.DB.setData("UPDATE `ass_qstart` \n" +
                            "SET \n" +
                            "`ass_Qstart_LY_Warrant` = '" + warant + "',\n" +
                            "`ass_Qstart_LYC_Warrant` = '" + warant + "',\n" +
                            "`ass_Qstart_LQC_Arreas` = 0,\n" +
                            "`ass_Qstart_LQ_Warrant` = 0,\n" +
                            "`ass_Qstart_LQC_Warrant` = 0,\n" +
                            "`ass_Qstart_tyold_warant` = '" + warant + "' \n" +
                            "WHERE\n" +
                            "\t`idass_Qstart` = " + idass_qstart);
                    System.out.println("updated");
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }
}
