package org.cagrid.ivy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.LogOptions;
import org.apache.ivy.core.cache.DefaultResolutionCacheManager;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.resolve.ResolvedModuleRevision;
import org.apache.ivy.core.retrieve.RetrieveOptions;
import org.apache.ivy.plugins.matcher.PatternMatcher;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;
import org.cagrid.grape.configuration.Grid;

public class Retrieve {
	private static Log log = LogFactory.getLog(Retrieve.class);
	
	URL ivySettings = null;
	String cacheDir = null;
	
	Ivy ivy = null;
	
	public Retrieve(URL ivySettings, String cacheDir) throws Exception {
		this.ivySettings = ivySettings;
		this.cacheDir = cacheDir;
		
		ivy = Ivy.newInstance();
		ivy.setVariable("cache", cacheDir);
		
		if (!log.isDebugEnabled()) {
			ivy.setVariable("log", "quiet");
			ivy.getLoggerEngine().setDefaultLogger(new DefaultMessageLogger(Message.MSG_ERR));
		} 
		
		ivy.configure(ivySettings);
		
	}

	public int execute(URL ivyDependencies, String baseDownloadDir, String organisation, String module, Grid grid) throws Exception {
		int retrieved = 0;
		String targetGridName = grid.getSystemName();
		
		ivy.setVariable("target.grid", targetGridName);
		ivy.setVariable("organisation", organisation);
		ivy.setVariable("module", module);
		
		DefaultResolutionCacheManager resolveEngine = (DefaultResolutionCacheManager) ivy.getResolutionCacheManager();
		resolveEngine.setResolvedIvyPattern("[organisation]/[module]/ivy-[revision].xml");
				
		ResolveReport report = null;
		try {
			ResolveOptions options = new ResolveOptions();
			if (!log.isDebugEnabled()) {
				options.setLog(LogOptions.LOG_QUIET);
			}
			report = ivy.resolve(ivyDependencies, options);
		} catch (Exception e) {
			throw new Exception("Unable to resolve the ivy dependencies", e);
		}
		
		ModuleRevisionId mrid = ModuleRevisionId.newInstance(organisation, module, targetGridName);
		RetrieveOptions options = new RetrieveOptions();

		options.setResolveId("org.cagrid-target-grids");

		String[] confs = {"default"};
		options.setConfs(confs);
		
        ModuleRevisionId[] mrids = ivy.listModules(ModuleRevisionId.newInstance(organisation,
        		module, "*", targetGridName + ".*?"), ivy.getSettings().getMatcher(PatternMatcher.REGEXP));
        if (mrids == null || mrids.length < 1) {
			log.warn("Unable to find revisions for target grid: " + targetGridName);
			return 0;
        }
        
        Arrays.sort(mrids, new Comparator<ModuleRevisionId>() {
			public int compare(ModuleRevisionId o1, ModuleRevisionId o2) {
				return o1.getRevision().compareTo(o2.getRevision());
			}
		});
              
        String version = grid.getVersion();
        if (version == null) {
        	version = targetGridName;
        }
        File gridDir = new File(baseDownloadDir	+ File.separator + targetGridName);
		if (!gridDir.exists() || ((version.compareTo(mrids[mrids.length - 1].getRevision())) < 0)) {

			try {
				retrieved = ivy.retrieve(mrid, baseDownloadDir
						+ File.separator + targetGridName + File.separator
						+ "[artifact].[ext]", options);
				ResolvedModuleRevision resolved = ivy
						.findModule(mrids[mrids.length - 1]);
				grid.setVersion(resolved.getDescriptor().getRevision());
				grid.setPublicationDate(resolved.getPublicationDate());

				Map extraInfo = resolved.getDescriptor().getExtraInfo();
				String displayName = (String) extraInfo.get("grid:displayName");

				if (displayName == null || (displayName.length() == 0)) {
					grid.setDisplayName(grid.getSystemName());
				}
			} catch (IOException e) {
				throw new Exception("Unable to find configuration files for target grid: " + targetGridName);
			}
		}
		
		return retrieved;
	}
	
	private String getSystemName(ResolveReport report) {
		List dependencies = report.getDependencies();
		IvyNode ivyNode = (IvyNode) dependencies.get(0);
		ModuleDescriptor descriptor = ivyNode.getDescriptor();
		Map map = descriptor.getExtraInfo();
		return (String) map.get("grid:systemName");
	}

	private String getDisplayName(ResolveReport report) {
		List dependencies = report.getDependencies();
		IvyNode ivyNode = (IvyNode) dependencies.get(0);
		ModuleDescriptor descriptor = ivyNode.getDescriptor();
		Map map = descriptor.getExtraInfo();
		return (String) map.get("grid:displayName");
	}

}
