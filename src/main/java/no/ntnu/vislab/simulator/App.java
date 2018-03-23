package no.ntnu.vislab.simulator;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class App extends Application {
    private ProjectorSimulatorView projectorView;
    private SoundSystemView soundSystemView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Projector Simulator");
        projectorView = new ProjectorSimulatorView();
        soundSystemView = new SoundSystemView();
        TabPane tabPane = new TabPane();
        Tab projectorTab = new Tab("Projector");
        projectorTab.setContent(projectorView.getRoot());
        projectorTab.setClosable(false);
        Tab soundSystemTab = new Tab("SoundSystem");
        soundSystemTab.setContent(soundSystemView.getRoot());
        soundSystemTab.setClosable(false);
        tabPane.getTabs().addAll(projectorTab,soundSystemTab);
        primaryStage.setScene(new Scene(tabPane));
        primaryStage.show();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        projectorView.stop();
    }
}
