package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UsageHistoryController {

        @FXML
        private Button back;

        private int userid;
        private Connection connection;
        private Main main;
        @FXML
        private VBox vbx=new VBox();

        @FXML
        private TableView<History> table = new TableView<>();

        @   FXML
        void BackAction(ActionEvent event) {
            main.showFrontpage(userid);
        }




    public void initialise(int userid, Connection connection, Main main) {
            this.userid = userid;
            this.connection = connection;
            this.main = main;
            ObservableList<History> observableList = FXCollections.observableArrayList();

            TableColumn offerColoumn = new TableColumn("Offers");
            offerColoumn.setMinWidth(300);
            TableColumn dateColoumn = new TableColumn("Date");
            dateColoumn.setMinWidth(215);
            table.getColumns().addAll(offerColoumn,dateColoumn);

            offerColoumn.setCellValueFactory(new PropertyValueFactory<History,String>("offerDetails"));
            dateColoumn.setCellValueFactory(new PropertyValueFactory<History,String>("date"));

        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select date,description from user_uses_history,offers where user_uses_history.offer_id = offers.offer_id and id = "+Integer.toString(userid));
            if (stmt.execute("select date,description from user_uses_history,offers where user_uses_history.offer_id = offers.offer_id and id = "+Integer.toString(userid))) {
                rs = stmt.getResultSet();
                while(rs.next()){
                    String desc = rs.getString("description");
                    java.sql.Date dbSqlDate = rs.getDate("date");
                    java.util.Date dbSqlDateConverted = new java.util.Date(dbSqlDate.getTime());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date =  df.format(dbSqlDateConverted);
                    System.out.println(desc+" "+ date);
                    observableList.add(new History(desc,date));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(observableList);
        vbx.getChildren().addAll(table);
    }

}
