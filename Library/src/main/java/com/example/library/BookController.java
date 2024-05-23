package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class BookController {

    private static BookDAO dao;
    public ListView<Book> listKnjige;
    public TextField fldNaslov;
    public TextField fldAutor;
    public TextField fldISBN;

    @FXML
    public void initialize() throws SQLException {
        dao = BookDAO.getInstance();
        listKnjige.setItems(dao.sveKnjige());
        listKnjige.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldItem,newItem) ->{
                    Book oldKnjiga = (Book) oldItem;
                    Book newKnjiga = (Book) newItem;
                    if(oldItem != null){
                        fldNaslov.textProperty().unbindBidirectional(oldKnjiga.naslovProperty());
                        fldAutor.textProperty().unbindBidirectional(oldKnjiga.autorProperty());
                        fldISBN.textProperty().unbindBidirectional(oldKnjiga.isbnProperty());
                        //Mjenja stanje u bazi prilikom promjene na listi
                        dao.izmjeni(oldKnjiga);
                    }
                    if(newItem != null){
                        fldNaslov.textProperty().bindBidirectional(newKnjiga.naslovProperty());
                        fldAutor.textProperty().bindBidirectional(newKnjiga.autorProperty());
                        fldISBN.textProperty().bindBidirectional(newKnjiga.isbnProperty());
                    }else{
                        fldNaslov.setText("");
                        fldAutor.setText("");
                        fldISBN.setText("");
                    }
                }
        );
    }

    public void exitClick(ActionEvent actionEvent) {
        //Platform.exit();
        System.exit(0);
    }

    public void izmjenaClick(ActionEvent actionEvent) {
        for(Book k : listKnjige.getItems()){
                dao.izmjeni(k);
        }
        System.out.println("Azurirana baza!");
    }
}