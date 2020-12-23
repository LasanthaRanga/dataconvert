package polgahawela;

import conn.DB;

import java.sql.ResultSet;
import java.text.DecimalFormat;

public class Fix {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_qstart.Assessment_idAssessment,\n" +
                    "ward.ward_name,\n" +
                    "street.street_name,\n" +
                    "assessment.assessment_no,\n" +
                    "ass_qstart.process_update_arrears AS overpay,\n" +
                    "ass_qstart.idass_Qstart,\n" +
                    "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                    "ass_qstart.ass_Qstart_LYC_Arreas\n" +
                    "FROM\n" +
                    "ass_qstart\n" +
                    "INNER JOIN assessment ON ass_qstart.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN street ON assessment.Street_idStreet = street.idStreet\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard AND street.Ward_idWard = ward.idWard\n" +
                    "WHERE\n" +
                    "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                    "ass_qstart.process_update_arrears > 0 AND\n" +
                    "ass_qstart.process_update_warant IS NULL ");

            int x = 0;
            while (data.next()) {
                int idAssessment = data.getInt("Assessment_idAssessment");


                double overpay = data.getDouble("overpay");
                int idass_qstart = data.getInt("idass_Qstart");

                ResultSet ald = DB.getData("SELECT\n" +
                        "all_data_sheet.idAssessment,\n" +
                        "all_data_sheet.Allocation,\n" +
                        "all_data_sheet.quarter_value,\n" +
                        "all_data_sheet.balance0631\n" +
                        "FROM\n" +
                        "all_data_sheet\n" +
                        "WHERE\n" +
                        "all_data_sheet.idAssessment = " + idAssessment);

                if (ald.last()) {

                    double quarter_value = ald.getDouble("quarter_value");
                    double q2 = quarter_value * 2;
                    double balance0631 = ald.getDouble("balance0631");

                    System.out.println(q2 + "   ==   q2");

                    double bal = -1 * (balance0631) - (q2);

                    System.out.println(bal + "    ==   bal");

                    if (bal >= 0) {
                        String balancd = roundToString(bal);

                        System.out.println(balancd);

                        System.out.println(idAssessment + "  -  " + balance0631 + "    =   " + balancd);
                        x++;

                        conn.DB.setData("UPDATE `ass_qstart` \n" +
                                "SET `process_update_arrears` = '" + balancd + "'\n" +
                                "WHERE\n" +
                                "\t`Assessment_idAssessment` = '" + idAssessment + "'\n" +
                                "\tAND ass_Qstart_year = '2020'");
                    } else {
                        conn.DB.setData("UPDATE `ass_qstart` \n" +
                                "SET `process_update_arrears` = '" + 0 + "'\n" +
                                "WHERE\n" +
                                "\t`Assessment_idAssessment` = '" + idAssessment + "'\n" +
                                "\tAND ass_Qstart_year = '2020'");

                        String s = roundToString(-1 * bal);

                        conn.DB.setData("UPDATE `ass_qstart` \n" +
                                "SET \n" +
                                "`ass_Qstart_LY_Arreas` = '" + s + "',\n" +
                                "`ass_Qstart_LYC_Arreas` = '" + s + "'\n" +
                                "WHERE\n" +
                                "\t`idass_Qstart` = " + idass_qstart);


                        String balancd = roundToString(bal);

                        System.out.println(s+"                 ======== ");
                        System.out.println(idAssessment + "  -  " + balance0631 + "    =   " + 0);

                    }


                }


            }
            System.out.println("==================" + x);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static String roundToString(double value) {
        return new DecimalFormat("0.00").format(value);
    }

}
