package no.ntnu.vislab.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AbstractSimulatorView {

    private ArrayList<String> activeFilters;
    private ComboBox<String> filterList;
    private ObservableList<String> filters;
    private ArrayList<Communicator> connecedList;
    private Text connections;
    private ObservableList<Log> logs;
    private ArrayList<Log> rawLog = new ArrayList<>();
    private ListView<Log> logView;
    private long time = System.currentTimeMillis();
    private Logger logger;
    private boolean running = false;
    private BorderPane root;
    private Server server;

    public Region getRoot() {
        return root;
    }
    public void setServer(Server s){
        server = s;
        server.start();
    }

    public AbstractSimulatorView(String connectionTextString) {
        setUpLists();
        root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane(logView);
        scrollPane.fitToHeightProperty().setValue(true);
        scrollPane.fitToWidthProperty().setValue(true);
        root.setCenter(scrollPane);

        root.setTop(setUpTop(connectionTextString));
        root.setBottom(setUpBottom());
        running = true;
        logger = new Logger();
        logger.start();
    }

    private Node setUpBottom() {
        HBox bottom = new HBox();
        Button clearLog = new Button("Clear log");
        clearLog.setOnAction(event -> clearLog());
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottom.getChildren().addAll(spacer, clearLog);
        return bottom;
    }

    private Node setUpTop(String connectionTextString){
        connections = new Text("0");
        VBox top = new VBox();
        HBox mainBar = new HBox();
        Text connectionsText = new Text(connectionTextString);
        Tooltip t = new Tooltip();
        connectionsText.setOnMouseClicked(e->{
            Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
            popup.setContentText("Disconnect all connections?");
            popup.showAndWait();
            if(popup.getResult().equals(ButtonType.OK)){
                connecedList.forEach(Communicator::stopRunning);
            }
        });
        connectionsText.setOnMouseEntered(e->{
            StringBuilder str = new StringBuilder();
            connecedList.forEach(d-> str.append(d.getHostIp() + "\n"));
            t.setText(str.toString());
            if(!t.getText().isEmpty()){
                Tooltip.install(connectionsText,t);
            }
        });
        connectionsText.setOnMouseExited(e->{
            Tooltip.uninstall(connectionsText, t);
        });
        mainBar.getChildren().addAll(connectionsText, connections);
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
        return top;
    }
    private void setUpLists() {
        logView = new ListView<>();
        logView.setCellFactory(list -> new LogCell());
        connecedList = new ArrayList<>();
        logs = FXCollections.observableArrayList();
        filters = FXCollections.observableArrayList();
        logView.setItems(logs);
        activeFilters = new ArrayList<>();
    }

    void handleDummy(Communicator d) {
        connecedList.add(d);
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

    protected abstract void updateFields();

    public void stop() throws Exception {
        running = false;
        logger.join();
        if(server != null) {
            server.stopRunning();
        }
        connecedList.forEach(Communicator::stopRunning);
    }
    public void setLeft(Node left){
        root.setLeft(left);
    }

    class Logger extends Thread {

        public void run() {
            while (running) {
                if(server != null) {
                    Log log = server.readLogLine();
                    if (log != null) {
                        Platform.runLater(() -> addToLog(log));
                        time = System.currentTimeMillis();
                    }
                    connecedList.removeIf(dummy -> !dummy.isRunning());
                    connections.setText("" + connecedList.size());
                    if (time + 60 * 60 * 1000 < System.currentTimeMillis() && connecedList.size() == 0) {
                        clearLog();
                    }
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
