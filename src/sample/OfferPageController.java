package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OfferPageController {
    private Connection connection;
    private int userid;
    private Main main;
    @FXML
    private Button Back;
    @FXML
    private Text balance;
    @FXML
    private Button sms;
    @FXML
    private VBox vbx = new VBox();
    private ListView listView = new ListView();
    @FXML
    private Button mb;
    private Double accountBalance;
    private Double purchaseCost;
    @FXML
    private Button talktime;
    @FXML
    private Button confirm;
    @FXML
    private Button select;
    @FXML
    private Text current;
    @FXML
    private Text rem;
    private int validityDays;
    private int mins;
    private int messages;
    private int internets;
    private int oid;
    @FXML
    private Text cur;
    @FXML
    private Text ofr;
    @FXML
    private Text rmb;

    @FXML
    private Text offerCost;
    public void initialise(int userid, Connection connection, Main main) {
    this.main = main;
    this.connection = connection;
    this.userid = userid;
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select balance,internet_balance, sms, talktime from account_details where id = "+Integer.toString(userid));
            if (stmt.execute("select balance,internet_balance, sms, talktime from account_details where id = "+Integer.toString(userid))) {
                rs = stmt.getResultSet();
                while(rs.next()){
                    balance.setText(Double.toString(rs.getDouble("balance")));
                    mb.setText(Double.toString(rs.getDouble("internet_balance"))+"  MB");
                    sms.setText(Double.toString(rs.getDouble("sms"))+"  sms");
                    talktime.setText(Double.toString(rs.getDouble("talktime"))+"  min");
                    accountBalance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //codes for the listView
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery("select description from offers");
            if (stmt.execute("select description from offers")) {
                resultSet = stmt.getResultSet();
                while(resultSet.next()){
                    System.out.println(resultSet.getString("description"));
                    observableList.add(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.getItems().addAll(observableList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);//sets selection mode to single
        vbx.getChildren().addAll(listView);

    }

    Double cost;
    @FXML
    void BackAction(ActionEvent event) {
        main.showFrontpage(userid);
    }
    @FXML
    void SelectAction(ActionEvent event) {
        ObservableList<String> selectedDetails = listView.getSelectionModel().getSelectedItems();
        String selected = selectedDetails.toString();
        cur.setText("Current balance:");
        ofr.setText("Offer cost:");
        rmb.setText("Remaining balance");
        String s = selected.substring(1,selected.length()-1);
        System.out.println(s);
        ResultSet resultSet = null;
        try {
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery("select offer_id,cost,data,sms,talktime,validity from offers where description like '"+ s+"'");
            if (stmt.execute("select offer_id,cost,data,sms,talktime,validity from offers where description like '"+ s+"'")) {
                resultSet = stmt.getResultSet();
                while(resultSet.next()){
                    cost = resultSet.getDouble("cost");
                    offerCost.setText(Double.toString(cost));
                    current.setText(Double.toString(accountBalance));
                    rem.setText(Double.toString(accountBalance - cost));

                    oid = resultSet.getInt("offer_id");
                    validityDays = resultSet.getInt("validity");
                    mins = resultSet.getInt("talktime");
                    messages = resultSet.getInt("sms");
                    internets = resultSet.getInt("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ConfirmAction(ActionEvent event) {
        cur.setText("");
        ofr.setText("");
        rmb.setText("");
        current.setText("");
        offerCost.setText("");
        rem.setText("");
        if((accountBalance-cost)>0){
            try {
                Statement stmt = connection.createStatement();
                String command = "update account_details set balance = "+Double.toString(accountBalance-cost)+",talktime = talktime+"+Integer.toString(mins)+",sms=sms+"+Integer.toString(messages)+",internet_balance=internet_balance+"+Integer.toString(internets)+",validity = validity+"+Integer.toString(validityDays)+" where id = "+Integer.toString(userid);
                System.out.println(command);
                stmt.executeUpdate(command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Statement stmt = connection.createStatement();
                String command ="Insert into user_uses_history values ('"+Integer.toString(userid)+"',current_date,'"+Integer.toString(oid)+"')";
                System.out.println(command);
                stmt.executeUpdate(command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            main.showFrontpage(userid);

        }else{
            rmb.setText("Insufficient Balance");
        }
    }
}