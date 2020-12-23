import conn.DB;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;

public class ReversPaidArrias {

    public static void main(String[] args) {
        try {
            ResultSet data = conn.DB.getData("SELECT\n" +
                    "\tass_payment.idass_Payment,\n" +
                    "\tass_payto.ass_payto_Qno,\n" +
                    "\tass_payto.ass_payto_warrant,\n" +
                    "\tass_payto.ass_payto_arrears,\n" +
                    "\tass_payment.Assessment_idAssessment,\n" +
                    "\tass_payment.ass_Payment_Q_Number\n" +
                    "FROM\n" +
                    "\tass_payment\n" +
                    "INNER JOIN ass_payto ON ass_payto.ass_Payment_idass_Payment = ass_payment.idass_Payment\n" +
                    "WHERE\n" +
                    "\t(ass_payto_warrant > 0 OR ass_payto_arrears > 0\t)\n" +
                    "AND ass_payment.ass_Payment_date > '2019-06-30'\n" +
                    "AND ass_Payment_Status = 1");


            HashMap<Integer, holder> hm = new HashMap<>();

            while (data.next()) {

                int assessment_idAssessment = data.getInt("Assessment_idAssessment");


                holder holder = hm.get(assessment_idAssessment);
                if (holder != null) {
                    holder.a += data.getDouble("ass_payto_arrears");
                    holder.w += data.getDouble("ass_payto_warrant");
                    holder.id = assessment_idAssessment;
                    hm.put(assessment_idAssessment, holder);
                } else {
                    holder = new holder(assessment_idAssessment, data.getDouble("ass_payto_arrears"), data.getDouble("ass_payto_warrant"));
                    hm.put(assessment_idAssessment, holder);
                }

            }


            Set<Integer> integers = hm.keySet();

            for (int x : integers) {
                holder holder = hm.get(x);
                System.out.println(holder.id + "  | " + holder.a + "   |   " + holder.w);


                ResultSet datax = DB.getData("SELECT\n" +
                        "ass_qstart.idass_Qstart,\n" +
                        "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                        "ass_qstart.ass_Qstart_tyold_warant\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber = 3 AND\n" +
                        "ass_qstart.Assessment_idAssessment = " + holder.id);

                if (datax.last()) {

                    int idass_qstart = datax.getInt("idass_Qstart");
                    double ass_qstart_tyold_arrias = datax.getDouble("ass_Qstart_tyold_arrias");
                    double ass_qstart_tyold_warant = datax.getDouble("ass_Qstart_tyold_warant");

                    ass_qstart_tyold_arrias += holder.a;
                    ass_qstart_tyold_warant += holder.w;

                    conn.DB.setData("UPDATE `ass_qstart`SET \n" +
                            "\n" +
                            " `ass_Qstart_tyold_arrias` = '" + round(ass_qstart_tyold_arrias) + "',\n" +
                            " `ass_Qstart_tyold_warant` = '" + round(ass_qstart_tyold_warant) + "'\n" +
                            "WHERE\n" +
                            "\t(`idass_Qstart` = '" + idass_qstart + "')\n");


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    static int x = 0;

    public static void update(HashMap hm) {
        x++;
        try {


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

}


class holder {
    int id = 0;

    public holder(int id, Double a, Double w) {
        this.id = id;
        this.a += a;
        this.w += w;
    }

    Double a = 0.0;
    Double w = 0.0;
}