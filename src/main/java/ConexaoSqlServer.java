import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSqlServer extends Conexao{

    public ConexaoSqlServer(JdbcTemplate conexaoDoBanco) {
        super(conexaoDoBanco);
            BasicDataSource bds = new BasicDataSource();
            bds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            bds.setUrl("jdbc:sqlserver://100.27.172.173:1433;database=ideabd;trustServerCertificate=true;");
            bds.setUsername("sa");
            bds.setPassword("Duda-2901");

           this.conexaoDoBanco = conexaoDoBanco = new JdbcTemplate(bds);

    }

    @Override
    public JdbcTemplate getConexaoDoBanco() {
        return this.conexaoDoBanco;
    }
}
