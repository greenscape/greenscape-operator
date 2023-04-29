package io.greenscape.operator;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("org.greeenscape")
@Version("v1")
public class GreenscapeEngineSchema extends CustomResource<GreenscapeEngineSpec, GreenscapeEngineStatus> implements Namespaced {
	private static final long serialVersionUID = 6573725255743272742L;

}
