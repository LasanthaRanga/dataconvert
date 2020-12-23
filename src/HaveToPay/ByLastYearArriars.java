package HaveToPay;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2020-03-22.
 */
public class ByLastYearArriars {

    public static void main(String[] args) {
        String qq = "SELECT\n" +
                "ass_qstart.idass_Qstart,\n" +
                "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                "ass_qstart.ass_Qstart_LYC_Warrant,\n" +
                "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                "ass_qstart.Assessment_idAssessment,\n" +
                "assessment.idAssessment,\n" +
                "ass_allocation.ass_allocation,\n" +
                "ass_nature.ass_nature_year_rate,\n" +
                "ROUND(ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate /400,2) as qval\n" +
                "FROM\n" +
                "ass_qstart\n" +
                "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                "WHERE\n" +
                "ass_qstart.ass_Qstart_QuaterNumber = 1 AND\n" +
                "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                "ass_qstart.ass_Qstart_HaveToQPay <= 0 AND\n" +
                "(ass_qstart.ass_Qstart_QPay = 0 OR\n" +
                "ass_qstart.ass_Qstart_LQC_Arreas > 0) AND\n" +
                "ass_allocation.ass_allocation_status = 1";

        int x = 0;
        try {
            ResultSet data = DB.getData(qq);
            while (data.next()) {
                int idass_qstart = data.getInt("idass_Qstart");
                x++;
                System.out.println(idass_qstart);
                System.out.println(x);
                double qval = round(data.getDouble("qval"));
              DB.setData("UPDATE `ass_qstart` SET `ass_Qstart_HaveToQPay`= " + qval + " WHERE `idass_Qstart`=" + idass_qstart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

}
