package com.demo.jsonUtils;
import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;
/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 15:16
 * @version: V1.0
 * @detail:
 **/
@Slf4j
public class LogUtils {
    private static Class clazz = LogUtils.class;


    public static void error(String str, Exception e,Class clazz){
        LogUtils.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<Object>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());
        log.error(str + " : {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }


    public static void info(String str, Exception e,Class clazz){
        LogUtils.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<Object>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());
        log.info(str + " : {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }

    public static void debug(String str, Exception e,Class clazz){
        LogUtils.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<Object>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());

        log.info(str + ": {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }

}
