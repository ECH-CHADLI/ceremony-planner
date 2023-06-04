package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import dbConnection.jdbc;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Profile extends CRUDApplication{
	private Stage primaryStage;
	private Connection connection;
	 CRUDApplication crud = new CRUDApplication();
	
	private Button update;

	private String organizerName;
	private String date_event;
	private String time_event;
	private String type_event;
	private String email_event;
	private String id_event;
	private String phone_event;
	private String price_event;
	
	tableViewContent tbl = new tableViewContent();
	
	public Scene profileClient(Stage primaryStage, StringProperty selectedId) {
		this.primaryStage = primaryStage;

		client_db(selectedId);
		
		BorderPane root = new BorderPane();
		Label client = new Label("CLIENT INFORMATION");
		client.setStyle("-fx-font-size: 20px; -fx-text-fill: #00008B");
		client.getStyleClass().add("client-label");
		
		 Color white = Color.valueOf("white");
	    
        BackgroundFill backgroundFill = new BackgroundFill(white, null, null);
        
        Background background = new Background(backgroundFill);
        
        root.setBackground(background);
	      
		client.getStyleClass().add("client-label");
		
		Label lbl1 = new Label("         ID : " + id_event) ; lbl1.setStyle("-fx-font-size: 16px;");
        Label lbl2 = new Label("         PHONE : " + phone_event); lbl2.setStyle("-fx-font-size: 16px;");
        Label lbl3 = new Label("         E-MAIL : " + email_event); lbl3.setStyle("-fx-font-size: 16px;");
        Label lbl4 = new Label("         PRICE : " + price_event); lbl4.setStyle("-fx-font-size: 16px;");
        
        GridPane gridPane = new GridPane();
        gridPane.add(lbl1,2,3);
        gridPane.add(lbl2,2,4);
        gridPane.add(lbl3,2,5);
        gridPane.add(lbl4,2,6);
        gridPane.setVgap(10); 
        root.setLeft(gridPane);
		
		Label paragraph = new Label("We are thrilled to introduce "+organizerName+", an awesome human being who will be the King (Queen) of our\n"
									+ "ceremony halls, it's an honor to have them be a part of the history of this place that will be remembered one day.\n"
									+ "They will organize a lovely "+type_event+" party with friends and family by their side, we wish them the best of what\n"
									+ "life can offer. The event is set in "+date_event+" and exactly at "+time_event+" you can find contact numbers on the \n"
									+ "top of the page.\n"
									+ "with joy, laughter, and cherished memories. " + organizerName + " has put their heart and soul into planning \n"
									+ "every detail to ensure that every guest has a remarkable experience.\n"
									+ "We invite you to join us and celebrate this special occasion. The ceremony halls have been elegantly decorated to create\n"
									+ "an enchanting atmosphere. You'll be captivated by the beautiful ambiance and the warmth of the gathering.\n"
									+ "Please mark your calendars and save the date. We can't wait to share this extraordinary moment with you and create\n"
									+ "everlasting memories together. Your presence will make the event even more special. We look forward to seeing you.\n"
									+ "SIGNATURE:");
		paragraph.setStyle("-fx-font-size: 16px;");
		
		update = new Button("update");
		update.setOnAction(e -> {
			switchForm(selectedId);
			
		});
		Button delete = new Button("delete");
	    delete.setOnAction(e ->{
		 try {
			    String id = selectedId.getValue();
			    String sql = "DELETE FROM ceremony WHERE id = ?";
			    PreparedStatement statement = connection.prepareStatement(sql);
			    statement.setString(1, id); 

			    int rowsAffected = statement.executeUpdate();

			    if (rowsAffected > 0) {
			        System.out.println("La ligne a été supprimée avec succès de la base de données.");
			    } else {
			        System.out.println("Aucune ligne n'a été supprimée de la base de données.");
			    }
			    
			    Alert alert = new Alert(Alert.AlertType.INFORMATION);
			    alert.setTitle("Organizer Registation");
			    alert.setHeaderText("Organizer Registation");
			    alert.setContentText("Deleted");
			    alert.showAndWait();
			} catch (SQLException i) {
			    i.printStackTrace();
			}});
		Button print = new Button("print");
		print.setOnAction(event -> generatePDF());
		
		root.setTop(client);
		BorderPane.setAlignment(client, Pos.CENTER);
		root.setCenter(paragraph);
		
		Hyperlink return_db = new Hyperlink("<< return to database");
		return_db.setOnAction(event -> switchTableView());
		Hyperlink return_fm = new Hyperlink("return to form >>");
		return_fm.setOnAction(event -> switchForm(selectedId));
		
		HBox hbox1 = new HBox(update, print, delete);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(5);
        
        HBox hbox2 = new HBox(return_db, return_fm);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(5);
        
        VBox vbox = new VBox(hbox1, hbox2);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);

        root.setBottom(vbox);
        BorderPane.setAlignment(vbox, Pos.CENTER);
		
		Scene scene = new Scene(root, 1000, 750);
		scene.getStylesheets().add(getClass().getResource("bussiness.css").toExternalForm());
		
		return scene;
	}
	
	/*SWITCHING BETWEEN SCENES*/
	 public void switchTableView() {
	    	tableViewContent tableViewContent = new tableViewContent();
	        Scene tableViewScene = tableViewContent.createScene(primaryStage);
	        primaryStage.setScene(tableViewScene);
	        primaryStage.show();
	    }
	 public void switchForm(StringProperty selectedId) {
	    	CRUDApplication frm = new CRUDApplication();
			Scene frmScene = frm.createAnotherScene(primaryStage, selectedId);
	        primaryStage.setScene(frmScene);
	        primaryStage.show();
	    }
	 /*END*/
	 
	 /*set-up database connection*/
	public Profile() {
		try {
			connection = jdbc.getConnection(); //import dbConnection package first
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void client_db(StringProperty selectedId) {

		try {
			String id = selectedId.getValue();
	        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ceremony WHERE id = ?");
	        statement.setString(1, id);
	        ResultSet resultSet = statement.executeQuery();
	
			if (resultSet.next()) {
			    organizerName = resultSet.getString("name");
			    date_event = resultSet.getString("date");
			    time_event = resultSet.getString("time");
			    type_event = resultSet.getString("type");
			    email_event = resultSet.getString("email");
			    phone_event = resultSet.getString("phone");
			    price_event = resultSet.getString("price");
			    id_event = resultSet.getString("id");
			}
		} catch (SQLException e){
            e.printStackTrace();
        }	
	}
		
	
	
	public void generatePDF() {
		// Set up the printer job
        /*Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
        
        if (printerJob != null && printerJob.showPrintDialog(primaryStage)) {
            // Print the scene
            boolean printed = printerJob.printPage(scene.getRoot());

            if (printed) {
                printerJob.endJob();

                // Generate PDF from printed scene
                try {
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    printerJob.printPage(page);
                    contentStream.close();

                    // Save the PDF file
                    File file = new File("output.pdf");
                    document.save(file);
                    document.close();

                    System.out.println("PDF saved successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
