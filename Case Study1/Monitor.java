package case1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.TimerTask;

public class MonitoringScheduler extends TimerTask{

	private String archiveDirectory = "";
	private String securedDirectory = "";
	
	private void loadDirectories() {
		FileReader fr = null;
		try {
			fr = new FileReader("config.properties");
			Properties p = new Properties();
			p.load(fr);
			archiveDirectory = p.getProperty("archiveFolder");
			securedDirectory = p.getProperty("securedFolder");	
		}catch(Exception e) {
			
		}finally {
			if(fr!=null) {
				try {
					fr.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public boolean directoryValidation() {
		File archDir = new File(archiveDirectory);
		
		if(!archDir.exists()) {
			archDir.mkdirs();
		}
		
		File securedDir = new File(securedDirectory);
		return archDir.exists() && securedDir.exists();
		
	}
	
	private void deleteExecutableFiles() {
		File archDir = new File(archiveDirectory);
		for(File file : archDir.listFiles()) {
			if(file.getName().endsWith(".bat") || file.getName().endsWith(".sh"))
				file.delete();
		}
	}
	
	private boolean isFileGreater(File file) {
		return file.length() > 100 * 1024 * 1024;
	}
	
	private boolean moveFileToArchive(File file) {
		return file.renameTo(new File(archiveDirectory+file.getName()));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		loadDirectories();
		BigInteger maxSize = new BigInteger(100 * 1024 * 1024 +"");
		BigInteger currentSize = new BigInteger("0");
		if(directoryValidation()) {
			deleteExecutableFiles();
			File f = new File(archiveDirectory);
			File[] files  = f.listFiles();
			Arrays.sort(files, new Comparator<File>() {
				public int compare(File o1, File o2) {
					if(o1.lastModified() > o2.lastModified()) {
						return -1;
					}else if(o1.lastModified() < o2.lastModified()) {
						return +1;
					}else {
						return 0;
					}
				}
			});
			
			ArrayList<String> fileGreaterThan100Mb = new ArrayList<String>();
			ArrayList<String> filesMoved = new ArrayList<String>();
			for(File file : files) {
				if(isFileGreater(file)) {
					if(!(moveFileToArchive(file))) {
						moveFileToArchive(file);
					}
					fileGreaterThan100Mb.add(file.getName());
				}else {
					if((maxSize.compareTo(currentSize.add(new BigInteger(file.length()+"")) )== 1 || maxSize.compareTo(currentSize.add(new BigInteger(file.length()+""))) == 0)) {
						currentSize = currentSize.add(new BigInteger(file.length()+""));
					}else {
						if(!moveFileToArchive(file))
							moveFileToArchive(file);
						filesMoved.add(file.getName());
					}
				}
			}
			
			System.out.println("Following files are moved to archive. Because they are greater than 100Mb");
			for(String fileName : fileGreaterThan100Mb) {
				System.out.println(fileName);
			}
			
			System.out.println("Following files are Moved: ");
			
			for(String fileName : filesMoved) {
				System.out.println(fileName);
			}
			
		}
	}
	
}

