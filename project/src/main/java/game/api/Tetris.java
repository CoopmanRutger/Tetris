package game.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;


public class Tetris extends AbstractVerticle {

    @Override
    public void start() {
        config().getJsonObject("components")
                .forEach(entry -> {
                    JsonObject json = (JsonObject) entry.getValue();
                    String optionsKey = "options";
                    if (json.containsKey(optionsKey)) {
                        JsonObject options = ((JsonObject) entry.getValue()).getJsonObject(optionsKey);
                        vertx.deployVerticle(entry.getKey(), new DeploymentOptions(options));
                    } else {
                        vertx.deployVerticle(entry.getKey());
                    }
                });
    }
}