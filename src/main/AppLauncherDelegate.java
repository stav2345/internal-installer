package main;

import java.io.File;
import java.io.IOException;
import config.GithubConfig;

public class AppLauncherDelegate {

	/**
	 * Launch the real application
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void launchApp() throws InterruptedException, IOException {
		
		// get the paths
		GithubConfig config = new GithubConfig();
		
		String appPath = config.getAppPath();
		String jarName = config.getValue(GithubConfig.JAR_PATH);
		//String javaPath = config.getValue(GithubConfig.JAVA_PATH);
		
		System.out.println("Launching " + jarName 
				//+ " with java in " + javaPath 
				+ " from folder " + appPath);
		
		// uncomment and add as parameter for mac os x
		// String firstThread = "-XstartOnFirstThread";
		
		//for Windows 32/64 bit version with custom heap size
		ProcessBuilder pb = new ProcessBuilder("java", "-Xms128m", "-Xmx768m", "-jar", jarName); 
		
		//for os x 32/64 bit version
		//ProcessBuilder pb = new ProcessBuilder(javaPath, "-jar", jarName, heapSize, gc, firstThread); 
		
		// set the new working directory
		pb.directory(new File(appPath));
		
		// inherit console
		pb.inheritIO();

		// start the process
		Process p = pb.start();
		
		p.waitFor();
	}
}
