package sample.AppFinance;

import io.reactivex.Observable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String... args) {
        Observable<String> stream = Observable.fromIterable(SUPER_HEROES)
                .filter(s -> {
                    return s.startsWith("A");
                });
        stream.subscribe(
                //name -> System.out.println(name)
        );


        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("VisualApp.fxml"));
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(250);
    }
}
