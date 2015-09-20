/* *********************************************************************
 * Compilation:     javac JavaShell.java
 * Execution:       java JavaShell
 * Dependencies:    none
 *
 * Example: ("$ " stands for the prompt of the current system shell)
 * --------------------------------------------------
 * $ java JavaShell
 * 
 * Welcome to Java Simulation of UNIX shell!
 *
 * [Java-Shell@Linux: /home/username]$ ls
 * JavaShell.class
 * JavaShell.java
 * [Java-Shell@Linux: /home/username]$ pwd
 * /home/username
 * [Java-Shell@Linux: /home/username]$ exit
 * $ 
 * --------------------------------------------------
 *
 * A primitive simulation of UNIX shell written in Java.
 *
 ***********************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class is a Java implementation that mimics the command line interaction
 * of a common UNIX shell (like bash). Run this class and then a prompt that
 * indicates the program is ready for command line input appears on the
 * console. One can try out the command like <tt>cd, ls, pwd, cat</tt>, etc.
 *
 * @author Xin Sun
 */
public class JavaShell {
    private Path homeDir;
    private String osName;
    private Path currentWorkingDir;

    public JavaShell() {
        homeDir = Paths.get(System.getProperty("user.home"));
        osName = System.getProperty("os.name");
        currentWorkingDir = homeDir.toAbsolutePath();
    }

    public static void main(String[] args) {
        new JavaShell().runShell();
    }

    public void runShell() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("\nWelcome to Java Simulation of UNIX shell!\n\n");

        while (true) {
            System.out.print("[Java-Shell@" + osName + ": " + currentWorkingDir.getFileName() + "]$ ");
            String line = sc.nextLine();

            if (line.equals("exit") || line.equals("quit"))
                break;
            else
                executeCommand(line);
        }
        sc.close();
    }

    private void executeCommand(String line) {
        String[] commandList = line.split(" ");
        String baseCommand = commandList[0];

        // ls command
        if (baseCommand.equals("ls")) {
            Path dirName;
            if (commandList.length == 1)
                dirName = currentWorkingDir;
            else
                dirName = Paths.get(commandList[1]);

            // File dir = new File(dirName.toString());
            // String[] fileList = dir.list();
            // for (int i = 0; i < fileList.length; i++)
            //     System.out.println(fileList[i]);
            try {
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirName);
                for (Path p : dirStream)
                    System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // cd command
        if (baseCommand.equals("cd")) {
            if (commandList.length == 1) {
                currentWorkingDir = homeDir;    
                return;
            }
            if (Paths.get(commandList[1]).getRoot() == null) // case where user's input is a relative path
                currentWorkingDir = currentWorkingDir.resolve(Paths.get(commandList[1])).normalize();
            else // case where user's input is an absolute path
                currentWorkingDir = Paths.get(commandList[1]);
            return;
        }

        // pwd command
        if (baseCommand.equals("pwd")) {
            System.out.println(currentWorkingDir);
            return;
        }
        
        // cat command
        if (baseCommand.equals("cat")) {
            try {
                Path filename;
                if (Paths.get(commandList[1]).getRoot() == null)
                    filename = currentWorkingDir.resolve(Paths.get(commandList[1]));
                else
                    filename = Paths.get(commandList[1]);

                BufferedReader reader = new BufferedReader(new FileReader(filename.toString()));
                String lineOfText = null;
                while ((lineOfText = reader.readLine()) != null)
                    System.out.println(lineOfText);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        System.out.format("Command '%s' not supported.\n", baseCommand);
    }
}

