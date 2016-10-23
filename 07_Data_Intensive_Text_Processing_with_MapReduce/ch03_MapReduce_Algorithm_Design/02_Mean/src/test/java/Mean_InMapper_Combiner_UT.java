
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

public class Mean_InMapper_Combiner_UT extends AbstractUnitTest {

    @Test
    public void test() throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ",");

        Job job = Job.getInstance(conf, "Mean");
        job.setJarByClass(Mean_InMapper_Combiner.class);
        job.setMapperClass(Mean_InMapper_Combiner.MeanMapper.class);
        job.setReducerClass(Mean_InMapper_Combiner.MeanReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Mean_InMapper_Combiner.SumCount.class);
        job.setOutputValueClass(DoubleWritable.class);

        this.execJob(job, "/case1");
    }
}
