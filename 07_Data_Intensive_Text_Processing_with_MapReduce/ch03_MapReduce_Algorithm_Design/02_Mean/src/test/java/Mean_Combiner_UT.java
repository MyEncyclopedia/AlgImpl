
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

public class Mean_Combiner_UT extends AbstractUnitTest {

    @Test
    public void test() throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ",");

        Job job = Job.getInstance(conf, "Mean");
        job.setJarByClass(Mean_Combiner.class);
        job.setMapperClass(Mean_Combiner.MeanMapper.class);
        job.setMapOutputValueClass(Mean_Combiner.SumCount.class);
        job.setCombinerClass(Mean_Combiner.MeanCombiner.class);
        job.setReducerClass(Mean_Combiner.MeanReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        this.execJob(job, "/case1");
    }
}
