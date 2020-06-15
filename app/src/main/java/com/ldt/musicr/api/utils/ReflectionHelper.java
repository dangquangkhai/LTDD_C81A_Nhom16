package com.ldt.musicr.api.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionHelper {

    public static ObjectMapper _mapper() {
        ObjectMapper _map = new ObjectMapper();
        _map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return _map;
    }

    // Generic function to convert array to list
    public static <T> List<T> arrayToList(T array[]) {
        // create a list from the Array
        return Arrays
                .stream(array)
                .collect(Collectors.toList());
    }

    public static HashMap<Object, Object> cloneObj(Object oldO, Object newO) {
        HashMap<Object, Object> oldObj = _mapper().convertValue(oldO, HashMap.class);
        HashMap<Object, Object> newObj = _mapper().convertValue(newO, HashMap.class);

        //Return hash map value
        HashMap<Object, Object> res = new HashMap<>();

        oldObj.forEach((keyOld, valOld) -> {
            res.put(keyOld.toString(), valOld);
            newObj.forEach((keyNew, valNew) -> {
                if (keyNew.toString().equals(keyOld.toString())) {
                    res.put(keyNew.toString(), valNew);
                }
            });
        });
        return res;
    }

    public static HashMap<Object, Object> cloneObj(Object oldO, Object newO, String[] exclude) {
        HashMap<Object, Object> oldObj = _mapper().convertValue(oldO, HashMap.class);
        HashMap<Object, Object> newObj = _mapper().convertValue(newO, HashMap.class);

        //Return hash map value
        HashMap<Object, Object> res = new HashMap<>();

        oldObj.forEach((keyOld, valOld) -> {
            res.put(keyOld.toString(), valOld);
            newObj.forEach((keyNew, valNew) -> {
                if (keyNew.toString().equals(keyOld.toString()) && !ReflectionHelper.arrayToList(exclude).contains(keyNew.toString())) {
                    res.put(keyNew.toString(), valNew);
                }
            });
        });
        return res;
    }

    public static HashMap<Object, Object> exluceField(Object data, String[] exclude) {
        HashMap<Object, Object> obj = _mapper().convertValue(data, HashMap.class);
        //Return hash map value
        HashMap<Object, Object> res = new HashMap<>();
        obj.forEach((key, val) -> {
            if (!ReflectionHelper.arrayToList(exclude).contains(key.toString())) {
                res.put(key.toString(), val);
            }
        });
        return res;
    }
}
