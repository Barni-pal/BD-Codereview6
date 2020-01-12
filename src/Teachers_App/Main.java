package Teachers_App;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.sql.*;
import java.util.List;

public class Main extends Application {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    static ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    static ObservableList<String> classes = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        resultSet = getConnection("SELECT * FROM teachers");
        while (resultSet.next()) {
            int teacher_id = resultSet.getInt("teacher_id");
            String teacher_name = resultSet.getString("name");
            String teacher_surname = resultSet.getString("surname");
            String teacher_email = resultSet.getString("email");
            teachers.add(new Teacher(teacher_id, teacher_name, teacher_surname, teacher_email));
        }
        while (resultSet.next()){
            String class_name = resultSet.getString("class_name");
        }
        ListView<Teacher> listOfTeachers = new ListView<>();
        listOfTeachers.getItems().addAll(teachers);
        ListView<String> classOfTeacher = new ListView<>();
        classOfTeacher.getItems().addAll(classes);

        TextField teacherIdField = new TextField();
        TextField teacherNameField = new TextField();
        TextField teacherSurNameField = new TextField();
        TextField teacherEmailField = new TextField();

        Label teachers = new Label("Teachers");
        Label thisTeacher = new Label("this teacher: ");
        VBox vbox2 = new VBox(teacherIdField, teacherNameField, teacherSurNameField, teacherEmailField);
        VBox vBox = new VBox(teachers, listOfTeachers);
        VBox vBox1 = new VBox(thisTeacher, vbox2);
        HBox hBox1 = new HBox(vBox, vBox1);

        listOfTeachers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                    teacherIdField.setText(String.valueOf(newValue.getId()));
                    teacherNameField.setText(newValue.getName());
                    teacherSurNameField.setText(newValue.getSurname());
                    teacherEmailField.setText(newValue.getEmail());
        });
        primaryStage.setScene(new Scene(hBox1, 700, 300));
        primaryStage.setTitle("Teachers");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

        public static void main (String[]args) {
            launch(args);
        }

        public static ResultSet getConnection (String query) throws Exception{
                String url = "jdbc:mysql://localhost:3306/cr6_barnabas_doebroessy_school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                String uname = "root";
                String pass = "admin";
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, uname, pass);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                return resultSet;
        }
}