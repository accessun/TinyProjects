/* *********************************************************************
 * Compilation:		javac JavaShell.java
 * Execution:		java JavaShell
 * Dependencies:	none
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

import java.io.File;
import java.util.Scanner;

/**
 *
 */
public class JavaShell {
	private String homeDir = System.getProperty("user.home");
	private String osName = System.getProperty("os.name");
	private String currentWorkingDir;

	public JavaShell() {
		homeDir = System.getProperty("user.home");
		osName = System.getProperty("os.name");
		currentWorkingDir = new File(".").getAbsolutePath();
	}

	public static void main(String[] args) {
		new JavaShell().runShell();
	}

	public void runShell() {
		Scanner sc = new Scanner(System.in);
		System.out.printf("\nWelcome to Java Simulation of UNIX shell!\n\n");

		while (true) {
			System.out.print("[Java-Shell@" + osName + ": " + currentWorkingDir + "]$ ");
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

		// "ls" command
		if (baseCommand.equals("ls")) {
			String dirName;
			if (commandList.length == 1) dirName = currentWorkingDir;
			else dirName = commandList[1];

			File dir = new File(dirName);
			String[] fileList = dir.list();
			for (int i = 0; i < fileList.length; i++)
				System.out.println(fileList[i]);
			return;
		}

		// cd command
		if (baseCommand.equals("cd")) {
			if (commandList.length == 1) {
				currentWorkingDir = homeDir;	
				return;
			}
			currentWorkingDir = commandList[1];
			return;
		}

		// pwd command
		if (baseCommand.equals("pwd")) {
			System.out.println(currentWorkingDir);
			return;
		}
	}
}

