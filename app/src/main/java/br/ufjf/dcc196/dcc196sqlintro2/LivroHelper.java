package br.ufjf.dcc196.dcc196sqlintro2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LivroHelper {
    private SQLiteDatabase db;

    public LivroHelper(SQLiteDatabase db) {
        this.db = db;
        criaTabela();
    }

    private void criaTabela() {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS livro (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, autor VARCHAR, editora VARCHAR, ano INTEGER, preco FLOAT)");
        }catch (Exception e){
            Log.e("Livro", "Erro ao criar a tabela!");
            Log.e("Livro", e.getMessage());
        }
    }

    public void criarLivro(Livro l){
        try{
        db.execSQL("INSERT INTO livro (titulo, autor, editora, ano, preco) VALUES ('" +
                l.getTitulo()+"', '" +
                l.getAutor()+"', '" +
                l.getEditora()+"', " +
                l.getAno()+", " +
                l.getPreco()+")");
        //db.execSQL(String.format("INSERT INTO livro (titulo, autor, editora, ano, preco) VALUES ('%s','%s','%s',%d,%f)", l.getTitulo(), l.getAutor(), l.getEditora(), l.getAno(), l.getPreco()));

        }catch(Exception e){
            Log.e("Livro", "Erro ao inserir um livro");
            Log.e("Livro", e.getLocalizedMessage());
        };
    }

    public List<Livro> listarTodos() {
        Cursor resultado = db.rawQuery("SELECT titulo, autor, editora, ano, preco FROM livro", null);
        List<Livro> livros = new ArrayList<>();
        resultado.moveToPosition(-1);
        while (resultado.moveToNext()){
            Livro l = new Livro();
            l.setTitulo(resultado.getString(0));
            l.setAutor(resultado.getString(1));
            l.setEditora(resultado.getString(2));
            l.setAno(resultado.getInt(3));
            l.setPreco(resultado.getFloat(4));
            livros.add(l);
        }
        return livros;
    }
}
