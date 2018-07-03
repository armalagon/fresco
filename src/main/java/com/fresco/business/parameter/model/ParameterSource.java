package com.fresco.business.parameter.model;

import com.zacate.identifier.NaturalIdentifier;
import com.zacate.model.ReadOnlyIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ParameterSource extends ReadOnlyIdentifier<Integer> implements NaturalIdentifier<String> {

    private final String code;
    private final Integer parameterId;
    private final String fullyQualifiedClassname;
    private final String query;
    private final Short sequenceNumber;
    private final Parameter parameter;

    public ParameterSource(Integer id, String code, Integer parameterId, String fullyQualifiedClassname, String query,
            Short sequenceNumber) {
        super(id);
        this.code = code;
        this.parameterId = parameterId;
        this.fullyQualifiedClassname = fullyQualifiedClassname;
        this.query = query;
        this.sequenceNumber = sequenceNumber;
        this.parameter = null;
    }

    public ParameterSource(ParameterSource parameterSource, Parameter parameter) {
        super(parameterSource.id);
        this.code = parameterSource.code;
        this.parameterId = parameter.getId();
        this.fullyQualifiedClassname = parameterSource.fullyQualifiedClassname;
        this.query = parameterSource.query;
        this.sequenceNumber = parameterSource.sequenceNumber;
        this.parameter = parameter;
    }

    @Override
    public String getCode() {
        return code;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public String getFullyQualifiedClassname() {
        return fullyQualifiedClassname;
    }

    public String getQuery() {
        return query;
    }

    public Short getSequenceNumber() {
        return sequenceNumber;
    }

    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return "ParameterSource{" + "id=" + id + ", code=" + code + ", fullyQualifiedClassname=" + fullyQualifiedClassname + ", query=" +
                query + ", sequenceNumber=" + sequenceNumber + '}';
    }

}
