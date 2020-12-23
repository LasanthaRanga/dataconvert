package Account;

import conn.DB;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Ranga Rathnayake on 2020-12-21.
 */
public class Account {
    public static double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    public static void main(String[] args) {

        getAssessment();

    }

    public static void getAssessment() {
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
                    "assessment.isWarrant,\n" +
                    "ass_nature.ass_nature_year_rate,\n" +
                    "ward.ward_no,\n" +
                    "street.street_name\n" +
                    "FROM\n" +
                    "assessment\n" +
                    "INNER JOIN ass_nature ON assessment.ass_nature_idass_nature = ass_nature.idass_nature\n" +
                    "INNER JOIN ward ON assessment.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN street ON street.Ward_idWard = ward.idWard AND assessment.Street_idStreet = street.idStreet\n" +
                    "WHERE\n" +
                    "assessment.assessment_syn = 0 AND\n" +
                    "street.idStreet = 1");

            double t_lyq = 0;
            double t_lyw = 0;
            double t_billing = 0;
            double t_tya_warant = 0;
            double t_debit = 0;

            double t_over = 0;
            double t_10_paid = 0;
            double t_paid = 0;
            double t_discout = 0;

            double t_next = 0;

            double haveToPay = 0;

            while (data.next()) {


                int idAssessment = data.getInt("idAssessment");
                double ass_nature_year_rate = data.getDouble("ass_nature_year_rate");


                ResultSet allo = DB.getData("SELECT\n" +
                        "ass_allocation.ass_allocation\n" +
                        "FROM\n" +
                        "ass_allocation\n" +
                        "WHERE\n" +
                        "ass_allocation.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_allocation.ass_allocation_status = 1\n");

                double allocation = 0;

                if (allo.last()) {
                    allocation = allo.getDouble("ass_allocation");
                }

                double v = allocation * ass_nature_year_rate / 100;


//                System.out.println(idAssessment + "---------------" + allocation + "  -  " + v);


                Dis disco = new Discount().getDiscount(idAssessment);


                ResultSet arr1231 = DB.getData("SELECT\n" +
                        "ass_qstart.ass_Qstart_LY_Arreas,\n" +
                        "ass_qstart.ass_Qstart_LY_Warrant,\n" +
                        "ass_qstart.process_update_warant,\n" +
                        "ass_qstart.process_update_arrears\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber = 1 AND\n" +
                        "ass_qstart.ass_Qstart_year = 2020");

                double lya = 0;
                double lyw = 0;
                double process_update_warant = 0;
                double process_update_arrears = 0;

                if (arr1231.last()) {
                    lya = arr1231.getDouble("ass_Qstart_LY_Arreas");
                    lyw = arr1231.getDouble("ass_Qstart_LY_Warrant");

                    process_update_warant = arr1231.getDouble("process_update_warant");
                    process_update_arrears = arr1231.getDouble("process_update_arrears");

//                    System.out.println("   LYA " + lya);
//                    System.out.println("   LYW " + lyw);

//                    System.out.println("  from Last ss " + process_update_warant);
//                    System.out.println("  from Last nn " + process_update_arrears);


                }


                ResultSet ddd = DB.getData("SELECT\n" +
                        "ass_creditdebit.idAss_CreditDebit,\n" +
                        "ass_creditdebit.Ass_CreditDebit_discription,\n" +
                        "ass_creditdebit.Ass_CreditDebit_cd,\n" +
                        "ass_creditdebit.Ass_CreditDebit_amount,\n" +
                        "ass_creditdebit.Ass_balance,\n" +
                        "ass_creditdebit.Ass_CreditDebit_date,\n" +
                        "ass_creditdebit.Assessment_idAssessment,\n" +
                        "ass_creditdebit.Ass_CreditDebit_status,\n" +
                        "ass_creditdebit.user_id\n" +
                        "FROM\n" +
                        "ass_creditdebit\n" +
                        "WHERE\n" +
                        "ass_creditdebit.Assessment_idAssessment = '" + idAssessment + "'AND\n" +
                        "ass_creditdebit.Ass_CreditDebit_date > '2018-12-31'");

                double debit = 0;
                double credit = 0;

                while (ddd.next()) {
                    double ass_creditDebit_amount = ddd.getDouble("Ass_CreditDebit_amount");
                    double ass_creditDebit_cd = ddd.getDouble("Ass_CreditDebit_cd");
                    String ass_creditDebit_date = ddd.getString("Ass_CreditDebit_date");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parse1 = simpleDateFormat.parse("2020-01-01");
                    java.util.Date parse = simpleDateFormat.parse(ass_creditDebit_date);


                    System.out.println("      CD " + ass_creditDebit_date + "  " + ass_creditDebit_cd + "   " + ass_creditDebit_amount);
                    if (ass_creditDebit_cd > 0) {
                        debit = ass_creditDebit_amount;
                        System.out.println("-------------------------------------------------------------------------------------------------------------------- DEBIT  "+ debit);
                    } else {
                        credit = ass_creditDebit_amount;
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------- credit "+credit);
                    }

                }


                ResultSet asspay = DB.getData("SELECT\n" +
                        "ass_payment.idass_Payment,\n" +
                        "ass_payment.ass_Payment_Q_Number,\n" +
                        "ass_payment.ass_Payment_ThisYear,\n" +
                        "ass_payment.ass_Payment_date,\n" +
                        "ass_payment.ass_Payment_LY_Arrears,\n" +
                        "ass_payment.ass_Payment_LY_Warrant,\n" +
                        "ass_payment.ass_Payment_fullTotal,\n" +
                        "ass_payment.ass_Payment_idUser,\n" +
                        "ass_payment.Assessment_idAssessment,\n" +
                        "ass_payment.Receipt_idReceipt,\n" +
                        "ass_payment.ass_nature_idass_nature,\n" +
                        "ass_payment.ass_allocation_idass_allocation,\n" +
                        "ass_payment.ass_Payment_goto_debit,\n" +
                        "ass_payment.ass_Payment_Status,\n" +
                        "ass_payment.cd_balance\n" +
                        "FROM\n" +
                        "ass_payment\n" +
                        "WHERE\n" +
                        "ass_payment.ass_Payment_ThisYear = '2020' AND\n" +
                        "ass_payment.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_payment.ass_Payment_Status = 1\n");

                double lyap = 0;
                double lywp = 0;


                double quaterPay = 0;
                double tyap = 0;
                double tywp = 0;
                double discount = 0;

                double nextYarPay = 0;
                double amout = 0;


                while (asspay.next()) {

                    int idass_payment = asspay.getInt("idass_Payment");
                    String ass_payment_date = asspay.getString("ass_Payment_date");
                    double ass_payment_ly_arrears = asspay.getDouble("ass_Payment_LY_Arrears");
                    double ass_payment_ly_warrant = asspay.getDouble("ass_Payment_LY_Warrant");
                    double cd_balance = asspay.getDouble("cd_balance");
                    double ass_payment_goto_debit = asspay.getDouble("ass_Payment_goto_debit");
                    int receipt_idReceipt = asspay.getInt("Receipt_idReceipt");


                    nextYarPay += ass_payment_goto_debit;

                    lyap += ass_payment_ly_arrears;
                    lywp += ass_payment_ly_warrant;

//                    System.out.println("    Payment   " + ass_payment_date);
//                    System.out.println("LYA " + ass_payment_ly_arrears);
//                    System.out.println("LYW " + ass_payment_ly_warrant);
//                    System.out.println("CD  " + cd_balance);


                    ResultSet payto = DB.getData("SELECT\n" +
                            "ass_payto.idass_payto,\n" +
                            "ass_payto.ass_payto_Qno,\n" +
                            "ass_payto.ass_payto_warrant,\n" +
                            "ass_payto.ass_payto_arrears,\n" +
                            "ass_payto.ass_payto_qvalue,\n" +
                            "ass_payto.ass_payto_discount,\n" +
                            "ass_payto.ass_payto_discount_rate,\n" +
                            "ass_payto.ass_Payment_idass_Payment,\n" +
                            "ass_payto.ass_payto_status\n" +
                            "FROM\n" +
                            "ass_payto\n" +
                            "WHERE\n" +
                            "ass_payto.ass_Payment_idass_Payment = '" + idass_payment + "'");


                    while (payto.next()) {
                        double ass_payto_warrant = payto.getDouble("ass_payto_warrant");
                        double ass_payto_arrears = payto.getDouble("ass_payto_arrears");
                        double ass_payto_qvalue = payto.getDouble("ass_payto_qvalue");
                        double ass_payto_discount = payto.getDouble("ass_payto_discount");

                        quaterPay += ass_payto_qvalue;
                        tyap += ass_payto_arrears;
                        tywp += ass_payto_warrant;
                        discount += ass_payto_discount;
                    }

                    ResultSet res = DB.getData("SELECT\n" +
                            "receipt.idReceipt,\n" +
                            "receipt.Application_Catagory_idApplication_Catagory,\n" +
                            "receipt.recept_applicationId,\n" +
                            "receipt.receipt_print_no,\n" +
                            "receipt.cheack,\n" +
                            "receipt.cesh,\n" +
                            "receipt.receipt_total,\n" +
                            "receipt.receipt_day,\n" +
                            "receipt.receipt_status,\n" +
                            "receipt.receipt_syn,\n" +
                            "receipt.chque_no,\n" +
                            "receipt.chque_date,\n" +
                            "receipt.chque_bank,\n" +
                            "receipt.oder,\n" +
                            "receipt.office_idOffice,\n" +
                            "receipt.receipt_account_id,\n" +
                            "receipt.receipt_user_id,\n" +
                            "receipt.ribno,\n" +
                            "receipt.receipt_time,\n" +
                            "receipt.income_expense,\n" +
                            "receipt.cus_id,\n" +
                            "receipt.cross_recipt_or_voucher,\n" +
                            "receipt.pay_type,\n" +
                            "receipt.amount,\n" +
                            "receipt.cancle_user,\n" +
                            "receipt.cancle_dt,\n" +
                            "receipt.cancle_reson\n" +
                            "FROM\n" +
                            "receipt\n" +
                            "WHERE\n" +
                            "receipt.idReceipt = " + receipt_idReceipt);


                    if (res.last()) {
                        String receipt_print_no = res.getString("receipt_print_no");
                        double cheack = res.getDouble("cheack");
                        double cesh = res.getDouble("cesh");
                        double receipt_total = res.getDouble("receipt_total");
                        amout += res.getDouble("amount");

                        //    System.out.println(receipt_print_no + "  -  " + receipt_total + "  -  " + amout);

                    }
                }


                ResultSet ccaw = DB.getData("SELECT\n" +
                        "round(Sum(ass_qstart.ass_Qstart_LQ_Arreas),2) as lqa,\n" +
                        "round(Sum(ass_qstart.ass_Qstart_LQC_Arreas),2)as lqca,\n" +
                        "round(Sum(ass_qstart.ass_Qstart_LQ_Warrant),2) as lqw,\n" +
                        "round(Sum(ass_qstart.ass_Qstart_LQC_Warrant),2) as lqcw,\n" +
                        "ass_qstart.ass_Qstart_tyold_arrias,\n" +
                        "ass_qstart.ass_Qstart_tyold_warant\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_qstart.ass_Qstart_year = 2020 AND\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber > 1");

                ResultSet have = DB.getData("SELECT\n" +
                        "ass_qstart.ass_Qstart_HaveToQPay,\n" +
                        "ass_qstart.ass_Qstart_year,\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber\n" +
                        "FROM\n" +
                        "ass_qstart\n" +
                        "WHERE\n" +
                        "ass_qstart.Assessment_idAssessment = '" + idAssessment + "' AND\n" +
                        "ass_qstart.ass_Qstart_QuaterNumber = 4 AND\n" +
                        "ass_qstart.ass_Qstart_year = 2020");

                double ha = 0;

                if (have.last()) {

                    double ass_qstart_haveToQPay = have.getDouble("ass_Qstart_HaveToQPay");
                    ha = ass_qstart_haveToQPay;
                    haveToPay += ass_qstart_haveToQPay;

                    //   System.out.println("                HAVE to " + ass_qstart_haveToQPay);


                }

                double tya = 0;
                double tyw = 0;

                if (ccaw.last()) {

                    tya = ccaw.getDouble("lqa");
                    tyw = ccaw.getDouble("lqw");

                    double tyca = ccaw.getDouble("lqca");
                    double tycw = ccaw.getDouble("lqcw");

//                    System.out.println("                TY Arrears " + tya);
//                    System.out.println("                TY warrant " + tyw);


                }


//                System.out.println();
//
//                System.out.println("                LYAP TOT " + lyap);
//                System.out.println("                LYWP TOT " + lywp);
//
//
//                System.out.println("                TYQP TOT " + quaterPay);
//                System.out.println("                TYAP TOT " + tyap);
//                System.out.println("                TYWP TOT " + tywp);
//                System.out.println("                TDIS TOT " + discount);
//                System.out.println("                NEXT TOT " + nextYarPay);


                System.out.println("---------------------------------------------------------------------------------------------------------" + idAssessment);
                System.out.println("LYA        : " + lya + "   ");
                System.out.println("LYW        : " + lyw + "  ");
                System.out.println("Billing    : " + v + "   ");
                System.out.println("TYA Warant : " + tyw + "    ");
                System.out.println("Debit      : " + debit + "   ");
                System.out.println("TOTAL      : " + (lya + lyw + v + tyw + debit) + " ");
                System.out.println(" --have to : " + ha);
                System.out.println("                                        OVER 1231 : " + credit);
                System.out.println("                                        Paid 10%  : " + (process_update_warant * -1));
                System.out.println("                                        Paid 10%  : " + process_update_arrears);
                System.out.println("                                        PAID      : " + (amout + disco.getPaid()));

                System.out.println("                                        TDIS TOT  : " + (discount + disco.getDiscount()));
                System.out.println("                                        total     : " + (credit + (process_update_warant * -1) + amout + discount));
                System.out.println("                                        ----------------------------------------- ");
                System.out.println("                                        Next Yar  : " + (nextYarPay + disco.getVer()));


                t_lyq += lya;
                t_lyw += lyw;
                t_billing += v;
                t_tya_warant += tyw;
                t_debit += debit;

                t_over += credit;
                t_10_paid += process_update_warant;
                t_paid += amout + disco.getPaid();
                t_discout += discount + disco.getDiscount();

                t_next += nextYarPay + disco.getVer();

            }

            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("t_lya " + df.format(round(t_lyq)));
            System.out.println("t_lyq " + df.format(round(t_lyw)));
            System.out.println("t_billing " + df.format(round(t_billing)));
            System.out.println("t_ty_warrant " + df.format(round(t_tya_warant)));
            System.out.println("t_debit " + df.format(round(t_debit)));
            System.out.println("t_over " + df.format(round(t_over)));
            System.out.println("t_10_paid " + df.format(round(t_10_paid)));
            System.out.println("t_paid " + df.format(round(t_paid)));
            System.out.println("t_discout " + df.format(round(t_discout)));
            System.out.println("t_next " + df.format(round(t_next)));

            System.out.println(haveToPay + "  --  " + haveToPay);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
