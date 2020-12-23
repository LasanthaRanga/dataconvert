package TotPaidStillArriars;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2020-04-21.
 */
public class fix {
    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_payhistry.Assessment_idAssessment,\n" +
                    "ass_payhistry.ass_PayHistry_Over,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias\n" +
                    "FROM\n" +
                    "ass_payhistry\n" +
                    "INNER JOIN ass_qstart ON ass_qstart.Assessment_idAssessment = ass_payhistry.Assessment_idAssessment\n" +
                    "WHERE\n" +
                    "ass_payhistry.ass_PayHistry_year = 2020 AND\n" +
                    "ass_payhistry.ass_PayHistry_Over > 0 AND\n" +
                    "(ass_qstart.ass_Qstart_LQC_Arreas > 0 OR\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias > 0) AND\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_qstart.ass_Qstart_year = 2020\n" +
                    "GROUP BY\n" +
                    "ass_payhistry.Assessment_idAssessment\n");

            while (data.next()) {
                int idass_qstart = data.getInt("idass_Qstart");
                System.out.println(idass_qstart);
                conn.DB.setData("UPDATE `ass_qstart` \n" +
                        "SET \n" +
                        "`ass_Qstart_LQ_Arreas` = 0,\n" +
                        "`ass_Qstart_LQC_Arreas` = 0, ass_qstart.ass_Qstart_tyold_arrias = 0 \n" +
                        "WHERE\n" +
                        "\t`idass_Qstart` = " + idass_qstart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
