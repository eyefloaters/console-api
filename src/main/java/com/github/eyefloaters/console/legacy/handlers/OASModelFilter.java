package com.github.eyefloaters.console.legacy.handlers;

import java.util.List;
import java.util.TreeMap;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;

import com.github.eyefloaters.console.api.support.StringListParamConverterProvider.StringListParamConverter;

public class OASModelFilter implements OASFilter {

    @Override
    public Schema filterSchema(Schema schema) {
        List<Schema> allOf = schema.getAllOf();

        // Remove superfluous `nullable: false` added by OpenAPI scanner
        if (allOf != null && allOf.stream().anyMatch(s -> Boolean.FALSE.equals(s.getNullable()))) {
            allOf.stream()
                .filter(s -> s.getRef() != null)
                .findFirst()
                .ifPresent(ref -> {
                    schema.setRef(ref.getRef());
                    schema.setAllOf(null);
                });
        }

        if (schema.getType() == SchemaType.ARRAY && schema.getDefaultValue() instanceof String dflt) {
            schema.setDefaultValue(new StringListParamConverter().fromString(dflt));
        }

        if (schema.getType() == SchemaType.ARRAY && schema.getEnumeration() != null) {
            schema.getItems().setEnumeration(schema.getEnumeration());
            schema.setEnumeration(null);
        }

        return OASFilter.super.filterSchema(schema);
    }

    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        openAPI.getComponents().addSchema("OffsetSpec", OASFactory.createSchema()
                .type(SchemaType.STRING)
                .defaultValue("latest")
                .addOneOf(OASFactory.createSchema()
                        .type(SchemaType.STRING)
                        .addEnumeration("earliest")
                        .addEnumeration("latest")
                        .addEnumeration("maxTimestamp"))
                .addOneOf(OASFactory.createSchema()
                        .ref("Instant")));

        // Sort global schemas
        openAPI.getComponents().setSchemas(new TreeMap<>(openAPI.getComponents().getSchemas()));
    }

}
