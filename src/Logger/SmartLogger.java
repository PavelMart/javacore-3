package Logger;

import java.time.LocalDateTime;

public class SmartLogger implements Logger {
    private int orderNumber = 1;

    @Override
    public String log(String msg) {
        LocalDateTime date = LocalDateTime.now();
        String type = "INFO";

        if (msg.toLowerCase().contains("Ошибка")) type = "ERROR";

        String log = type + "#" + orderNumber + " [" + date + "] " + msg + "\n" ;

        orderNumber++;
        return log;
    }
}
