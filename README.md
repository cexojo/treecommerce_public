## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev -pl treecommerce-app
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `treecommerce-core-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/treecommerce-core-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/treecommerce-core-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide.

## Swagger API

`/swagger`

## Authentication
The authentication to use the TCApi REST methods is custom. The regular HTTP Authentication uses the following format:

`Authorization = Bearer {jwtToken}`

Instead, the calls to the TCApi must meet the following pattern:

`Authorization = Bearer Tree {tokenUid}`

This way, the TreEcommerce custom authentication is triggered. The `{tokenUid}` value is returned as a result of a successful login or impersonation, with
 the calls to the following methods:
 
 `POST /credential/do-login`
 `POST /credential/do-impersonate`
 
As a result, the JWT is never exposed to the outside. Internally, the authentication mechanism retrieves the JWT attached to the given token UID and performs
 the authentication the same way as it received the real JWT. The tokens are internally purged according to the expiry time.