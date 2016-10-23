
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class Mean_InMapper_Combiner {

    public static class SumCount implements WritableComparable<SumCount> {

        LongWritable sum;
        IntWritable count;

        public SumCount() {
            set(new LongWritable(0), new IntWritable(0));
        }

        public SumCount(Long sum, Integer count) {
            set(new LongWritable(sum), new IntWritable(count));
        }

        public void set(LongWritable sum, IntWritable count) {
            this.sum = sum;
            this.count = count;
        }

        public LongWritable getSum() {
            return sum;
        }

        public IntWritable getCount() {
            return count;
        }

        public void addSumCount(SumCount sumCount) {
            set(new LongWritable(this.sum.get() + sumCount.getSum().get()), new IntWritable(this.count.get() + sumCount.getCount().get()));
        }

        @Override
        public void write(DataOutput dataOutput) throws IOException {
            sum.write(dataOutput);
            count.write(dataOutput);
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
            sum.readFields(dataInput);
            count.readFields(dataInput);
        }

        @Override
        public int compareTo(SumCount sumCount) {
            // compares the first of the two values
            int comparison = sum.compareTo(sumCount.sum);

            // if they're not equal, return the value of compareTo between the "sum" value
            if (comparison != 0) {
                return comparison;
            }

            // else return the value of compareTo between the "count" value
            return count.compareTo(sumCount.count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SumCount sumCount = (SumCount) o;

            return count.equals(sumCount.count) && sum.equals(sumCount.sum);
        }

        @Override
        public int hashCode() {
            int result = sum.hashCode();
            result = 31 * result + count.hashCode();
            return result;
        }

    }

    public static class MeanMapper extends Mapper<Object, Text, Text, SumCount> {

        private Map<String, Long> sumMap = new HashMap<>();
        private Map<String, Integer> countMap = new HashMap<>();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split(",");
            if (fields.length != 2) {
                return;
            }
            String type = fields[0];
            int v = Integer.parseInt(fields[1]);
            sumMap.put(type, sumMap.getOrDefault(type, 0L) + v);
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            for (String key : sumMap.keySet()) {
                context.write(new Text(key), new SumCount(sumMap.get(key), countMap.get(key)));

            }
        }
    }

    public static class MeanReducer extends Reducer<Text, SumCount, Text, DoubleWritable> {

        private DoubleWritable result = new DoubleWritable();

        @Override
        public void reduce(Text key, Iterable<SumCount> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            int count = 0;
            for (SumCount pair : values) {
                sum += pair.getSum().get();
                count += pair.getCount().get();
            }
            result.set(sum / ((double) count));
            context.write(key, result);
        }
    }

}
