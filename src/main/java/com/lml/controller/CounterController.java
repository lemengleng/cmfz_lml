package com.lml.controller;

import com.lml.entity.Counter;
import com.lml.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    private CounterService counterService;
    @RequestMapping("queryAll")
    public Map queryAll(String id,String uid){
        List<Counter> counters = counterService.queryAll(new Counter().setCourse_id(id));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("counters",counters);
        return map;
    }
    @RequestMapping("addCounter")
    public Map addCounter(String uid,Counter counter,String id){
        counter.setCourse_id(id);
        counter.setCreate_date(new Date());
        counterService.add(counter);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("counter",counter);
        return map;
    }
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String uid,String id){
        List<Counter> counters = counterService.queryAll(new Counter().setId(id));
        counterService.delete(new Counter().setId(id));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("counter",counters.get(0));
        return map;
    }
    @RequestMapping("updateCounter")
    public Map updateCounter(Counter counter,String uid){
        counterService.update(counter);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",200);
        map.put("counter",counter);
        return map;
    }
}
