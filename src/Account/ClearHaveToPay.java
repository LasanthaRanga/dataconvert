package Account;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-12-24.
 */
public class ClearHaveToPay {

    public static void main(String[] args) {

        fix();

    }


    public static void fix() {

        try {


            ResultSet data = DB.getData("SELECT\n" +
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
                    "assessment.office_idOffice,\n" +
                    "assessment.isWarrant\n" +
                    "FROM\n" +
                    "assessment");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");

                ResultSet haveToPayStatus = DB.getData("SELECT\n" +
                        "ass_payhistry.ass_PayHistry_Qcunt,\n" +
                        "ass_payhistry.ass_PayHistry_Q1Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q2Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q3Status,\n" +
                        "ass_payhistry.ass_PayHistry_Q4Status\n" +
                        "FROM\n" +
                        "ass_payhistry\n" +
                        "WHERE\n" +
                        "ass_payhistry.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_payhistry.ass_PayHistry_year = 2020 ");

                while (haveToPayStatus.next()) {
                    int status = haveToPayStatus.getInt("ass_PayHistry_Q4Status");
                    if (status == 1) {

                        int i = DB.setData("UPDATE ass_qstart set\n" +
                                "ass_Qstart_HaveToQPay = '0'\n" +
                                "WHERE\n" +
                                "ass_qstart.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                                "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                                "ass_qstart.ass_Qstart_QuaterNumber = 4");

                        System.out.println(i + " ---------------------------------------------  UPDATE");

                    }else {
                        System.out.println("NO");
                    }
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
