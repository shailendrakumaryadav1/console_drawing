package drawing;

public class Canvas
{
    private static final char BLANK = ' ';
    protected static final char POINT = 'x';
    private static final char EDGE_VER = '|';
    private static final char EDGE_HOR = '-';
    private static final char DUMMY_COLOR = (char) -1;

    private int width, height;
    private char[][] canvas;

    public Canvas(int width, int height)
    {
        this.width = width;
        this.height = height;
        if (width <= 0)
            throw new IllegalArgumentException("Width must be greater than 0");
        else if (height <= 0)
            throw new IllegalArgumentException("Height must be greater than 0");
        initialize();
    }

    private void initialize()
    {
        canvas = new char[height + 2][width + 2];
        for (int j = 1; j <= height; j++)
        {
            canvas[j][0] = canvas[j][width + 1] = EDGE_VER;
            for (int i = 1; i <= width; i++)
                canvas[j][i] = BLANK;
        }
        for (int i = 0; i <= width + 1; i++)
            canvas[0][i] = canvas[height + 1][i] = EDGE_HOR;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void paint()
    {
        for (char[] row : canvas)
        {
            for (char item : row)
                System.out.print(item);
            System.out.println();
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2)
    {
        if (x1 != x2 && y1 != y2)
            throw new IllegalArgumentException(Constants.ERR_LINE_STRAIGHT);
        if (y1 == y2)
        { // horizontal line
            if (x1 > x2)
            { // swap values if x1 is larger than x2
                x1 ^= x2;
                x2 ^= x1;
                x1 ^= x2;
            }
            if (x1 < 1 || x1 > width || x2 > width || y1 < 1 || y1 > height)
                throw new IllegalArgumentException(Constants.ERR_LINE_BOUNDARIES);
            for (int i = x1; i <= x2; i++)
                canvas[y1][i] = POINT;
        } else
        { // vertical line
            if (y1 > y2)
            { // swap values if x1 is larger than x2
                y1 ^= y2;
                y2 ^= y1;
                y1 ^= y2;
            }
            if (y1 < 1 || y1 > height || y2 > height || x1 < 1 || x1 > width)
                throw new IllegalArgumentException(Constants.ERR_LINE_BOUNDARIES);
            for (int j = y1; j <= y2; j++)
                canvas[j][x1] = POINT;
        }
    }

    public void drawRect(int x1, int y1, int x2, int y2)
    {
        drawLine(x1, y1, x1, y2);
        drawLine(x2, y1, x2, y2);
        drawLine(x1, y1, x2, y1);
        drawLine(x1, y2, x2, y2);
    }

    public void fill(int x, int y, char color)
    {
        if (x < 1 || x > width || y < 1 || y > height)
            throw new IllegalArgumentException(Constants.ERR_FILL_BOUNDARIES);
        char originalColor = canvas[y][x];
        replaceColor(originalColor, DUMMY_COLOR);
        doFill(x, y, color);
        replaceColor(DUMMY_COLOR, originalColor);
    }

    private void doFill(int x, int y, char color)
    {
        if (canvas[y][x] == DUMMY_COLOR &&
                x >= 1 && x <= width && y >= 1 && y <= height)
        {
            canvas[y][x] = color;
            doFill(x + 1, y, color);
            doFill(x, y + 1, color);
            doFill(x - 1, y, color);
            doFill(x, y - 1, color);
        }
    }

    private void replaceColor(char fromColor, char toColor)
    {
        for (int j = 1; j <= height; j++)
            for (int i = 1; i <= width; i++)
                if (canvas[j][i] == fromColor)
                    canvas[j][i] = toColor;
    }

    public char getPoint(int x, int y)
    {
        if (x < 1 || x > width || y < 1 || y > height)
            throw new IllegalArgumentException(Constants.ERR_POINT_BOUNDARIES);
        return canvas[y][x];
    }
}
