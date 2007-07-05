/**
 * 
 */

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ProgressCalc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int progress = 0;
		ATask[] tasks = new ATask[]{
			new ATask(1, 1),
			new ATask(4, 3)
		};
		int scale = 1000;
		for(int i = 0; i < tasks.length; i++){
			int numSteps = tasks[i].numSteps;
			double taskWeight = scale / tasks.length;
			double stepWeight = taskWeight / numSteps;
			progress += stepWeight * tasks[i].lastStep;
		}
		System.out.println("progress = " + progress);
	}
	
	private static class ATask{
		int numSteps;
		int lastStep;
		ATask(int numSteps, int lastStep){
			this.numSteps = numSteps;
			this.lastStep = lastStep;
		}
	}
}
