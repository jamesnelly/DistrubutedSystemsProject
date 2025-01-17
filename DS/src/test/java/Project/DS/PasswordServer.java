package Project.DS;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.logging.Logger;

public class PasswordServer {

	private Server grpcServer;
	private static final Logger logger = Logger.getLogger(PasswordServer.class.getName());
	private static final int PORT = 50551;

	private void start() throws IOException {
		//grpcServer = ServerBuilder.forPort(PORT).addService((BindableService) new PasswordServiceImpl()).build().start();
		logger.info("Server started, listening on " + PORT);
	}

	private void stop() {
		if (grpcServer != null) {
			grpcServer.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (grpcServer != null) {
			grpcServer.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final PasswordServer passwordServer = new PasswordServer();
		passwordServer.start();
		passwordServer.blockUntilShutdown();
	}
}