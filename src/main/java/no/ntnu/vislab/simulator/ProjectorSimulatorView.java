package no.ntnu.vislab.simulator;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjectorSimulatorView extends AbstractSimulatorView {
    private ArrayList<TextField> fields;

    private Projector projector;

    public ProjectorSimulatorView() {
        super("Connections: ");
        projector = new Projector();
        fields = new ArrayList<>();
        Button updateAll = new Button("Update all fields");
        updateAll.setOnAction(e->setAll());
        setLeft(new VBox(setUpProjector(), setUpResponses(), updateAll));
        setServer(new Server(1025, projector, this::handleDummy, this::updateFields));
        setUpFieldListeners();
        updateFields();
    }

    private void setUpFieldListeners() {
        fields.forEach(f -> f.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                setFieldValue(f);
                getRoot().requestFocus();
            }
        }));
    }

    private GridPane setUpProjector() {
        GridPane pane = new GridPane();
        Text t1 = new Text("POWER");
        final TextField tf1 = new TextField();
        tf1.setId("POWER");
        Button b1 = new Button("SET");
        b1.setOnAction(e -> setFieldValue(tf1));
        pane.add(t1, 0, 0);
        pane.add(tf1, 1, 0);
        pane.add(b1, 2, 0);
        fields.add(tf1);
        Text t2 = new Text("POWER STATE");
        final TextField tf2 = new TextField();
        tf2.setId("POWER STATE");
        Button b2 = new Button("SET");
        b2.setOnAction(e -> setFieldValue(tf2));
        pane.add(t2, 0, 1);
        pane.add(tf2, 1, 1);
        pane.add(b2, 2, 1);
        fields.add(tf2);
        Text t3 = new Text("MUTE");
        final TextField tf3 = new TextField();
        tf3.setId("MUTE");
        Button b3 = new Button("SET");
        b3.setOnAction(e -> setFieldValue(tf3));
        pane.add(t3, 0, 2);
        pane.add(tf3, 1, 2);
        pane.add(b3, 2, 2);
        fields.add(tf3);
        Text t4 = new Text("BRIGHTNESS");
        final TextField tf4 = new TextField();
        tf4.setId("BRIGHTNESS");
        Button b4 = new Button("SET");
        b4.setOnAction(e -> setFieldValue(tf4));
        pane.add(t4, 0, 3);
        pane.add(tf4, 1, 3);
        pane.add(b4, 2, 3);
        fields.add(tf4);
        Text t5 = new Text("CONTRAST");
        final TextField tf5 = new TextField();
        tf5.setId("CONTRAST");
        Button b5 = new Button("SET");
        b5.setOnAction(e -> setFieldValue(tf5));
        pane.add(t5, 0, 4);
        pane.add(tf5, 1, 4);
        pane.add(b5, 2, 4);
        fields.add(tf5);
        Text t6 = new Text("TEST IMAGE");
        final TextField tf6 = new TextField();
        tf6.setId("TEST IMAGE");
        Button b6 = new Button("SET");
        b6.setOnAction(e -> setFieldValue(tf6));
        pane.add(t6, 0, 5);
        pane.add(tf6, 1, 5);
        pane.add(b6, 2, 5);
        fields.add(tf6);
        Text t7 = new Text("LAMP 1 RUNTIME");
        final TextField tf7 = new TextField();
        tf7.setId("LAMP 1 RUNTIME");
        Button b7 = new Button("SET");
        b7.setOnAction(e -> setFieldValue(tf7));
        pane.add(t7, 0, 6);
        pane.add(tf7, 1, 6);
        pane.add(b7, 2, 6);
        fields.add(tf7);
        Text t8 = new Text("LAMP 2 RUNTIME");
        final TextField tf8 = new TextField();
        tf8.setId("LAMP 2 RUNTIME");
        Button b8 = new Button("SET");
        b8.setOnAction(e -> setFieldValue(tf8));
        pane.add(t8, 0, 7);
        pane.add(tf8, 1, 7);
        pane.add(b8, 2, 7);
        fields.add(tf8);
        Text t9 = new Text("LAMP 1 TIME REMAINING");
        final TextField tf9 = new TextField();
        tf9.setId("LAMP 1 TIME REMAINING");
        Button b9 = new Button("SET");
        b9.setOnAction(e -> setFieldValue(tf9));
        pane.add(t9, 0, 8);
        pane.add(tf9, 1, 8);
        pane.add(b9, 2, 8);
        fields.add(tf9);
        Text t10 = new Text("LAMP 2 TIME REMAINING");
        final TextField tf10 = new TextField();
        tf10.setId("LAMP 2 TIME REMAINING");
        Button b10 = new Button("SET");
        b10.setOnAction(e -> setFieldValue(tf10));
        pane.add(t10, 0, 9);
        pane.add(tf10, 1, 9);
        pane.add(b10, 2, 9);
        fields.add(tf10);
        Text t11 = new Text("LAMP 1 STATUS");
        final TextField tf11 = new TextField();
        tf11.setId("LAMP 1 STATUS");
        Button b11 = new Button("SET");
        b11.setOnAction(e -> setFieldValue(tf11));
        pane.add(t11, 0, 10);
        pane.add(tf11, 1, 10);
        pane.add(b11, 2, 10);
        fields.add(tf11);
        Text t12 = new Text("LAMP 2 STATUS");
        final TextField tf12 = new TextField();
        tf12.setId("LAMP 2 STATUS");
        Button b12 = new Button("SET");
        b12.setOnAction(e -> setFieldValue(tf12));
        pane.add(t12, 0, 11);
        pane.add(tf12, 1, 11);
        pane.add(b12, 2, 11);
        fields.add(tf12);
        Text t13 = new Text("THERMAL");
        final TextField tf13 = new TextField();
        tf13.setId("THERMAL");
        Button b13 = new Button("SET");
        b13.setOnAction(e -> setFieldValue(tf13));
        pane.add(t13, 0, 12);
        pane.add(tf13, 1, 12);
        pane.add(b13, 2, 12);
        fields.add(tf13);
        Text t14 = new Text("UNIT TOTAL TIME");
        final TextField tf14 = new TextField();
        tf14.setId("UNIT TOTAL TIME");
        Button b14 = new Button("SET");
        b14.setOnAction(e -> setFieldValue(tf14));
        pane.add(t14, 0, 13);
        pane.add(tf14, 1, 13);
        pane.add(b14, 2, 13);
        fields.add(tf14);
        return pane;
    }

    private void setAll() {
        fields.forEach(f->setFieldValue(f,false));
    }

    private void setFieldValue(TextField tf){
        setFieldValue(tf,true);
    }
    private void setFieldValue(TextField tf, boolean showAlert) {
        try {
            int num = Integer.parseInt(tf.getText());
            setProjectorValue(num, tf.getId());
            tf.setText("");
            tf.setPromptText("" + num);
        } catch (NumberFormatException ex) {
            if(showAlert) {
                tf.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ERROR: " + ex.getMessage());
                alert.showAndWait();
                tf.setPromptText(getProjectorValue(tf.getId()));
            }
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
            setFieldValue(normalField);
        });
        corruptButton.setOnAction(event -> {
            setFieldValue(corruptField);
        });
        delayedButton.setOnAction(event -> {
            setFieldValue(delayedField);
        });
        incorrectButton.setOnAction(event -> {
            setFieldValue(incorrectField);
        });
        noButton.setOnAction(event -> {
            setFieldValue(noField);
        });
        pane.add(normalButton, 2, 1);
        pane.add(corruptButton, 2, 2);
        pane.add(delayedButton, 2, 3);
        pane.add(incorrectButton, 2, 4);
        pane.add(noButton, 2, 5);

        return pane;
    }

    public void updateFields() {
        fields.forEach(this::updateField);
    }

    public void updateField(TextField tf) {
        switch (tf.getId()) {
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

    public void setProjectorValue(int num, String id) {
        switch (id) {
            case "POWER":
                projector.setPower(num);
                break;
            case "POWER STATE":
                projector.setPowerState(num);
                break;
            case "MUTE":
                projector.setMute(num);
                break;
            case "BRIGHTNESS":
                projector.setBrightness(num);
                break;
            case "CONTRAST":
                projector.setContrast(num);
                break;
            case "LAMP 1 RUNTIME":
                projector.setLamp1Runtime(num);
                break;
            case "LAMP 2 RUNTIME":
                projector.setLamp2Runtime(num);
                break;
            case "LAMP 1 STATUS":
                projector.setLamp1Status(num);
                break;
            case "LAMP 2 STATUS":
                projector.setLamp2Status(num);
                break;
            case "LAMP 1 TIME REMAINING":
                projector.setLamp1TimeRemaining(num);
                break;
            case "LAMP 2 TIME REMAINING":
                projector.setLamp2TimeRemaining(num);
                break;
            case "THERMAL":
                projector.setThermal(num);
                break;
            case "TEST IMAGE":
                projector.setTestImage(num);
                break;
            case "UNIT TOTAL TIME":
                projector.setTotalRuntime(num);
                break;
            case "NORMAL":
                ProjectorCommunicator.setNormalWeight(num);
                break;
            case "DELAY":
                ProjectorCommunicator.setDelayedWeight(num);
                break;
            case "CORRUPT":
                ProjectorCommunicator.setCorruptWeight(num);
                break;
            case "INCORRECT":
                ProjectorCommunicator.setIncorrectWeight(num);
                break;
            case "NO RESPONSE":
                ProjectorCommunicator.setNoResponseWeight(num);
                break;
        }
    }

    private String getProjectorValue(String id) {
        int num = 0;
        switch (id) {
            case "POWER":
                num = projector.getPower();
                break;
            case "POWER STATE":
                num = projector.getPowerState();
                break;
            case "MUTE":
                num = projector.getMute();
                break;
            case "BRIGHTNESS":
                num = projector.getBrightness();
                break;
            case "CONTRAST":
                num = projector.getContrast();
                break;
            case "LAMP 1 RUNTIME":
                num = projector.getLamp1Runtime();
                break;
            case "LAMP 2 RUNTIME":
                num = projector.getLamp2Runtime();
                break;
            case "LAMP 1 STATUS":
                num = projector.getLamp1Status();
                break;
            case "LAMP 2 STATUS":
                num = projector.getLamp2Status();
                break;
            case "LAMP 1 TIME REMAINING":
                num = projector.getLamp1TimeRemaining();
                break;
            case "LAMP 2 TIME REMAINING":
                num = projector.getLamp2TimeRemaining();
                break;
            case "THERMAL":
                num = projector.getThermal();
                break;
            case "TEST IMAGE":
                num = projector.getTestImage();
                break;
            case "UNIT TOTAL TIME":
                num = projector.getTotalRuntime();
                break;
            case "NORMAL":
                num = ProjectorCommunicator.getNormalWeight();
                break;
            case "DELAY":
                num = ProjectorCommunicator.getDelayedWeight();
                break;
            case "CORRUPT":
                num = ProjectorCommunicator.getCorruptWeight();
                break;
            case "INCORRECT":
                num = ProjectorCommunicator.getIncorrectWeight();
                break;
            case "NO RESPONSE":
                num = ProjectorCommunicator.getNoResponseWeight();
                break;
        }
        return num + "";
    }

}
