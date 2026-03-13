package erp.Salao.domain.usuario;

public enum UsuarioRole {
    ADMIN("admin"),
    GESTOR("gestor"),
    FUNCIONARIO("funcionario"),
    RECEPCAO("recepcao"),
    CLIENTE("cliente");

    private String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
