package wennappuwa;

import conn.DB;

import java.sql.ResultSet;

public class Porcess {

    public static void main(String[] args) {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "\tassessment.idAssessment,\n" +
                    "\tall_data_sheet.balance0931, quarter_value,\n" +
                    "\tass_allocation.ass_allocation,\n" +
                    "\tass_nature.ass_nature_year_rate,\n" +
                    "\tass_nature_warrant_rate,\n" +
                    "\tpaid_quater,\n" +
                    "\tround( ass_allocation.ass_allocation * ass_nature.ass_nature_year_rate / 400, 2 ) AS qvalue \n" +
                    "FROM\n" +
                    "\tassessment\n" +
                    "\tLEFT JOIN all_data_sheet ON assessment.idAssessment = all_data_sheet.idAssessment\n" +
                    "\tINNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                    "\tINNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature \n" +
                    "WHERE\n" +
                    "\tass_allocation.ass_allocation_status = 1");

            int x = 0;
            int y = 0;

            while (data.next()) {
                x++;
                // System.out.println(x);
                int idAssessment = data.getInt("idAssessment");
                double b6 = data.getDouble("all_data_sheet.balance0931");


              //  double allocation = data.getDouble("Allocation");
                double quarter_value = data.getDouble("quarter_value");

                // double paha = quarter_value * 5 / 100;
                //   double pahaadu = quarter_value - quarter_value;

                //  double dahaya = quarter_value * 10 / 100;
                //   double dahayaadu = quarter_value - dahaya;

                int paid_quater = data.getInt("paid_quater");

                if (b6 < 0) {
                    double over = b6 * -1;

                    double next = 0;
                    //         if (paid_quater == 4) {
                    next = over - quarter_value;
                    insertQstart(0, round(quarter_value), 0, idAssessment, round(quarter_value), 1, next);
                    //              }

//                    else {
//                        insertQstart(b6, round(quarter_value), round(quarter_value), idAssessment, 0, 0, 0);
//                    }

                } else {
                    //attiars
                    insertQstart(round(b6), round(quarter_value), round(quarter_value), idAssessment, 0, 0, 0);

                }


            }
            System.out.println("YYYYYY   =" + y);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static void insertQstart(double lqa, double quvalue, double haveto, int idass,

                                    double q4pay, int q4s, double over


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
                "\t`process_update_comment`\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t4,\n" +
                "\t\t'2019-10-01',\n" +
                "\t\t'0',\n" +
                "\t\t0,\n" +
                "\t\t'0',\n" +
                "\t\t0,\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t'" + round(haveto) + "',\n" +
                "\t\t'0',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t1,\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t2019,\n" +
                "\t\t0,\n" +
                "\t\t '" + round(lqa) + "',\n" +
                "\t\t'manual process'\n" +
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
                "\t\t4,\n" +
                "\t\t2019,\n" +
                "\t\t'2019-10-01',\n" +
                "\t\t1,\n" +
                "\t\t'manual process',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'" + over + "',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'" + q4pay + "',\n" +
                "\t\t1,\n" +
                "\t\t1,\n" +
                "\t\t'1',\n" +
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
