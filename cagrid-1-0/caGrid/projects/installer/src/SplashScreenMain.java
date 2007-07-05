import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import org.cagrid.installer.SplashScreen;

public class SplashScreenMain {

	SplashScreen screen;

	public SplashScreenMain() {
		splashScreenInit();

		for (int i = 0; i <= 100; i++) {
			for (long j = 0; j < 50000; ++j) {
				String poop = " " + (j + i);
			}
			screen.setProgress("Downloading configuration..." + i, i);
		}
		splashScreenDestruct();
		System.exit(0);
	}

	private void splashScreenDestruct() {
		screen.setScreenVisible(false);
	}

	private void splashScreenInit() {
		ImageIcon myImage = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource(
						"images/caGrid-notext.gif"));
		screen = new SplashScreen(myImage);
		screen.setLocationRelativeTo(null);
		screen.setProgressMax(100);
		screen.setScreenVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SplashScreenMain();
	}

}