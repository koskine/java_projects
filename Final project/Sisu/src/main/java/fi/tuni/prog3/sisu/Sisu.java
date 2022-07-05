package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
        

/*
    Luokka toteuttaa koko ohjelman. Aluksi se luo aloitusnäkymän UI:lle.
    Kun käyttäjä valitsee "Tutki tutkintorakenteita" avataan ohjelman pääikkuna,
    josta voidaan avata Tietotekniikan DI-ohjelman tutkintorakenne. Kun käyttäjä
    valitsee "Avaa" tutkintorakenne ilmestyy näytölle. Molemmissa ikkunoissa on
    "Lopeta"-nappi, jota painamalla ohjelman suoritus keskeytetään.
*/
public class Sisu extends Application {

    @Override
    public void start(Stage stage) {
        
        //alkumenu
       var ryh = new FlowPane();
       Button startButton = new Button("Tutki tutkintorakenteita");
       Button stopButton = new Button("Lopeta");
       stopButton.setOnAction((ActionEvent event) -> {
       Platform.exit();
       });
       ryh.getChildren().add(startButton);
       ryh.getChildren().add(stopButton);
       Scene sce = new Scene(ryh,200,100);
       stage.setScene(sce);
        
        //pääikkuna
        stage.setTitle("SISU");
        Label label = new Label("Tietotekniikan DI-ohjelma");
        Button printButton = new Button("Avaa");
        GridPane group = new GridPane();
        printButton.setOnAction((event) -> {
           try {
               openMainJson(group);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
           }
        });   
        Button quitButton = new Button("Lopeta");
        quitButton.setOnAction((ActionEvent event) -> {
        Platform.exit();
    });
        
        group.add(label, 0, 0);
        group.add(printButton, 1, 0);
        group.add(quitButton, 2, 0);
        
        Scene scene = new Scene(group, 640, 480);
        startButton.setOnAction((event) -> {
            stage.setScene(scene);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    // openMainJson etsii tiedostoista tutkinnon tietoja ja kirjoittaa
    // ne UI:hin parametrina olevan GridPanen kautta
    public void openMainJson(GridPane grid) throws FileNotFoundException{
        Text programInfo = new Text("Tietotekniikan DI-ohjelma 120op");
        grid.add(programInfo, 0, 1);
        
        String file = "otm-3990be25-c9fd-4dae-904c-547ac11e8302.json";
        JsonObject root = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

        // Haetaan tutkintorakenteseen kuuluvien JSON-tiedostojen group ID:t
        JsonArray rules = root.getAsJsonObject("rule").getAsJsonObject("rule").getAsJsonArray("rules");
        JsonArray nextJsons = rules.get(0).getAsJsonObject().getAsJsonArray("rules");
        // counter kertoo mille riville tutkintotieto lisätään UI:ssa
        int counter = 2;
        for(JsonElement info : nextJsons) {
            String object = info.getAsJsonObject().get("moduleGroupId").getAsString();
            String filename;
            // Yksi saaduista group ID:sta ei ole suoraan tiedostonimi,
            // joten siihen viittava tiedosto avataan manuaalisesti
            if(object.equalsIgnoreCase("tut-sm-g-3662")) {
                filename = "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf.json";
            }
            else {
                filename = object + ".json";
            }
            // Yritetään avata tiedostoja ja jos tiedostoa
            // ei löydy, ei tehdä mitään
            try {
                JsonObject root2 =  JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
                String name = root2.getAsJsonObject("name").get("fi").getAsString();
                Text text = new Text("   " + name);
                grid.add(text, 0, counter);
                counter++;
            }
            
            catch(Exception e) {
            }
        }
        
    }
}
