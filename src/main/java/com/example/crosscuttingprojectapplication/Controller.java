package com.example.crosscuttingprojectapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import processors.ProcessingFile;
import processors.ProcessingJsonFile;
import processors.ProcessingTxtFile;
import processors.ProcessingXmlFile;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private Stage stage;

    @FXML
    private Button calculateButton;

    @FXML
    private ToggleGroup fileFormat;

    @FXML
    private Button inputButton;

    @FXML
    private TextField inputField;

    @FXML
    private Button outputButton;

    @FXML
    private TextField outputField;

    @FXML
    private RadioButton radioButtonJSON;

    @FXML
    private RadioButton radioButtonTXT;

    @FXML
    private RadioButton radioButtonXML;

    private FileChannel desktop;

    public void selectInputFile(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\programmes\\2_course\\Industrial programming\\CrossCuttingProject-master\\files"));
        inputButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inputField.clear();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    openFile(file);
                    List<File> files = Arrays.asList(file);
                    printLog(inputField, files);
                }
            }
        });

        outputButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                outputField.clear();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    openFile(file);
                    List<File> files = Arrays.asList(file);
                    printLog(outputField, files);
                }
            }
        });
    }

    private void printLog(TextField textArea, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
            textArea.appendText(file.getAbsolutePath() + "\n");
        }
    }

    private void openFile(File file) {
        try {
            this.desktop.open(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Process(ActionEvent event) throws Exception {
        String inputFileName = inputField.getText();
        String outputFileName = outputField.getText();

        if (radioButtonJSON.isSelected()) {
            processInputFile("json");
        } else if (radioButtonTXT.isSelected()) {
            processInputFile("txt");
        } else {
            processInputFile("xml");
        }

        stage = (Stage) calculateButton.getScene().getWindow();
        stage.close();
    }

    public void processInputFile(String typeFile) throws Exception {
        String inputFileName = inputField.getText();
        String outputFileName = outputField.getText();
        ProcessingFile processingFile = null;

        if (typeFile.equals("txt")) {
            processingFile = new ProcessingTxtFile(inputFileName);
        } else if (typeFile.equals("xml")) {
            processingFile = new ProcessingXmlFile(inputFileName);
        } else if (typeFile.equals("json")) {
            processingFile = new ProcessingJsonFile(inputFileName);
        }

        processingFile.writeResultToFile(outputFileName);
    }
}
