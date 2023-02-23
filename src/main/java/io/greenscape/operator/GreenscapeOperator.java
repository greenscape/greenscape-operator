package io.greenscape.operator;

import javax.inject.Inject;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import io.javaoperatorsdk.operator.Operator;

@QuarkusMain
public class GreenscapeOperator implements QuarkusApplication {

    @Inject
    Operator operator;

    public static void main(String... args) {
        Quarkus.run(GreenscapeOperator.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        operator.start();
        Quarkus.waitForExit();
        return 0;
    }
}