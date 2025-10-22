package com.gaoyifeng.aioserver.infrastructure.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.extern.slf4j.Slf4j;

import java.io.Writer;

/**
 * XML工具类 - Infrastructure层
 * 提供XML与Java对象的转换功能
 */
@Slf4j
public class XmlUtil {

    /**
     * 扩展XStream，支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * Java对象转XML
     * @param obj Java对象
     * @return XML字符串
     */
    public static String beanToXml(Object obj) {
        try {
            xstream.alias("xml", obj.getClass());
            String xml = xstream.toXML(obj);
            log.debug("对象转XML成功：{}", xml);
            return xml;
        } catch (Exception e) {
            log.error("对象转XML失败：{}", obj, e);
            return "";
        }
    }

    /**
     * XML转Java对象
     * @param xml XML字符串
     * @param clazz 目标类
     * @param <T> 泛型
     * @return Java对象
     */
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        try {
            xstream.alias("xml", clazz);
            T obj = (T) xstream.fromXML(xml);
            log.debug("XML转对象成功：{}", xml);
            return obj;
        } catch (Exception e) {
            log.error("XML转对象失败：{}", xml, e);
            return null;
        }
    }

    /**
     * 初始化XStream配置
     * @param clazz 要处理的类
     */
    public static void initXStream(Class<?> clazz) {
        xstream.alias("xml", clazz);
        xstream.processAnnotations(clazz);
    }

    /**
     * 安全的XML转Java对象，忽略未知元素
     * @param xml XML字符串
     * @param clazz 目标类
     * @param <T> 泛型
     * @return Java对象
     */
    public static <T> T xmlToBeanSafe(String xml, Class<T> clazz) {
        try {
            XStream safeXstream = new XStream(new XppDriver() {
                @Override
                public HierarchicalStreamWriter createWriter(Writer out) {
                    return new PrettyPrintWriter(out) {
                        boolean cdata = true;

                        @Override
                        protected void writeText(QuickWriter writer, String text) {
                            if (cdata) {
                                writer.write("<![CDATA[");
                                writer.write(text);
                                writer.write("]]>");
                            } else {
                                writer.write(text);
                            }
                        }
                    };
                }
            });

            // 忽略未知元素
            safeXstream.ignoreUnknownElements();
            safeXstream.alias("xml", clazz);
            safeXstream.processAnnotations(clazz);

            return (T) safeXstream.fromXML(xml);
        } catch (Exception e) {
            log.error("安全XML转对象失败：{}", xml, e);
            return null;
        }
    }
}