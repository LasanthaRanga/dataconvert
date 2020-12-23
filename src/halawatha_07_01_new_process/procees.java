package halawatha_07_01_new_process;

import conn.DB;


import java.sql.ResultSet;

public class procees {


    public static void main(String[] args) {
        load();
    }

    public static void load() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance1231,\n" +
                    "all_data_sheet.balance0331,\n" +
                    "all_data_sheet.balance0631,\n" +
                    "all_data_sheet.q1,\n" +
                    "all_data_sheet.q2,\n" +
                    "all_data_sheet.q3,\n" +
                    "all_data_sheet.q4\n" +
                    "FROM `all_data_sheet`");

            int x = 0;

            while (data.next()) {
                x++;
                System.out.println(x);
                int idAssessment = data.getInt("idAssessment");
                double b6 = data.getDouble("balance0631");
                // double b12 = data.getDouble("balance1231");
                double quarter_value = data.getDouble("quarter_value");
                double allocation = data.getDouble("Allocation");
                int q1 = data.getInt("q1");
                int q2 = data.getInt("q2");
                int q3 = data.getInt("q3");
                int q4 = data.getInt("q4");

                if (b6 < 0) {
                    b6 = b6*-1;
                    insertQstart(0,quarter_value,idAssessment,q3,q4,0,b6);
                } else {
                    insertQstart(b6,quarter_value,idAssessment,q3,q4,0,0);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static void insertQstart(double lqa, double quvalue, int idass,

                                    int q3s, int q4s, double lya, double over


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
                "\t\t3,\n" +
                "\t\t'2019-07-01',\n" +
                "\t\t'" + round(lya) + "',\n" +
                "\t\t0,\n" +
                "\t\t'" + round(lya) + "',\n" +
                "\t\t0,\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t'" + round(quvalue) + "',\n" +
                "\t\t'0',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t1,\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t2019,\n" +
                "\t\tNULL,\n" +
                "\t\tNULL,\n" +
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
                "\t\t3,\n" +
                "\t\t2019,\n" +
                "\t\t'2019-07-01',\n" +
                "\t\t1,\n" +
                "\t\t'manual process',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'"+over+"',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t'0',\n" +
                "\t\t1,\n" +
                "\t\t1,\n" +
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
