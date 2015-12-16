/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.trial.app;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gayatri
 */
public class ResultsDefinition {
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
    public static ResultsDefinition create(Node node) {
        ResultsDefinition definition = new ResultsDefinition();
        definition.definitionId = getAttributeValue(node, "definition_id");
        definition.result = getAttributeValue(node, "result");
        definition.version = getAttributeValue(node, "version");
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node n = childNodes.item(i);
            if (n.getNodeName().equals("criteria")) {
                definition.criteria = parseCriteria(n);
            }
        }
        return definition;
    }
    public static class Criteria {

        public Criteria(String operator, String result) {
            this.operator = operator;
            this.result = result;
            this.criteriaType = CRITERIA_TYPE.CRITERION;
        }
        void addCriterion(Criterion criterion) {
            criterionList.add(criterion);
            criteriaType = CRITERIA_TYPE.CRITERION;
        }
        void addExtendDefinition(ExtendDefinition ed, Criteria criteria) {
            criteriaType = CRITERIA_TYPE.EXTEND_DEFINITION;
            extendDefinition = ed;
            this.criteria = criteria;
        }
        enum CRITERIA_TYPE {
            CRITERION,
            EXTEND_DEFINITION
        }

        @Override
        public String toString() {
            return "Criteria{" +
                    "criteriaType=" + criteriaType +
                    ", criteria=" + criteria +
                    ", extendDefinition=" + extendDefinition +
                    ", operator='" + operator + '\'' +
                    ", result='" + result + '\'' +
                    ", criterionList=" + criterionList +
                    '}';
        }

        private CRITERIA_TYPE criteriaType;
        private Criteria criteria;
        private ExtendDefinition extendDefinition;
        private String operator;
        private String result;
        List<Criterion> criterionList = new ArrayList<Criterion>();
    }
    public static class ExtendDefinition {
        public ExtendDefinition(String definitionRef, String result, String version) {
            this.definitionRef = definitionRef;
            this.result = result;
            this.version = version;
        }

        public String getDefinitionRef() {
            return definitionRef;
        }

        public void setDefinitionRef(String definitionRef) {
            this.definitionRef = definitionRef;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "ExtendDefinition{" +
                    "definitionRef='" + definitionRef + '\'' +
                    ", result='" + result + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }

        private String definitionRef;
        private String result;
        private String version;
    }
    public static class Criterion {

        public Criterion(String testRef, String version, String result) {
            this.testRef = testRef;
            this.version = version;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Criterion{" + "testRef=" + testRef + ", version=" + version + '}';
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTestRef() {
            return testRef;
        }

        public void setTestRef(String testRef) {
            this.testRef = testRef;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        private String testRef;
        private String version;
        private String result;
    }

    private static Criteria parseCriteria(Node node) {
        Criteria criteria = new Criteria(getAttributeValue(node, "operator"), getAttributeValue(node, "result"));
        NodeList childNodes = node.getChildNodes();
        ExtendDefinition ed = null;
        for(int i = 0; i < childNodes.getLength(); ++i) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals("extend_definition")) {
                ed = new ExtendDefinition(getAttributeValue(childNode, "definition_ref"),
                        getAttributeValue(childNode, "result"),
                        getAttributeValue(childNode, "version"));
            }
            else if (childNode.getNodeName().equals("criteria")) {

                Criteria subCriteria = parseCriteria(childNode);
                criteria.addExtendDefinition(ed, subCriteria);
            }
            else if (childNode.getNodeName().equals("criterion")) {
                Criterion c = new Criterion(getAttributeValue(childNode, "test_ref"),
                        getAttributeValue(childNode, "version"),
                        getAttributeValue(childNode, "result"));
                criteria.addCriterion(c);
            }
        }
        return criteria;
    }

    @Override
    public String toString() {
        return "ResultsDefinition{" +
                "definitionId='" + definitionId + '\'' +
                ", result='" + result + '\'' +
                ", version='" + version + '\'' +
                ", criteria=" + criteria +
                '}';
    }

    private String definitionId;
    private String result;
    private String version;
    private Criteria criteria;
    
}
