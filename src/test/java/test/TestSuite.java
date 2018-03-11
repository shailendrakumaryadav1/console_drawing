package test;

import drawing.CanvasTest;
import drawing.DrawingTest;

@org.junit.runner.RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses(
        {
                CanvasTest.class,
                DrawingTest.class
        })
public class TestSuite
{

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @org.junit.Before
    public void setUp() throws Exception
    {
    }

    @org.junit.After
    public void tearDown() throws Exception
    {
    }
}
