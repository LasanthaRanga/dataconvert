package kakapalliya;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-06-26.
 */
public class OldFix {
    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance0931,\n" +
                    "all_data_sheet.paid_quater\n" +
                    "FROM\n" +
                    "all_data_sheet\n" +
                    "WHERE\n" +
                    "all_data_sheet.paid_quater = 3 AND\n" +
                    "all_data_sheet.balance0931 <> 0");
            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double balance0931 = -1 * data.getDouble("balance0931");
                double quarter_value = data.getDouble("quarter_value");
                double v = quarter_value - balance0931;
                System.out.println(idAssessment + "  =  " + balance0931);

                DB.setData("UPDATE `ass_payhistry` \n" +
                        "SET \n" +
                        "`ass_PayHistry_Q4` = '" + balance0931 + "',\n" +
                        "`ass_PayHistry_Over` = 0,\n" +
                        "`ass_PayHistry_DRQ4` = 0,\n" +
                        "`ass_PayHistry_Q4Status` = 0 \n" +
                        "WHERE\n" +
                        "\t`Assessment_idAssessment` = " + idAssessment);

                DB.setData("UPDATE `ass_qstart` SET `ass_Qstart_HaveToQPay` = '" + v + "' WHERE\t`Assessment_idAssessment` = " + idAssessment);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
