package org.opentosca.toscana.core.csar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.opentosca.toscana.core.transformation.Transformation;
import org.opentosca.toscana.core.transformation.logging.Log;
import org.opentosca.toscana.core.transformation.properties.Property;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.winery.model.tosca.yaml.TServiceTemplate;

public class CsarImpl implements Csar {

    /**
     Stores all scheduled, ongoing or finished transformations of this CSAR. Key is the platform identifier.
     */
    private final Map<String, Transformation> transformations = new HashMap<>();
    private final String identifier;
    /**
     null if not yet parsed
     */
    private TServiceTemplate template;
    private final Log log;

    public CsarImpl(String identifier, Log log) {
        this.identifier = identifier;
        this.log = log;
    }

    @Override
    public Map<String, Transformation> getTransformations() {
        return transformations;
    }

    @Override
    public Optional<Transformation> getTransformation(String platformId) {
        Transformation t = transformations.get(platformId);
        return Optional.ofNullable(t);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Optional<TServiceTemplate> getTemplate() {
        return Optional.ofNullable(template);
    }

    @Override
    public Set<Property> getModelSpecificProperties() {
        return new HashSet<>();
    }

    @Override
    public void setTemplate(TServiceTemplate template) {
        this.template = template;
    }

    @Override
    public Log getLog() {
        return log;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Csar) && (((Csar) obj).getIdentifier().equals(identifier));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(identifier)
            .toHashCode();
    }

    @Override
    public void setTransformations(List<Transformation> transformations) {
        for (Transformation transformation : transformations) {
            this.transformations.put(transformation.getPlatform().id, transformation);
        }
    }
}
