
import com.google.common.io.Files;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Assert;
import org.junit.Before;

public class AbstractUnitTest {

    public AbstractUnitTest() {
    }

    File tempOutDir;

    @Before
    public void prepare() {
        tempOutDir = new File(Files.createTempDir(), "out");
    }

    public static void compareFileContent(File expected, File output) throws Exception {
        List<String> expectedContent = Files.readLines(expected, Charset.forName("UTF-8"));
        List<String> outputContent = Files.readLines(output, Charset.forName("UTF-8"));
        Collections.sort(outputContent);
        Assert.assertArrayEquals("", expectedContent.toArray(), outputContent.toArray());
    }

    public void execJob(Job job, String inputFile) throws Exception {
        URL urlInput = AbstractUnitTest.class.getResource(inputFile);
        URL urlResult = AbstractUnitTest.class.getResource(inputFile + "_result");

        FileInputFormat.addInputPath(job, new Path(urlInput.getPath()));
        FileOutputFormat.setOutputPath(job, new Path(tempOutDir.toString()));
        job.waitForCompletion(true);

        compareFileContent(new File(urlResult.getPath()), new File(tempOutDir, "part-r-00000"));
    }

}
