package kuliuc;

import conn.DB;

import java.sql.ResultSet;

public class KuliUC {

    public static void main(String[] args) {
        Process();
    }


    public static void Process() {

        try {


            ResultSet data = DB.getData("SELECT\n" +
                    "assessment.idAssessment,\n" +
                    " all_data_sheet.balance1231, all_data_sheet.balance0631,\n" +
                    "ass_allocation.ass_allocation,\n" +
                    "ass_nature.ass_nature_year_rate, ass_nature_warrant_rate, \n" +
                    "round(ass_allocation.ass_allocation* ass_nature.ass_nature_year_rate/400,2) as qvalue\n" +
                    "FROM\n" +
                    "assessment\n" +
                    "LEFT JOIN all_data_sheet ON assessment.idAssessment = all_data_sheet.idAssessment\n" +
                    "INNER JOIN ass_allocation ON ass_allocation.Assessment_idAssessment = assessment.idAssessment\n" +
                    "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "WHERE\n" +
                    "ass_allocation.ass_allocation_status = 1");


            int x = 0;
            int y = 0;


            while (data.next()) {
                x++;
                int idAssessment = 0;
                double balance = 0;
                double allocation = 0;
                double yr = 0;
                double qvalue = 0;


                idAssessment = data.getInt("idAssessment");
                balance = data.getDouble("balance1231");
                allocation = data.getDouble("ass_allocation");
                yr = data.getDouble("ass_nature_year_rate");
                qvalue = round(allocation * yr / 400);
                double warrant_rate = data.getDouble("ass_nature_warrant_rate");

//                System.out.println(idAssessment);
//                System.out.println(balance);
//                System.out.println(allocation);
//                System.out.println(yr);
//                System.out.println(qvalue);


                if (balance <= 0) {
                    System.out.println("                =====================   OVER   " + balance);

                    payHistry(idAssessment, 1, 2020, "2020-01-01", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    saveQstart(1, "2020-01-01", idAssessment, 2020, 0, 0, 0, 0, qvalue, 0, 0, 0, 0, 0, -1 * (balance), "Year End Process", 0, 0);

                } else {
                    System.out.println("                =====================                          Arriars" + balance);

                    double warrant = qvalue * warrant_rate / 100;
                    warrant = round(warrant);


                    payHistry(idAssessment, 1, 2020, "2020-01-01", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    saveQstart(1, "2020-01-01", idAssessment, 2020, balance, warrant, 0, 0, qvalue, 0, 0, 0, 0, 0, 0, "Year End Process", 0, 0);


                }


            }
            System.out.println(" ==============   " + x);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }


    public static void payHistry(int idAssessment, int qn, int curentYear, String day, double tot,
                                 double q1p, double q2p, double q3p, double q4p, double over,
                                 double q1dr, double q2dr, double q3dr, double q4dr,
                                 int q1s, int q2s, int q3s, int q4s) {
        String qq = "INSERT INTO `ass_payhistry` (\n" +
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
                "\t(\n" +
                "\t\t'" + qn + "',\n" +
                "\t\t'" + curentYear + "',\n" +
                "\t\t'" + day + "',\n" +
                "\t\t'1',\n" +
                "\t\t'22',\n" +
                "\t\t'" + tot + "',\n" +
                "\t\t'" + q1p + "',\n" +
                "\t\t'" + q2p + "',\n" +
                "\t\t'" + q3p + "',\n" +
                "\t\t'" + q4p + "',\n" +
                "\t\t'" + over + "',\n" +
                "\t\t'" + idAssessment + "',\n" +
                "\t\t'" + q1dr + "',\n" +
                "\t\t'" + q2dr + "',\n" +
                "\t\t'" + q3dr + "',\n" +
                "\t\t'" + q4dr + "',\n" +
                "\t\t'" + q1s + "',\n" +
                "\t\t'" + q2s + "',\n" +
                "\t\t'" + q3s + "',\n" +
                "\t\t'" + q4s + "'\n" +
                "\t)\n" +
                "\n";

        try {
            conn.DB.setData(qq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveQstart(
            int qno,
            String pdate,
            int idAssessment, int year,
            double lya, double lyw,
            double lqa, double lqw,
            double hvpay, double pay,
            double discount, double qtot, double fulltot,
            double upwarant, double uparriars,
            String com,
            double qendtotA, double qendtotW) {

        String q = "INSERT INTO `ass_qstart` (\n" +
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
                "\t`process_update_comment`,\n" +
                "\t`ass_Qstart_tyold_arrias`,\n" +
                "\t`ass_Qstart_tyold_warant` \n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n"
                + qno + ",\n " +
                "'" + pdate + "',\n" +
                "\t\t" + lya + ",\n" +
                "\t\t" + lyw + ",\n" +
                "\t\t" + lya + ",\n" +
                "\t\t" + lyw + ",\n" +
                "\t\t" + lqa + ",\n" +
                "\t\t" + lqa + ",\n" +
                "\t\t" + lqw + ",\n" +
                "\t\t" + lqw + ",\n" +
                "\t\t" + hvpay + ",\n" +
                "\t\t" + pay + ",\n" +
                "\t\t" + discount + ",\n" +
                "\t\t" + qtot + ",\n" +
                "\t\t" + fulltot + ",\n" +
                "\t\t1,\n" +
                "\t\t" + idAssessment + ",\n" +
                "\t\t" + year + ",\n" +
                "\t\tNULL,\n" +
                "\t\t" + uparriars + ",\n" +
                "\t\t'" + com + "',\n" +
                "\t\t" + qendtotA + ",\n" +
                "\t\t" + qendtotW + " \n" +
                "\t)";

        try {
            conn.DB.setData(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
