package app;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartController {

    @FXML public ComboBox<String> languageSelector;
    @FXML public Label labelNumItems, labelTotal;
    @FXML public TextField numItemsField;
    @FXML public VBox itemsContainer;

    private ResourceBundle bundle;
    private Locale currentLocale;

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageSelector.setValue("English");
        loadLanguage("English");

        languageSelector.setOnAction(e -> {
            loadLanguage(languageSelector.getValue());
            updateTexts();
        });
    }

    private void loadLanguage(String lang) {
        switch (lang) {
            case "Finnish" -> currentLocale = new Locale("fi", "FI");
            case "Swedish" -> currentLocale = new Locale("sv", "SE");
            case "Japanese" -> currentLocale = new Locale("ja", "JP");
            case "Arabic" -> currentLocale = new Locale("ar", "AR");
            default -> currentLocale = new Locale("en", "US");
        }
        bundle = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }

    private void updateTexts() {
        labelNumItems.setText(bundle.getString("enter_items"));
        labelTotal.setText(bundle.getString("total_cost") + ":");
        generateItemFields();
    }

    @FXML
    public void generateItemFields() {
        itemsContainer.getChildren().clear();

        if (numItemsField.getText().isEmpty()) return;

        int count;
        try {
            count = Integer.parseInt(numItemsField.getText());
        } catch (Exception e) {
            return;
        }

        for (int i = 0; i < count; i++) {
            HBox row = new HBox(10);

            Label priceLabel = new Label(bundle.getString("enter_price"));
            TextField priceField = new TextField();

            Label qtyLabel = new Label(bundle.getString("enter_quantity"));
            TextField qtyField = new TextField();

            row.getChildren().addAll(priceLabel, priceField, qtyLabel, qtyField);
            itemsContainer.getChildren().add(row);
        }
    }

    @FXML
    public void calculateTotal() {
        double total = 0;

        for (Node node : itemsContainer.getChildren()) {
            HBox row = (HBox) node;

            TextField price = (TextField) row.getChildren().get(1);
            TextField qty = (TextField) row.getChildren().get(3);

            total += Double.parseDouble(price.getText()) *
                    Integer.parseInt(qty.getText());
        }

        labelTotal.setText(bundle.getString("total_cost") + ": " + total);
    }
}