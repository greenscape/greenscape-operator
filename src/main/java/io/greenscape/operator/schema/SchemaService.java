package io.greenscape.operator.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.logging.Log;

@ApplicationScoped
public class SchemaService {

	public void createSchemaAndRelatedUser(Connection connection, String schemaName, String encoding, String userName,
			String password) {

	}

	public void deleteSchemaAndRelatedUser(Connection connection, String schemaName, String userName) {

	}

	private boolean userExists(Connection connection, String username) {
		try (PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM mysql.user WHERE user = ?")) {
			ps.setString(1, username);
			try (ResultSet resultSet = ps.executeQuery()) {
				return resultSet.next();
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public Optional<Schema> getSchema(Connection connection, String schemaName) {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT * FROM information_schema.schemata WHERE schema_name = ?")) {
			ps.setString(1, schemaName);
			try (ResultSet resultSet = ps.executeQuery()) {
				// CATALOG_NAME, SCHEMA_NAME, DEFAULT_CHARACTER_SET_NAME,
				// DEFAULT_COLLATION_NAME, SQL_PATH
				var exists = resultSet.next();
				if (!exists) {
					return Optional.empty();
				} else {
					return Optional.of(new Schema(resultSet.getString("SCHEMA_NAME"),
							resultSet.getString("DEFAULT_CHARACTER_SET_NAME")));
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}