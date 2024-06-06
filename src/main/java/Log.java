import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Log {

        static int fileCount = 0;
        static final int maxLogLines = 150;
        static int totalTextLines = 0;
        static List<String> tipos = List.of("DANGER", "WARNING", "INFO", "CONFIG", "OK");
        static BufferedWriter bw;
        static FileWriter fw;


        public static void createLog(Class<?> classe, String tipo, String mensagem) throws IOException {

            if (bw == null) {
                openNewLogFile();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(formatter);

            for (String tipoLog : tipos) {
                if (tipo.toUpperCase().equals(tipoLog)) {

                    if (totalTextLines >= maxLogLines) {
                        bw.close();
                        fw.close();
                        totalTextLines = 0;
                        openNewLogFile();
                    }
                    else {
                        bw.write(formattedDateTime + " - " + tipoLog + " - " +  classe + " - " +  mensagem);
                        bw.newLine();
                        totalTextLines++;
                        bw.flush();
                        break;
                    }
                }
            }
        }

        private static void openNewLogFile() throws IOException {
            String user = System.getProperty("user.name");
            String so = System.getProperty("os.name").toLowerCase();

            Path aaa;

            if (so.contains("win")) {
                aaa = Paths.get("C:\\Users\\%s\\Documents".formatted(user));
            } else {
                aaa = Paths.get("/home/%s/Documents".formatted(user));
            }

            if (!Files.exists(aaa)) {
                Files.createDirectory(aaa);
            }

            Path looogJuju = aaa.resolve("logs");

            if (!Files.exists(looogJuju)) {
                Files.createDirectory(looogJuju);
            }

            String logFileName = "log" + fileCount + ".txt";
            File logFile = new File(looogJuju.toString(), logFileName);

            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            fw = new FileWriter(logFile, true);
            bw = new BufferedWriter(fw);
        }
    }

