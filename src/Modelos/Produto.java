package Modelos;

public class Produto {
    public String Codigo;
    public String Descricao;
    public int Quantidade;
    public double Valor;
    public String Observacoes;

    public Produto() {
        super();
    }

    public Produto(String codigo, String descricao, int quantidade, double valor, String observacoes) {
        this.Codigo = codigo;
        this.Descricao = descricao;
        this.Quantidade = quantidade;
        this.Valor = valor;
        this.Observacoes = observacoes;
    }
}
