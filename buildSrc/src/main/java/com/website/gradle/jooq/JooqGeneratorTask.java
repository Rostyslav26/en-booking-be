package com.website.gradle.jooq;

import org.flywaydb.core.Flyway;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class JooqGeneratorTask extends DefaultTask {
    private static final Logger logger = LoggerFactory.getLogger(JooqGeneratorTask.class);
    private static final String POSTGRESQL_DOCKER_IMAGE = "postgres:11.6";

    private String projectDir;
    private String jdbcUrl;
    private String username;
    private String password;

    @TaskAction
    public void generateJooqData() {
        try (PostgreSQLContainer<?> pgContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRESQL_DOCKER_IMAGE))) {
            pgContainer.start();

            initTaskProperties(pgContainer);
            applyMigrations();
            jooqGenerate();
        }
    }

    private void initTaskProperties(PostgreSQLContainer<?> pgContainer) {
        this.jdbcUrl = pgContainer.getJdbcUrl();
        this.username = pgContainer.getUsername();
        this.password = pgContainer.getPassword();

        this.projectDir = getProject().getProjectDir().getAbsolutePath();
    }

    private void applyMigrations() {
        final Flyway flyway = Flyway.configure()
            .dataSource(jdbcUrl, username, password)
            .outOfOrder(true)
            .ignoreFutureMigrations(true)
            .locations("filesystem:" + projectDir + "/src/main/resources/db/")
            .cleanDisabled(true)
            .table("schema_history")
            .load();

        flyway.migrate();
    }

    private void jooqGenerate() {
        try {
            Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(jdbcUrl)
                    .withUser(username)
                    .withPassword(password))
                .withGenerator(new Generator()
                    .withName("org.jooq.codegen.JavaGenerator")
                    .withGenerate(new Generate()
                        .withPojos(false)
                        .withDaos(false)
                        .withSequences(true)
                        .withRecords(true)
                        .withJavaTimeTypes(true)
                        .withIndexes(true)
                        .withKeys(true)
                        .withSerializableInterfaces(false)
                    )
                    .withDatabase(new Database()
                        .withName("org.jooq.meta.postgres.PostgresDatabase")
                        .withIncludes(".*")
                        .withExcludes("schema_history")
                        .withSchemata(new SchemaMappingType().withInputSchema("public"))
                    )
                    .withTarget(new Target()
                        .withPackageName("com.website.enbookingbe.data.jooq")
                        .withDirectory(projectDir + "/src/main/java")));
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            logger.error("Error during generating jooq tables", e);
        }
    }
}