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
    private final String fullyQualifiedClassname;
    private final String query;
    private final Short sequenceNumber;

    public ParameterSource(Integer id, String code, String fullyQualifiedClassname, String query, Short sequenceNumber) {
        super(id);
        this.code = code;
        this.fullyQualifiedClassname = fullyQualifiedClassname;
        this.query = query;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String getCode() {
        return code;
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

    @Override
    public String toString() {
        return "ParameterSource{" + "code=" + code + ", fullyQualifiedClassname=" + fullyQualifiedClassname + ", query=" + query +
                ", sequenceNumber=" + sequenceNumber + '}';
    }

}
