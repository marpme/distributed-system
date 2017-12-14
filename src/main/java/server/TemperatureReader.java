package server;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TemperatureReader {

    private static final String PATH = "/Users/marvinpiekarek/study/dist-systems/src/main/resources/temperatures.csv";
    private static TemperatureReader instance;
    private List<TemperatureHistory> temperatures = null;

    @SuppressWarnings({"rawtypes", "unchecked"})
    private TemperatureReader() {
        try {
            temperatures = new CsvToBeanBuilder(new FileReader(PATH))
                    .withType(TemperatureHistory.class)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static synchronized TemperatureReader getInstance() {
        if (TemperatureReader.instance == null) {
            TemperatureReader.instance = new TemperatureReader();
        }

        return TemperatureReader.instance;
    }


    Optional<TemperatureHistory> receiveTemperatureForDate(Date date) {
        return temperatures.stream().filter(temp -> temp.getDate().equals(date)).findFirst();
    }
}
