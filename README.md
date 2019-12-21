# kafka-flow

A tool for Kafka that allows you to consume and produce messages in browsers.

一个可以在浏览器里生产数据和消费数据的 Kafka 工具。

## 安装方法

在 github 的 releases 页面下载二进制压缩包：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4btuyornj30q40a7t9i.jpg)

或者使用 `wget` 下载：

```bash
wget https://github.com/iamabug/kafka-flow/releases/download/v0.1.0/kafka-flow-dist-0.1.0-bin.tar.gz
```

解压：

```bash
tar xvf kafka-flow-dist-0.1.0-bin.tar.gz
```

进入目录，并启动服务：

```bash
cd kafka-flow-0.1.0-bin
bin/kafka-flow.sh start
```

在浏览器里访问对应地址（比如：[http://localhost:12345](http://localhost:12345)），出现如下页面说明启动成功。

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4c3vhebsj30xi0axwf5.jpg)

## 使用方法

使用方法很简单：**先配置集群，然后进行生产和消费。**

### 集群配置

点击"集群配置"，再点击"添加集群"：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4c6kgzxsj30w105k74l.jpg)

在新页面中输入 Kafka 集群的名称和 bootstrap.servers 的地址（多个地址用逗号分隔即可）：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4c7ldfmkj30vm0a4gmd.jpg)

点击确认之后，可以看到集群列表：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4c8xc5huj30vn07h74t.jpg)

集群添加成功。

目前只支持集群的删除，不支持修改。

### 生产数据

点击"生产数据"，选择对应的集群，输入 topic 名称，然后在下方文本框中输入要生产的消息，一条或多条都可以：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4ccdnfo6j30vf0a9mxy.jpg)

点击"生产"按钮即可，上图中生产了两条消息。

### 消费数据

点击"消费数据"，选择对应的集群，输入 topic 名称，选择 offset reset 的类型，earliest 从最老的数据消费，latest 从最新的数据消费：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4cfjivy8j30vz0bp754.jpg)

点击"开始消费"按钮，稍等一会，下方的文本框就会出现消费到的数据：

![](https://tva1.sinaimg.cn/large/006tNbRwly1ga4cioy2osj30ve0cbq3y.jpg)

点击"停止消费"按钮，即可停止消费。

## 源码编译

源码编译也很简单：

```bash
git clone https://github.com/iamabug/kafka-flow
cd kafka-flow
mvn clean package
```

编译出来的二进制包的位置为 `./kafka-flow-dist/target` 目录下。

## 其它

目前是 0.1.0 版本，欢迎试用和反馈，觉得有帮助请 Star。

