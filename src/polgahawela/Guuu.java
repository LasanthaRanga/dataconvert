package polgahawela;

import conn.DB2;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-09-24.
 */
public class Guuu {

    public static void main(String[] args) {
        try {
            ResultSet data = DB2.getData("SELECT\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                    "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                    "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                    "ass_qstart.ass_Qstart_QPay,\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                    "ass_qstart.ass_Qstart_tyold_warant\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_QuaterNumber = 2 AND\n" +
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.Assessment_idAssessment IN ((SELECT\n" +
                    "error_fix.assid\n" +
                    "FROM\n" +
                    "error_fix\n" +
                    "WHERE\n" +
                    "error_fix.`comment` LIKE '%-%'\n" +
                    "ORDER BY\n" +
                    "error_fix.assid ASC))\n" +
                    "GROUP BY\n" +
                    "ass_qstart.idass_Qstart\n");
            while (data.next()) {
                int idass_qstart = data.getInt("idass_Qstart");
                double ass_qstart_lq_arreas = data.getDouble("ass_Qstart_LQ_Arreas");
                double ass_qstart_haveToQPay = data.getDouble("ass_Qstart_HaveToQPay");
                double ass_qstart_qPay = data.getDouble("ass_Qstart_QPay");
                double ass_qstart_tyold_arrias = data.getDouble("ass_Qstart_tyold_arrias");


//                conn.DB.setData("UPDATE `ass_qstart` \n" +
//                        "SET `ass_Qstart_LQ_Arreas` = " + ass_qstart_lq_arreas + ",\n" +
//                        "`ass_Qstart_LQC_Arreas` = " + ass_qstart_lq_arreas + ",\n" +
//                        "`ass_Qstart_HaveToQPay` = " + ass_qstart_haveToQPay + ",\n" +
//                        "`ass_Qstart_QPay` = " + ass_qstart_qPay + ",\n" +
//                        "`ass_Qstart_tyold_arrias` = " + ass_qstart_tyold_arrias + "\n" +
//                        "WHERE\n" +
//                        "\t `idass_Qstart` = " + idass_qstart);


                System.out.println();
                System.out.println(idass_qstart + "  -  " + ass_qstart_lq_arreas + "  -  " + ass_qstart_haveToQPay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
