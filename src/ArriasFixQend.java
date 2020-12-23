import conn.DB;

import java.sql.ResultSet;

public class ArriasFixQend {

    public static void main(String[] args) {
        try {

            int x = 0;

            ResultSet dd = DB.getData("SELECT\n" +
                    "assessment.idAssessment,\n" +
                    "assessment.Customer_idCustomer,\n" +
                    "assessment.Ward_idWard,\n" +
                    "assessment.Street_idStreet,\n" +
                    "assessment.ass_nature_idass_nature,\n" +
                    "assessment.ass_discription_idass_discription,\n" +
                    "assessment.User_idUser,\n" +
                    "assessment.assessment_oder,\n" +
                    "assessment.assessment_no,\n" +
                    "assessment.assessment_status,\n" +
                    "assessment.assessment_syn,\n" +
                    "assessment.assessment_comment,\n" +
                    "assessment.assessment_obsolete,\n" +
                    "assessment.office_idOffice\n" +
                    "FROM\n" +
                    "assessment");

            while (dd.next()) {

                int idAssessment = dd.getInt("idAssessment");


                ResultSet data = DB.getData("SELECT\n" +
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
                        "FROM `ass_qstart`\n" +
                        "WHERE ass_qstart.Assessment_idAssessment = " + idAssessment);


                double arrias = 0;
                double warrant = 0;

                while (data.next()) {
                    int ass_qstart_quaterNumber = data.getInt("ass_Qstart_QuaterNumber");

                    switch (ass_qstart_quaterNumber) {
//                        case 2:
//
//                            arrias += data.getDouble("ass_Qstart_LQC_Arreas");
//                            warrant += data.getDouble("ass_Qstart_LQC_Warrant");
//
//                            System.out.println(1);
//                            break;
                        case 3:

                            arrias += data.getDouble("ass_Qstart_LQC_Arreas");
                            warrant += data.getDouble("ass_Qstart_LQC_Warrant");

                            System.out.println(2);
                            break;
                        case 4:

                            arrias += data.getDouble("ass_Qstart_LQC_Arreas");
                            warrant += data.getDouble("ass_Qstart_LQC_Warrant");

                            System.out.println(3);


                            x++;
                            int idass_qstart = data.getInt("idass_Qstart");

                            conn.DB.setData("UPDATE `ass_qstart`\n" +
                                    "SET \n" +
                                    " `ass_Qstart_tyold_arrias` = '" + round(arrias) + "',\n" +
                                    " `ass_Qstart_tyold_warant` = '" + round(warrant) + "'\n" +
                                    "WHERE\n" +
                                    "\t(`idass_Qstart` = '" + idass_qstart + "')");

                            arrias = 0;
                            warrant = 0;
                            System.out.println("====  " + x);
                            break;
                    }


                }


            }

            System.out.println(x + "   -------------- done");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }


}
