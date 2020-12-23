package naththandiya;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-11-10.
 */
public class ErrorFix {

    public static void main(String[] args) {
        getData();
    }


    public static void getData() {

        String query = "SELECT\n" +
                "all_data_sheet.idAssessment,\n" +
                "all_data_sheet.Allocation,\n" +
                "all_data_sheet.quarter_value,\n" +
                "all_data_sheet.balance1231,\n" +
                "all_data_sheet.balance0331,\n" +
                "all_data_sheet.balance0631\n" +
                "FROM\n" +
                "all_data_sheet\n" +
                "WHERE\n" +
                "all_data_sheet.balance0631 < 0 AND\n" +
                "all_data_sheet.balance0631 *-1 < all_data_sheet.quarter_value";

        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {

                int idAssessment = data.getInt("idAssessment");

                double balance0631 = data.getDouble("balance0631");
                balance0631 = balance0631 * -1;

                double quarter_value = data.getDouble("quarter_value");
                double v = quarter_value - balance0631;
                v = round(v);

                //  System.out.println(data.getInt("idAssessment") + "  --   " + v);


                ResultSet data1 = DB.getData("SELECT\n" +
                        "assessment.idAssessment,\n" +
                        "ass_nature.ass_nature_warrant_rate\n" +
                        "FROM\n" +
                        "assessment\n" +
                        "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                        "WHERE\n" +
                        "assessment.idAssessment = '" + idAssessment + "'");

                if (data1.last()) {
                    double ass_nature_warrant_rate = data1.getDouble("ass_nature_warrant_rate");
                    double warant = quarter_value * ass_nature_warrant_rate / 100;
                    warant = round(warant);
                    System.out.println(data.getInt("idAssessment") + "  --   " + quarter_value + "   --   " + v + "   --   " + ass_nature_warrant_rate + "  --   " + warant);

//                    conn.DB.setData("UPDATE `ass_qstart` " +
//                            "  SET `ass_Qstart_LQ_Warrant` = '0'," +
//                            "`ass_Qstart_LQC_Warrant` = '0', " +
//                            "`ass_Qstart_tyold_warant` = '0' " +
//                            " WHERE\n" +
//                            "\t`ass_Qstart_QuaterNumber` = 4 \n" +
//                            "\tAND `ass_Qstart_process_date` = '2020-10-01' \n" +
//                            "\tAND `Assessment_idAssessment` = '" + idAssessment + "'");

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
