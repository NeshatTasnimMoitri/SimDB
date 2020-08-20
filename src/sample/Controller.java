package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ResourceBundle resources;
    Main main;
    Connection connection;

    @FXML
    private URL location;

    @FXML
    private TextField userid;

    @FXML
    private TextField pass;

    @FXML
    private Button login;
    @FXML
    private Text invalid;

    @FXML
    void initialize() {
        //assert userid != null : "fx:id=\"userid\" was not injected: check your FXML file 'sample.fxml'.";
        //assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'sample.fxml'.";
        //assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'sample.fxml'.";

    }
    @FXML
    void LognActon(ActionEvent event) {
        String user;
        String p;
        if(userid.getText()!= null && pass.getText()!= null ) {
            user = userid.getText();
            p = pass.getText();
            String dbpass = null;

            //codes for queery execution starts
            ResultSet rs = null;
            try {
                Statement stmt = connection.createStatement();

                rs = stmt.executeQuery("select password from user where id = "+user);
                System.out.println("select password from user where id = " + user);
                if (stmt.execute("select password from user where id = "+user)) {
                    rs = stmt.getResultSet();
                    while(rs.next()){
                        //System.out.println(rs.getInt("id"));
                        //System.out.println(rs.getString("password"));
                        dbpass = rs.getString("password");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(dbpass.equalsIgnoreCase(pass.getText())){
                main.showFrontpage(Integer.parseInt(userid.getText()));
            } else{
                invalid.setText("Invalid userid and passwords");
            }
        }else{
            invalid.setText("Invalid userid and passwords");
        }


    }
    public void setMain(Main main) {
        this.main = main;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
