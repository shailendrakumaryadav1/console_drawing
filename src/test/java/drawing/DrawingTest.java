package drawing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static junit.framework.Assert.assertEquals;

public class DrawingTest
{

    public DrawingTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }


    /**
     * Test of processCommand method, of class Drawing.
     */
    @org.junit.Test
    public void testProcessCommand()
    {
        System.out.println("processCommand");
        String[] cmd = new String[]{
                "L 1 2 6 2",
                "R 14 1 18 3",

                "C 20 4",
                "L 1 2 6 2",
                "L 6 3 6 4",
                "R 14 1 18 3",
                "B 10 3 o",
                "Q",

                "Z",
                "C 5",
                "C -2 50",

                "C 30 30",
                "L 1 2 6 2",
                "L 6 3 6 4",
                "R 14 1 18 3",
                "R 20 20 25 28",
                "B 24 28 X",
                "B 10 3 o",
                "B 10 3 r",
                "B 29 29 b",
                "B 25 28 Y",
                "B 29 29  ", // special case filling with blank
                "Q"
        };
        boolean[] shouldRaiseError = new boolean[]
                {
                        true,
                        true,

                        false,
                        false,
                        false,
                        false,
                        false,
                        false,

                        true,
                        true,
                        true,

                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false
                };
        Drawing instance = new Drawing();
        for (int idx = 0; idx < cmd.length; idx++)
        {
            try
            {
                if (!instance.processCommand(cmd[idx]))
                    instance = new Drawing();
                assertEquals(shouldRaiseError[idx], false);
            } catch (IllegalArgumentException e)
            {
                assertEquals(shouldRaiseError[idx], true);
            }
            instance.paint();
        }
    }

}
