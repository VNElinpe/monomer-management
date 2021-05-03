package top.vnelinpe.management.core;//package top.vnelinpe.management.core;
//
//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.common.profile.HttpProfile;
//import com.tencentcloudapi.sms.v20190711.SmsClient;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
//import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.cglib.proxy.MethodInterceptor;
//import org.springframework.cglib.proxy.MethodProxy;
//import top.vnelinpe.management.ManagementApplication;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class VerifyCodeHandlerTest {
//    public static void main( String[] args ) {
//        Dog dog = new Dog();
//        Object proxy = Proxy.newProxyInstance(Dog.class.getClassLoader(), new Class[]{Animal.class, Plaint.class}, (obj, method, hargs) -> method.invoke(dog,hargs));
//        ((Animal)proxy).run();
//        ((Plaint)proxy).stand();
//
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Dog.class);
//        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
//            System.out.println(o.getClass().getName());
//            System.out.println(method.getName());
//            System.out.println(objects.getClass().getName());
//            System.out.println(methodProxy.getClass().getName());
//            return methodProxy.invokeSuper(o,objects);
//        });
//
//        Dog dogCglib = (Dog) enhancer.create();
//        dogCglib.run();
//        dogCglib.stand();
//        dogCglib.watch();
//
//    }
//}
//
//interface Animal{
//    void run();
//}
//
//interface Plaint{
//    void stand();
//}
//
//class Dog implements Animal,Plaint{
//
//    @Override
//    public void run() {
//        System.out.println("dog run");
//    }
//
//    @Override
//    public void stand() {
//        System.out.println("dog stand");
//    }
//
//    public void watch(){
//        System.out.println("dog watch");
//    }
//}
//
//

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootTest
public class VerifyCodeHandlerTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"));
    }
}