import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TupleUtilTest {
    
    public TupleUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of combineTwoTuples method, of class TupleUtil.
     */
    @Test
    public void testCombineTwoTuples() {
        System.out.println("combineTwoTuples");
        Tuple t1 = null;
        Tuple t2 = null;
        Tuple expResult = null;
        Tuple result = TupleUtil.combineTwoTuples(t1, t2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateAllPossible method, of class TupleUtil.
     */
    @Test
    public void testGenerateAllPossible() {
        System.out.println("generateAllPossible");
        Tuple tuple = null;
        List<Tuple> expResult = null;
        List<Tuple> result = TupleUtil.generateAllPossibleLower(tuple);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class TupleUtil.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Tuple tuple = null;
        Tuple sub = null;
        boolean expResult = false;
        boolean result = TupleUtil.contains(tuple, sub);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
