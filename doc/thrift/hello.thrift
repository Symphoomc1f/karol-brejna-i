namespace java org.lantern.thrift.service

service HelloService{
   string sayHello();

   string sayName(1:string name);
}
