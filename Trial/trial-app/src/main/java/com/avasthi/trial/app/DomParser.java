/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.trial.app;

/**
 *
 * @author Gayatri
 */
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
//import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomParser {

    private final static Map<ObjectFactoryKey, Class<?>> factoryMap = new HashMap<ObjectFactoryKey, Class<?>>();
    private final static String creatorMethodName = "create";
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        String xmlFile = "file:///home/vavasthi/Downloads/xml-output2.xml";

        factoryMap.put(new ObjectFactoryKey("/oval_results/oval_definitions/definitions", "definition"), Definition.class);
        factoryMap.put(new ObjectFactoryKey("/oval_results/results/system/definitions", "definition"), ResultsDefinition.class);
        DOMParser parser = new DOMParser();

        try {
            parser.parse(xmlFile);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Document document = parser.getDocument();
        handleNode(document, "");
    }
    private static void handleNode(Node node, String path) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node childNode = childNodes.item(i);
            ObjectFactoryKey key = new ObjectFactoryKey(path, childNode.getNodeName());
            Class<?> handlerClass = factoryMap.get(key);
            if (handlerClass != null) {
                Method method = handlerClass.getMethod(creatorMethodName, Node.class);
                Object o = method.invoke(null, childNode);
                System.out.println(o.toString());
            }
            else {
                handleNode(childNode, path + "/" + childNode.getNodeName());
            }
        }
    }
}
