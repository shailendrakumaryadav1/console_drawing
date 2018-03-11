package drawing;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;


public class Drawing
{
    public static void main(String[] args)
    {
        try
        {
            runConsole();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void runConsole() throws IOException
    {
        Console console = System.console();
        BufferedReader ideConsoleReader = null;
        if (console == null)
            ideConsoleReader = new BufferedReader(new InputStreamReader(System.in));
        Drawing drawing = new Drawing();
        while (true)
        {
            System.out.println(Constants.PROMPT_MSG);
            String cmd = (console != null ? console.readLine() : ideConsoleReader.readLine());
            boolean endsWithSpace = false;
            if (cmd.endsWith(" "))
                endsWithSpace = true;
            cmd = cmd.trim();
            if (cmd.isEmpty())
                continue;
            try
            {
                if (!drawing.processCommand(cmd, endsWithSpace))
                {
                    return;
                }
            } catch (IllegalArgumentException e)
            {
                System.err.println(e.getMessage());
            }
            drawing.paint();
        }
    }

    public Drawing()
    {
        canvas = null;
    }

    private Canvas canvas;

    public boolean processCommand(String cmd)
    {
        return processCommand(cmd, cmd.endsWith(" "));
    }

    public boolean processCommand(String cmd, boolean endsWithSpace)
    {
        cmd = cmd.trim();
        String[] cmdArgs = cmd.split("\\s+");
        if (cmdArgs.length == 0)
            return true;
        switch (cmdArgs[0].toUpperCase())
        {
            case Constants.CMD_CREATE:
            {
                checkArgCount(cmdArgs.length, "CREATE", 2);
                canvas = new Canvas(getInt(cmdArgs[1]), getInt(cmdArgs[2]));
                break;
            }
            case Constants.CMD_LINE:
            {
                checkArgCount(cmdArgs.length, "LINE", 4);
                checkCanvas();
                canvas.drawLine(getInt(cmdArgs[1]), getInt(cmdArgs[2]), getInt(cmdArgs[3]), getInt(cmdArgs[4]));
                break;
            }
            case Constants.CMD_RECT:
            {
                checkArgCount(cmdArgs.length, "RECT", 4);
                checkCanvas();
                canvas.drawRect(getInt(cmdArgs[1]), getInt(cmdArgs[2]), getInt(cmdArgs[3]), getInt(cmdArgs[4]));
                break;
            }
            case Constants.CMD_FILL:
            {
                char fillChar;
                if (cmdArgs.length == 3 && endsWithSpace)
                    fillChar = ' '; // Special case, fill with blank space
                else
                {
                    checkArgCount(cmdArgs.length, "FILL", 3);
                    fillChar = getChar(cmdArgs[3]);
                }
                checkCanvas();
                canvas.fill(getInt(cmdArgs[1]), getInt(cmdArgs[2]), fillChar);
                break;
            }
            case Constants.CMD_QUIT:
                return false;
            default:
                throw new IllegalArgumentException(String.format("Invalid command: %s", cmd));
        }
        return true;
    }

    private void checkCanvas()
    {
        if (canvas == null)
            throw new IllegalArgumentException("Canvas not yet created.");
    }

    private int getInt(String arg)
    {
        try
        {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(String.format("Argument %s should be an integer", arg), e);
        }
    }

    private char getChar(String arg)
    {
        if (arg.length() != 1)
            throw new IllegalArgumentException(String.format("Argument %s should be a char", arg));
        else return arg.charAt(0);
    }

    public void paint()
    {
        if (canvas != null)
            canvas.paint();
    }

    private void checkArgCount(int count, String command, int requiredCount)
    {
        if (count - 1 != requiredCount)
            throw new IllegalArgumentException(String.format("Command %s requires %d parameters", command, requiredCount));
    }

}