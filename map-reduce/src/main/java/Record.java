import java.util.Random;

public class Record {

    public int id;
    public float duration;
    public String protocol;
    public int sbytes;
    public int dbytes;
    public int label;
    public int prediction = -1; // Not predicted yet.

    Record() {
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setDuration(String duration) {
        this.duration = Float.parseFloat(duration);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setSbytes(String sbytes) {
        this.sbytes = Integer.parseInt(sbytes);
    }

    public void setDbytes(String dbytes) {
        this.dbytes = Integer.parseInt(dbytes);
    }

    public void setLabel(String label) {
        this.label = Integer.parseInt(label);
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public void setFalsePrediction(){
        this.setPrediction(0);
    }

    public void setTruePrediction(){
        this.setPrediction(1);
    }

    public void setRandomPrediction(){
        this.setPrediction(new Random().nextBoolean() ? 1 : 0);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", duration=" + duration +
                ", protocol='" + protocol + '\'' +
                ", sbytes=" + sbytes +
                ", dbytes=" + dbytes +
                ", label=" + label +
                ", prediction=" + prediction +
                '}';
    }
}
