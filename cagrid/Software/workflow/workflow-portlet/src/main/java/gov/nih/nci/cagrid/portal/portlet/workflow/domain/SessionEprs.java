package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionEprs implements Map<String, WorkflowSubmitted>, Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String, WorkflowSubmitted> eprs;

	public Map<String, WorkflowSubmitted> getEprs() {
		if(this.eprs==null) this.eprs = new HashMap<String, WorkflowSubmitted>();
		return eprs;
	}
	public void setEprs(Map<String, WorkflowSubmitted> eprs) {
		this.eprs = eprs;
	}
	
	@Override
	public int size() {
		return getEprs().size();
	}
	@Override
	public boolean isEmpty() {
		return getEprs().size()==0;
	}
	@Override
	public boolean containsKey(Object key) {
		return getEprs().containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return getEprs().containsValue(value);
	}
	@Override
	public WorkflowSubmitted get(Object key) {
		return getEprs().get(key);
	}
	@Override
	public WorkflowSubmitted put(String key, WorkflowSubmitted value) {
		return getEprs().put(key,value);
	}
	@Override
	public WorkflowSubmitted remove(Object key) {
		return getEprs().remove(key);
	}
	@Override
	public void putAll(Map<? extends String, ? extends WorkflowSubmitted> m) {
		getEprs().putAll(m);
	}
	@Override
	public void clear() {
		getEprs().clear();
	}
	@Override
	public Set<String> keySet() {
		return getEprs().keySet();
	}
	@Override
	public Collection<WorkflowSubmitted> values() {
		return getEprs().values();
	}
	@Override
	public Set<java.util.Map.Entry<String, WorkflowSubmitted>> entrySet() {
		return getEprs().entrySet();
	}
}
