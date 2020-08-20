package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FrontPage {
    @FXML
    private Text balance;
    private Connection connection;
    private Main main;
    int userid;

    @FXML
    private Button mb;//etar full form to masturbate

    @FXML
    private Button sms;

    @FXML
    private Button talktime;

    @FXML
    private Button validity;

    @FXML
    private Button offer;

    @FXML
    private Button History;

    @FXML
    void HistoryAction(ActionEvent event) {
        main.showUsageHistory(userid);
    }

    @FXML
    void OfferAction(ActionEvent event) {
        main.showOfferpage(userid);
    }
    @FXML
    private Text name;
    private Button logout;
    public void initialise(int userid, Connection connection,Main main){
        this.connection = connection;
        this.main = main;
        this.userid = userid;
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select name,balance, internet_balance, sms, talktime, validity from account_details where id = "+Integer.toString(userid));
            if (stmt.execute("select name, balance, internet_balance, sms, talktime, validity from account_details where id = "+Integer.toString(userid))) {
                rs = stmt.getResultSet();
                while(rs.next()){
                    balance.setText(Double.toString(rs.getDouble("balance")));
                    mb.setText(Double.toString(rs.getDouble("internet_balance"))+"  MB");
                    sms.setText(Double.toString(rs.getDouble("sms"))+"  sms");
                    talktime.setText(Double.toString(rs.getDouble("talktime"))+"  min");
                    java.sql.Date dbSqlDate = rs.getDate("validity");
                    java.util.Date dbSqlDateConverted = new java.util.Date(dbSqlDate.getTime());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    validity.setText(df.format(dbSqlDateConverted));
                    name.setText(rs.getString("name"));


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void LogoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}