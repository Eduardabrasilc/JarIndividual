

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Conexao {

    protected JdbcTemplate conexaoDoBanco;

    public Conexao(JdbcTemplate conexaoDoBanco) {
        this.conexaoDoBanco = conexaoDoBanco;
    }

    public abstract JdbcTemplate getConexaoDoBanco();

}
