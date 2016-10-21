
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

public class WordCount_InMapper_Combiner_Test extends AbstractUnitTest {

    @Test
    public void test() throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount_InMapper_Combiner.class);
        job.setMapperClass(WordCount_InMapper_Combiner.TokenizerMapper.class);
        job.setReducerClass(WordCount_InMapper_Combiner.IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        this.execJob(job, "/case1");
    }
}
