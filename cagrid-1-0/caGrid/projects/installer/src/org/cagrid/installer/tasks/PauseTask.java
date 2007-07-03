/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class PauseTask extends BasicTask {

	private long pauseTime;

	/**
	 * @param name
	 * @param description
	 */
	public PauseTask(String name, String description, long pauseTime) {
		super(name, description);
		this.pauseTime = pauseTime;
	}

	/* (non-Javadoc)
	 * @see org.cagrid.installer.tasks.BasicTask#internalExecute(java.util.Map)
	 */
	@Override
	protected Object internalExecute(Map state) throws Exception {
		Thread.sleep(this.pauseTime);
		return null;
	}

}
