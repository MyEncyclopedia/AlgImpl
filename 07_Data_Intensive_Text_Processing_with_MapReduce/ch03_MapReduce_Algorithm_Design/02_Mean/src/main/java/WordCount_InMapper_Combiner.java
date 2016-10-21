
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCount_InMapper_Combiner {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private Map<String, Integer> countMap = new HashMap<>();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String word = itr.nextToken().trim();
                if (countMap.containsKey(word)) {
                    countMap.put(word, countMap.get(word) + 1);
                } else {
                    countMap.put(word, 1);
                }
            }
        }
        
        @Override
        public void cleanup(Context context)  throws IOException, InterruptedException {
            for (Entry<String, Integer> entry : countMap.entrySet()) {
                context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

}
