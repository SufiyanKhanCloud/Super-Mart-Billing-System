package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class SuperMartBillingSystemGUI extends Application {

    private static final String[] categories = {
            "Beverages",
            "Fruits and Vegetables",
            "Dairy Products",
            "Bakery and Breakfast",
            "Noodles and Pasta",
            "Snacks and Confectionery"
    };

    private static final String[][] items = {
            {"Mineral Water (Rs 125)", "Soft Drink (Rs 80)", "Juice (Rs 375)", "Energy Drink (Rs 180)", "Sparkling Water (Rs 545)"},
            {"Bananas (Rs 250)", "Avocado (Rs 950)", "Coconut (Rs 600)", "Fresh Parsley Leaves (Rs 200)", "Brinjals (Rs 60)", "Iceberg (Rs 250)"},
            {"Eggs (Rs 520)", "Flavoured Milk (Rs 400)", "Yogurt (Rs 105)", "Processed Cheese (Rs 750)", "Cream Cheese (Rs 1050)", "Dairy Cream (Rs 220)"},
            {"Apricot Jam (Rs 350)", "Honey (Rs 1495)", "Butter (Rs 600)", "Chicken Spread (Rs 695)", "Coconut Oil (Rs 999)", "Wheat Porridge (Rs 195)", "Organic Oats (Rs 270)", "Bread (Rs 250)", "Fruits and Nuts Granola Bar (Rs 180)"},
            {"Korean 2x Noodles (Rs 510)", "Big Elbow Pasta (Rs 195)", "Kolson Spaghetti (Rs 265)", "Ramen Noodle Soup (Rs 450)", "Vermicelli (Rs 65)"},
            {"Chips (Rs 80)", "Nimco and Crackers (Rs 215)", "Biscuits (Rs 310)", "Cookies (Rs 210)", "Wafers (Rs 115)", "Candies (Rs 50)", "Mint and Gums (Rs 1110)", "Popcorns (Rs 180)", "Fresh Cake (Rs 320)", "Dried Fruits (Rs 970)", "Nuts (Rs 1180)"}
    };

    private static final int[][] prices = {
            {125, 80, 375, 180, 545},
            {250, 950, 600, 200, 60, 250},
            {520, 400, 105, 750, 1050, 220},
            {350, 1495, 600, 695, 999, 195, 270, 250, 180},
            {510, 195, 265, 450, 65},
            {80, 215, 310, 210, 115, 50, 1110, 180, 320, 970, 1180}
    };

    private HashMap<String, Integer> cart = new HashMap<>();
    private String userName;
    private String contactNumber;
    private String email;
    private String address;
    private Scene userDetailsScene, mainMenuScene, categoryScene, cartScene, checkoutScene;
    private List<String> previousOrders = new ArrayList<>(); // Stores receipts of past orders
    @Override
    public void start(Stage primaryStage) {
        // User Details Screen
        VBox userDetailsScreen = new VBox(10);
        userDetailsScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        Label welcomeLabel = new Label("WELCOME TO HARVEST HUB");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 38));
        welcomeLabel.setStyle("-fx-text-fill: #1a1a1a;");

        Label nameLabel = new Label("Enter your Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setOnKeyReleased(event -> {
            String text = nameField.getText();
            if (!text.matches("[a-zA-Z ]*")) {
                nameField.setText(text.replaceAll("[^a-zA-Z ]", "")); // Remove invalid characters
            }
        });

        Label contactLabel = new Label("Enter your Contact Number:");
        TextField contactField = new TextField();
        contactField.setPromptText("Enter your contact number");
        contactField.setOnKeyReleased(event -> {
            String text = contactField.getText();
            if (!text.matches("[0-9]*")) {
                contactField.setText(text.replaceAll("[^0-9]", "")); // Remove non-digit characters
            }
        });

        Label emailLabel = new Label("Enter your Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email address");

        Label addressLabel = new Label("Enter your Address:");
        TextArea addressField = new TextArea();
        addressField.setPromptText("Enter your complete address");
        addressField.setPrefRowCount(3);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");

        userDetailsScreen.getChildren().addAll(welcomeLabel, nameLabel, nameField, contactLabel, contactField, emailLabel, emailField,
                addressLabel, addressField, submitButton);

        userDetailsScene = new Scene(userDetailsScreen, 800, 600);

        // Main Menu Screen
        VBox mainMenu = new VBox(10);
        mainMenu.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        Label restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        restaurantNameLabel.setStyle("-fx-text-fill: #1a1a1a; -fx-padding: 10");

        Button userDetailsButton = new Button("User Details");
        Button browseCategoriesButton = new Button("Browse Categories");
        Button viewCartButton = new Button("View Cart");
        Button checkoutButton = new Button("Checkout");
        Button previousOrdersButton = new Button("Previous Orders");

        browseCategoriesButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");
        viewCartButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");
        previousOrdersButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");
        userDetailsButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white; -fx-font-size: 16px;");

        mainMenu.getChildren().addAll(restaurantNameLabel, userDetailsButton, browseCategoriesButton, viewCartButton, checkoutButton, previousOrdersButton);

        mainMenuScene = new Scene(mainMenu, 800, 600);

        // Categories Screen
        VBox categoriesScreen = new VBox(10);
        categoriesScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 34));

        Label categoryLabel = new Label("Choose a Category");
        categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: black;");

        ListView<String> categoryListView = new ListView<>();
        categoryListView.getItems().addAll(categories);
        Button goToCartButton = new Button("Go to Cart");
        goToCartButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        Button backFromCategoriesScene = new Button("Back To Main Menu");
        backFromCategoriesScene.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");


        categoriesScreen.getChildren().addAll(restaurantNameLabel, categoryLabel, categoryListView, goToCartButton, backFromCategoriesScene);

        categoryScene = new Scene(categoriesScreen, 800, 600);

        // Cart Screen
        VBox cartScreen = new VBox(10);
        cartScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Impact", FontWeight.BOLD, 38));

        Label cartLabel = new Label("Your Cart");
        cartLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: black;");

        ListView<String> cartListView = new ListView<>();
        Button removeItemButton = new Button("Remove Item");
        Button goToCheckoutButton = new Button("Go to Checkout");
        Button backFromCartButton = new Button("Back to Main Menu");

        removeItemButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        goToCheckoutButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        backFromCartButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");

        cartScreen.getChildren().addAll(restaurantNameLabel, cartLabel, cartListView, removeItemButton, goToCheckoutButton, backFromCartButton);

        cartScene = new Scene(cartScreen, 800, 600);

        // Checkout Screen
        VBox checkoutScreen = new VBox(10);
        checkoutScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 34));

        Label checkoutLabel = new Label("CHECKOUT");
        checkoutLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: black; ");

        Label userDetailsLabel = new Label("Name: " + userName + "\nContact: " + contactNumber + "\nEmail: " + email + "\nAddress: " + address);
        userDetailsLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: black;");
        userDetailsLabel.setAlignment(Pos.CENTER);

        Label itemsHeadingLabel = new Label("Summary:");
        itemsHeadingLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a; -fx-font-size: 13px;");

        TextArea checkoutSummary = new TextArea();
        checkoutSummary.setEditable(false);
        checkoutSummary.setStyle("-fx-font-size: 12px;");

        Button confirmOrderButton = new Button("Confirm Order");
        Button backFromCheckoutButton = new Button("Back to Main Menu");

        confirmOrderButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");
        backFromCheckoutButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");

        checkoutScreen.getChildren().addAll(restaurantNameLabel, checkoutLabel, userDetailsLabel, itemsHeadingLabel, checkoutSummary, confirmOrderButton, backFromCheckoutButton);

        checkoutScene = new Scene(checkoutScreen, 800, 600);

        //Previous Orders Screen
        VBox previousOrdersScreen = new VBox(10);
        previousOrdersScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 34));

        Label previousOrdersLabel = new Label("Your Previous Orders");
        previousOrdersLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: black;");
        Button backFromPreviousOrders = new Button("Back to Main Menu");
        backFromPreviousOrders.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");

        ListView<String> orderHistoryList = new ListView<>();
        // Populate orderHistoryList with data from your storage
        orderHistoryList.getItems().addAll(previousOrders);
        previousOrdersScreen.getChildren().addAll(restaurantNameLabel, previousOrdersLabel, orderHistoryList, backFromPreviousOrders);

        Scene previousOrdersScene = new Scene(previousOrdersScreen, 800, 600);

        // Button Actions for user details screen
        submitButton.setOnAction(e -> {
            userName = nameField.getText();
            contactNumber = contactField.getText();
            email = emailField.getText();
            address = addressField.getText();
            // Validation
            if (!userName.isEmpty() && !contactNumber.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
                // Update user details in the checkout screen
                userDetailsLabel.setText("User Details:\nName: " + userName + "\nContact: " + contactNumber +
                        "\nEmail: " + email + "\nAddress: " + address);
                primaryStage.setScene(mainMenuScene); // Navigate to the main menu if all fields are valid
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all the required fields.");
                alert.showAndWait();
            }
        });

        // Button Actions for User Details
        userDetailsButton.setOnAction(e -> {
            // Clear the user details fields
            nameField.clear();
            contactField.clear();
            emailField.clear();
            addressField.clear();
            // Clear the user details in the checkout screen (if applicable)
            userDetailsLabel.setText("User Details:\nName: \nContact: \nEmail: \nAddress: ");
            primaryStage.setScene(userDetailsScene);
        });

        // Button Actions for main menu
        browseCategoriesButton.setOnAction(e -> primaryStage.setScene(categoryScene));
        viewCartButton.setOnAction(e -> {
            cartListView.getItems().clear();
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                cartListView.getItems().add(entry.getKey() + " x" + entry.getValue());
            }
            primaryStage.setScene(cartScene);
        });

        // Action for "Go to Cart" button
        goToCartButton.setOnAction(e -> {
            // Update the cartListView with the current items in the cart
            cartListView.getItems().clear();
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                cartListView.getItems().add(entry.getKey() + " x" + entry.getValue());
            }
            // Switch to the cart scene
            primaryStage.setScene(cartScene);
        });
        // Populate the cart list
        cartListView.setOnMouseClicked(e -> {
            if (cartListView.getSelectionModel().getSelectedIndex() != -1) {
                removeItemButton.setDisable(false); // Enable the remove button when an item is selected
            }
        });

        // Action for Remove Item Button
        removeItemButton.setOnAction(e -> {
            String selectedItem = cartListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Show confirmation dialog before removing the item
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Item");
                alert.setHeaderText("Remove Item from Cart");
                alert.setContentText("Are you sure you want to remove \"" + selectedItem.split(" x")[0] + "\" from your cart?");

                // Wait for user response
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Remove the item from the cart and update the ListView
                        cart.remove(selectedItem.split(" x")[0]); // Remove the item based on its name
                        cartListView.getItems().remove(selectedItem); // Update the ListView
                        removeItemButton.setDisable(true); // Disable the remove button after removing the item

                        // Show an informational alert after the item is removed
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION,
                                "\"" + selectedItem.split(" x")[0] + "\" has been removed from your cart.");
                        infoAlert.showAndWait();
                    }
                });
            }
        });

        checkoutButton.setOnAction(e -> {
            // Update user details in the checkout screen
            userDetailsLabel.setText("User Details:\nName: " + userName + "\nContact: " + contactNumber +
                    "\nEmail: " + email + "\nAddress: " + address);
            StringBuilder summary = new StringBuilder();
            int total = 0;

            // Iterate over cart and display item names, prices, and quantities
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String item = entry.getKey();
                int quantity = entry.getValue();
                int price = getPrice(item);

                // Append item name, quantity, and price to the summary
                summary.append(item).append(" x").append(quantity).append(" (Rs ").append(price).append(" each)\n");
                total += price * quantity; // Accumulate total price
            }

            // Calculate GST (18%)
            double gstRate = 0.18;
            double gstAmount = total * gstRate;
            double totalWithGst = total + gstAmount;

            // Append total and GST details
            summary.append("\nTotal (Excluding GST): Rs ").append(total);
            summary.append("\nGST (18%): Rs ").append(String.format("%.2f", gstAmount));
            summary.append("\nTotal (Including GST): Rs ").append(String.format("%.2f", totalWithGst));

            checkoutSummary.setText(summary.toString());
            primaryStage.setScene(checkoutScene);
        });

        // Button Actions for category screen
        categoryListView.setOnMouseClicked(e -> {
            if (categoryListView.getSelectionModel().getSelectedIndex() != -1) {
                int selectedCategory = categoryListView.getSelectionModel().getSelectedIndex();
                showItemsForCategory(primaryStage, selectedCategory);
            }
        });

        // Button Actions for cart screen
        goToCheckoutButton.setOnAction(e -> {
            StringBuilder summary = new StringBuilder();
            int total = 0;

            // Iterate over cart and display item names, prices, and quantities
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String item = entry.getKey();
                int quantity = entry.getValue();
                int price = getPrice(item);

                // Append item name, quantity, and price to the summary
                summary.append(item).append(" x").append(quantity).append(" (Rs ").append(price).append(" each)\n");
                total += price * quantity; // Accumulate total price
            }

            // Calculate GST (18%)
            double gstRate = 0.18;
            double gstAmount = total * gstRate;
            double totalWithGst = total + gstAmount;

            // Append total and GST details
            summary.append("\nTotal (Excluding GST): Rs ").append(total);
            summary.append("\nGST (18%): Rs ").append(String.format("%.2f", gstAmount));
            summary.append("\nTotal (Including GST): Rs ").append(String.format("%.2f", totalWithGst));

            // Update user details label
            userDetailsLabel.setText("User Details:\nName: " + userName + "\nContact: " + contactNumber +
                    "\nEmail: " + email + "\nAddress: " + address);

            checkoutSummary.setText(summary.toString());
            primaryStage.setScene(checkoutScene);
        });

        // Action for the "Previous Orders" button
        previousOrdersButton.setOnAction(e -> {

            // Update orderHistoryList with the latest receipts
            orderHistoryList.getItems().clear();
            // orderHistoryList.getItems().addAll(previousOrders);
            for (String receipt : previousOrders) {
                orderHistoryList.getItems().add("---------------------------------------------------\n" + receipt + "\n---------------------------------------------------");
            }
            primaryStage.setScene(previousOrdersScene);
        });

        // Button Actions for confirm order
        confirmOrderButton.setOnAction(e -> {
            // Show a confirmation alert before confirming the order
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Order");
            confirmationAlert.setHeaderText("Are you sure you want to confirm your order?");
            confirmationAlert.setContentText("Once confirmed, you cannot modify your cart.");

            // Show the alert and wait for user response
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Build the receipt with ordered items and total
                    StringBuilder receipt = new StringBuilder();

                    // Add user details to the top of the receipt
                    receipt.append("Name: ").append(userName).append("\n")
                            .append("Contact: ").append(contactNumber).append("\n")
                            .append("Email: ").append(email).append("\n")
                            .append("Address: ").append(address).append("\n\n");

                    receipt.append("Ordered Items:\n");

                    int total = 0;

                    // Iterate over cart and display item names, prices, and quantities
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        String item = entry.getKey();
                        int quantity = entry.getValue();
                        int price = getPrice(item);

                        // Append item name, quantity, and price to the receipt
                        receipt.append(item).append(" x").append(quantity).append(" (Rs ").append(price).append(" each)\n");
                        total += price * quantity; // Accumulate total price
                    }

                    // Calculate GST (18%)
                    double gstRate = 0.18;
                    double gstAmount = total * gstRate;
                    double totalWithGst = total + gstAmount;

                    // Append total and GST details
                    receipt.append("\nTotal (Excluding GST): Rs ").append(total);
                    receipt.append("\nGST (18%): Rs ").append(String.format("%.2f", gstAmount));
                    receipt.append("\nTotal (Including GST): Rs ").append(String.format("%.2f", totalWithGst));

                    // Store the complete receipt in the previousOrders list
                    previousOrders.add(receipt.toString());

                    // Show the receipt in an alert
                    Alert orderConfirmedAlert = new Alert(Alert.AlertType.INFORMATION);
                    orderConfirmedAlert.setTitle("Order Confirmation");
                    orderConfirmedAlert.setHeaderText("Your order has been confirmed!");
                    orderConfirmedAlert.setContentText("Receipt:\n" + receipt.toString()+"\n\nThank you for shopping with us!");
                    orderConfirmedAlert.showAndWait();

                    // Clear the cart and reset the checkout screen
                    cart.clear(); // Clear the cart data
                    checkoutSummary.clear(); // Clear the checkout summary
                    primaryStage.setScene(mainMenuScene); // After confirming, go back to the main menu

                    // Clear user input fields (optional)
                    nameField.clear();
                    contactField.clear();
                    emailField.clear();
                    addressField.clear();
                }
            });
        });

        // Button Actions for back buttons
        backFromCategoriesScene.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        backFromPreviousOrders.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        backFromCartButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        backFromCheckoutButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        primaryStage.setScene(userDetailsScene);
        primaryStage.setTitle("Super Mart Billing System");
        primaryStage.show();
    }

    private void showItemsForCategory(Stage primaryStage, int categoryIndex) {
        VBox itemScreen = new VBox(10);
        itemScreen.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #D8C9A3;");

        Label restaurantNameLabel = new Label("HARVEST HUB\n\n");
        restaurantNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 34));

        Label itemLabel = new Label("Items in " + categories[categoryIndex]);
        itemLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: black;");

        ListView<String> itemListView = new ListView<>();
        itemListView.getItems().addAll(items[categoryIndex]);

        Button backButton = new Button("Back to Categories");
        backButton.setStyle("-fx-background-color: #808000; -fx-text-fill: white;");

        itemScreen.getChildren().addAll(restaurantNameLabel, itemLabel, itemListView, backButton);

        itemListView.setOnMouseClicked(e -> {
            if (itemListView.getSelectionModel().getSelectedIndex() != -1) {
                String selectedItem = itemListView.getSelectionModel().getSelectedItem();
                int price = getPrice(selectedItem);

                // Display an alert before adding the item to the cart
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add to Cart");
                alert.setHeaderText("Add Item to Cart");
                alert.setContentText("Do you want to add \"" + selectedItem + "\" to your cart?");

                // Show the alert and wait for user confirmation
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        cart.put(selectedItem, cart.getOrDefault(selectedItem, 0) + 1);
                        Alert confirmation = new Alert(Alert.AlertType.INFORMATION,
                                "\"" + selectedItem + "\" has been added to your cart.");
                        confirmation.showAndWait();
                    }
                });
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(categoryScene));

        Scene itemScene = new Scene(itemScreen, 800, 600);
        primaryStage.setScene(itemScene);
    }

    private int getPrice(String item) {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j].equals(item)) {
                    return prices[i][j];
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

