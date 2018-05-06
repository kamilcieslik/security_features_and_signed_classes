package com.github.kamilcieslik.academic.app.javafx.controller;

import com.github.kamilcieslik.academic.app.javafx.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private TextField textFieldKeyPath, textFieldFilePath, textFieldEncryptedFilePath;

    @FXML
    private TextArea textAreaFileContent, textAreaEncryptedFileContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");
    }

    @FXML
    void buttonChooseEncryptedFilePath_onAction() {

    }

    @FXML
    void buttonChooseFilePath_onAction() {

    }

    @FXML
    void buttonChooseKeysPath_onAction() {

    }

    @FXML
    void buttonDecrypt_onAction() {

    }

    @FXML
    void buttonEncrypt_onAction() {

    }

    @FXML
    void buttonEncryptedFileOriginalContent_onAction() {

    }

    @FXML
    void buttonFileOriginalContent_onAction() {

    }

    @FXML
    void buttonGenerateKeys_onAction() {

    }
}
