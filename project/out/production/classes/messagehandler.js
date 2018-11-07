var eb = vertx.eventBus();

eb.consumer("chatty.events.message", function(message) {
    console.log(message.body());
    message.reply(message.body());
    eb.publish("chatty.events.main", message.body());
})