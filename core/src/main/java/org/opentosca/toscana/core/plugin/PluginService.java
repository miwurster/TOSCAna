package org.opentosca.toscana.core.plugin;

import org.opentosca.toscana.core.transformation.platform.Platform;
import org.opentosca.toscana.core.transformation.platform.PlatformService;

import java.util.List;

public interface PluginService extends PlatformService {
	
	List<TransformationPlugin> getPlugins();
	
	default TransformationPlugin findPluginByPlatform(Platform platform) {
		List<TransformationPlugin> p = getPlugins();
		for (TransformationPlugin e : p) {
			if(e.getPlatformDetails().id.equals(platform.id)) {
				return e;
			}
		}
		return null;
	}
}
