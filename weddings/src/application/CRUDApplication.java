package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import dbConnection.jdbc;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CRUDApplication extends Application {
	private Stage primaryStage;
	StackPane stackPane;
	StringProperty selectedId;

    protected TextField idField;
    protected TextField nameField;
    protected TextField phoneField;
    protected TextField emailField;
    protected DatePicker dateField;
    protected TextField priceField;
    protected Button addButton;
    ChoiceBox<String> choiceType;
    ChoiceBox<String> choiceTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	createAnotherScene(primaryStage, selectedId);
    }
    
    public Scene createAnotherScene(Stage primaryStage, StringProperty selectedId) {
    	this.primaryStage = primaryStage;
    	
        primaryStage.setTitle("CRUD Application");
        
        Label titleLabel = new Label("Ceremony Planner");
        titleLabel.setStyle("-fx-text-fill: purple;");
        titleLabel.getStyleClass().add("my-title-style");
        
        Label idLabel = new Label("ID");
        idField = new TextField();
        Label nameLabel = new Label("Name");
        nameField = new TextField();
        Label phoneLabel = new Label("Phone");
        phoneField = new TextField();
        Label emailLabel = new Label("Email");
        emailField = new TextField();
        
        Label dateLabel = new Label("Date");
        dateField = new DatePicker();
        
        Label timeLabel = new Label("Time");
        choiceTime = new ChoiceBox<>(FXCollections.observableArrayList(
                "10am - 2pm", "3pm - 7pm", "8pm - 2am"));
        
        Label priceLabel = new Label("Price");
        priceField = new TextField();
        
        Label typeLabel = new Label("Type");
        choiceType = new ChoiceBox<>(FXCollections.observableArrayList(
                "Marriage", "Retirement", "New born"));
        
        //text styling
        String labelStyle = "-fx-text-fill: white;";
        idLabel.setStyle(labelStyle); nameLabel.setStyle(labelStyle);
        phoneLabel.setStyle(labelStyle); emailLabel.setStyle(labelStyle);
        dateLabel.setStyle(labelStyle); priceLabel.setStyle(labelStyle);
        typeLabel.setStyle(labelStyle); timeLabel.setStyle(labelStyle);
	       
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> add());
        addButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");
        
        Button updButton = new Button("Update");
        updButton.setOnAction(event -> update(selectedId));
        updButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(addButton, updButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5));
        hbox.setSpacing(10);
        
        // Create hyperlink for the Database
        Hyperlink checkDatabaseLink = new Hyperlink("Check Database >>");
        checkDatabaseLink.setStyle("-fx-border-color: transparent; -fx-underline: true;");
        checkDatabaseLink.setOnAction(event -> switchTableView());
        
        //hidden feature ($$$)
        Hyperlink stats = new Hyperlink("Stats");
        stats.setStyle("-fx-border-color: transparent;");
        stats.setOnAction(event -> switchStats());
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(checkDatabaseLink, stats);
        vbox.setAlignment(Pos.CENTER);
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER); 
        
        gridPane.add(titleLabel, 0, 0, 2, 1);
        
        gridPane.add(idLabel, 0, 4); gridPane.add(idField, 1, 4);
        gridPane.add(nameLabel, 0, 5); gridPane.add(nameField, 1, 5);
        gridPane.add(phoneLabel, 0, 6); gridPane.add(phoneField, 1, 6);
        gridPane.add(emailLabel, 0, 7); gridPane.add(emailField, 1, 7);
        gridPane.add(priceLabel, 0, 8); gridPane.add(priceField, 1, 8);
        gridPane.add(dateLabel, 0, 9); gridPane.add(dateField, 1, 9);
        gridPane.add(timeLabel, 0, 10); gridPane.add(choiceTime, 1, 10);        
        gridPane.add(typeLabel, 0, 11); gridPane.add(choiceType, 1, 11);
        
        gridPane.add(hbox, 1, 12, 3, 1); gridPane.add(vbox, 1, 13, 3, 1);
        
        Rectangle rect = new Rectangle(450, 450);
        Color rectColor = Color.rgb(0, 0, 0, 0.7); 
        rect.setFill(rectColor);
        rect.setArcWidth(20);
        rect.setArcHeight(20);

        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(rect, gridPane);

        StackPane.setMargin(gridPane, new Insets(10));

        Scene scene = new Scene(stackPane, 1000, 650);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Formulaire");
		primaryStage.setScene(scene);
		primaryStage.show();
		return scene;
    }
    
    //SWITCH BETWEEN SCENES
    public void switchTableView() {
    	tableViewContent tableViewContent = new tableViewContent();
        Scene tableViewScene = tableViewContent.createScene(primaryStage);
        primaryStage.setScene(tableViewScene);
        primaryStage.show();
    }
    
    private Connection connection;

    public CRUDApplication() {
        try {
			connection = jdbc.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void add() {
    	String Id ,Name, Type, Time, Price, Phone, Email;
    	LocalDate sqlDate;
    	Id = idField.getText();
        Name = nameField.getText();
        Phone = phoneField.getText();
        Email = emailField.getText();
        sqlDate = dateField.getValue();
        Type = choiceType.getValue();
        Time = choiceTime.getValue();
        Price = priceField.getText();
        
        java.sql.Date sqlDate1 = java.sql.Date.valueOf(sqlDate);
        addingEvent(Id, Name, Type, sqlDate1, Time, Price, Phone, Email);
    }
    
    public void addingEvent(String Id, String Name, String Type, Date sqlDate, String Time, String Price, String Phone, String Email) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ceremony (id, name, type, date, time, price, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, Id);
            statement.setString(2, Name);
            statement.setString(3, Type);
            statement.setDate(4, sqlDate);
            statement.setString(5, Time);
            statement.setString(6, Price);
            statement.setString(7, Phone);
            statement.setString(8, Email);
            statement.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Organizer Registation");
            alert.setHeaderText("Organizer Registation");
            alert.setContentText("Record added!");
            alert.showAndWait();
            
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void switchStats() {
    	primaryStage.setScene(stats(primaryStage));
        primaryStage.show();
    }
    
    public Scene stats(Stage primaryStage) {
    	
    	stats_db();
		Label header = new Label("Business statistics");
		header.getStyleClass().add("my-bussiness-title");
        header.setStyle("-fx-underline: true;");

		Label reservations = new Label("Number of reservations: "); 
		reservations.setStyle("-fx-font-family: Arial; -fx-font-size: 20px; -fx-padding: 5px;");
		Label num_res = new Label(" "+resTotal);
		num_res.setStyle("-fx-font-family: Arial; -fx-font-size: 20px;");
		
		Label profit = new Label("Money gained: "); 
		profit.setStyle("-fx-font-family: Arial; -fx-font-size: 20px; -fx-padding: 5px;");
		Label num_prf = new Label(" "+priceTotal+"$");
		num_prf.setStyle("-fx-font-family: Arial; -fx-font-size: 20px;");
		
		Hyperlink form = new Hyperlink("Form");
		form.setOnAction(e->{
			createAnotherScene(primaryStage, selectedId);
		});
		form.setStyle("-fx-text-decoration: none; -fx-border-color: transparent; -fx-font-size: 15px; -fx-padding: 5px;");

		VBox vbox = new VBox();
        vbox.getChildren().add(form);
        vbox.setAlignment(Pos.CENTER);
		
		GridPane GP = new GridPane();
        GP.setHgap(10);
        GP.setVgap(10);
        GP.setAlignment(Pos.CENTER);
        GP.addColumn(0, reservations, profit, vbox);
        GP.addColumn(1, num_res, num_prf);
        
        Rectangle rect = new Rectangle(400, 190);
        Color rectColor = Color.rgb(255, 255, 255, 0.7); 
        rect.setFill(rectColor);
        rect.setArcWidth(20);
        rect.setArcHeight(20);

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(rect, GP);

        StackPane.setMargin(GP, new Insets(10));

        BorderPane BP = new BorderPane();
        
        String imagePath = "resources/background-image/money_jfx.jpeg"; 
        Image image = new Image(imagePath);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        BP.setBackground(background);
        
        BP.setTop(header);
        BP.setPadding(new Insets(75));
		BorderPane.setAlignment(header, Pos.TOP_CENTER);
		
		BP.setCenter(stack);
		
		
		Scene scene = new Scene(BP, 1000, 600);
		
		scene.getStylesheets().add(getClass().getResource("bussiness.css").toExternalForm());
		
    	return scene;
    }
    
    int resTotal;
    double priceTotal;
    
    public void stats_db() {

		try {
	        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*), SUM(CAST(price AS DECIMAL(10,2))) FROM ceremony");
	        ResultSet resultSet = statement.executeQuery();
	
			if (resultSet.next()) {
			    resTotal = resultSet.getInt(1);
			    priceTotal = resultSet.getDouble(2);
			}
		} catch (SQLException e){
            e.printStackTrace();
        }	
	}
    
    int myIndex, id;
    public void update(StringProperty selectedId) {
		
    	String Id ,Name, Type, Time, Price, Phone, Email;
    	LocalDate sqlDate;
        
        Id = idField.getText();
        Name = nameField.getText();
        Phone = phoneField.getText();
        Email = emailField.getText();
        sqlDate = dateField.getValue();
        Type = choiceType.getValue();
        Time = choiceTime.getValue();
        Price = priceField.getText();
        java.sql.Date sqlDate1 = java.sql.Date.valueOf(sqlDate);
       try{
    	   String id = selectedId.getValue();
           PreparedStatement statement = connection.prepareStatement("UPDATE ceremony SET id=?, name=?, type=?, date=?, time=?, price=?, phone=?, email=? where id = ? ");
           statement.setString(1, Id);
           statement.setString(2, Name);
           statement.setString(3, Type);
           statement.setDate(4, sqlDate1);
           statement.setString(5, Time);
           statement.setString(6, Price);
           statement.setString(7, Phone);
           statement.setString(8, Email);
           statement.setString(9, id);
           statement.executeUpdate();
           
	       Alert alert = new Alert(Alert.AlertType.INFORMATION);
	       alert.setTitle("Organizer Registation");
	       alert.setHeaderText("Organizer Registation");
	       alert.setContentText("Updated!");
	       alert.showAndWait();
       }
       catch (SQLException ex)
       {
           ex.printStackTrace();;
       }
    }
    
}

