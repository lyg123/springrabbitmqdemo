package com.liyghting.rabbitmqdemo;

import com.liyghting.rabbitmqdemo.core.ProducerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("producer")
public class TestProducer {

    @RequestMapping("test1")
    public ResponseEntity test1() {
        SendUser user = new SendUser();
        user.setAge(10);
        user.setName("liyg");
        user.setSex("男");
        ProducerUtil.send("test1Producer", user);
        return ResponseEntity.ok().body("OK");
    }

    @RequestMapping("test2")
    public ResponseEntity test2() {
        SendUser user1 = new SendUser();
        user1.setAge(10);
        user1.setName("liyg");
        user1.setSex("男");

        SendUser user2 = new SendUser();
        user2.setAge(10);
        user2.setName("yuanjojo");
        user2.setSex("女");

        ProducerUtil.send("test2Producer", Arrays.asList(user1, user2));
        return ResponseEntity.ok().body("OK");
    }
}
