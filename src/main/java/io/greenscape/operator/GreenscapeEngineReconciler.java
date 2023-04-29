package io.greenscape.operator;

import javax.inject.Inject;

import io.fabric8.kubernetes.api.model.Secret;
import io.greenscape.operator.dependent.SchemaDependentResource;
import io.greenscape.operator.schema.Schema;
import io.greenscape.operator.schema.SchemaService;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.quarkus.logging.Log;

@ControllerConfiguration
public class GreenscapeEngineReconciler
		implements Reconciler<GreenscapeEngineSchema>, ErrorStatusHandler<GreenscapeEngineSchema> {

	@Inject
	SchemaService schemaService;

	@Override
	public UpdateControl<GreenscapeEngineSchema> reconcile(GreenscapeEngineSchema schema,
			Context<GreenscapeEngineSchema> context) {
		// we only need to update the status if we just built the schema, i.e. when it's
		// present in the context
		return context.getSecondaryResource(Secret.class).map(secret -> {
			return context.getSecondaryResource(Schema.class, SchemaDependentResource.NAME).map(s -> {
				updateStatusPojo(schema, secret.getMetadata().getName(), "");
				return UpdateControl.updateStatus(schema);
			}).orElse(UpdateControl.noUpdate());
		}).orElse(UpdateControl.noUpdate());
	}

	@Override
	public ErrorStatusUpdateControl<GreenscapeEngineSchema> updateErrorStatus(GreenscapeEngineSchema schema,
			Context<GreenscapeEngineSchema> context, Exception e) {
		Log.error("updateErrorStatus", e);
		GreenscapeEngineStatus status = new GreenscapeEngineStatus();
		status.setUrl(null);
		status.setUserName(null);
		status.setSecretName(null);
		status.setStatus("ERROR: " + e.getMessage());
		schema.setStatus(status);

		return ErrorStatusUpdateControl.updateStatus(schema);
	}

	private void updateStatusPojo(GreenscapeEngineSchema schema, String secretName, String userName) {
		GreenscapeEngineStatus status = new GreenscapeEngineStatus();
		status.setUserName(userName);
		status.setSecretName(secretName);
		status.setStatus("CREATED");
		schema.setStatus(status);
	}
}