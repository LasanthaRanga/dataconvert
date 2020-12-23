package quater_end_report;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-02.
 */
public class Quater2EndReportFix {

    public static void main(String[] args) {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "assessment.idAssessment\n" +
                    "FROM\n" +
                    "assessment\n" +
                    "WHERE\n" +
                    "assessment.assessment_syn = 0");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                System.out.println(idAssessment);


                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_qstart.idass_Qstart,\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                        "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                        "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                        "ass_qstart.ass_Qstart_tyold_warant,\n" +
                        "ass_qstart.Assessment_idAssessment\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber IN (2, 3) AND\n" +
                        "ass_qstart.Assessment_idAssessment = " + idAssessment);

                double a = 0;
                double w = 0;

                while (data1.next()) {
                    int ass_qstart_quaterNumber = data1.getInt("ass_Qstart_QuaterNumber");
                    a += data1.getDouble("ass_Qstart_LQC_Arreas");
                    w += data1.getDouble("ass_Qstart_LQC_Warrant");

                    a = round(a);
                    w = round(w);

                    if (ass_qstart_quaterNumber == 3) {
                        int idass_qstart = data1.getInt("idass_Qstart");
                        System.out.println("   " + idass_qstart + "  ===  " + a + "  " + w);
                        conn.DB.setData("UPDATE `ass_qstart` SET  `ass_Qstart_tyold_arrias` = '" + a + "', `ass_Qstart_tyold_warant` = '" + w + "' WHERE \t`idass_Qstart` = " + idass_qstart);
                    }
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
