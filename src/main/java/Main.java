import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Main {
    public static void main(String[] args) {
        ConexaoMySql conexaoMySql = new ConexaoMySql(new JdbcTemplate());
        ConexaoSqlServer conexaoSql = new ConexaoSqlServer(new JdbcTemplate());
        JdbcTemplate conMySql = conexaoMySql.getConexaoDoBanco();
        JdbcTemplate conSql = conexaoSql.getConexaoDoBanco();

        conMySql.execute("DROP TABLE IF EXISTS TotemStatus");

        conMySql.execute("CREATE TABLE TotemStatus ( " +
                "idTotem INT PRIMARY KEY, " +
                "status ENUM('ativo', 'inativo') NOT NULL, " +
                "ultima_verificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ");");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Insira o código do totem desejado: ");
        int codigoTotem = scanner.nextInt();
        scanner.close();

        // Buscando o totem especificado pelo usuário
        List<Totem> totens = conSql.query("SELECT * FROM totem WHERE codigoTotem = ?", new Object[]{codigoTotem}, new RowMapper<Totem>() {
            @Override
            public Totem mapRow(ResultSet rs, int rowNum) throws SQLException {
                Totem totem = new Totem();
                totem.setIdTotem(rs.getInt("codigoTotem"));
                totem.setUltimaVerificacao(new Date());
                return totem;
            }
        });

        if (totens.isEmpty()) {
            System.out.println("Totem com código " + codigoTotem + " não encontrado.");
        } else {
            // Verificando status e inserindo na nova tabela
            Totem totem = totens.get(0);
            boolean ativo = Totem.capturarDadosDoTotem(conMySql, totem.getIdTotem());
            totem.setStatus(ativo ? "ativo" : "inativo");
            Totem.notificarStatus(ativo);
            try {
                conMySql.update("INSERT INTO TotemStatu (idTotem, status, ultima_verificacao) VALUES (?, ?, ?)",
                        totem.getIdTotem(), totem.getStatus(), totem.getUltimaVerificacao());
            } catch (Exception e) {
                try {
                    Log.createLog(Main.class, "WARNING", e.toString());
                    System.out.println(e);
                }catch (IOException a){
                    System.out.println(a);
                }
            }

            // Buscando dados inseridos na nova tabela para verificar
            List<Totem> ultimaVerificacao = conMySql.query("SELECT * FROM TotemStatus WHERE idTotem = ?", new Object[]{codigoTotem}, new BeanPropertyRowMapper<>(Totem.class));
            System.out.println(ultimaVerificacao);
        }
    }
}