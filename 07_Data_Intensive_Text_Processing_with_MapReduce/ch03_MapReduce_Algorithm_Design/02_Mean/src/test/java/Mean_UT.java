
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

public class Mean_UT extends AbstractUnitTest {

    @Test
    public void test() throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ","); 
        Job job = Job.getInstance(conf, "Mean");
        job.setJarByClass(Mean.class);
        job.setMapperClass(Mean.MeanMapper.class);
        job.setReducerClass(Mean.MeanReducer.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        
        this.execJob(job, "/case1");
    }
}
