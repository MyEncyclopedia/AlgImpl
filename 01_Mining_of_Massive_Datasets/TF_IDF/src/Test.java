
public class Test {

    public static void main(String[] args) {
        String[] docs = new String[]{"this is a a sample", "this is another another example example example"};
        TF_IDF tf_idf = new TF_IDF(docs);
        for (String s : tf_idf.getWordVector()) {
            System.out.print(s + "\t");
        }
        System.out.println("");
        for (double[] docV : tf_idf.getTF_IDFMatrix()) {
            for (double v : docV) {
                System.out.print(v + "\t");
            }
            System.out.println("");
        }
    }
}
