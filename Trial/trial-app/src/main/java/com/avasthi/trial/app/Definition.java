/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.trial.app;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Gayatri
 */
public class Definition {
    private static String getAttributeValue(Node node, String attrname) {
        NamedNodeMap nnm = node.getAttributes();
        if (nnm != null) {
            Node a = nnm.getNamedItem(attrname);
            if (a != null) {
                String val = a.getNodeValue();
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }
    public static Definition create(Node node) {
        Definition definition = new Definition();
        definition.id = getAttributeValue(node, "id");
        definition.idclass = getAttributeValue(node, "class");
        definition.version = getAttributeValue(node, "version");
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node n = childNodes.item(i);
            if (n.getNodeName().equals("metadata")) {
                NodeList grandChildNodes = n.getChildNodes();
                for (int j = 0; j < grandChildNodes.getLength(); ++j) {
                    Node gcn = grandChildNodes.item(j);
                    System.out.println("###########" + gcn.getNodeName());
                    if (gcn.getNodeName().equals("title")) {
                        
                        definition.title = gcn.getChildNodes().item(0).getNodeValue().toString();
                    }
                    else if (gcn.getNodeName().equals("affected")) {
                        definition.affected = new Affected(getAttributeValue(gcn, "family"),
                                gcn.getChildNodes().item(0).getNodeValue());
                    }
                    else if (gcn.getNodeName().equals("reference")) {
                        String source = getAttributeValue(gcn, "source");
                        String refId = getAttributeValue(gcn, "ref_id");
                        String refUrl = getAttributeValue(gcn, "ref_url");
                        definition.references.add(new Reference(source, refId, refUrl));
                    }
                    else if (gcn.getNodeName().equals("description")) {
                        definition.description = gcn.getTextContent();
                    }
                }
            }
            else if(n.getNodeName().equals("criteria")) {
                
                NodeList grandChildNodes = n.getChildNodes();
                for (int j = 0; j < grandChildNodes.getLength(); ++j) {
                    
                    Node gcn = grandChildNodes.item(j);
                    if (gcn.getNodeName().equals("criterion")) {
                        
                        String testRef = getAttributeValue(gcn, "test_ref");
                        String comment = getAttributeValue(gcn, "comment");
                        definition.criteria.add(new Criterion(testRef, comment));
                    }
                }
            }
        }
        return definition;
    }
    public static class Affected{
        public Affected(String family, String platform) {
            this.family = family;
            this.platform = platform;
        }

        @Override
        public String toString() {
            return "Affected{" + "family=" + family + ", platform=" + platform + '}';
        }
        
        private String family;
        private String platform;
        
    };
    public static class Reference{

        public Reference(String source, String refId, String refUrl) {
            this.refId = refId;
            this.source = source;
            this.refUrl = refUrl;
        }

        @Override
        public String toString() {
            return "Reference{" + "refId=" + refId + ", source=" + source + ", refUrl=" + refUrl + '}';
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        } 

        public String getRefUrl() {
            return refUrl;
        }

        public void setRefUrl(String refUrl) {
            this.refUrl = refUrl;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
        
        private String refId;
        private String source;
        private String refUrl;
    }
    public static class Criterion {

        public Criterion(String testRef, String comment) {
            this.testRef = testRef;
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "Criterion{" + "testRef=" + testRef + ", comment=" + comment + '}';
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getTestRef() {
            return testRef;
        }

        public void setTestRef(String testRef) {
            this.testRef = testRef;
        }
        
        private String testRef;
        private String comment;
    }

    @Override
    public String toString() {
        return "Definition{" + "id=" + id + ", idclass=" + idclass + ", version=" + version + ", title=" + title + ", description=" + description + ", affected=" + affected + ", references=" + references + ", criteria=" + criteria + '}';
    }
    
    private String id;
    private String idclass;
    private String version;
    private String title;
    private String description;
    private Affected affected;
    private List<Reference> references = new ArrayList<Reference>();
    private List<Criterion> criteria = new ArrayList<Criterion>();
    
}
