package org.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private AccountManager accountManager = new AccountManager();
    VBox loginForm, signUpForm, title;

    @Override
    public void start(Stage stage){
        loginForm = createLoginForm();
        title = titleForm();
        signUpForm = createRegisterForm();

        StackPane root = new StackPane();
        StackPane.setAlignment(loginForm, Pos.CENTER_RIGHT);
        StackPane.setAlignment(signUpForm, Pos.CENTER_RIGHT);
        StackPane.setAlignment(title, Pos.CENTER_LEFT);

        loginForm.setMaxSize(300, 200);
        signUpForm.setMaxSize(300, 200);
        title.setMaxSize(300, 600);

        signUpForm.setVisible(false);
        root.getChildren().addAll(title, loginForm, signUpForm);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Cafe Shop Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox createLoginForm() {
        VBox login = new VBox(20);
        login.setPadding(new Insets(10, 50, 50, 50));
        login.setAlignment(Pos.CENTER_LEFT);

        Label loginTitle = new Label("Login Account");
        loginTitle.getStyleClass().add("login-title");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(30);
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(30);
        passwordField.setPrefWidth(200);

        // TextField for showing password dynamically
        TextField passwordTextField = new TextField();
        passwordTextField.setManaged(false); // Not visible initially
        passwordTextField.setVisible(false);

        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setStyle("-fx-text-fill: #000; -fx-font-family: \"Arial\";");

        // Synchronize the text between the PasswordField and TextField
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Toggle visibility on checkbox
        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                passwordTextField.setVisible(true);
                passwordTextField.setManaged(true);
            } else {
                passwordTextField.setVisible(false);
                passwordTextField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.getStyleClass().add("forgot-password-link");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button-login");
        loginButton.setPrefHeight(30);
        loginButton.setPrefWidth(200);

        Label warning = new Label();

        // Event handler for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                warning.setText("Please fill in all fields.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
                return;
            }
            if (accountManager.verify(username, password)) {
                warning.setText("Login successful!");
                warning.setStyle("-fx-text-fill: green; -fx-font-family: \"Arial\";");
                //showDashboard();

            } else {
                warning.setText("Invalid username or password.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
            }
        });

        login.getChildren().addAll(loginTitle, usernameField, passwordField, passwordTextField, showPasswordCheckBox, forgotPasswordLink, loginButton, warning);
        login.setMargin(loginTitle, new Insets(30, 0, 0, 0));
        return login;
    }


    private VBox createRegisterForm() {
        VBox register = new VBox(20);
        register.setPadding(new Insets(50, 50, 50, 50));
        register.setAlignment(Pos.CENTER_LEFT);

        Label registerTitle = new Label("Register Account");
        registerTitle.getStyleClass().add("register-title");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(30);
        usernameField.setPrefWidth(200);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefHeight(30);
        emailField.setPrefWidth(200);

        // Password fields
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(30);
        passwordField.setPrefWidth(200);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setPrefHeight(30);
        confirmPasswordField.setPrefWidth(200);

        // TextFields for showing passwords dynamically
        TextField passwordTextField = new TextField();
        passwordTextField.setManaged(false);
        passwordTextField.setVisible(false);

        TextField confirmPasswordTextField = new TextField();
        confirmPasswordTextField.setManaged(false);
        confirmPasswordTextField.setVisible(false);

        // Synchronize text properties
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(confirmPasswordField.textProperty());

        // Checkbox to toggle password visibility
        CheckBox showPasswordCheckBox = new CheckBox("Show Passwords");
        showPasswordCheckBox.setStyle("-fx-text-fill: #000; -fx-font-family: \"Arial\";");

        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                confirmPasswordField.setVisible(false);
                confirmPasswordField.setManaged(false);

                passwordTextField.setVisible(true);
                passwordTextField.setManaged(true);
                confirmPasswordTextField.setVisible(true);
                confirmPasswordTextField.setManaged(true);
            } else {
                passwordTextField.setVisible(false);
                passwordTextField.setManaged(false);
                confirmPasswordTextField.setVisible(false);
                confirmPasswordTextField.setManaged(false);

                passwordField.setVisible(true);
                passwordField.setManaged(true);
                confirmPasswordField.setVisible(true);
                confirmPasswordField.setManaged(true);
            }
        });

        Button signupButton = new Button("Signup");
        signupButton.getStyleClass().add("button-login");
        signupButton.setPrefHeight(30);
        signupButton.setPrefWidth(200);

        Label warning = new Label();

        // Event handler for signup button
        signupButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText(); // Get the password from the PasswordField
            String confirmPassword = confirmPasswordField.getText(); // Get confirm password from PasswordField

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                warning.setText("Please fill in all fields.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
                return;
            }
            if (password.length() < 8) {
                warning.setText("Password must be at least 8 characters long.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
                return;
            }
            if (!password.equals(confirmPassword)) {
                warning.setText("Passwords do not match.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
                return;
            }
            if (accountManager.register(username, password, email)) {
                warning.setText("Account created successfully!");
                warning.setStyle("-fx-text-fill: green; -fx-font-family: \"Arial\";");
                loginForm.setVisible(true);
                signUpForm.setVisible(false);
            } else {
                warning.setText("Username already exists.");
                warning.setStyle("-fx-text-fill: red; -fx-font-family: \"Arial\";");
            }
        });

        register.getChildren().addAll(registerTitle, usernameField, emailField, passwordField, passwordTextField,
                confirmPasswordField, confirmPasswordTextField, showPasswordCheckBox, signupButton, warning);

        return register;
    }


    private VBox titleForm() {
        VBox switchPane = new VBox(20);
        switchPane.setAlignment(Pos.CENTER);
        switchPane.getStyleClass().add("shift-pane");

        Label logo = new Label("\uD83C\uDF7D");
        logo.setStyle("-fx-font-size: 100; -fx-text-fill: white;");

        Label systemTitle = new Label("Food Hive");
        systemTitle.getStyleClass().add("title");

        Button createAccountButton = new Button("Create new Account");
        createAccountButton.getStyleClass().add("button-create-account");
        createAccountButton.setOnAction(e -> {
            signUpForm.setVisible(true);
            loginForm.setVisible(false);
        });

        Button alreadyAccountButton = new Button("Already have Account");
        alreadyAccountButton.getStyleClass().add("button-create-account");
        alreadyAccountButton.setOnAction(e -> {
            loginForm.setVisible(true);
            signUpForm.setVisible(false);
        });

        switchPane.getChildren().addAll(logo, systemTitle, createAccountButton, alreadyAccountButton);
        return switchPane;
    }

    
    public class Item {

        private final String name;
        private final int quantity;
        private final double price;

        public Item(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}