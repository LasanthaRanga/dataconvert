package customer_change;

import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga on 2020-04-18.
 */
public class CChange {
    public static void main(String[] args) {
        System.out.println("ELA");
      //    mainCustomer();
        getSubOwners();
    }


    public static void mainCustomer() {
        String query = "SELECT\n" +
                "assessment.idAssessment,\n" +
                "assessment.Customer_idCustomer\n" +
                "FROM\n" +
                "assessment\n";

        try {
            ResultSet data = DB.getData(query);
            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                int customer_idCustomer = data.getInt("Customer_idCustomer");


                System.out.println(idAssessment + "   --   " + customer_idCustomer);
                saveNewTable(idAssessment, customer_idCustomer);
            }
        } catch (Exception e) {

        }
    }


    public static void saveNewTable(int idAss, int cus) {
        try {
            conn.DB.setData("INSERT INTO `cushasassess`( `assessment_id`, `customer_id`, `status`) VALUES ('" + idAss + "', '" + cus + "', 1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSubOwners() {
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "ass_subowner.idass_subOwner,\n" +
                    "ass_subowner.ass_subOwner_name,\n" +
                    "ass_subowner.ass_subOwner_nic,\n" +
                    "ass_subowner.ass_subOwner_status,\n" +
                    "ass_subowner.Assessment_idAssessment,\n" +
                    "ass_subowner.title,\n" +
                    "ass_subowner.ass_subOwner_namesinhala\n" +
                    "FROM\n" +
                    "ass_subowner\n");

            while (data.next()) {

                int assessment_idAssessment = data.getInt("Assessment_idAssessment");
                String ass_subOwner_name = data.getString("ass_subOwner_name");
                String ass_subOwner_nic = data.getString("ass_subOwner_nic");
                String ass_subowner_status = data.getString("ass_subowner_status");
                String ass_subOwner_namesinhala = data.getString("ass_subOwner_namesinhala");
                int title = data.getInt("title");

                conn.DB.setData("INSERT INTO `customer`( `cus_name`, `cus_person_title`, `cus_nic`, `cus_status`, `cus_syn`,`cus_name_sinhala`) " +
                        "VALUES ( '" + ass_subOwner_name + "', '" + title + "', '" + ass_subOwner_nic + "', '1', '" + ass_subowner_status + "', '" + ass_subOwner_namesinhala + "')");

                int idCustomer = 0;

                ResultSet data1 = DB.getData("SELECT customer.idCustomer FROM customer ORDER BY customer.idCustomer DESC LIMIT 1");

                if (data1.last()) {
                    idCustomer = data1.getInt("idCustomer");
                }

                System.out.println(idCustomer);

                saveNewTable(assessment_idAssessment, idCustomer);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


