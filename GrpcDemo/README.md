### gRPC
##### Install
- Config `pom.xml` in module.
- Run plugin `protobuf:compile` and `protobuf:compile-custom`.
- Make sure the directory `target` including `xxx.class` defined in proto file.
- The model class and grpc service class must be generated in target.
- The plugin `protoc-{version}-{OS}.exe` is used to generating the model class.
- The plugin `protoc-gen-grpc-java-{version}-{OS}` is used to generating the grpc service class.

##### Run
- Run `RouteGuideServer.class` firstly. And run `RouteGuideClient.class`.
- You can use debug mode to know about the whole process of remote call procedure.
