package io.greenscape.operator.dependent;

import java.util.Base64;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.greenscape.operator.GreenscapeEngineSchema;
import io.greenscape.operator.schema.Schema;
import io.greenscape.operator.schema.SchemaService;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Deleter;
import io.javaoperatorsdk.operator.api.reconciler.dependent.EventSourceProvider;
import io.javaoperatorsdk.operator.api.reconciler.dependent.managed.DependentResourceConfigurator;
import io.javaoperatorsdk.operator.processing.dependent.Creator;
import io.javaoperatorsdk.operator.processing.dependent.external.PerResourcePollingDependentResource;

@ApplicationScoped
public class SchemaDependentResource extends PerResourcePollingDependentResource<Schema, GreenscapeEngineSchema>
		implements EventSourceProvider<GreenscapeEngineSchema>, DependentResourceConfigurator<ResourcePollerConfig>,
		Creator<Schema, GreenscapeEngineSchema>, Deleter<GreenscapeEngineSchema> {
	public static final String NAME = "schema";
	private static final Logger log = LoggerFactory.getLogger(SchemaDependentResource.class);

	@Inject
	SchemaService schemaService;

	public SchemaDependentResource() {
		super(Schema.class);
	}

	@Override
	public void configureWith(ResourcePollerConfig config) {
		if (config != null) {
			setPollingPeriod(config.getPollPeriod());
		}
	}

	@Override
	public Optional<ResourcePollerConfig> configuration() {
		return Optional.of(new ResourcePollerConfig((int) getPollingPeriod()));
	}

	@Override
	public Schema desired(GreenscapeEngineSchema primary, Context<GreenscapeEngineSchema> context) {
		return new Schema(primary.getMetadata().getName(), primary.getSpec().getEncoding());
	}

	@Override
	public Schema create(Schema target, GreenscapeEngineSchema mySQLSchema, Context<GreenscapeEngineSchema> context) {
		return null;
	}

	@Override
	public void delete(GreenscapeEngineSchema primary, Context<GreenscapeEngineSchema> context) {
	}

	@Override
	public Set<Schema> fetchResources(GreenscapeEngineSchema primaryResource) {
		return null;
	}

	public static String decode(String value) {
		return new String(Base64.getDecoder().decode(value.getBytes()));
	}
}
