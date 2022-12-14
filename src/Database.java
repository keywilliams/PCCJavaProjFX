import java.sql.*;
import Modelos.*;

public class Database {

    private static String usuarioDB = "root";
    private static String senhaDB = "Qwerty12";
    // private static String senhaDB = "mhbg8pq5";

    public boolean ValidarUsuarioESenha(String usuario, String senha) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pccjavaprojfx", usuarioDB, senhaDB);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE Nome = '" + usuario + "' AND Senha = '" + senha + "'");
            if (!rs.isBeforeFirst()) {
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario ObterUsuario(String nome) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pccjavaprojfx", usuarioDB, senhaDB);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE Nome = '" + nome + "'");
            Usuario usuario = new Usuario();
            while (rs.next()) {
                usuario.Nome = rs.getString("Nome");
                usuario.Senha = rs.getString("Senha");
                usuario.CadastroProduto = rs.getBoolean("CadastroProduto");
                usuario.EntradaMercadoria = rs.getBoolean("EntradaMercadoria");
                usuario.SaidaMercadoria = rs.getBoolean("SaidaMercadoria");
                break;
              }
            con.close();
            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean CadastrarUsuario(Usuario user) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pccjavaprojfx", usuarioDB, senhaDB);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Query to retrieve records
            String query = "SELECT * FROM usuarios";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int id = rs.getInt("Id")+1;
            rs.moveToInsertRow();
            rs.updateInt("Id", id);
            rs.updateString("Nome", user.Nome);
            rs.updateString("Senha", user.Senha);
            rs.updateBoolean("CadastroProduto", user.CadastroProduto);
            rs.updateBoolean("EntradaMercadoria", user.EntradaMercadoria);
            rs.updateBoolean("SaidaMercadoria", user.SaidaMercadoria);
            rs.insertRow();
            rs.beforeFirst();
            if (!rs.isBeforeFirst()) {
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean CadastrarProduto(Produto produto) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pccjavaprojfx", usuarioDB, senhaDB);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Query to retrieve records
            String query = "SELECT * FROM produtos";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int id = rs.getInt("Id")+1;
            rs.moveToInsertRow();
            rs.updateInt("Id", id);
            rs.updateString("Codigo", produto.Codigo);
            rs.updateString("Descricao", produto.Descricao);
            rs.updateInt("Quantidade", produto.Quantidade);
            rs.updateDouble("Valor", produto.Valor);
            rs.updateString("Observacoes", produto.Observacoes);
            rs.insertRow();
            rs.beforeFirst();
            if (!rs.isBeforeFirst()) {
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
