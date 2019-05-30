package entidades;

public class Usuarios {
    private Integer id;
    private String usuario;
    private String senha;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Usuarios(Integer id, String usuario, String senha, Boolean status) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.status = status;
    }

    public Usuarios() {
    }
}
