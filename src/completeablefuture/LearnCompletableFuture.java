package completeablefuture;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LearnCompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> completableFuture = testAsync();

        String result = completableFuture.get();
        System.out.println(result);
    }

    static Future<String> testAsync() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(1000);
            completableFuture.complete("Hello World!");
            return null;
        });

        return completableFuture;

    }
}
