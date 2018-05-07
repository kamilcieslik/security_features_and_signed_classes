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
    private String originalFileContent;
    private String originalEncryptedFileContent;

    @FXML
    private TextField textFieldFilePath, textFieldEncryptedFilePath;

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
            originalEncryptedFileContent = textAreaEncryptedFileContent.getText();
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
            originalFileContent = textAreaFileContent.getText();
        }
    }

    @FXML
    void buttonDecrypt_onAction() {
        if (!textFieldEncryptedFilePath.getText().equals("")) {
            FileChooser frontCoversFileChooser = new FileChooser();
            frontCoversFileChooser.setTitle("Wybierz klucz prywatny");
            frontCoversFileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("klucze", "*.key"));
            File file = frontCoversFileChooser.showOpenDialog(textAreaEncryptedFileContent.getScene().getWindow());
            if (file != null) {
                String pathForDecryptedFile = textFieldFilePath.getText().substring(0,
                        textFieldFilePath.getText().lastIndexOf('\\')) + "\\decrypted_file.txt";
                try {
                    encoderDecoder.encryptFile(textFieldEncryptedFilePath.getText(), pathForDecryptedFile,
                            file.toString());
                    readFileContent(null, textAreaEncryptedFileContent, pathForDecryptedFile);
                } catch (IllegalBlockSizeException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja deszyfrowania pliku nie powiodła się.",
                            "Powód: zbyt duży rozmiar pliku.").showAndWait();
                } catch (NoSuchAlgorithmException | BadPaddingException | IOException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja deszyfrowania pliku nie powiodła się.",
                            "Powód: " + e.getMessage() + ".").showAndWait();
                } catch (InvalidKeyException | InvalidKeySpecException e) {
                    e.printStackTrace();
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja deszyfrowania pliku nie powiodła się.",
                            "Powód: niepoprawny klucz prywatny.").showAndWait();
                }
            }
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja deszyfrowania pliku nie powiedzie się.",
                    "Powód: nie wybrano pliku.").showAndWait();
    }

    @FXML
    void buttonEncrypt_onAction() {
        if (!textFieldFilePath.getText().equals("")) {
            FileChooser frontCoversFileChooser = new FileChooser();
            frontCoversFileChooser.setTitle("Wybierz klucz publiczny");
            frontCoversFileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("klucze", "*.key"));
            File file = frontCoversFileChooser.showOpenDialog(textAreaEncryptedFileContent.getScene().getWindow());
            if (file != null) {
                String pathForEncryptedFile = textFieldFilePath.getText().substring(0,
                        textFieldFilePath.getText().lastIndexOf('\\')) + "\\encrypted_file.txt";
                try {
                    encoderDecoder.encryptFile(textFieldFilePath.getText(), pathForEncryptedFile, file.toString());
                    readFileContent(null, textAreaFileContent, pathForEncryptedFile);
                } catch (IllegalBlockSizeException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja szyfrowania pliku nie powiodła się.",
                            "Powód: zbyt duży rozmiar pliku.").showAndWait();
                } catch (NoSuchAlgorithmException | BadPaddingException | IOException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja szyfrowania pliku nie powiodła się.",
                            "Powód: " + e.getMessage() + ".").showAndWait();
                } catch (InvalidKeyException | InvalidKeySpecException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja szyfrowania pliku nie powiodła się.",
                            "Powód: niepoprawny klucz publiczny.").showAndWait();
                }
            }
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja szyfrowania pliku nie powiedzie się.",
                    "Powód: nie wybrano pliku.").showAndWait();
    }

    @FXML
    void buttonEncryptedFileOriginalContent_onAction() {
        if (originalEncryptedFileContent != null)
            textAreaEncryptedFileContent.setText(originalEncryptedFileContent);
    }

    @FXML
    void buttonFileOriginalContent_onAction() {
        if (originalFileContent != null)
            textAreaFileContent.setText(originalFileContent);
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
