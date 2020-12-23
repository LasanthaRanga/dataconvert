package panduwasnuwara;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-27.
 */
public class Newdata {
    public static void main(String[] args) {
       // getAllData();
    }

    public static void getAllData() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.`Q1 PAY`,\n" +
                    "all_data_sheet.`Q1 Discount`,\n" +
                    "all_data_sheet.`Q2 PAY`,\n" +
                    "all_data_sheet.`Q2 Discount`,\n" +
                    "all_data_sheet.`Q3 PAY`,\n" +
                    "all_data_sheet.`Q3 Discount`,\n" +
                    "all_data_sheet.`Q4 PAY`,\n" +
                    "all_data_sheet.`Q4 Discount`,\n" +
                    "all_data_sheet.`Next Over Payment`,\n" +
                    "all_data_sheet.`2020-6-30 Arreas`,\n" +
                    "all_data_sheet.`2019-12-31 Arrears`\n" +
                    "FROM\n" +
                    "all_data_sheet");
            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double quarter_value = data.getDouble("quarter_value");
                double q1paid = data.getDouble("Q1 PAY");
                double q2paid = data.getDouble("Q2 PAY");
                double q3paid = data.getDouble("Q3 PAY");
                double q4paid = data.getDouble("Q4 PAY");
                double a1d = data.getDouble("Q1 Discount");
                double a2d = data.getDouble("Q2 Discount");
                double a3d = data.getDouble("Q3 Discount");
                double a4d = data.getDouble("Q4 Discount");
                double over = data.getDouble("Next Over Payment");
                double arrears = data.getDouble("2020-6-30 Arreas");
                System.out.println(idAssessment);

                if (arrears <= 0) {
                    //over
                    over = over * -1;
                    if (q4paid > 0 && q3paid > 0) {
                        insertQstart(idAssessment, 0, quarter_value, q3paid, quarter_value, 1, 1, 1, 1, q1paid, q2paid, q3paid, q4paid, over, 0, a4d);
                    } else if (q3paid > 0) {
                        insertQstart(idAssessment, 0, quarter_value, q3paid, quarter_value, 1, 1, 1, 0, q1paid, q2paid, q3paid, q4paid, over, 0, a4d);
                    } else {
                        insertQstart(idAssessment, 0, quarter_value, q3paid, quarter_value, 1, 1, 0, 0, q1paid, q2paid, q3paid, q4paid, over, 0, a4d);
                    }
                } else {
                    //arrears
                    insertQstart(idAssessment, arrears, quarter_value, 0, quarter_value, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static void insertQstart(
            int idass,
            double lqa, double havetopay, double tqpay, double quvalue,
            int q1s, int q2s, int q3s, int q4s,
            double q1p, double q2p, double q3p, double q4p,
            double over, double lqw, double q1d
    ) {
        String query = "INSERT INTO `ass_qstart` (\n" +
                "\t`ass_Qstart_QuaterNumber`,\n" +
                "\t`ass_Qstart_process_date`,\n" +
                "\t`ass_Qstart_LY_Arreas`,\n" +
                "\t`ass_Qstart_LY_Warrant`,\n" +
                "\t`ass_Qstart_LYC_Arreas`,\n" +
                "\t`ass_Qstart_LYC_Warrant`,\n" +
                "\t`ass_Qstart_LQ_Arreas`,\n" +
                "\t`ass_Qstart_LQC_Arreas`,\n" +
                "\t`ass_Qstart_LQ_Warrant`,\n" +
                "\t`ass_Qstart_LQC_Warrant`,\n" +
                "\t`ass_Qstart_HaveToQPay`,\n" +
                "\t`ass_Qstart_QPay`,\n" +
                "\t`ass_Qstart_QDiscont`,\n" +
                "\t`ass_Qstart_QTot`,\n" +
                "\t`ass_Qstart_FullTotal`,\n" +
                "\t`ass_Qstart_status`,\n" +
                "\t`Assessment_idAssessment`,\n" +
                "\t`ass_Qstart_year`,\n" +
                "\t`process_update_warant`,\n" +
                "\t`process_update_arrears`,\n" +
                "\t`process_update_comment`, ass_Qstart_tyold_arrias, ass_Qstart_tyold_warant\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t3,\n" +
                "\t\t'2020-07-01',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqw) + "',\n" +
                "\t\t'" + round(lqw) + "',\n" +
                "\t\t'" + round(havetopay) + "',\n" +
                "\t\t'" + round(tqpay) + "',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'1',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t2020,\n" +
                "\t\tNULL,\n" +
                "\t\tNULL,\n" +
                "\t\t'manual process','" + round(lqa) + "','0'\n" +
                "\t)";

        String qu = "INSERT INTO `ass_payhistry` (\t\n" +
                "\t`ass_PayHistry_Qcunt`,\n" +
                "\t`ass_PayHistry_year`,\n" +
                "\t`ass_PayHistry_Date`,\n" +
                "\t`ass_PayHistry_status`,\n" +
                "\t`ass_PayHistry_comment`,\n" +
                "\t`ass_PayHistry_TotalPayid`,\n" +
                "\t`ass_PayHistry_Q1`,\n" +
                "\t`ass_PayHistry_Q2`,\n" +
                "\t`ass_PayHistry_Q3`,\n" +
                "\t`ass_PayHistry_Q4`,\n" +
                "\t`ass_PayHistry_Over`,\n" +
                "\t`Assessment_idAssessment`,\n" +
                "\t`ass_PayHistry_DRQ1`,\n" +
                "\t`ass_PayHistry_DRQ2`,\n" +
                "\t`ass_PayHistry_DRQ3`,\n" +
                "\t`ass_PayHistry_DRQ4`,\n" +
                "\t`ass_PayHistry_Q1Status`,\n" +
                "\t`ass_PayHistry_Q2Status`,\n" +
                "\t`ass_PayHistry_Q3Status`,\n" +
                "\t`ass_PayHistry_Q4Status`\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\t\n" +
                "\t\t3,\n" +
                "\t\t2020,\n" +
                "\t\t'2020-07-01',\n" +
                "\t\t1,\n" +
                "\t\t'manual process',\n" +
                "\t\t'0',\n" +
                "\t\t'" + round(q1p) + "',\n" +
                "\t\t'" + round(q2p) + "',\n" +
                "\t\t'" + round(q3p) + "',\n" +
                "\t\t'" + round(q4p) + "',\n" +
                "\t\t'" + round(over) + "',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t'" + q1d + "',\n" +
                "\t\t'" + q1d + "',\n" +
                "\t\t'" + q1d + "',\n" +
                "\t\t'" + q1d + "',\n" +
                "\t\t'" + q1s + "',\n" +
                "\t\t'" + q2s + "',\n" +
                "\t\t'" + q3s + "',\n" +
                "\t\t'" + q4s + "'\n" +
                "\t)";
        try {
            conn.DB.setData(query);
            conn.DB.setData(qu);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


}
