import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import Modelos.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class SaidaDeMercadoria {
    private Stage Palco;
    private Scene Cena;
    private Parent Raiz;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSalvarSaida;

    @FXML
    private Button btnVoltarPaginaInicial;

    @FXML
    private TextField txtCodigoProduto;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtMotivo;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtValorTotal;

    @FXML
    void AcaoSalvarSaida(ActionEvent event) {
        String codigoProduto = txtCodigoProduto.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String quantidade = txtQuantidade.getText().trim();
        String valorTotal = txtValorTotal.getText().trim();
        String motivo = txtMotivo.getText().trim();

        if(codigoProduto.isEmpty() || codigoProduto == null){
            JOptionPane.showMessageDialog(null, "Código do Produto é obrigatório!");
            return;
        }

        if(descricao.isEmpty() || descricao == null){
            JOptionPane.showMessageDialog(null, "Descrição é obrigatório!");
            return;
        }

        if(quantidade.isEmpty() || quantidade == null){
            JOptionPane.showMessageDialog(null, "Quantidade é obrigatório!");
            return;
        }else if (!quantidade.matches("[0-9]*")){
            JOptionPane.showMessageDialog(null, "Quantidade invalida!");
            return;
        }

        if(valorTotal.isEmpty() || valorTotal == null){
            JOptionPane.showMessageDialog(null, "Valor Total é obrigatório!");
            return;
        }
        else if(!isNumeric(valorTotal)){
            JOptionPane.showMessageDialog(null, "Valor Total invalido!");
            return;
        }

        if(motivo.isEmpty() || motivo == null){
            JOptionPane.showMessageDialog(null, "Observações é obrigatório!");
            return;
        }

        Saida saida = new Saida(codigoProduto, descricao, Integer.parseInt(quantidade), Double.parseDouble(valorTotal), motivo);

        Database database = new Database();
        boolean cadastradoSaida = database.CadastrarSaida(saida);

        Produto produto = database.ObterProdutoPorCodigo(saida.CodigoProduto);
        boolean alteradoProduto = database.AlterarQuantidadeProduto(saida.CodigoProduto, (produto.Quantidade - saida.Quantidade));

        if(alteradoProduto && cadastradoSaida){
            txtCodigoProduto.clear();
            txtDescricao.clear();

            JOptionPane.showMessageDialog(null, "Saida cadastrado!");
        }else{
            JOptionPane.showMessageDialog(null, "Saida não cadastrado!");
        }
    }

    @FXML
    void AcaoVoltarPaginaInicial(ActionEvent event) throws IOException {

        Database database = new Database();
        database.SetAlterar(txtCodigoProduto.getText(), false);
        
        Raiz = FXMLLoader.load(getClass().getResource("Listagem.fxml"));
        Palco = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Cena = new Scene(Raiz);
        Palco.setScene(Cena);
        Palco.show();
    }

    @FXML
    void initialize() {
        assert btnSalvarSaida != null : "fx:id=\"btnSalvarSaida\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert btnVoltarPaginaInicial != null : "fx:id=\"btnVoltarPaginaInicial\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert txtCodigoProduto != null : "fx:id=\"txtCodigoProduto\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert txtDescricao != null : "fx:id=\"txtDescricao\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert txtMotivo != null : "fx:id=\"txtMotivo\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert txtQuantidade != null : "fx:id=\"txtQuantidade\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        assert txtValorTotal != null : "fx:id=\"txtValorTotal\" was not injected: check your FXML file 'SaidaDeMercadoria.fxml'.";
        
        Database database = new Database();
        Produto produto = database.ObterProdutoPorAlterar();

        txtCodigoProduto.setText(produto.Codigo);
        txtDescricao.setText(produto.Descricao);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
