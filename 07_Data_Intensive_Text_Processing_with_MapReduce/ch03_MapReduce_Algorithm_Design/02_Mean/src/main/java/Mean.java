
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class Mean {

    public static class MeanMapper extends Mapper<Object, Text, Text, IntWritable> {

        private Map<String, Integer> countMap = new HashMap<>();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split(",");
            if (fields.length != 2) {
                return;
            }
            String type = fields[0];
            int v = Integer.parseInt(fields[1]);
            context.write(new Text(type), new IntWritable(v));
        }

    }

    public static class MeanReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

        private DoubleWritable result = new DoubleWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            int count = 0;
            for (IntWritable val : values) {
                sum += val.get();
                count++;
            }
            result.set(sum / ((double) count));
            context.write(key, result);
        }
    }

}
