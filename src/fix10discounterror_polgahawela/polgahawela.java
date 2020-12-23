package fix10discounterror_polgahawela;

import conn.DB;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by Ranga Rathnayake on 2020-08-13.
 */
public class polgahawela {


    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\tall_data_sheet.idAssessment AS idAssessment,\n" +
                    "\tall_data_sheet.Allocation AS Allocation,\n" +
                    "\tall_data_sheet.quarter_value AS quarter_value,\n" +
                    "\tall_data_sheet.balance0631 AS balance0631,\n" +
                    "\t( `all_data_sheet`.`balance0631` * - ( 1 ) ) AS balance,\n" +
                    "\tround( ( ( `all_data_sheet`.`quarter_value` / 100 ) * 10 ), 2 ) AS discount,\n" +
                    "\tround( ( ( `all_data_sheet`.`balance0631` * - ( 1 ) ) - ( `all_data_sheet`.`quarter_value` * 2 ) ), 2 ) AS ithuru,\n" +
                    "\t( `all_data_sheet`.`quarter_value` * 2 ) AS realval,\n" +
                    "\tround(\n" +
                    "\t\t(\n" +
                    "\t\t\t( ( `all_data_sheet`.`balance0631` * - ( 1 ) ) - ( `all_data_sheet`.`quarter_value` * 2 ) ) + ( ( `all_data_sheet`.`quarter_value` / 100 ) * 10 ) \n" +
                    "\t\t),\n" +
                    "\t\t2 \n" +
                    "\t) AS debited \n" +
                    "FROM\n" +
                    "\tall_data_sheet \n" +
                    "WHERE\n" +
                    "\t(\n" +
                    "\t\t( all_data_sheet.balance0631 < 0 ) \n" +
                    "\t\tAND ( ( ( `all_data_sheet`.`quarter_value` * 2 ) < ( `all_data_sheet`.`balance0631` * - ( 1 ) ) ) ) \n" +
                    "\t)\n");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double discount = data.getDouble("discount");
                System.out.println(idAssessment + "  -  " + discount);
                ResultSet data1 = DB.getData("SELECT\n" +
                        "ass_payhistry.idass_PayHistry,\n" +
                        "ass_payhistry.Assessment_idAssessment,\n" +
                        "ass_payhistry.ass_PayHistry_year,\n" +
                        "ass_payhistry.ass_PayHistry_Over,\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                        "ass_payhistry.ass_PayHistry_Date\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment ='" + idAssessment + "' AND\n" +
                        "ass_payhistry.ass_PayHistry_year = 2020");
                double over = 0;
                String o = "";
                HashMap<Integer, Double> hm = new HashMap<>();
                while (data1.next()) {
                    double ass_payHistry_over = data1.getDouble("ass_PayHistry_Over");
                    int idass_payHistry = data1.getInt("idass_PayHistry");
                    if (ass_payHistry_over > 0) {
                        hm.put(idass_payHistry, ass_payHistry_over);
                        over += ass_payHistry_over;
                    }
                }
                if (discount >= over) {
                    over = 0; // over 0 add debit
                    conn.DB.setData("UPDATE `ass_payhistry` SET \n" +
                            "`ass_PayHistry_Over` = 0\n" +
                            "WHERE\n" +
                            "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                            "ass_payhistry.ass_PayHistry_year = 2020");
                    double v = discount - over;
                    if (v > 0) {
                        conn.DB.setData("INSERT INTO `ass_creditdebit` \n" +
                                "(`Ass_CreditDebit_discription`, `Ass_CreditDebit_cd`, `Ass_CreditDebit_amount`, `Ass_balance`, `Ass_CreditDebit_date`, `Assessment_idAssessment`, `Ass_CreditDebit_status`, `user_id` )\n" +
                                "VALUES\t( 'Assessment Tax Starting Data Entry Error Fix. 10% additional discount error fix', 1, '" + v + "', '" + v + "', '2020-08-13', '" + idAssessment + "', 1, 1 )");
                    }
                } else {
                    double v = over - discount;
                    // no debit set over as v
                    conn.DB.setData("UPDATE `ass_payhistry` SET \n" +
                            "`ass_PayHistry_Over` = 0\n" +
                            "WHERE\n" +
                            "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                            "ass_payhistry.ass_PayHistry_year = 2020");

                    conn.DB.setData("UPDATE `ass_payhistry` SET \n" +
                            "`ass_PayHistry_Over` = '" + v + "'\n" +
                            "WHERE\n" +
                            "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                            "ass_payhistry.ass_PayHistry_year = 2020 AND\n" +
                            "ass_payhistry.ass_PayHistry_Date = '2020-07-01'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
