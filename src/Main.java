import conn.DB;

import java.sql.ResultSet;
import java.util.zip.DeflaterOutputStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        load();
    }


    public static void load() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance1231,\n" +
                    "all_data_sheet.balance0331\n" +
                    "FROM `all_data_sheet`");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double balancd = data.getDouble("balance0331");
//                double allocation = data.getDouble("Allocation");
//                double year_value = data.getDouble("year_value");
                double quarter_value = data.getDouble("quarter_value");
                System.out.println(idAssessment);
//                double balance1231 = data.getDouble("balance1231");


                double dis10 = quarter_value * 10 / 100;
                double dis5 = quarter_value * 5 / 100;

                double bal = (balancd * -1);
                double over = 0;
                double thisyara = 0;
                double lastyar = 0;
                if (bal < 0) {

                    System.out.println("arriars");

                    if (bal * -1 > quarter_value) {
                        lastyar = (bal * -1) - quarter_value;
                        thisyara = (bal * -1) - lastyar;

                        insertQstart(thisyara, quarter_value, 0, idAssessment, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, lastyar);
                    } else {


                        insertQstart(bal * -1, quarter_value, 0, idAssessment, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    }


                } else {

                    if (bal >= (quarter_value * 3 - dis10 * 3)) {
                        double qpay = quarter_value - dis10;
                        System.out.println("===================================");
                        over = bal - qpay * 3;

                        insertQstart(0, 0, qpay, idAssessment, (qpay * 3 + over), qpay, qpay, qpay, over, 10, 10, 10, 1, 1, 1, 0);


                    } else {
                        if (bal >= quarter_value * 2 - dis5 * 2) {
                            double qpay = quarter_value = dis5;
                            over = bal - qpay * 2;
                            System.out.println("5x2");

                            insertQstart(0, 0, qpay, idAssessment, (qpay * 2 + over), qpay, qpay, over, 0, 5, 5, 0, 1, 1, 0, 0);

                        } else if (bal >= quarter_value - dis5) {
                            double qpay = quarter_value - dis5;
                            over = bal - qpay;
                            System.out.println("5x1");

                            insertQstart(0, 0, qpay, idAssessment, (qpay + over), qpay, over, 0, 0, 5, 0, 0, 1, 0, 0, 0);

                        } else if (bal > 0 && bal < quarter_value) {
                            double qpay = bal;
                            over = 0;
                            System.out.println("quater - ");
                            insertQstart(0, quarter_value - bal, bal, idAssessment, bal, bal, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                        }

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

    public static void insertQstart(double lqa, double quvalue, double qpay, int idass,
                                    double total,
                                    double q2, double q3, double q4,
                                    double over,
                                    double q2d, double q3d, double q4d,
                                    int q2s, int q3s, int q4s, double lya


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

                "\t\t2,\n" +
                "\t\t'2019-06-04',\n" +
                "\t\t'" + round(lya) + "',\n" +
                "\t\t0,\n" +
                "\t\t'" + round(lya) + "',\n" +
                "\t\t0,\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t'" + round(lqa) + "',\n" +
                "\t\t0,\n" +
                "\t\t0,\n" +
                "\t\t'" + round(quvalue) + "',\n" +
                "\t\t'" + round(qpay) + "',\n" +
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
                "\t\t2,\n" +
                "\t\t2019,\n" +
                "\t\t'2019-06-04',\n" +
                "\t\t1,\n" +
                "\t\t'manual process',\n" +
                "\t\t'" + round(total) + "',\n" +
                "\t\t'0',\n" +
                "\t\t'" + round(q2) + "',\n" +
                "\t\t'" + round(q3) + "',\n" +
                "\t\t'" + round(q4) + "',\n" +
                "\t\t'" + round(over) + "',\n" +
                "\t\t'" + idass + "',\n" +
                "\t\t0,\n" +
                "\t\t'" + q2d + "',\n" +
                "\t\t'" + q3d + "',\n" +
                "\t\t'" + q4d + "',\n" +
                "\t\t1,\n" +
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





