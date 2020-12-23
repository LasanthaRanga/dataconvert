package karuwalagaswewa;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-30.
 */
public class YearEndProcessMissing {

    public static void main(String[] args) {

      //  getAllData();

    }

    public static void getAllData() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance0931\n" +
                    "FROM\n" +
                    "all_data_sheet\n" +
                    "WHERE\n" +
                    "all_data_sheet.balance0931 > 0 AND\n" +
                    "all_data_sheet.idAssessment NOT IN ((SELECT\n" +
                    "ass_payment.Assessment_idAssessment\n" +
                    "FROM\n" +
                    "ass_payment\n" +
                    "WHERE\n" +
                    "ass_payment.ass_Payment_Status = 1))");

            while (data.next()) {
                int assessment_idAssessment = data.getInt("idAssessment");
                double lya = data.getDouble("balance0931");

                System.out.println(assessment_idAssessment + "   -    " + lya);

                conn.DB.setData("UPDATE  `ass_qstart` \n" +
                        "SET \n" +
                        "`ass_Qstart_LY_Arreas` = '" + lya + "',\n" +
                        "`ass_Qstart_LYC_Arreas` = '" + lya + "'\n" +
                        "WHERE\n" +
                        "\tass_qstart.ass_Qstart_year = 2020 AND\n" +
                        "  ass_qstart.Assessment_idAssessment =" + assessment_idAssessment);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
