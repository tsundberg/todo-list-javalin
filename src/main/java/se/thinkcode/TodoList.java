package se.thinkcode;

import io.javalin.Javalin;

public class TodoList {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000); // create and launch server

        Routes routes = new Routes();
        routes.routes(app);
    }
}
