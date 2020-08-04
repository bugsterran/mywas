package com.was.app;

import java.util.List;
import java.util.Map;

public class Config {
    private int port;
    private List<VirtualHosts> VirtualHosts;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<VirtualHosts> getVirtualHosts() {
        return VirtualHosts;
    }

    public void setVirtualHosts(List<VirtualHosts> virtualHosts) {
        this.VirtualHosts = virtualHosts;
    }

    public class VirtualHosts {

        private String SeverName;
        private String DocumentRoot;
        private Map<String, String> ErrorDocument;
        private boolean UseURLMapping;
        private Map<String, String> MappingUrl;
        private String ServiceLocation;
        private Map<String, List<String>> UsePathRules;

        public String getSeverName() {
            return SeverName;
        }

        public void setSeverName(String severName) {
            SeverName = severName;
        }

        public String getDocumentRoot() {
            return DocumentRoot;
        }

        public void setDocumentRoot(String documentRoot) {
            DocumentRoot = documentRoot;
        }

        public Map<String, String> getErrorDocument() {
            return ErrorDocument;
        }

        public void setErrorDocument(Map<String, String> errorDocument) {
            ErrorDocument = errorDocument;
        }

        public boolean isUseURLMapping() {
            return UseURLMapping;
        }

        public void setUseURLMapping(boolean useURLMapping) {
            UseURLMapping = useURLMapping;
        }

        public String getServiceLocation() {
            return ServiceLocation;
        }

        public void setServiceLocation(String serviceLocation) {
            ServiceLocation = serviceLocation;
        }

        public Map<String, String> getMappingUrl() {
            return MappingUrl;
        }

        public void setMappingUrl(Map<String, String> mappingUrl) {
            MappingUrl = mappingUrl;
        }

        public Map<String, List<String>> getUsePathRules() {
            return UsePathRules;
        }

        public void setUsePathRules(Map<String, List<String>> usePathRules) {
            UsePathRules = usePathRules;
        }
    }
}
