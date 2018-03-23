package no.ntnu.vislab.simulator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class App extends Application {
    private boolean running = false;
    private Projector projector;
    private ArrayList<TextField> fields;
    private ArrayList<String> activeFilters;
    private ComboBox<String> filterList;
    private ObservableList<Log> logs;
    private ObservableList<String> filters;
    private Server server;
    private ArrayList<ProjectorCommunicator> dummies;
    private ArrayList<Log> rawLog = new ArrayList<>();
    private ListView<Log> logView;
    private Text numProjectors;
    private long time = System.currentTimeMillis();
    private Logger logger;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Projector Simulator");
        BorderPane root = new BorderPane();
        logView = new ListView<>();
        logView.setCellFactory(list -> new LogCell());
        projector = new Projector();
        ScrollPane scrollPane = new ScrollPane(logView);
        scrollPane.fitToHeightProperty().setValue(true);
        scrollPane.fitToWidthProperty().setValue(true);
        root.setCenter(scrollPane);


        numProjectors = new Text("0");
        VBox top = new VBox();
        HBox mainBar = new HBox();
        Text numbProjectorsText = new Text("Number of projector threads: ");
        Tooltip t = new Tooltip();
        numbProjectorsText.setOnMouseClicked(e->{
            Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
            popup.setContentText("Disconnect all connections?");
            popup.showAndWait();
            if(popup.getResult().equals(ButtonType.OK)){
                dummies.forEach(ProjectorCommunicator::stopRunning);
            }
        });
        numbProjectorsText.setOnMouseEntered(e->{
            StringBuilder str = new StringBuilder();
            dummies.forEach(d-> str.append(d.getHostIp() + "\n"));
            t.setText(str.toString());
            if(!t.getText().isEmpty()){
                Tooltip.install(numbProjectorsText,t);
            }
        });
        numbProjectorsText.setOnMouseExited(e->{
            Tooltip.uninstall(numbProjectorsText, t);
        });
        mainBar.getChildren().addAll(numbProjectorsText, numProjectors);
        root.setTop(top);
        HBox bottom = new HBox();
        Button clearLog = new Button("Clear log");
        clearLog.setOnAction(event -> clearLog());
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottom.getChildren().addAll(spacer, clearLog);
        root.setBottom(bottom);
        VBox left = new VBox();
        left.getChildren().addAll(setUpProjector(), setUpResponses());
        updateFields();
        root.setLeft(left);
        activeFilters = new ArrayList<>();
        logs = FXCollections.observableArrayList();
        filters = FXCollections.observableArrayList();
        filterList = new ComboBox<>(filters);
        Pane spacer2 = new Pane();
        mainBar.getChildren().add(spacer2);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        mainBar.getChildren().addAll(new Text("Filters"), filterList);
        HBox subBar = new HBox();
        subBar.setPrefHeight(25);
        top.getChildren().addAll(mainBar, subBar);
        filterList.setPrefWidth(200);
        filterList.setOnAction(event -> {
            String filter = filterList.getSelectionModel().getSelectedItem();
            if (filter != null && !filter.isEmpty()) {
                if (!activeFilters.contains(filter)) {
                    Button button = new Button(filter);
                    button.setOnAction(e -> {
                        subBar.getChildren().remove(button);
                        activeFilters.remove(filter);
                        updateView();
                    });
                    subBar.getChildren().add(button);
                    activeFilters.add(filter);
                    updateView();
                }
                Platform.runLater(() -> filterList.getSelectionModel().clearSelection());
            }
        });
        logView.setItems(logs);
        logView.setCellFactory((Callback<ListView<Log>, ListCell<Log>>) e -> new LogCell());
        dummies = new ArrayList<>();
        server = new Server(projector, d -> handleDummy(d), this::updateFields);
        server.start();
        running = true;
        logger = new Logger();
        logger.start();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void handleDummy(ProjectorCommunicator d) {
        dummies.add(d);
    }

    private void addToLog(Log log) {
        if (!filters.contains(log.getTag())) {
            filters.add(log.getTag());
        }
        if (!filters.contains(log.getThreadId())) {
            filters.add(log.getThreadId());
        }
        Collections.sort(filters);
        rawLog.add(log);
        updateView();
    }

    private void clearLog() {
        time = System.currentTimeMillis();
        logs.clear();
        rawLog.clear();
        filters.clear();
        logs.setAll(rawLog);
        addToLog(new Log(new Date(), Thread.currentThread().getName(), Log.SYSTEM, "  CLEARED LOG"));
        updateView();
    }

    private void updateView() {
        if(activeFilters.isEmpty()){
            logs.clear();
            logs.addAll(rawLog);
        } else {
            final ArrayList<Log> temp = new ArrayList<>();
            for (Log log : rawLog) {
                for (String activeFilter : activeFilters) {
                    if(!temp.contains(log) &&log.hasTag(activeFilter)){
                        temp.add(log);
                    }
                }
            }
            logs.clear();
            logs.addAll(temp);
        }
    }

    private GridPane setUpResponses() {
        GridPane pane = new GridPane();
        Text responseText = new Text("Response type");
        Text probText = new Text("Relative Probability");
        pane.add(responseText, 0, 0);
        pane.add(probText, 1, 0);
        Text normalText = new Text("Normal response");
        Text corruptText = new Text("Corrupt response");
        Text delayedText = new Text("Delayed response");
        Text incorrectText = new Text("Incorrect response");
        Text noText = new Text("No response");
        pane.add(normalText, 0, 1);
        pane.add(corruptText, 0, 2);
        pane.add(delayedText, 0, 3);
        pane.add(incorrectText, 0, 4);
        pane.add(noText, 0, 5);

        TextField normalField = new TextField();
        TextField corruptField = new TextField();
        TextField delayedField = new TextField();
        TextField incorrectField = new TextField();
        TextField noField = new TextField();
        normalField.setId("NORMAL");
        corruptField.setId("CORRUPT");
        delayedField.setId("DELAY");
        incorrectField.setId("INCORRECT");
        noField.setId("NO RESPONSE");
        fields.addAll(Arrays.asList(normalField, corruptField, delayedField, incorrectField, noField));


        pane.add(normalField, 1, 1);
        pane.add(corruptField, 1, 2);
        pane.add(delayedField, 1, 3);
        pane.add(incorrectField, 1, 4);
        pane.add(noField, 1, 5);

        Button normalButton = new Button("SET");
        Button corruptButton = new Button("SET");
        Button delayedButton = new Button("SET");
        Button incorrectButton = new Button("SET");
        Button noButton = new Button("SET");

        normalButton.setOnAction(event -> {
            try {
                int num = Integer.parseInt(normalField.getText());
                ProjectorCommunicator.setNormalWeight(num);
                normalField.setText("");
                updateFields();
            } catch (NumberFormatException ex) {
                alert(ex, normalField);
            }
        });
        corruptButton.setOnAction(event -> {
            try {
                int num = Integer.parseInt(corruptField.getText());
                ProjectorCommunicator.setCorruptWeight(num);
                corruptField.setText("");
                updateFields();
            } catch (NumberFormatException ex) {
                alert(ex, corruptField);
            }
        });
        delayedButton.setOnAction(event -> {
            try {
                int num = Integer.parseInt(delayedField.getText());
                ProjectorCommunicator.setDelayedWeight(num);
                delayedField.setText("");
                updateFields();
            } catch (NumberFormatException ex) {
                alert(ex, delayedField);
            }
        });
        incorrectButton.setOnAction(event -> {
            try {
                int num = Integer.parseInt(incorrectField.getText());
                ProjectorCommunicator.setIncorrectWeight(num);
                incorrectField.setText("");
                updateFields();
            } catch (NumberFormatException ex) {
                alert(ex, incorrectField);
            }
        });
        noButton.setOnAction(event -> {
            try {
                int num = Integer.parseInt(noField.getText());
                ProjectorCommunicator.setNoResponseWeight(num);
                noField.setText("");
                updateFields();
            } catch (NumberFormatException ex) {
                alert(ex, noField);
            }
        });
        pane.add(normalButton, 2, 1);
        pane.add(corruptButton, 2, 2);
        pane.add(delayedButton, 2, 3);
        pane.add(incorrectButton, 2, 4);
        pane.add(noButton, 2, 5);
        return pane;
    }

    private void alert(NumberFormatException ex, TextField field) {
        field.setText("");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("ERROR: " + ex.getMessage());
        alert.showAndWait();
        updateFields();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        running = false;
        logger.join();
        server.stopRunning();
        dummies.forEach(ProjectorCommunicator::stopRunning);
    }

    public void updateFields() {
        fields.forEach(textField -> updateField(textField, textField.getId()));
    }

    public void updateField(TextField tf, String id) {
        switch (id) {
            case "POWER":
                tf.setPromptText("" + projector.getPower());
                break;
            case "POWER STATE":
                tf.setPromptText("" + projector.getPowerState());
                break;
            case "MUTE":
                tf.setPromptText("" + projector.getMute());
                break;
            case "BRIGHTNESS":
                tf.setPromptText("" + projector.getBrightness());
                break;
            case "CONTRAST":
                tf.setPromptText("" + projector.getContrast());
                break;
            case "LAMP 1 RUNTIME":
                tf.setPromptText("" + projector.getLamp1Runtime());
                break;
            case "LAMP 2 RUNTIME":
                tf.setPromptText("" + projector.getLamp2Runtime());
                break;
            case "LAMP 1 STATUS":
                tf.setPromptText("" + projector.getLamp1Status());
                break;
            case "LAMP 2 STATUS":
                tf.setPromptText("" + projector.getPower());
                break;
            case "LAMP 1 TIME REMAINING":
                tf.setPromptText("" + projector.getLamp1TimeRemaining());
                break;
            case "LAMP 2 TIME REMAINING":
                tf.setPromptText("" + projector.getLamp2TimeRemaining());
                break;
            case "THERMAL":
                tf.setPromptText("" + projector.getThermal());
                break;
            case "TEST IMAGE":
                tf.setPromptText("" + projector.getTestImage());
                break;
            case "UNIT TOTAL TIME":
                tf.setPromptText("" + projector.getTotalRuntime());
                break;
            case "NORMAL":
                tf.setPromptText("" + ProjectorCommunicator.getNormalWeight());
                break;
            case "DELAY":
                tf.setPromptText("" + ProjectorCommunicator.getDelayedWeight());
                break;
            case "CORRUPT":
                tf.setPromptText("" + ProjectorCommunicator.getCorruptWeight());
                break;
            case "INCORRECT":
                tf.setPromptText("" + ProjectorCommunicator.getIncorrectWeight());
                break;
            case "NO RESPONSE":
                tf.setPromptText("" + ProjectorCommunicator.getNoResponseWeight());
                break;
        }
    }

    private GridPane setUpProjector() {
        GridPane pane = new GridPane();
        fields = new ArrayList<>();
        Text t1 = new Text("POWER");
        final TextField tf1 = new TextField();
        tf1.setId("POWER");
        Button b1 = new Button("SET");
        b1.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf1.getText());
                if (num > 1 || num < 0) {
                    throw new NumberFormatException("Number must be between 1 and 0");
                }
                projector.setPower(num);
                tf1.setText("");
                tf1.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf1.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf1.setPromptText("" + projector.getPower());
            }
        });
        pane.add(t1, 0, 0);
        pane.add(tf1, 1, 0);
        pane.add(b1, 2, 0);
        fields.add(tf1);
        Text t2 = new Text("POWER STATE");
        final TextField tf2 = new TextField();
        tf2.setId("POWER STATE");
        Button b2 = new Button("SET");
        b2.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf2.getText());
                if (num > 6 || num < 0) {
                    throw new NumberFormatException("Number must be between 6 and 0");
                }
                projector.setPowerState(num);
                tf2.setText("");
                tf2.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf2.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf2.setPromptText("" + projector.getPowerState());
            }
        });
        pane.add(t2, 0, 1);
        pane.add(tf2, 1, 1);
        pane.add(b2, 2, 1);
        fields.add(tf2);
        Text t3 = new Text("MUTE");
        final TextField tf3 = new TextField();
        tf3.setId("MUTE");
        Button b3 = new Button("SET");
        b3.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf3.getText());
                if (num > 1 || num < 0) {
                    throw new NumberFormatException("Number must be between 1 and 0");
                }
                projector.setMute(num);
                tf3.setText("");
                tf3.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf3.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf3.setPromptText("" + projector.getMute());
            }
        });
        pane.add(t3, 0, 2);
        pane.add(tf3, 1, 2);
        pane.add(b3, 2, 2);
        fields.add(tf3);
        Text t4 = new Text("BRIGHTNESS");
        final TextField tf4 = new TextField();
        tf4.setId("BRIGHTNESS");
        Button b4 = new Button("SET");
        b4.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf4.getText());
                if (num > 100 || num < 0) {
                    throw new NumberFormatException("Number must be between 100 and 0");
                }
                projector.setBrightness(num);
                tf4.setText("");
                tf4.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf4.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf4.setPromptText("" + projector.getBrightness());
            }
        });
        pane.add(t4, 0, 3);
        pane.add(tf4, 1, 3);
        pane.add(b4, 2, 3);
        fields.add(tf4);
        Text t5 = new Text("CONTRAST");
        final TextField tf5 = new TextField();
        tf5.setId("CONTRAST");
        Button b5 = new Button("SET");
        b5.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf5.getText());
                if (num > 100 || num < 0) {
                    throw new NumberFormatException("Number must be between 100 and 0");
                }
                projector.setContrast(num);
                tf5.setText("");
                tf5.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf5.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf5.setPromptText("" + projector.getContrast());
            }
        });
        pane.add(t5, 0, 4);
        pane.add(tf5, 1, 4);
        pane.add(b5, 2, 4);
        fields.add(tf5);
        Text t6 = new Text("TEST IMAGE");
        final TextField tf6 = new TextField();
        tf6.setId("TEST IMAGE");
        Button b6 = new Button("SET");
        b6.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf6.getText());
                if (num > 7 || num < 0) {
                    throw new NumberFormatException("Number must be between 7 and 0");
                }
                projector.setTestImage(num);
                tf6.setText("");
                tf6.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf6.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf6.setPromptText("" + projector.getTestImage());
            }
        });
        pane.add(t6, 0, 5);
        pane.add(tf6, 1, 5);
        pane.add(b6, 2, 5);
        fields.add(tf6);
        Text t7 = new Text("LAMP 1 RUNTIME");
        final TextField tf7 = new TextField();
        tf7.setId("LAMP 1 RUNTIME");
        Button b7 = new Button("SET");
        b7.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf7.getText());
                projector.setLamp1Runtime(num);
                tf7.setText("");
                tf7.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf7.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf7.setPromptText("" + projector.getLamp1Runtime());
            }
        });
        pane.add(t7, 0, 6);
        pane.add(tf7, 1, 6);
        pane.add(b7, 2, 6);
        fields.add(tf7);
        Text t8 = new Text("LAMP 2 RUNTIME");
        final TextField tf8 = new TextField();
        tf8.setId("LAMP 2 RUNTIME");
        Button b8 = new Button("SET");
        b8.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf8.getText());
                projector.setLamp2Runtime(num);
                tf8.setText("");
                tf8.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf8.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf8.setPromptText("" + projector.getLamp2Runtime());
            }
        });
        pane.add(t8, 0, 7);
        pane.add(tf8, 1, 7);
        pane.add(b8, 2, 7);
        fields.add(tf8);
        Text t9 = new Text("LAMP 1 TIME REMAINING");
        final TextField tf9 = new TextField();
        tf9.setId("LAMP 1 TIME REMAINING");
        Button b9 = new Button("SET");
        b9.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf9.getText());
                projector.setLamp1TimeRemaining(num);
                tf9.setText("");
                tf9.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf9.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf9.setPromptText("" + projector.getLamp1TimeRemaining());
            }
        });
        pane.add(t9, 0, 8);
        pane.add(tf9, 1, 8);
        pane.add(b9, 2, 8);
        fields.add(tf9);
        Text t10 = new Text("LAMP 2 TIME REMAINING");
        final TextField tf10 = new TextField();
        tf10.setId("LAMP 2 TIME REMAINING");
        Button b10 = new Button("SET");
        b10.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf10.getText());
                projector.setLamp2TimeRemaining(num);
                tf10.setText("");
                tf10.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf10.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf10.setPromptText("" + projector.getLamp2TimeRemaining());
            }
        });
        pane.add(t10, 0, 9);
        pane.add(tf10, 1, 9);
        pane.add(b10, 2, 9);
        fields.add(tf10);
        Text t11 = new Text("LAMP 1 STATUS");
        final TextField tf11 = new TextField();
        tf11.setId("LAMP 1 STATUS");
        Button b11 = new Button("SET");
        b11.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf11.getText());
                if (num > 5 || num < 0) {
                    throw new NumberFormatException("Number must be between 5 and 0");
                }
                projector.setLamp1Status(num);
                tf11.setText("");
                tf11.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf11.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf11.setPromptText("" + projector.getLamp1Status());
            }
        });
        pane.add(t11, 0, 10);
        pane.add(tf11, 1, 10);
        pane.add(b11, 2, 10);
        fields.add(tf11);
        Text t12 = new Text("LAMP 2 STATUS");
        final TextField tf12 = new TextField();
        tf12.setId("LAMP 2 STATUS");
        Button b12 = new Button("SET");
        b12.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf12.getText());
                if (num > 5 || num < 0) {
                    throw new NumberFormatException("Number must be between 5 and 0");
                }
                projector.setLamp2Status(num);
                tf12.setText("");
                tf12.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf12.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf12.setPromptText("" + projector.getLamp2Status());
            }
        });
        pane.add(t12, 0, 11);
        pane.add(tf12, 1, 11);
        pane.add(b12, 2, 11);
        fields.add(tf12);
        Text t13 = new Text("THERMAL");
        final TextField tf13 = new TextField();
        tf13.setId("THERMAL");
        Button b13 = new Button("SET");
        b13.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf13.getText());
                projector.setThermal(num);
                tf13.setText("");
                tf13.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf13.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf13.setPromptText("" + projector.getThermal());
            }
        });
        pane.add(t13, 0, 12);
        pane.add(tf13, 1, 12);
        pane.add(b13, 2, 12);
        fields.add(tf13);
        Text t14 = new Text("UNIT TOTAL TIME");
        final TextField tf14 = new TextField();
        tf14.setId("UNIT TOTAL TIME");
        Button b14 = new Button("SET");
        b14.setOnAction(e -> {
            try {
                int num = Integer.parseInt(tf14.getText());
                projector.setTotalRuntime(num);
                tf14.setText("");
                tf14.setPromptText("" + num);
            } catch (NumberFormatException ex) {
                tf14.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf14.setPromptText("" + projector.getTotalRuntime());
            }
        });
        pane.add(t14, 0, 13);
        pane.add(tf14, 1, 13);
        pane.add(b14, 2, 13);
        fields.add(tf14);
        return pane;
    }

    class Logger extends Thread {

        public void run() {
            while (running) {
                Log log = server.readLogLine();
                if (log != null) {
                    Platform.runLater(() -> addToLog(log));
                    time = System.currentTimeMillis();
                }
                dummies.removeIf(dummy -> !dummy.isRunning());
                numProjectors.setText("" + dummies.size());
                if (time + 60 * 60 * 1000 < System.currentTimeMillis() && dummies.size() == 0) {
                    clearLog();
                }
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class LogCell extends ListCell<Log> {
        @Override
        protected void updateItem(Log item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.toString());
            } else {
                setDisable(false);
                setText("");
            }
        }
    }
}
