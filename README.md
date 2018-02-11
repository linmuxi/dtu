# dtu
基于swing可视化dubbo测试工具，仅供测试使用

### 2018-2-11 记录
公司研发架构采用了dubbo，每次进行接口调试都是通过代码test，很繁琐不方便；后面为了快速测试及其方便测试同事开展测试， 写了一个基于swing的可视化界面进行接口调试（模拟发送请求报文）的小程序；

### 具体操作步骤
1、修改config.properties文件,配置dubbo服务提供地址“server.ip”和端口“server.port”<br/>
2、打包编译mvn clean install<br/>
3、启动程序:java -jar dtu-0.0.1-SNAPSHOT-jar-with-dependencies.jar config.properties<br/>
启动界面后，程序能自动将提供的dubbo服务加载出来，选择相应的服务接口和方法之后，即可进行报文发送测试联调工作
