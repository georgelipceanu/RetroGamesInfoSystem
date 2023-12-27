package com.example.retro;

import com.example.retro.models.Game;
import com.example.retro.models.GamePort;
import com.example.retro.models.GameSystem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {
    public static Scene homeS,searchS;
    public static Stage mainStage;

    public static MyNeatList<GameSystem> gameSystems = new MyNeatList<>();
    public static MyNeatList<Game> games = new MyNeatList<>();
    public static MyNeatList<GamePort> ports = new MyNeatList<>();

    public static Scene changeScene(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(file));
        return new Scene(fxmlLoader.load(), 900, 600);
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Retro Video Games Information System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void load() throws Exception { // load
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{GameSystem.class, Game.class, GamePort.class, MyNeatList.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader("retro.xml"));
        gameSystems = (MyNeatList<GameSystem>) in.readObject();//loading data from retro.xml
        in.close();
    }
    public static void save() throws Exception { // save
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("retro.xml"));
        out.writeObject(ports); ;//saving only game systems to retro.fxml since all data is linked to these

        out.close();
    }

}