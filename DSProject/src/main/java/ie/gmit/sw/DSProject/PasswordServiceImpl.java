package ie.gmit.sw.DSProject;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;

import io.grpc.stub.StreamObserver;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase {

	PasswordServiceImpl() {}

	@Override
	public void hash(HashRequest request, StreamObserver<HashResponse> responseObserver) {
		try {
			String userPassword = request.getPassword();
            char[] stringToHash = userPassword.toCharArray();

            byte[] addSaltToHash = Passwords.getNextSalt();
            byte[] hashedPassword = Passwords.hash(stringToHash, addSaltToHash);
            responseObserver.onNext(HashResponse.newBuilder().setUserID(request.getUserID())
                    .setHashedPassword(ByteString.copyFrom(hashedPassword))
                    .setSalt(ByteString.copyFrom(addSaltToHash))
                    .build());
		}
		 catch(RuntimeException ex){
	            responseObserver.onNext(HashResponse.newBuilder().getDefaultInstanceForType());
		 }
	            responseObserver.onCompleted();
	}

	@Override
	public void validate(ValidateRequest request, StreamObserver<BoolValue> responseObserver) {

		super.validate(request, responseObserver);
	}

}
