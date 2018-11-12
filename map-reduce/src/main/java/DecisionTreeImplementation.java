import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DecisionTreeImplementation {

    private static final String TRAINING_DATA_SET_FILENAME = "UNSW_NB15_training-set.csv";
    private static final String TESTING_DATA_SET_FILENAME = "UNSW_NB15_testing-set.csv";
    private static final String CSV_SEPARATOR = ",";

    public static void main(String[] args) {
        DecisionTreeImplementation dt = new DecisionTreeImplementation();
        dt.predict();

    }

    private Function<String, Record> mapToRecord = (line) -> {
        Record item = new Record();
        String[] attribute = line.split(CSV_SEPARATOR);

        item.setId(attribute[0]);
        item.setDuration(attribute[1]);
        item.setProtocol(attribute[2]);
        item.setSbytes(attribute[7]);
        item.setDbytes(attribute[8]);
        item.setLabel(attribute[44]);

        return item;
    };

    private List<Record> get_records(String data) {

        List<Record> records = new ArrayList<>();

        try {
            InputStream inputStream = this.getClass().getResourceAsStream(data);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            records = bufferedReader.lines().skip(1).map(mapToRecord).collect(Collectors.toList());

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    private List<Record> get_training_data() {
        return get_records(TRAINING_DATA_SET_FILENAME);
    }

    private List<Record> get_testing_data() {
        return get_records(TESTING_DATA_SET_FILENAME);
    }

    public void predict() {
        List<Record> training_data = get_training_data();

        int ff = 0;
        int tt = 0;

        for (Record record : training_data) {
            if (record.duration < 0) {
                if (record.sbytes < 109) {
                    switch (record.protocol) {
                        case "udp":
                            record.setFalsePrediction();
                            break;
                        case "arp":
                            record.setFalsePrediction();
                            break;
                        case "tcp":
                            record.setFalsePrediction();
                            break;
                        case "ospf":
                            record.setFalsePrediction();
                            break;
                        case "sctp":
                            record.setTruePrediction();
                            break;
                        case "unas":
                            record.setFalsePrediction();
                            break;
                        default:
                            record.setTruePrediction();
                            break;
                    }
                } else if (record.sbytes < 201) record.setTruePrediction();
                else record.setFalsePrediction();
            } else if (record.duration < 0.16) {
                if(record.sbytes < 128) record.setTruePrediction();
                else record.setFalsePrediction();
            }else {
                switch (record.protocol) {
                    case "udp":
                        record.setFalsePrediction();
                        break;
                    case "arp":
                        record.setFalsePrediction();
                        break;
                    case "tcp":
                        record.setFalsePrediction();
                        break;
                    case "ospf":
                        record.setTruePrediction();
                        break;
                    case "sctp":
                        record.setTruePrediction();
                        break;
                    case "unas":
                        record.setFalsePrediction();
                        break;
                    default:
                        record.setTruePrediction();
                        break;
                }
            }

            if (record.label == record.prediction) {
                tt++;
            }else{
                ff++;
            }
        }

        System.out.println("tt=" + tt);
        System.out.println("ff=" + ff);
    }

}
