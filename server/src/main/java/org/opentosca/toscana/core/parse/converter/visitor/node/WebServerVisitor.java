package org.opentosca.toscana.core.parse.converter.visitor.node;

import org.opentosca.toscana.core.parse.converter.visitor.capability.AdminEndpointCapabilityVisitor;
import org.opentosca.toscana.core.parse.converter.visitor.capability.ContainerCapabilityVisitor;
import org.opentosca.toscana.core.parse.converter.visitor.capability.EndpointCapabilityVisitor;
import org.opentosca.toscana.model.node.WebServer;
import org.opentosca.toscana.model.node.WebServer.WebServerBuilder;

import org.eclipse.winery.model.tosca.yaml.TCapabilityAssignment;

public class WebServerVisitor<NodeT extends WebServer, BuilderT extends WebServerBuilder> extends SoftwareComponentVisitor<NodeT, BuilderT> {

    private static final String DATA_ENDPOINT_CAPABILITY = "data_endpoint";
    private static final String ADMIN_ENDPOINT_CAPABILITY = "admin_endpoint";
    private static final String HOST_CAPABILITY = "host";

    @Override
    protected void handleCapability(TCapabilityAssignment node, BuilderT builder, String key) {
        switch (key) {
            case DATA_ENDPOINT_CAPABILITY:
                builder.dataEndpoint(new EndpointCapabilityVisitor<>().handle(node));
                break;
            case ADMIN_ENDPOINT_CAPABILITY:
                builder.adminEndpoint(new AdminEndpointCapabilityVisitor<>().handle(node));
                break;
            case HOST_CAPABILITY:
                builder.containerHost(new ContainerCapabilityVisitor<>().handle(node));
                break;
            default:
                super.handleCapability(node, builder, key);
                break;
        }
    }
}