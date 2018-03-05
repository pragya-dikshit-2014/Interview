package case1;

import java.util.Timer;

public class FolderMonitor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Timer().scheduleAtFixedRate(new Monitor(), 0, 30000);
		new Timer().schedule(new CopyThread(), 0, 12000);
	}
}
