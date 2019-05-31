package entidades;

public class Receitas {
    private Integer id;
    private String data;
    private String data_venc;
    private String descricao;
    private Double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_des) {
        this.id = id_des;
    }

    public String getData_venc() {
        return data_venc;
    }

    public void setData_venc(String data_venc) {
        this.data_venc = data_venc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Receitas() {
    }

    public Receitas(Integer id_des, String data_venc, String data, String descricao, Double valor) {
        this.id = id_des;
        this.data = data;
        this.data_venc = data_venc;
        this.descricao = descricao;
        this.valor = valor;
    }
}
