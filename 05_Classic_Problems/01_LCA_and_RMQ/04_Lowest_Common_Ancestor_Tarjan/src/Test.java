
public class Test {

    public static void main(String[] args) {
        Main_Recurse.TarjanLCA tarjan = new Main_Recurse.TarjanLCA(5);
        tarjan.createChild("Adam", "Sam");
        tarjan.createChild("Sam", "Joey");
        tarjan.createChild("Sam", "Micheal");
        tarjan.createChild("Adam", "Kevin");
        tarjan.addQuery("Sam", "Sam");
        tarjan.addQuery("Adam", "Sam");
        tarjan.addQuery("Micheal", "Kevin");
        tarjan.addQuery("Joey", "Micheal");
        for (String s : tarjan.computeAnswers()) {
            System.out.println(s);
        }
    }
}
