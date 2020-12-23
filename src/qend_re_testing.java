import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-13.
 */
public class qend_re_testing {
    public static void main(String[] args) {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_qstart_copy1\n" +
                    "WHERE\n" +
                    "ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_Qstart_year = 2020 AND\n" +
                    "ass_Qstart_status = 0");

            while (data.next()) {
                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                conn.DB.setData("DELETE FROM ass_qstart WHERE ass_Qstart_process_date = '2020-07-01' AND Assessment_idAssessment = " + assessment_idAssessment);
                conn.DB.setData("DELETE FROM ass_payhistry WHERE ass_PayHistry_Date = '2020-07-01' AND Assessment_idAssessment = " + assessment_idAssessment);
                conn.DB.setData("UPDATE `ass_qstart` SET `ass_Qstart_status`=1 WHERE `Assessment_idAssessment`= '" + assessment_idAssessment + "' AND ass_Qstart_process_date='2020-04-01'");
                System.out.println(assessment_idAssessment);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
