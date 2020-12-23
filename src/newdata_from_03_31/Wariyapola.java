package newdata_from_03_31;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-07-08.
 */
public class Wariyapola {
    public static void main(String[] args) {
        getData();
    }


    public static void getData() {
        try {

            ResultSet data = DB.getData("SELECT all_data_sheet.idAssessment,all_data_sheet.Allocation,all_data_sheet.quarter_value,all_data_sheet.balance0331 FROM all_data_sheet");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double quarter_value = data.getDouble("quarter_value");
                double balance0331 = data.getDouble("balance0331");

                System.out.println(data.getInt("idAssessment"));

                if (balance0331 > 0) {
                    System.out.println("++++++++++++++ " + idAssessment);
                    insertQstart(idAssessment, balance0331, quarter_value, 0, quarter_value, 1, 0, 0, 0, 0, 0, 0, 0, 0);

                } else if (balance0331 == 0) {
                    System.out.println("00000000000000 " + idAssessment);

                    insertQstart(idAssessment, 0, quarter_value, 0, quarter_value, 1, 0, 0, 0, 0, 0, 0, 0, 0);


                } else {

                    System.out.println("-------------- " + idAssessment);

                    double v = balance0331 * -1;
                    double bal = 0;
                    if (quarter_value * 3 <= v) {
                        System.out.println("4444444444444444444");
                        //q3 tama gewanna salli thiyanawa
                        bal = v - quarter_value * 3;
                        insertQstart(idAssessment, 0, 0, quarter_value, quarter_value, 1, 1, 1, 1, 0, quarter_value, quarter_value, quarter_value, bal);
                    } else if (quarter_value * 2 <= v) {
                        System.out.println("3333333333333333333");
                        //q2 katama thiyanawa
                        bal = v - quarter_value * 2;
                        insertQstart(idAssessment, 0, 0, quarter_value, quarter_value, 1, 1, 1, 0, 0, quarter_value, quarter_value, bal, 0);
                    } else if (quarter_value <= v) {
                        System.out.println("2222222222222222222");
                        // quate 1 kata thiyanawa
                        bal = v - quarter_value;
                        insertQstart(idAssessment, 0, 0, quarter_value, quarter_value, 1, 1, 0, 0, 0, quarter_value, bal, 0, 0);
                    } else {
                        System.out.println("madi madi madi");
                        //quater 1 kata madi
                        bal = v;
                        insertQstart(idAssessment, 0, 0, quarter_value, quarter_value, 1, 0, 0, 0, 0, bal, 0, 0, 0);

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

    public static void insertQstart(
            int idass,
            double lqa, double havetopay, double tqpay, double quvalue,
            int q1s, int q2s, int q3s, int q4s,
            double q1p, double q2p, double q3p, double q4p,
            double over
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
                "\t\t2,\n" +
                "\t\t'2020-04-01',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
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
                "\t\t2,\n" +
                "\t\t2020,\n" +
                "\t\t'2019-04-01',\n" +
                "\t\t1,\n" +
                "\t\t'manual process',\n" +
                "\t\t'0',\n" +
                "\t\t'" + round(q1p) + "',\n" +
                "\t\t'" + round(q2p) + "',\n" +
                "\t\t'" + round(q3p) + "',\n" +
                "\t\t'" + round(q4p) + "',\n" +
                "\t\t'" + round(over) + "',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
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
