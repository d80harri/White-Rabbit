package net.d80harri.wr.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.d80harri.wr.fx.task.TaskView;

import com.airhacks.afterburner.injection.Injector;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TaskView appView = new TaskView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("White rabbit v0.0.1");
//        final String uri = getClass().getResource("app.css").toExternalForm();
//        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}