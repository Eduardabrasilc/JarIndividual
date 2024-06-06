import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.Date;

public class Totem {
    private int idTotem;
    private String localizacao;
    private String status;
    private Date ultimaVerificacao;

    public Totem() {}

    public Totem(int idTotem, String localizacao, String status, Date ultimaVerificacao) {
        this.idTotem = idTotem;
        this.localizacao = localizacao;
        this.status = status;
        this.ultimaVerificacao = ultimaVerificacao;
    }

    public int getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(int idTotem) {
        this.idTotem = idTotem;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUltimaVerificacao() {
        return ultimaVerificacao;
    }

    public void setUltimaVerificacao(Date ultimaVerificacao) {
        this.ultimaVerificacao = ultimaVerificacao;
    }

    public static boolean capturarDadosDoTotem(JdbcTemplate connection, int idTotem) {
        String dadosHardwareQuery = "SELECT COUNT(*) FROM dadosHardWare WHERE fkTotem = ?";
        String alertaQuery = "SELECT COUNT(*) FROM alerta WHERE fkTotem = ?";

        int dadosHardwareCount = connection.queryForObject(dadosHardwareQuery, new Object[]{idTotem}, Integer.class);
        int alertaCount = connection.queryForObject(alertaQuery, new Object[]{idTotem}, Integer.class);

        // Se ambos os dados estiverem presentes, o totem é considerado ativo
        return dadosHardwareCount > 0 && alertaCount > 0;
    }

    public static void notificarStatus(boolean ativo) {
        if (ativo) {
            System.out.println("O totem está ativo.");
        } else {
            System.out.println("O totem está inativo.");
        }
    }
}
