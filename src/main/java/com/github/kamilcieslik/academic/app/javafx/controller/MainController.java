package com.github.kamilcieslik.academic.app.javafx.controller;

import com.github.kamilcieslik.academic.app.cryptography_library.EncoderDecoder;
import com.github.kamilcieslik.academic.app.javafx.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private EncoderDecoder encoderDecoder;

    @FXML
    private TextField textFieldKeyPath, textFieldFilePath, textFieldEncryptedFilePath;

    @FXML
    private TextArea textAreaFileContent, textAreaEncryptedFileContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");
        try {
            encoderDecoder = new EncoderDecoder();
        } catch (NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonChooseEncryptedFilePath_onAction() {
        FileChooser frontCoversFileChooser = new FileChooser();
        frontCoversFileChooser.setTitle("Wybierz zaszyfrowany plik");
        frontCoversFileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("pliki tekstowe", "*.txt"));
        File file = frontCoversFileChooser.showOpenDialog(textAreaEncryptedFileContent.getScene().getWindow());
        if (file != null) {
            String filePath = file.toString();
            readFileContent(textFieldEncryptedFilePath, textAreaEncryptedFileContent, filePath);
        }
    }

    @FXML
    void buttonChooseFilePath_onAction() {
        FileChooser frontCoversFileChooser = new FileChooser();
        frontCoversFileChooser.setTitle("Wybierz plik");
        frontCoversFileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("pliki tekstowe", "*.txt"));
        File file = frontCoversFileChooser.showOpenDialog(textAreaEncryptedFileContent.getScene().getWindow());
        if (file != null) {
            String filePath = file.toString();
            readFileContent(textFieldFilePath, textAreaFileContent, filePath);
        }
    }

    @FXML
    void buttonChooseKeysPath_onAction() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Wybierz katalog z kluczami");
        File directory = chooser.showDialog(textAreaEncryptedFileContent.getScene().getWindow());
        if (directory != null && directory.isDirectory())
            textFieldKeyPath.setText(directory.toString());
    }

    @FXML
    void buttonDecrypt_onAction() {

    }

    @FXML
    void buttonEncrypt_onAction() {

        String pathForEncryptedFile = textFieldFilePath.getText().substring(0, textFieldFilePath.getText().lastIndexOf('\\')) + "\\encrypted_file.txt";
        System.out.println(pathForEncryptedFile);
        try {
            encoderDecoder.encryptFile(textFieldFilePath.getText(), pathForEncryptedFile);

            readFileContent(null, textAreaFileContent, pathForEncryptedFile);
        } catch (IOException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja szyfrowania nie powiodła się.", "Powód: " + "błąd odczytu z pliku").showAndWait();
        } catch (IllegalBlockSizeException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja szyfrowania nie powiodła się.", "Powód: " + "zbyt duży rozmiar pliku.").showAndWait();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja szyfrowania nie powiodła się.", "Powód: " + "Bład szyfrowania").showAndWait();
        } catch (InvalidKeyException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie", "Operacja szyfrowania nie powiodła się.", "Powód: " + "Zbyt duży rozmiar pliku").showAndWait();
        }
    }

    @FXML
    void buttonEncryptedFileOriginalContent_onAction() {

    }

    @FXML
    void buttonFileOriginalContent_onAction() {

    }

    @FXML
    void buttonGenerateKeys_onAction() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Wybierz katalog utworzenia kluczy");
        File directory = chooser.showDialog(textAreaEncryptedFileContent.getScene().getWindow());
        if (directory != null && directory.isDirectory()) {
            try {
                encoderDecoder.createNewKeys(directory.toString());
            } catch (IOException e) {
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Operacja wygenerowania kluczy nie powiodła się.",
                        "Powód: " + e.getMessage() + ".").showAndWait();
            }
        }
    }

    private void readFileContent(TextField textFieldFilePath, TextArea textAreaFileContent, String filePath) {
        BufferedReader bufferedReader;

        try {
            if (textFieldFilePath != null)
                textFieldFilePath.setText(filePath);
            bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            textAreaFileContent.setText(stringBuilder.toString());
        } catch (IOException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja odczytu z pliku nie powiodła się.", "Powód: " + e.getMessage() + ".")
                    .showAndWait();
        }
    }
}
