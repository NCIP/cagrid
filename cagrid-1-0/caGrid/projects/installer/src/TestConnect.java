import java.io.InputStream;
import java.net.URL;


public class TestConnect {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		String url = "http://localhost:8080/yadda";
//		String url = "http://www.google.com";
//		String url = "file:/Users/joshua/downloads/apache-ant-1.6.5-bin.zip";
		String url = "http://localhost:8080/downloads/caGrid.zip";
		ConnectThread t = new ConnectThread(new URL(url));
		t.start();
		t.join(10000);
		if(t.getEx() != null){
			throw t.getEx();
		}
		if(!t.isFinished()){
			throw new Exception("timed out");
		}
		InputStream in = t.getIn();
		System.out.println("Available: " + in.available());
	}
	
	private static class ConnectThread extends Thread{
		private Exception ex;
		private URL url;
		private boolean finished;
		private InputStream in;
		
		ConnectThread(URL url){
			this.url = url;
		}
		public void run(){
			try{
				in = url.openStream();
				finished = true;
			}catch(Exception ex){
				this.ex = ex;
			}
		}
		Exception getEx(){
			return ex;
		}
		boolean isFinished(){
			return finished;
		}
		InputStream getIn(){
			return in;
		}
	}

}
