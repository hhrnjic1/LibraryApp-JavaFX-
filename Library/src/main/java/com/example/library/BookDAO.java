package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BookDAO {
    private static BookDAO instance = null;

    private Connection conn;
    private PreparedStatement pretragaUpit,dodavanjeUpit,izmjenaUpit,brisanjeUpit, sveKnjigeUpit;
    private PreparedStatement  noviIdUpit,pretragaIdUpit;

    private BookDAO() throws SQLException {
        //String url = "jdbc:sqlite:"+ System.getProperty("user.home")+"/.knjigeApp/knjige.db";
        String url = "jdbc:sqlite:knjige.db";
        conn = DriverManager.getConnection(url);
        try {
            pretragaUpit = conn.prepareStatement("SELECT * FROM KNJIGA WHERE naslov =? OR autor=?");
        }catch (SQLException e){
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM KNJIGA WHERE naslov =? OR autor=?");
        }
        dodavanjeUpit = conn.prepareStatement("INSERT INTO knjiga VALUES(?,?,?,?)");
        noviIdUpit = conn.prepareStatement("SELECT MAX(id) FROM knjiga");
        izmjenaUpit = conn.prepareStatement("UPDATE knjiga set naslov=?,autor=?,isbn=? WHERE id=?");
        pretragaIdUpit = conn.prepareStatement("SELECT id FROM knjiga WHERE id = ?");
        brisanjeUpit = conn.prepareStatement("DELETE FROM knjiga WHERE id=?");
        sveKnjigeUpit = conn.prepareStatement("SELECT * FROM knjiga ORDER BY naslov");
    }

    private void kreirajBazu() {
        Scanner ulaz = null;
        try{
            ulaz = new Scanner(new FileInputStream("knjiga.sql"));
            String sqlUpit = "";
            while(ulaz.hasNextLine()){
                sqlUpit = sqlUpit + ulaz.nextLine();
                if(sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length()-1) == ';'){
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavlja sa praznom bazom.");
        }
    }

    public static BookDAO getInstance() throws SQLException {
        if(instance == null) instance = new BookDAO();
        return instance;
    }

    public static void removeInstance() throws SQLException {
        if(instance == null) return;
        instance.conn.close();
        instance = null;
    }


    ArrayList<Book> pretraga(String pretraga){
        ArrayList<Book> rezultat = new ArrayList<Book>();
        try {
            pretragaUpit.setString(1, pretraga);
            pretragaUpit.setString(2, pretraga);
            ResultSet result = pretragaUpit.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String naslov = result.getString(2);
                String autor = result.getString(3);
                String isbn = result.getString(4);
                rezultat.add(new Book(id,naslov,autor,isbn));
            }
        }catch (SQLException e){
            e.getMessage();
        }
        return  rezultat;
    }

    public Book dodaj(Book k) throws SQLException {
        ResultSet rs = noviIdUpit.executeQuery();
        if(rs.next()) k.setId(rs.getInt(1) + 1);
        else k.setId(1);
        dodavanjeUpit.setInt(1,k.getId());
        dodavanjeUpit.setString(2,k.getNaslov());
        dodavanjeUpit.setString(3, k.getAutor());
        dodavanjeUpit.setString(4, k.getIsbn());
        dodavanjeUpit.execute();
        return k;
    }

    public Book izmjeni(Book k) {
        try {
            pretragaIdUpit.setInt(1,k.getId());
            ResultSet rs = pretragaIdUpit.executeQuery();
            if(rs.next()){
                izmjenaUpit.setString(1,k.getNaslov());
                izmjenaUpit.setString(2,k.getAutor());
                izmjenaUpit.setString(3,k.getIsbn());
                izmjenaUpit.setInt(4,rs.getInt(1));
                izmjenaUpit.executeUpdate();
            }else{
                System.out.println("Knjiga sa unesenim id ne postoji!");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;
    }

    public void brisi(int id) {
        try {
            brisanjeUpit.setInt(1,id);
            brisanjeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Book> sveKnjige(){
        ObservableList<Book> knjige = FXCollections.observableArrayList();
        try {
            ResultSet result = sveKnjigeUpit.executeQuery();
            while (result.next()) {
                knjige.add(new Book(result.getInt(1),result.getString(2),result.getString(3),result.getString(4)));
            }
        }catch (SQLException e){
            e.getMessage();
        }
        return knjige;
    }
}
