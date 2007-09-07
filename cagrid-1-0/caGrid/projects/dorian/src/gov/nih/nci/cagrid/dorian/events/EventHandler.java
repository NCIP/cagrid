package gov.nih.nci.cagrid.dorian.events;

import java.util.HashMap;
import java.util.Map;


public abstract class EventHandler {

	private SubjectResolver subjectResolver;

	private Map<String, String> properties;


	public EventHandler(EventHandlerConfiguration conf) {
		properties = new HashMap<String, String>();
		if (conf != null) {
			Property[] props = conf.getProperty();
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					properties.put(props[i].getName(), props[i].getValue());
				}
			}
		}
	}


	public SubjectResolver getSubjectResolver() {
		return subjectResolver;
	}


	protected void setSubjectResolver(SubjectResolver subjectResolver) {
		this.subjectResolver = subjectResolver;
	}


	public abstract void handleEvent(Event event);

}
