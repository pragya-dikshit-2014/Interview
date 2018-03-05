package case1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.TimerTask;

public class CopyThread extends TimerTask {

	private String tempDirectory = "";
	private String securedDirectory= "";
	
	private void loadDirectories() {
		FileReader fr = null;
		try {
			fr = new FileReader("config.properties");
			Properties p = new Properties();
			p.load(fr);
			tempDirectory = p.getProperty("tempFolder");
			securedDirectory = p.getProperty("securedFolder");
		}catch(Exception e) {
			
		}finally {
			if(fr!=null) {
				try {
					fr.close();
				}catch(IOException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
	
	public boolean directoryValidation() {
		File tempDir = new File(tempDirectory);
		File secureDir = new File(securedDirectory);
		if(!(secureDir.exists()))
			secureDir.mkdirs();
		return secureDir.exists() && tempDir.exists();
	}
	
	private int copyingFiles() {
		File[] files = new File(tempDirectory).listFiles();
		for(File file : files) {
			try {
				Files.copy(file.toPath(), new File(securedDirectory).toPath());
			}catch(IOException io) {
				io.printStackTrace();
			}
		}
		return files.length;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		loadDirectories();
		if(directoryValidation()) {
			System.out.println("Total files copied from temp to secured directory: "+copyingFiles());
		}else {
			System.out.println("Directories are not available. Not copying files from temp to secured folder");
		}
	}
}
