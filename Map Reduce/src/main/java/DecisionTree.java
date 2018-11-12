import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;

public class DecisionTree {

    private static final String inputPath = "/UNSW-NB15/training-set.csv";
    private static final String outputPath = "/UNSW-NB15/confusion-matrix";

    public static class DTMapper extends Mapper<Object, Text, Text, IntWritable> {

        private Text confusionMatrix = new Text();
        private static final String CSV_SEPARATOR = ",";
        private final IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split(CSV_SEPARATOR);

            float duration = Float.parseFloat(line[1]);
            String protocol = line[2];
            int sbytes = Integer.parseInt(line[7]);
            int label = Integer.parseInt(line[44]);
            int prediction;

            if (duration < 0) {
                if (sbytes < 109) {
                    switch (protocol) {
                        case "udp":
                            prediction = 0;
                            break;
                        case "arp":
                            prediction = 0;
                            break;
                        case "tcp":
                            prediction = 0;
                            break;
                        case "ospf":
                            prediction = 0;
                            break;
                        case "sctp":
                            prediction = 1;
                            break;
                        case "unas":
                            prediction = 0;
                            break;
                        default:
                            prediction = 1;
                            break;
                    }
                } else if (sbytes < 201) prediction = 1;
                else prediction = 0;
            } else if (duration < 0.16) {
                if (sbytes < 128) prediction = 1;
                else prediction = 0;
            } else {
                switch (protocol) {
                    case "udp":
                        prediction = 0;
                        break;
                    case "arp":
                        prediction = 0;
                        break;
                    case "tcp":
                        prediction = 0;
                        break;
                    case "ospf":
                        prediction = 1;
                        break;
                    case "sctp":
                        prediction = 1;
                        break;
                    case "unas":
                        prediction = 0;
                        break;
                    default:
                        prediction = 1;
                        break;
                }
            }

            confusionMatrix.set(prediction + "," + label);
            context.write(confusionMatrix, one);
        }
    }

    public static class DTReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();
        ArrayList<Integer> predictions = new ArrayList<>();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int totalValues = 0;
            for (IntWritable value : values) {
                predictions.add(value.get());
                totalValues++;
            }

            result.set(totalValues);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "Map Reduce - Decision Tree");
        job.setJarByClass(DecisionTree.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(DTMapper.class);
        job.setCombinerClass(DTReducer.class);
        job.setReducerClass(DTReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
