import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySql extends Conexao {

    public ConexaoMySql(JdbcTemplate conexaoDoBanco) {
        super(conexaoDoBanco);
        BasicDataSource dataSource = new BasicDataSource();
    /*
         Exemplo de driverClassName:
            com.mysql.cj.jdbc.Driver <- EXEMPLO PARA MYSQL
            com.microsoft.sqlserver.jdbc.SQLServerDriver <- EXEMPLO PARA SQL SERVER
    */
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    /*
         Exemplo de string de conexões:
            jdbc:mysql://localhost:3306/mydb <- EXEMPLO PARA MYSQL

           jdbc:sqlserver://localhost:1433;database=mydb <- EXEMPLO PARA SQL SERVER
    */
        dataSource.setUrl("jdbc:mysql://localhost:3306/ideabd");
        dataSource.setUsername("root");
        dataSource.setPassword("Duda-2901");

        this.conexaoDoBanco = conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    @Override
    public JdbcTemplate getConexaoDoBanco() {
        return this.conexaoDoBanco;
    }
}
