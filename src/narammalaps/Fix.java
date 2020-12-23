package narammalaps;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-14.
 */
public class Fix {
    public static void main(String[] args) {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.balance1231,\n" +
                    "all_data_sheet.balance0331,\n" +
                    "all_data_sheet.balance0631,\n" +
                    "all_data_sheet.balance0931\n" +
                    "FROM\n" +
                    "all_data_sheet\n");

            int paid = 0;
            int no = 0;
            int x1 = 0;
            int x2 = 0;
            int x3 = 0;
            int x4 = 0;
            int x = 0;

            while (data.next()) {

                int idAssessment = data.getInt("idAssessment");
                double quarter_value = data.getDouble("quarter_value");
                double balance0931 = data.getDouble("balance0931");

                if (balance0931 < 0) {

                    double v = balance0931 * -1;
                    if (v - quarter_value > 0) {


                        ResultSet data1 = DB.getData("SELECT\n" +
                                "ass_payment.idass_Payment,\n" +
                                "ass_payment.Assessment_idAssessment\n" +
                                "FROM\n" +
                                "ass_payment\n" +
                                "WHERE\n" +
                                "ass_payment.ass_Payment_Status = 1 AND\n" +
                                "ass_payment.Assessment_idAssessment = " + idAssessment);

                        if (data1.last()) {

                            //  System.out.println("----- Paid" + idAssessment);
                            paid++;


                            ResultSet data2 = DB.getData("SELECT\n" +
                                    "ass_payhistry.Assessment_idAssessment,\n" +
                                    "ass_payhistry.ass_PayHistry_year,\n" +
                                    "Sum(ass_payhistry.ass_PayHistry_Over) as over\n" +
                                    "FROM\n" +
                                    "ass_payhistry\n" +
                                    "WHERE\n" +
                                    "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND \n" +
                                    "ass_payhistry.ass_PayHistry_year = 2020\n" +
                                    "GROUP BY\n" +
                                    "ass_payhistry.Assessment_idAssessment,\n" +
                                    "ass_payhistry.ass_PayHistry_year \n");


                            ResultSet data3 = DB.getData("SELECT\n" +
                                    "ass_payhistry.Assessment_idAssessment,\n" +
                                    "ass_payhistry.ass_PayHistry_year,\n" +
                                    "Sum(ass_payhistry.ass_PayHistry_Over) as over\n" +
                                    "FROM\n" +
                                    "ass_payhistry\n" +
                                    "WHERE\n" +
                                    "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                                    "ass_payhistry.ass_PayHistry_year = 2019\n" +
                                    "GROUP BY\n" +
                                    "ass_payhistry.Assessment_idAssessment,\n" +
                                    "ass_payhistry.ass_PayHistry_year\n");


                            double overPaid = 0;
                            double overReal = v - quarter_value;

                            if (data3.last()) {
                                overPaid = data3.getDouble("over");
                            }

                            double nextOverReal = overPaid - overReal;

                            if (data2.last()) {
                                double over = data2.getDouble("over");

                                if (over > 0) {
                                    System.out.println("------------------------------  " + over + " -- " + idAssessment);
                                    System.out.println("" + idAssessment + "    " + overPaid + "   -----  " + overReal + "  ------------------ =" + nextOverReal);

                                    if (over >= nextOverReal) {
                                        //over payment eken adu karala shep karanna puluwan
                                        System.out.println("ADU");
                                        double newOver = over - nextOverReal;
                                        zeroOverAndSetNew(idAssessment, round(newOver));

                                    } else {
                                        //debit karanna wenawa over payment eka 0 karanawa
                                        double debit_value = nextOverReal - over;
                                        zeroOverAndSetNew(idAssessment, 0);

                                        System.out.println("DEBIT  " + debit_value);
                                        debit(idAssessment, round(debit_value));
                                    }


                                } else {
                                    System.out.println("0000000  " + idAssessment + "    " + overPaid + "   -----  " + overReal + "  ------------------ =" + nextOverReal);
                                    debit(idAssessment, round(nextOverReal));
                                }
                            }


                            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                                    "`ass_PayHistry_Over` = '" + overReal + "' \n" +
                                    "WHERE\n" +

                                    "\t `ass_PayHistry_year` = 2019 \n" +
                                    "\tAND `Assessment_idAssessment` = " + idAssessment);


                        } else {


                            no++;
                            //   System.out.println("----- Not Paid == ass id :" + idAssessment);


                            if (v >= quarter_value * 5) {
                                System.out.println("------------------------------------------------------------- X4  " + idAssessment);
                                double d10 = quarter_value - (quarter_value * 10 / 100);

                                double bal = v - (quarter_value + (d10 * 4));

                                graterForeThreeQuaterPaid(idAssessment, round(v - quarter_value), round(bal));

                                x4++;
                            } else if (v >= quarter_value * 4) {
                                System.out.println("------------------------------------------------------------- X3  " + idAssessment);

                                double d5 = quarter_value - (quarter_value * 5 / 100);
                                System.out.println(d5);

                                double bal = v - (quarter_value + (d5 * 3));
                                System.out.println(bal);

                                graterThanThreeQuaterPaid(idAssessment, round(v - quarter_value), round(bal), 0, round(quarter_value));


                                x3++;
                            } else if (v >= quarter_value * 3) {
                                System.out.println(idAssessment + " - - " + balance0931 + "  -   " + (v - quarter_value));
                                System.out.println("------------------------------------------------------------- X2  " + idAssessment);

                                double d5 = quarter_value - (quarter_value * 5 / 100);
                                System.out.println(d5);

                                double bal = v - (quarter_value + (d5 * 2));
                                System.out.println(bal);

                                double arriars = (quarter_value - bal);
                                System.out.println(arriars);

                                graterThanTwoQuaterPaid(idAssessment, round(v - quarter_value), round(bal), round(arriars), round(quarter_value));

                                x2++;
                            } else if (v >= quarter_value * 2) {
                                System.out.println(idAssessment + " - - " + balance0931 + "  -   " + (v - quarter_value));
                                System.out.println("------------------------------------------------------------- X3  " + idAssessment);

                                double d5 = quarter_value - (quarter_value * 5 / 100);
                                System.out.println(d5);

                                double bal = v - (quarter_value + d5);
                                System.out.println(bal);

                                double arriars = (quarter_value - bal);
                                System.out.println(arriars);

                                graterThanOneQuaterPaid(idAssessment, round(v - quarter_value), round(bal), round(arriars), round(quarter_value));

                                x1++;
                            } else {
                                double bal = v - quarter_value;
                                double arriars = (quarter_value - bal);

                                lesThanOneQuaterPaid(idAssessment, round(v - quarter_value), round(arriars), round(quarter_value));

                                System.out.println("------------------------------------------------------------- x  " + idAssessment + "  --   " + arriars);
                                x++;
                            }
                        }


                    }

                }


                //

            }


            System.out.println("apid = " + paid + "     -- not paid =" + no + " ====== X1" + x1);
            System.out.println("X1  = " + x1);
            System.out.println("X2  = " + x2);
            System.out.println("X3  = " + x3);
            System.out.println("X4  = " + x4);
            System.out.println("X  = " + x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }


    public static void debit(int idA, double amount) {
        try {

            conn.DB.setData("INSERT INTO  `ass_creditdebit` (  `Ass_CreditDebit_discription`, `Ass_CreditDebit_cd`, `Ass_CreditDebit_amount`, `Ass_balance`, `Ass_CreditDebit_date`, `Assessment_idAssessment`, `Ass_CreditDebit_status`, `user_id` )\n" +
                    "VALUES\t( 'fix', - 1, '" + amount + "', '" + amount + "', '2020-12-20', '" + idA + "', 1, 1 )");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zeroOverAndSetNew(int idA, double newOver) {

        try {
            conn.DB.setData("UPDATE  `ass_payhistry` \n" +
                    "SET \n" +
                    "`ass_PayHistry_Over` = 0\n" +
                    "WHERE\n" +
                    "`ass_PayHistry_year` = 2020 and `Assessment_idAssessment` = " + idA);


            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_Over` ='" + newOver + "' \n" +
                    "WHERE\n" +
                    "\t`ass_PayHistry_year` = 2020 \n" +
                    "\tAND `Assessment_idAssessment` = '" + idA + "' \n" +
                    "\tAND `ass_PayHistry_Qcunt` = 4 \n" +
                    "\tAND `ass_PayHistry_comment` = '22'");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void lesThanOneQuaterPaid(int idA, double over, double arrears, double qv) {

        try {
            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_Over` = '" + over + "' \n" +
                    "WHERE\n" +

                    "\t `ass_PayHistry_year` = 2019 \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET \n" +
                    "`ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_TotalPayid` = '" + over + "',\n" +
                    "`ass_PayHistry_Q1` = '" + over + "'\n" +
                    "WHERE\n" +
                    " `Assessment_idAssessment` = '" + idA + "' and `ass_PayHistry_Date` = '2020-01-31'");

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET \n" +
                    "`process_update_warant` = '" + (over * -1) + "'\n" +
                    "WHERE\n" +
                    "`Assessment_idAssessment` = '" + idA + "' and `ass_Qstart_process_date` = '2020-01-01'");

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_LQ_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_LQC_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_HaveToQPay` = '" + arrears + "',\n" +
                    "`ass_Qstart_tyold_arrias` = '" + arrears + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_process_date` = '2020-04-01' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            double q2 = arrears;
            double q3 = arrears + qv;
            double q4 = arrears + (qv * 2);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + q2 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '2' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + q3 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '3' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + q4 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '4' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void graterThanOneQuaterPaid(int idA, double over, double q2, double arrears, double qv) {
        try {
            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_Over` = '" + over + "' \n" +
                    "WHERE\n" +

                    "\t `ass_PayHistry_year` = 2019 \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_Q2` = '" + q2 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_PayHistry_Date` = '2020-01-31' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET \n" +
                    "`process_update_warant` = '" + (over * -1) + "'\n" +
                    "WHERE\n" +
                    "`Assessment_idAssessment` = '" + idA + "' and `ass_Qstart_process_date` = '2020-01-01'");

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_LQ_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_LQC_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_HaveToQPay` = '" + arrears + "',\n" +
                    "`ass_Qstart_tyold_arrias` = '" + arrears + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_process_date` = '2020-07-01' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            double q3 = arrears;
            double q4 = arrears + qv;

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + round(q3) + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '3' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_tyold_arrias` = '" + round(q4) + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_QuaterNumber` = '4' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void graterThanTwoQuaterPaid(int idA, double over, double q3, double arrears, double qv) {

        try {
            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_Over` = '" + over + "' \n" +
                    "WHERE\n" +

                    "\t `ass_PayHistry_year` = 2019 \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_Q3` = '" + q3 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_PayHistry_Date` = '2020-01-31' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET \n" +
                    "`process_update_warant` = '" + (over * -1) + "'\n" +
                    "WHERE\n" +
                    "`Assessment_idAssessment` = '" + idA + "' and `ass_Qstart_process_date` = '2020-01-01'");

            conn.DB.setData("UPDATE `ass_qstart` \n" +
                    "SET `ass_Qstart_LQ_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_LQC_Arreas` = '" + arrears + "',\n" +
                    "`ass_Qstart_HaveToQPay` = '" + arrears + "',\n" +
                    "`ass_Qstart_tyold_arrias` = '" + arrears + "' \n" +
                    "WHERE\n" +
                    "\t`ass_Qstart_process_date` = '2020-10-01' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void graterThanThreeQuaterPaid(int idA, double over, double q4, double arrears, double qv) {
        try {

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_Over` = '" + over + "' \n" +
                    "WHERE\n" +

                    "\t `ass_PayHistry_year` = 2019 \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_Q4` = '" + q4 + "' \n" +
                    "WHERE\n" +
                    "\t`ass_PayHistry_Date` = '2020-01-31' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void graterForeThreeQuaterPaid(int idA, double over, double next) {
        try {

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_comment` = 'error fixd lesthan one quater',\n" +
                    "`ass_PayHistry_Over` = '" + over + "' \n" +
                    "WHERE\n" +

                    "\t `ass_PayHistry_year` = 2019 \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);

            conn.DB.setData("UPDATE `ass_payhistry` \n" +
                    "SET `ass_PayHistry_Over` = '" + next + "' \n" +
                    "WHERE\n" +
                    "\t`ass_PayHistry_Date` = '2020-01-01' \n" +
                    "\tAND `Assessment_idAssessment` = " + idA);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
