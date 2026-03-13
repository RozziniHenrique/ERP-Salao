package erp.Salao.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE u.login = :login AND u.ativo = true")
    UserDetails findByLoginAndAtivoTrue(String login);

    @Query("SELECT u.role, COUNT(u) FROM Usuario u GROUP BY u.role")
    List<Object[]> contarUsuariosPorRole();

    boolean existsByLogin(String login);

}
