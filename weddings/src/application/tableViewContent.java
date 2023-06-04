package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.jdbc;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//tableView doesn't have a setOnAction event
public class tableViewContent {
	private Stage primaryStage;
	StringProperty selectedId;
	
	int myIndex, id;
	
	TableView<Organizers> tableView = new TableView<>();
    ObservableList<Organizers> organizerList;
    Connection connection;
    
    TableColumn<Organizers, String> idColumn;
    TableColumn<Organizers, String> nameColumn;
    TableColumn<Organizers, String> typeColumn;
    TableColumn<Organizers, String> dateColumn;
    
    public Scene createScene(Stage primaryStage) {
    	this.primaryStage = primaryStage;


        idColumn = new TableColumn<>("ID");

        nameColumn = new TableColumn<>("Name");

        typeColumn = new TableColumn<>("Type");

        dateColumn = new TableColumn<>("Date");

        table();
        
        tableView.getColumns().addAll(idColumn, nameColumn, typeColumn, dateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Eliminates the empty column that fills the space of the scene, and resizes the tableView accordingly

        Hyperlink Form = new Hyperlink("<< Form");
        Form.setStyle("-fx-underline: true;");
        Form.setOnAction(event -> switchForm());
        
        VBox vbox = new VBox(tableView, Form);
        Scene scene = new Scene(vbox, 1000, 650);
        vbox.setAlignment(Pos.CENTER);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        /*styles for css*/
        tableView.getStyleClass().add("my-table-view");
        idColumn.getStyleClass().add("table-header-cell");
        nameColumn.getStyleClass().add("table-header-cell");
        typeColumn.getStyleClass().add("table-header-cell");
        dateColumn.getStyleClass().add("table-header-cell");
        
        //recuperer l'id pour perfomer les fonctionalités de update et delete (WHERE id=?)
        tableView.setRowFactory(tv -> {
            TableRow<Organizers> row = new TableRow<>();
            row.getStyleClass().add("table-row-cell");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                	
                	Organizers selectedItem = tableView.getSelectionModel().getSelectedItem(); //recuperation de l'objet d'Organizers
                    StringProperty selectedId = selectedItem.idProperty(); //Les données de la ligne selectionnee sont recuperees.
                	
                    Organizers rowData = row.getItem();
                    Profile profile = new Profile();
                    Scene profileScene = profile.profileClient(primaryStage, selectedId);

                    primaryStage.setScene(profileScene);
                    primaryStage.show();
                }
            });
            return row;
        });
        /*end of it*/
        
        return scene;
    }
    
    //SWITCH BETWEEN SCENES
    public void switchForm() {
    	CRUDApplication frm = new CRUDApplication();
		Scene frmScene = frm.createAnotherScene(primaryStage, selectedId);
        primaryStage.setScene(frmScene);
        primaryStage.show();
    }
    
    public tableViewContent() {
        // Retrieve the database connection instance
        try {
			connection = jdbc.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void table(){
        ObservableList<Organizers> organizers = FXCollections.observableArrayList(); //stocker les objets Organizers
        try{
            PreparedStatement pst = connection.prepareStatement("select id, name, type, date from ceremony ORDER BY date");  
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            { //parcourir les resultats de la requete et cree un nouvel objet Organizers pour chaque ligne de la table
                Organizers st = new Organizers();
                st.setId(rs.getString("id")); //methode de la classe bean pour definir les valeurs des attributs a partir de la requete
                st.setName(rs.getString("name"));
                st.setType(rs.getString("type"));
                st.setDate(rs.getString("date"));
                organizers.add(st); //ajouter chaque objet a observableList
            }
                    tableView.setItems(organizers); //definir organizers(OBS list) comme source de donnees pour la table
                    //configurer les cellules de la table avec les methodes definit dans le Bean
                    idColumn.setCellValueFactory(f -> f.getValue().idProperty());
                    nameColumn.setCellValueFactory(f -> f.getValue().nameProperty());
                    typeColumn.setCellValueFactory(f -> f.getValue().typeProperty());
                    dateColumn.setCellValueFactory(f -> f.getValue().dateProperty());
        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
