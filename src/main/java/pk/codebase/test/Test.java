package pk.codebase.test;

import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.ExitInfo;

import java.util.concurrent.CompletableFuture;

public class Test {
    public static void main(String[] args) {
        connect();
    }

    private static int connect() {
        Session wampSession = new Session();
        wampSession.addOnJoinListener((session, details) -> {
            System.out.println(details);
            session.register("com.simplethings.echo", o -> {
                System.out.println(o);
                return "ABU";
            }).whenComplete((registration, throwable) -> {
                System.out.println("Registered,,,,,");
            });
        });

        Client client = new Client(wampSession, "ws://192.168.100.2:8081/ws", "realm1");
        CompletableFuture<ExitInfo> exitFuture = client.connect();

        try {
            ExitInfo exitInfo = exitFuture.get();
            return exitInfo.code;
        } catch (Exception e) {
            return 1;
        }
    }
}
