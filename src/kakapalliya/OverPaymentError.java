package kakapalliya;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-08-07.
 */
public class OverPaymentError {

    public static void main(String[] args) {

      //  getData();

    }


    public static void getData() {
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
                    "all_data_sheet.paid_quater = 4 AND\n" +
                    "all_data_sheet.balance0931 < 0");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                System.out.println(idAssessment);
                double balance0931 = data.getDouble("balance0931");
                double quarter_value = data.getDouble("quarter_value");

                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_qstart.idass_Qstart,\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber,\n" +
                        "ass_qstart.ass_Qstart_process_date,\n" +
                        "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LY_Warrant,\n" +
                        "ass_qstart.ass_Qstart_LYC_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LYC_Warrant,\n" +
                        "ass_qstart.ass_Qstart_LQ_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LQC_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LQ_Warrant,\n" +
                        "ass_qstart.ass_Qstart_LQC_Warrant,\n" +
                        "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                        "ass_qstart.ass_Qstart_QPay,\n" +
                        "ass_qstart.ass_Qstart_QDiscont,\n" +
                        "ass_qstart.ass_Qstart_QTot,\n" +
                        "ass_qstart.ass_Qstart_FullTotal,\n" +
                        "ass_qstart.ass_Qstart_status,\n" +
                        "ass_qstart.Assessment_idAssessment,\n" +
                        "ass_qstart.ass_Qstart_year,\n" +
                        "ass_qstart.process_update_warant,\n" +
                        "ass_qstart.process_update_arrears,\n" +
                        "ass_qstart.process_update_comment,\n" +
                        "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                        "ass_qstart.ass_Qstart_tyold_warant\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                        "ass_qstart.Assessment_idAssessment = " + idAssessment);

                if (data1.last()) {
                    int idass_qstart = data1.getInt("idass_Qstart");
                    double process_update_arrears = data1.getDouble("process_update_arrears");
                    String process_update_comment = data1.getString("process_update_comment");

                    if (process_update_comment.equals("Year Start Process Q4 10%")) {
                        ResultSet data2 = DB.getData("SELECT\n" +
                                "ass_payhistry.idass_PayHistry,\n" +
                                "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                                "ass_payhistry.ass_PayHistry_year,\n" +
                                "ass_payhistry.ass_PayHistry_Date,\n" +
                                "ass_payhistry.ass_PayHistry_status,\n" +
                                "ass_payhistry.ass_PayHistry_comment,\n" +
                                "ass_payhistry.ass_PayHistry_TotalPayid,\n" +
                                "ass_payhistry.ass_PayHistry_Q1,\n" +
                                "ass_payhistry.ass_PayHistry_Q2,\n" +
                                "ass_payhistry.ass_PayHistry_Q3,\n" +
                                "ass_payhistry.ass_PayHistry_Q4,\n" +
                                "ass_payhistry.ass_PayHistry_Over,\n" +
                                "ass_payhistry.Assessment_idAssessment,\n" +
                                "ass_payhistry.ass_PayHistry_DRQ1,\n" +
                                "ass_payhistry.ass_PayHistry_DRQ2,\n" +
                                "ass_payhistry.ass_PayHistry_DRQ3,\n" +
                                "ass_payhistry.ass_PayHistry_DRQ4,\n" +
                                "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                                "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                                "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                                "ass_payhistry.ass_PayHistry_Q4Status\n" +
                                "FROM\n" +
                                "ass_payhistry\n" +
                                "WHERE\n" +
                                "ass_payhistry.ass_PayHistry_year = 2020 AND\n" +
                                "ass_payhistry.Assessment_idAssessment = " + idAssessment);

                        if (data2.last()) {
                            int idass_payHistry = data2.getInt("idass_PayHistry");
                            double ass_payHistry_over = data2.getDouble("ass_PayHistry_Over");
                            double newOver = round(quarter_value + ass_payHistry_over);
                            conn.DB.setData("UPDATE `ass_payhistry` SET `ass_PayHistry_Over`= '" + newOver + "' WHERE `idass_PayHistry`=" + idass_payHistry);
                        }
                    } else {
                        // ============
                        double over = -1 * balance0931;
                        conn.DB.setData("UPDATE `ass_qstart` SET `ass_Qstart_HaveToQPay`='" + quarter_value + "',`process_update_arrears`='" + round(over) + "' WHERE `idass_Qstart`=" + idass_qstart);

                    }


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

}
